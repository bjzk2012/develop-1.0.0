package cn.kcyf.security.domain;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Tom
 */
public class OAuth2Token implements AuthenticationToken {

    public OAuth2Token(String authCode, String domain) {
        this.authCode = authCode;
        this.domain = domain;
    }

    private String authCode;
    private String principal;
    private String domain;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public Object getCredentials() {
        return authCode;
    }
}
