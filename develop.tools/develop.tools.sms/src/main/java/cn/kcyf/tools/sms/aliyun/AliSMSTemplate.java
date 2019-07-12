package cn.kcyf.tools.sms.aliyun;

/**
 * 短信短信模板
 *
 * @author Tom
 */
public enum AliSMSTemplate {
    /**
     * 验证码
     */
    CODE("SMS_44395202", "验证码"),
    /**
     * 登录验证码
     */
    LOGINCODE("SMS_44395202", "登录验证码"),
    /**
     * 注册验证码
     */
    REGISTCODE("SMS_44395202", "注册验证码"),
    /**
     * 通知消息
     */
    NOTICE("SMS_44395202", "通知消息");
    /**
     * 短信模板ID
     */
    private String templateId;
    /**
     * 短信模板名称
     */
    private String templateName;

    AliSMSTemplate(String templateId, String templateName) {
        this.templateId = templateId;
        this.templateName = templateName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getTemplateName() {
        return templateName;
    }
}
