package cn.kcyf.commons.utils.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.MessageDigest;

/**
 * 加密解密(包含MD5和3DES)
 *
 * @author Tom.
 */
public class SecurityUtils {
    private SecurityUtils() {
    }

    /**
     * 加解密统一使用的编码方式
     */
    private final static String encoding = "utf-8";

    /**
     * hex字符串转二进制字节数组
     *
     * @param hex 16进制字符
     * @return 二进制字节流
     * @throws IllegalArgumentException 抛出异常
     */
    public static byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    /**
     * 二进制字节数组转hex字符串
     *
     * @param b 二进制字节流
     * @return 16进制字符
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF);
            if (stmp.length() == 1) {
                hs += "0" + stmp;
            } else {
                hs += stmp;
            }
        }
        return hs;
    }

    /**
     * 3DES加密
     *
     * @param plainText 目标字符串
     * @param secretKey 加密密钥
     * @param IV        向量
     * @param ishex     是否转16进制
     * @return 加密后的字符串
     * @throws Exception 抛出异常
     */
    private static String des3Encode(String plainText, String secretKey, String IV, boolean ishex) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            Key deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            String des = new String(new BASE64Encoder().encode(encryptData));
            if (ishex) {
                return byte2hex(des.getBytes(encoding)).toUpperCase();
            }
            return des;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 3DES加密
     *
     * @param plainText 目标字符串
     * @param secretKey 加密密钥
     * @param IV        向量
     * @return 加密后的字符串
     */
    public static String des3Encode(String plainText, String secretKey, String IV) {
        return des3Encode(plainText, secretKey, IV, false);
    }

    /**
     * 3DES加密
     *
     * @param plainText 目标字符串
     * @param secretKey 加密密钥
     * @param IV        向量
     * @return 加密后的字符串
     */
    public static String des3EncodeHex(String plainText, String secretKey, String IV) {
        return des3Encode(plainText, secretKey, IV, true);
    }

    /**
     * 3DES解密
     *
     * @param encryptText 目标字符串
     * @param secretKey   解密密钥
     * @param IV          向量
     * @param ishex       是否转16进制
     * @return
     * @throws Exception
     */
    private static String des3Decode(String encryptText, String secretKey, String IV, boolean ishex) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            Key deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            String des = encryptText;
            if (ishex) {
                des = new String(hex2byte(encryptText), encoding);
            }
            byte[] bytes = new BASE64Decoder().decodeBuffer(des);
            byte[] decryptData = cipher.doFinal(bytes);
            return new String(decryptData, encoding);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 3DES解密
     *
     * @param encryptText 目标字符串
     * @param secretKey   解密密钥
     * @param IV          向量
     * @return 解密后的字符串
     * @throws Exception 抛出异常
     */
    public static String des3Decode(String encryptText, String secretKey, String IV) {
        return des3Decode(encryptText, secretKey, IV, false);
    }

    /**
     * 3DES解密
     *
     * @param encryptText 目标字符串
     * @param secretKey   解密密钥
     * @param IV          向量
     * @return 解密后的字符串
     * @throws Exception 抛出异常
     */
    public static String des3DecodeHex(String encryptText, String secretKey, String IV) {
        return des3Decode(encryptText, secretKey, IV, true);
    }

    /**
     * MD5加密
     *
     * @param plainText 目标字符串
     * @return 加密后的字符串
     * @throws Exception 加密异常
     */
    public static String md5Encoder(String plainText) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = plainText.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * MD5加密
     *
     * @param byteArray 目标字符串
     * @return 加密后的字符串
     * @throws Exception 加密异常
     */
    public static String md5Encoder(byte[] byteArray) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(byteArray);
            return byte2hex(md5Bytes);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取mdf
     *
     * @param plainText 目标字符串
     * @param secretKey 加密密钥
     * @param IV        向量
     * @return 加密后的字符串
     * @throws Exception 加密异常
     */
    public static String mdf(String plainText, String secretKey, String IV) {
        String des = des3EncodeHex(plainText, secretKey, IV);
        return md5Encoder(des + secretKey + IV);
    }
}
