package cn.kcyf.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Tom
 */
@Getter
@AllArgsConstructor
public class OAuth2Token implements AuthenticationToken {

    private String authCode;
    private String principal;
    private String domain;

    @Override
    public Object getCredentials() {
        return authCode;
    }
}
