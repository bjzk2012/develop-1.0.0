package cn.kcyf.security.filter;

import cn.kcyf.security.util.AjaxUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员登陆验证过滤器
 *
 * @author Tom Riddle
 */
public class UserFilter extends AccessControlFilter {

    /**
     * 是否已登录
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            saveRequest(request);
            return true;
        } else {
            if (!AjaxUtils.isAjax(request) && !AjaxUtils.isForm(request)) {
                saveRequest(request);
            }
            Subject subject = getSubject(request, response);
            return subject.getPrincipal() != null;
        }
    }

    /**
     * 验证失败回调
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (AjaxUtils.isAjax(request) || AjaxUtils.isForm(request)) {
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.reset();
            httpResponse.setHeader("session-status", "timeout");
            if (AjaxUtils.isForm(request)) {
                httpResponse.getWriter().write(AjaxUtils.TIMEOUT);
            }
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            return false;
        }
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }
}
