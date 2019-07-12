package cn.kcyf.commons.http;


import cn.kcyf.commons.http.enumerate.DataType;
import cn.kcyf.commons.http.enumerate.RequestMethod;

/**
 * 请求信息封装
 *
 * @author Tom
 */
public class TApi {

    /**
     * uri 访问路径
     */
    private String uri;

    /**
     * get或者post
     */
    private RequestMethod method;

    /**
     * 数据类型
     */
    private DataType dataType;

    /**
     * 媒体类型
     */
    private String contentType;

    public TApi(String uri, RequestMethod method, DataType dataType, String contentType) {
        this.uri = uri;
        this.method = method;
        this.dataType = dataType;
        this.contentType = contentType;
    }

    public String getUri() {
        return this.uri;
    }

    public RequestMethod getMethod() {
        return this.method;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public String getContentType() {
        return this.contentType;
    }
}
