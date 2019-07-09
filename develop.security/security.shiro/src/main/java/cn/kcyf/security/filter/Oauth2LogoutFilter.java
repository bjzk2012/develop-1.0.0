package cn.kcyf.security.filter;

import cn.kcyf.commons.web.CookieUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Oauth2LogoutFilter extends LogoutFilter{
    /**
     * 返回URL
     */
    public static final String USER_LOG_OUT_FLAG = "sso_logout";

    public static final String JSESSIONID = "JSESSIONID";

    protected String getRedirectUrl(ServletRequest req, ServletResponse resp, Subject subject) {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String domain = request.getServerName();
        if (domain.indexOf(".") > -1) {
            domain = domain.substring(domain.indexOf(".") + 1);
        }
        CookieUtils.addCookie(request, response, JSESSIONID, null, 0, domain, "/");
        CookieUtils.addCookie(request, response, USER_LOG_OUT_FLAG, Boolean.TRUE.toString(), null, domain, "/");
        subject.logout();
        return getRedirectUrl();
    }
}
