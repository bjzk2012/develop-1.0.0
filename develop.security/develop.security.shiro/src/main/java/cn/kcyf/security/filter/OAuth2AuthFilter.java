package cn.kcyf.security.filter;

import cn.kcyf.security.domain.OAuth2Token;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Tom
 */
public class OAuth2AuthFilter extends AuthenticatingFilter {

    //oauth2 authc code参数名
    private String authcCodeParam = "code";
    private String failureUrl;

    public void setAuthcCodeParam(String authcCodeParam) {
        this.authcCodeParam = authcCodeParam;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String code = httpRequest.getParameter(authcCodeParam);
        return new OAuth2Token(code, httpRequest.getRequestURL().toString().replace(httpRequest.getRequestURI(),""));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        if (!StringUtils.isEmpty(error)) {//如果服务端返回了错误
            WebUtils.issueRedirect(request, response, failureUrl + "?error=" + error + "error_description=" + errorDescription);
            return false;
        }

        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()) {
            if (StringUtils.isEmpty(request.getParameter(authcCodeParam))) {
                // 如果用户没有身份验证，且没有auth code，则重定向到服务端授权
                saveRequestAndRedirectToLogin(request, response);
                return false;
            }
        } else if (subject.getPrincipal() != null) {
            issueSuccessRedirect(request, response);
            return true;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        issueSuccessRedirect(request, response);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, failureUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    protected void redirectToLogin(ServletRequest request,
                                   ServletResponse response) throws IOException {
        String loginUrl = getLoginUrl();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        WebUtils.issueRedirect(request, response, String.format(loginUrl, httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(),"")));
    }

    public String getFailureUrl() {
        return failureUrl;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }
}
