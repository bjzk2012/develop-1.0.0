package cn.kcyf.security.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Shiro的用户对象
 *
 * @author Tom
 */
@Data
@AllArgsConstructor
public class ShiroUser implements Serializable {

    /**
     * 用户ID
     */
    protected Long id;

    /**
     * 用户名
     */
    protected String account;

    /**
     * 密码
     */
    protected String password;

    /**
     * 盐
     */
    protected String credentialsSalt;

    /**
     * 是否锁定
     */
    protected boolean locked;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 用户详情信息
     */
    private JSONObject detail;
}
