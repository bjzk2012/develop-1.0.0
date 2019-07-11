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

import java.util.UUID;

/**
 * 七牛云oss存储文件
 */
@Service("qiniuOssServiceImpl")
public class QiniuOssServiceImpl implements FileService, InitializingBean {

	@Autowired
	private UploadManager uploadManager;

	@Autowired
	private BucketManager bucketManager;

	@Autowired
	private Auth auth;

	@Value("${qiniu.oss.bucketName:5CBWKFd1pP-OSiusd1Bvhokp-ih4i3bs2QA2r-U2}")
	private String bucket;

	@Value("${qiniu.oss.endpoint:pugech0l5.bkt.clouddn.com}")
	private String endpoint;

	private StringMap putPolicy;

	@Override
	public String upload(MultipartFile file){
		try {
			uploadManager.put(file.getBytes(),  file.getOriginalFilename() , auth.uploadToken(bucket));
		} catch (Exception e) {
			return "";
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
			Response response = bucketManager.delete(this.bucket, path);
			int retry = 0;
			while (response.needRetry() && retry++ < 3) {
				response = bucketManager.delete(bucket, path);
			}
		} catch (QiniuException e) {
			return false ;
		}
		return true;

	}

}
