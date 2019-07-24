package cn.kcyf.security.util;


import org.apache.shiro.authc.AuthenticationException;

public class KaptchaException extends AuthenticationException {
    public KaptchaException() {
    }

    public KaptchaException(String message) {
        super(message);
    }

}
