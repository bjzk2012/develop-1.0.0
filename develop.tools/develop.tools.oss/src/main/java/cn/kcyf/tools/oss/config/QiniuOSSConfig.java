package cn.kcyf.tools.oss.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 七牛云配置
 */
@Configuration
public class QiniuOSSConfig {

	
	@Value("${oss.qiniu.access-key:xxx}")
	private String accessKeyId;
	@Value("${oss.qiniu.accessKeySecret:xxx}")
	private String accessKeySecret;

	 
	 /**
     * 华东机房
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Zone.zone0());
    }

    /**
     * 构建一个七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }

 

    /**
     * 认证信息实例
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(accessKeyId, accessKeySecret);
    }
    
    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }

}
