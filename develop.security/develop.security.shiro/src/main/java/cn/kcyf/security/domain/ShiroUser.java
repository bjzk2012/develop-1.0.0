package cn.kcyf.security.domain;

import java.io.Serializable;

/**
 * Shiro的用户对象
 *
 * @author Tom
 */
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 5326619124248054593L;

    /**
     * 用户ID
     */
    protected Long id;

    /**
     * 用户名
     */
    protected String username;

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
     * 成功跳转地址
     */
    protected String successUrl;

    public ShiroUser(Long id, String username, String password, String credentialsSalt, Boolean locked, String successUrl) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.credentialsSalt = credentialsSalt;
        this.locked = locked;
        this.successUrl = successUrl;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCredentialsSalt() {
        return credentialsSalt;
    }

    public void setCredentialsSalt(String credentialsSalt) {
        this.credentialsSalt = credentialsSalt;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }
}
