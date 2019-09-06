package cn.kcyf.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.shiro.authc.AuthenticationToken;

@Getter
@AllArgsConstructor
public class PhoneVCodeToken implements AuthenticationToken {
    private String phone;
    private String vcode;

    @Override
    public Object getPrincipal() {
        return getPhone();
    }

    @Override
    public Object getCredentials() {
        return "ok";
    }
}
