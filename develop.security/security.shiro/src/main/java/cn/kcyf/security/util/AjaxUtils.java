package cn.kcyf.security.util;

import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证Ajax的帮助类
 *
 * @author Tom
 */
public class AjaxUtils {
    private AjaxUtils() {
    }

    private final static String AJAX_PARAM_KEY = "isAjax";
    private final static String XHR = "XMLHttpRequest";
    private final static String XHR_HEADER = "X-Requested-With";
    public final static String UNAUTH = "{\"session-status\":\"unauth\",\"uri\":\"%s\"}";
    public final static String TIMEOUT = "{\"session-status\":\"timeout\"}";

    /**
     * 是否是传统XMLHttpRequest方式提交的ajax
     *
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return XHR.equals(httpRequest.getHeader(XHR_HEADER));
    }

    /**
     * 是否是表单方式提交的ajax
     *
     * @param request
     * @return
     */
    public static boolean isForm(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String isAjax = httpRequest.getParameter(AJAX_PARAM_KEY);
        return Boolean.parseBoolean(isAjax);
    }

    /**
     * 通过响应体输出文本
     *
     * @param response
     * @param content
     * @throws IOException
     */
    public static void write(HttpServletResponse response, String content) throws IOException {
        response.getWriter().write(content);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
