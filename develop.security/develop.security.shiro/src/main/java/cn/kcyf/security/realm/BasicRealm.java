package cn.kcyf.security.realm;

import cn.kcyf.security.domain.ShiroUser;
import cn.kcyf.security.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * BasicRealm
 *
 * @author Tom.
 */
public class BasicRealm extends AuthorizingRealm {
    @Autowired
    protected ShiroService shiroService;

    public void setShiroService(ShiroService shiroService) {
        this.shiroService = shiroService;
    }

    /**
     * 获取授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser user = (ShiroUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 设置当前用户角色
        authorizationInfo.setRoles(shiroService.getRoles(user.getUsername()));
        // 设置当前用户权限
        authorizationInfo.setStringPermissions(shiroService.getPermissions(user.getUsername()));
        return authorizationInfo;
    }

    /**
     * 获取认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken authtoken = (UsernamePasswordToken) token;
        ShiroUser shiroUser = shiroService.getUser(authtoken.getUsername());
        if (shiroUser == null) {
            // 没找到帐号
            throw new UnknownAccountException();
        }
        if (shiroUser.isLocked()) {
            // 帐号已锁定
            throw new LockedAccountException();
        }
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                shiroUser,
                shiroUser.getPassword(),
                ByteSource.Util.bytes(shiroUser.getCredentialsSalt()),
                getName()
        );
        return authenticationInfo;
    }
}
