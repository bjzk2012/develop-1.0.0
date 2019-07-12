package cn.kcyf.commons.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient帮助类
 *
 * @author Tom
 */
public class TUtil {
    private static final Logger logger = LoggerFactory.getLogger(TUtil.class);

    /**
     * 获取https类型的HttpClient对象
     *
     * @return
     */
    public static HttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLSv1");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;
        } catch (Exception e) {
            logger.error("设置https失败", e);
        }
        return null;
    }

    /**
     * URL转码
     *
     * @param value
     * @param charsetName
     * @return
     */
    public static String encode(String value, String charsetName) {
        if (value == null) {
            return "";
        }
        String encoded;

        try {
            encoded = URLEncoder.encode(value, charsetName);
            return encoded.replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (Exception e) {
            logger.error("URL转码失败", e);
        }
        return null;
    }

    /**
     * GET请求封装请求参数
     *
     * @param params
     * @return
     */
    public static List<NameValuePair> getParams(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (null != params && !params.isEmpty()) {
            for (String key : params.keySet()) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        return nvps;
    }
}
