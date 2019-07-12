package cn.kcyf.tools.oss.service.impl;

import cn.kcyf.tools.oss.service.FileService;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 华为云
 * TODO 未测试
 */
@Service("huaweiObsServiceImpl")
public class HuaweiObsServiceImpl implements FileService {
    @Value("${oss.huawei.bucketName:xxx}")
    public String bucketName;
    @Autowired
    public ObsClient obsClient;
    @Override
    public String upload(MultipartFile file) {
        try {
            PutObjectResult result = obsClient.putObject(bucketName, UUID.randomUUID().toString(), file.getInputStream());
            return result.getObjectUrl();
        } catch (IOException e) {
            throw new RuntimeException("【huawei】上传图片失败", e);
        }
    }

    @Override
    public boolean delete(String key) {
        obsClient.deleteObject(bucketName, key);
        return true;
    }
}
