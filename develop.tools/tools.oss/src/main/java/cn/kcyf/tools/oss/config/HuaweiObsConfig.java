package cn.kcyf.tools.oss.config;

import com.obs.services.ObsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HuaweiObsConfig {
    @Value("${huawei.oss.endpoint:xxx}")
    private String endpoint;
    @Value("${huawei.oss.access-key:xxx}")
    private String accessKeyId;
    @Value("${huawei.oss.accessKeySecret:xxx}")
    private String accessKeySecret;

    @Bean
    public ObsClient obsClient(){
        ObsClient obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
        return obsClient;
    }
}
