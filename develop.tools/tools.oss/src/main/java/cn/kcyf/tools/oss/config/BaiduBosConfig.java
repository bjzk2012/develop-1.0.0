package cn.kcyf.tools.oss.config;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaiduBosConfig {
    @Value("${baidu.oss.access-key:xxx}")
    public String accessKeyId;
    @Value("${baidu.oss.accessKeySecret:xxx}")
    public String accessKeySecret;
    @Value("${baidu.oss.bucketName:xxx}")
    public String bucketName;
    @Value("${baidu.oss.domain:xxx}")
    public String domain;

    @Bean
    public BosClient bosClient(){
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(accessKeyId, accessKeySecret));
        BosClient bosClient = new BosClient(config);
        return bosClient;
    }
}
