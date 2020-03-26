package cn.kcyf.security.enumerate;

public enum LoginType {
    ACCOUNT("账号登录"),
    OAUTH2("Oauth2登录"),
    DINGTALK_DRCODE("钉钉扫码"),
    PHONEVCODE("手机验证码"),
    JWT("JSON WEB TOKEN登录");

    LoginType(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
