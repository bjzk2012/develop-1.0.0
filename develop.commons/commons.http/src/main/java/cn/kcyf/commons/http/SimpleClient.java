package cn.kcyf.commons.http;

import cn.kcyf.commons.http.enumerate.DataType;
import cn.kcyf.commons.http.enumerate.RequestMethod;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单的httpClient
 *
 * @author Tom
 */
public class SimpleClient {
    private static final Logger logger = LoggerFactory.getLogger(SimpleClient.class);

    /**
     * 发送请求
     *
     * @param tApi   请求信息实体
     * @param params 参数列表
     * @return
     */
    public String send(TApi tApi, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest httpRequestBase = null;
        HttpEntity httpEntity;
        try {
            switch (tApi.getMethod()) {
                case GET:
                    String uri = tApi.getUri();
                    if (params != null && !params.isEmpty()) {
                        if (uri.indexOf("?") < 0) {
                            uri += "?";
                        }
                        for (String key : params.keySet()) {
                            uri = uri + key + "=" + TUtil.encode(params.get(key), "UTF-8") + "&";
                        }
                    }
                    httpRequestBase = new HttpGet(uri);
                    break;
                case POST:
                    httpRequestBase = new HttpPost(tApi.getUri());
                    ((HttpPost) httpRequestBase).setEntity(new UrlEncodedFormEntity(TUtil.getParams(params), Consts.UTF_8));
                    break;
            }
            CloseableHttpResponse response = httpClient.execute(httpRequestBase);
            httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            logger.error("http请求异常", e);
        }
        return null;
    }

    /**
     * 发送请求
     *
     * @param url
     * @param encoding
     * @param contentType
     * @param params
     * @return
     */
    public String sendPostByBody(String url, String encoding, String contentType, String params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpUriRequest httpRequestBase = new HttpPost(url);
            StringEntity entity = new StringEntity(params.toString(), encoding);// 解决中文乱码问题
            entity.setContentEncoding(encoding);
            entity.setContentType(contentType);
            ((HttpPost) httpRequestBase).setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpRequestBase);
            HttpEntity httpEntity = response.getEntity();
            String body = EntityUtils.toString(httpEntity, encoding);
            httpClient.close();
            return body;
        } catch (IOException e) {
            logger.error("http请求异常", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
