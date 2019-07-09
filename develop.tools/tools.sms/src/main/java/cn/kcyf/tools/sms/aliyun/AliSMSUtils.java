package cn.kcyf.tools.sms.aliyun;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 短信发送服务帮助类
 *
 * @author 张奎
 */
public final class AliSMSUtils {
    private static final Logger logger = LoggerFactory.getLogger(AliSMSUtils.class);

    private AliSMSUtils() {
    }

    private static final String URL = "http://gw.api.taobao.com/router/rest";
    private static final String APPKEY = "23618097";
    private static final String SECRET = "e0c81c72aa3dae124eda5415d4a9c1bf";

    /**
     * 发送短信
     *
     * @param mobile       接收手机号
     * @param freeSignName 签名
     * @param extend       透传参数,
     * @param params       参数列表,json格式
     * @param template     短信模板
     */
    public static void send(String mobile, String freeSignName, String extend, String params, AliSMSTemplate template) {
        TaobaoClient client = new DefaultTaobaoClient(URL, APPKEY, SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName(freeSignName);
        req.setSmsParamString(params);
        req.setExtend(extend);
        req.setRecNum(mobile);
        req.setSmsTemplateCode(template.getTemplateId());
        try {
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            logger.info(rsp.getBody());
        } catch (ApiException e) {
            logger.error("阿里接口调用错误", e);
            throw new RuntimeException(e.getErrMsg());
        }
    }

    public static void main(String[] args) {
        send("15529000562", "张奎", "", "{'code':'123123'}", AliSMSTemplate.CODE);
    }
}
