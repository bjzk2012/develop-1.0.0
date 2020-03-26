package cn.kcyf.security.domain;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 *
 */
@AllArgsConstructor
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
