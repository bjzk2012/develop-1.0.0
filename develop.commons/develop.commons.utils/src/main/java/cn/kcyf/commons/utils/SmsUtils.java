package cn.kcyf.commons.utils;

import cn.kcyf.commons.http.SimpleClient;
import cn.kcyf.commons.http.TApi;
import cn.kcyf.commons.http.enumerate.DataType;
import cn.kcyf.commons.http.enumerate.RequestMethod;
import cn.kcyf.commons.utils.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class SmsUtils {
    private static final String DEFAULT_SMS_URL = "http://dx110.ipyy.net/smsJson.aspx";
    @Value("${sms.ipyy.signature}")
    private String signature = "";
    @Value("${sms.ipyy.account}")
    private String account = "XPT30095";
    @Value("${sms.ipyy.password}")
    private String password = "XPT3009595";


    /**
     * @param mobile
     * @param content  默认发送验证码内容 ，不为空怎么其他内容
     * @param sendTime
     * @return
     */
    private Map<String, String> buildSms(String mobile, String content, String sendTime) {
        if (null == mobile || StringUtils.isEmpty(content)) {
            throw new IllegalArgumentException();
        }
        Map<String, String> paramsMap = new HashMap<>();
        content += signature;
        paramsMap.put("userid", "");
        paramsMap.put("account", account);
        paramsMap.put("password", SecurityUtils.md5Encoder(password).toUpperCase());
        paramsMap.put("action", "send");       // 固定值
        paramsMap.put("mobile", mobile);       // 接收手机号码, "," 分隔
        paramsMap.put("content", content);     // 短信内容
        paramsMap.put("sendTime", sendTime);
        paramsMap.put("extno", "");            // 扩展子号, 不支持留空
        return paramsMap;
    }

    public String send(String phone, String content, String sendTime) {
        Map<String, String> map = buildSms(phone, content, sendTime);
        TApi tApi = new TApi(DEFAULT_SMS_URL, RequestMethod.POST, DataType.JSON, "");
        SimpleClient client = new SimpleClient();
        return client.send(tApi, map);
    }

}
