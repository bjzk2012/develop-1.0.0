package cn.kcyf.tools.sms.zucp;

import cn.kcyf.commons.utils.security.SecurityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class ZucpSMSUtils {

    private static final String serviceURL = "http://sdk2.zucp.net:8060/webservice.asmx";

    private static final String sn = "SDK-BBX-010-17123";// 序列号
    private static final String password = "76d^498-";// 密码
    private static final Pattern pattern = Pattern.compile("<mdSmsSend_uResult>(.*)</mdSmsSend_uResult>");

    /*
     * 方法名称：mt
     * 功    能：发送短信 ,传多个手机号就是群发，一个手机号就是单条提交
     * 参    数：mobile,content,ext,stime,rrid(手机号，内容，扩展码，定时时间，唯一标识)
     * 返 回 值：唯一标识，如果不填写rrid将返回系统生成的
     */
    public static String send(String mobile, String content, String ext, String stime, String rrid) {
        try {
            content = URLEncoder.encode(content, "utf8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        String result = "";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<mdSmsSend_u xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + SecurityUtils.md5Encoder(sn + password) + "</pwd>";
        xml += "<mobile>" + mobile + "</mobile>";
        xml += "<content>" + content + "</content>";
        xml += "<ext>" + ext + "</ext>";
        xml += "<stime>" + stime + "</stime>";
        xml += "<rrid>" + rrid + "</rrid>";
        xml += "</mdSmsSend_u>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";
        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            // 如果您的系统是utf-8,这里请改成bout.write(xml.getBytes("GBK"));

            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", "http://tempuri.org/mdSmsSend_u");
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
