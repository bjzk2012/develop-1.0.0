package cn.kcyf.tools.oss.service.impl;

import cn.kcyf.tools.oss.service.FileService;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 阿里云
 */
@Service("aliyunOssServiceImpl")
public class AliyunOssServiceImpl implements FileService {

    @Autowired
    private OSSClient ossClient;

    @Value("${oss.aliyun.bucketName:xxx}")
    private String bucketName;
    @Value("${oss.aliyun.domain:xxx}")
    private String domain;

    @Override
    public String upload(MultipartFile file) {
        String key = UUID.randomUUID().toString();
        try {
            ossClient.putObject(bucketName, key, file.getInputStream());
        } catch (IOException e) {
            return "";
        }
        return domain + "/" + key;
    }

    @Override
    public boolean delete(String key) {
        ossClient.deleteObject(bucketName, key);
        return true;
    }

}
