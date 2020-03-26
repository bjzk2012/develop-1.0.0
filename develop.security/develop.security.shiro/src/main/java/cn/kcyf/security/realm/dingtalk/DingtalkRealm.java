package cn.kcyf.security.realm.dingtalk;

import cn.kcyf.security.domain.OpenIdToken;
import cn.kcyf.security.domain.ShiroUser;
import cn.kcyf.security.enumerate.LoginType;
import cn.kcyf.security.realm.BasicRealm;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

public class DingtalkRealm extends BasicRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OpenIdToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OpenIdToken openIdToken = (OpenIdToken) token;
        String openId = openIdToken.getOpenId();
        JSONObject detail = new JSONObject();
        detail.put("openId", openId);
        try {
            ShiroUser shiroUser = shiroService.checkUser(detail, LoginType.DINGTALK_DRCODE);
            if (shiroUser != null) {
                return new SimpleAuthenticationInfo(shiroUser, "ok", getName());
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }
}
