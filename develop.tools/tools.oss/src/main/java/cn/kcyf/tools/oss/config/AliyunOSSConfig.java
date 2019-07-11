package cn.kcyf.tools.oss.config;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 阿里云配置
 */
@Configuration
public class AliyunOSSConfig {

	@Value("${aliyun.oss.endpoint:xxx}")
	private String endpoint;
	@Value("${aliyun.oss.access-key:xxx}")
	private String accessKeyId;
	@Value("${aliyun.oss.accessKeySecret:xxx}")
	private String accessKeySecret;

	/**
	 * 阿里云文件存储client
	 * 只有配置了aliyun.oss.access-key才可以使用
	 */
	@Bean
	@ConditionalOnProperty(name = "aliyun.oss.access-key", matchIfMissing = true)
	public OSSClient ossClient() {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		return ossClient;
	}

}
