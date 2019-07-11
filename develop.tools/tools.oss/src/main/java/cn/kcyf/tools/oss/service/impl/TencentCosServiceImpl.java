package cn.kcyf.tools.oss.service.impl;

import cn.kcyf.tools.oss.service.FileService;
import com.qcloud.cos.COSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 腾讯云
 */
@Service("tencentCosServiceImpl")
public class TencentCosServiceImpl implements FileService {
    @Value("${oss.tencent.bucketName:xxx}")
    private String bucketName;
    @Value("${oss.tencent.domain:xxx}")
    private String domain;

    @Autowired
    private COSClient cosClient;

    @Override
    public String upload(MultipartFile file) {
        String key = UUID.randomUUID().toString();
        try {
            cosClient.putObject(bucketName, key, file.getInputStream(), null);
        } catch (IOException e) {
            throw new RuntimeException("【tencent】上传图片失败");
        }
        return domain + "/" + key;
    }

    @Override
    public boolean delete(String path) {
        cosClient.deleteObject(bucketName, path);
        return true;
    }
}
