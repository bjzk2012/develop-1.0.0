package cn.kcyf.security.realm.phone;

import cn.kcyf.security.domain.PhoneVCodeToken;
import cn.kcyf.security.domain.ShiroUser;
import cn.kcyf.security.enumerate.LoginType;
import cn.kcyf.security.realm.BasicRealm;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import java.util.Date;

public class PhoneRealm extends BasicRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof PhoneVCodeToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        PhoneVCodeToken phoneVCodeToken = (PhoneVCodeToken) token;
        String phone = phoneVCodeToken.getPhone();
        String vcode = phoneVCodeToken.getVcode();
        JSONObject detail = new JSONObject();
        detail.put("phone", phone);
        detail.put("vcode", vcode);
        try {
            ShiroUser shiroUser = shiroService.checkUser(detail, LoginType.PHONEVCODE);
            if (shiroUser != null) {
                return new SimpleAuthenticationInfo(shiroUser, "ok", getName());
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }
}
