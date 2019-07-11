package cn.kcyf.tools.oss.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TencentCosConfig {
    @Value("${tencent.oss.ap:xxx}")
    public String ap;
    @Value("${tencent.oss.access-key:xxx}")
    public String accessKeyId;
    @Value("${tencent.oss.accessKeySecret:xxx}")
    public String accessKeySecret;
    @Value("${tencent.oss.bucketName:xxx}")
    public String bucketName;
    @Value("${tencent.oss.domain:xxx}")
    public String domain;

    @Bean
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);
        ClientConfig clientConfig = new ClientConfig(new Region(ap));
        // 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

}
