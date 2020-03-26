package cn.kcyf.tools.oss.service.impl;

import cn.kcyf.tools.oss.service.FileService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 七牛云
 */
public class QiniuOssServiceImpl implements FileService, InitializingBean {

	@Autowired
	private UploadManager uploadManager;

	@Autowired
	private BucketManager bucketManager;

	@Autowired
	private Auth auth;

	@Value("${oss.qiniu.bucketName:xxx}")
	private String bucketName;

	@Value("${oss.qiniu.endpoint:xxx}")
	private String endpoint;

	private StringMap putPolicy;

	@Override
	public String upload(MultipartFile file){
		try {
			uploadManager.put(file.getBytes(),  file.getOriginalFilename() , auth.uploadToken(bucketName));
		} catch (Exception e) {
			throw new RuntimeException("【qiniu】文件上传发生错误", e);
		}
		return endpoint + "/" + file.getOriginalFilename();
	}
	@Override
	public void afterPropertiesSet(){
		this.putPolicy = new StringMap();
		putPolicy.put("returnBody",
				"{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
	}

	@Override
	public boolean delete(String path) {
		try {
			Response response = bucketManager.delete(this.bucketName, path);
			int retry = 0;
			while (response.needRetry() && retry++ < 3) {
				response = bucketManager.delete(bucketName, path);
			}
		} catch (QiniuException e) {
			return false ;
		}
		return true;

	}

}
