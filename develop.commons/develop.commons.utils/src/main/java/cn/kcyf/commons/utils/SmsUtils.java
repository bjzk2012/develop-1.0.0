package cn.kcyf.commons.utils;

import cn.kcyf.commons.http.SimpleClient;
import cn.kcyf.commons.http.TApi;
import cn.kcyf.commons.http.enumerate.DataType;
import cn.kcyf.commons.http.enumerate.RequestMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class SmsUtils {
    private final String DEFAULT_SMS_URL = "http://sz.ipyy.com/smsJson.aspx";
    @Value("${sms.ipyy.signature}")
    private String signature = "【军民融合标准资源共享平台】";
    @Value("${sms.ipyy.account}")
    private String account = "hyszzd00468";
    @Value("${sms.ipyy.password}")
    private String password = "123456";


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
        content = signature + content;
        paramsMap.put("userid", "");
        paramsMap.put("account", account);
        paramsMap.put("password", password);
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
