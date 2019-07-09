package cn.kcyf.security.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SingleSignLogoutFilter extends LogoutFilter{
    private String SERVICE = "service";

    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        String redirectUrl = WebUtils.toHttp(request).getParameter(SERVICE);
        if (!StringUtils.isEmpty(redirectUrl)){
            return redirectUrl;
        }
        return super.getRedirectUrl(request, response, subject);
    }
}
