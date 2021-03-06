package cn.kcyf.security.filter;

import cn.kcyf.commons.web.Servlets;
import cn.kcyf.security.domain.ShiroUser;
import cn.kcyf.security.service.ShiroService;
import cn.kcyf.security.util.KaptchaException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 表单验证的Filter
 *
 * @author Tom.
 */
public class DbFormAuthFilter extends FormAuthenticationFilter {
    @Autowired
    private ShiroService shiroService;

    public void setShiroService(ShiroService shiroService) {
        this.shiroService = shiroService;
    }

    private int loginType = 0;

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return new UsernamePasswordToken(username, password, rememberMe, host);
    }

    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = this.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        } else {
            try {
                String kaptcha = request.getParameter("kaptcha");
                Object kaptcha_session = WebUtils.toHttp(request).getSession().getAttribute("KAPTCHA_SESSION_KEY");
                if (kaptcha_session != null && !kaptcha.equalsIgnoreCase(kaptcha_session.toString())){
                    return this.onLoginFailure(token, new KaptchaException("Kaptcha is bad!"), request, response);
                }
                Subject subject = this.getSubject(request, response);
                subject.login(token);
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException e) {
                return this.onLoginFailure(token, e, request, response);
            }
        }
    }

    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        shiroService.loginLogger(shiroUser.getId(), shiroUser.getAccount(), Servlets.getRemoteAddr(WebUtils.toHttp(request)), loginType, true);
        WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
        return false;
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response){
        shiroService.loginLogger(null, token.getPrincipal().toString(), Servlets.getRemoteAddr(WebUtils.toHttp(request)), loginType, false);
        return super.onLoginFailure(token, e, request, response);
    }
}
