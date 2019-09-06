package cn.kcyf.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.shiro.authc.AuthenticationToken;

@Getter
@AllArgsConstructor
public class OpenIdToken implements AuthenticationToken {
    private String openId;

    @Override
    public Object getPrincipal() {
        return getOpenId();
    }

    @Override
    public Object getCredentials() {
        return "ok";
    }

}
