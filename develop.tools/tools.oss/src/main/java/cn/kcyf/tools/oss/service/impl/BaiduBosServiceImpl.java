package cn.kcyf.tools.oss.service.impl;

import cn.kcyf.tools.oss.service.FileService;
import com.baidubce.services.bos.BosClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 百度云
 */
@Service("baiduBosServiceImpl")
public class BaiduBosServiceImpl implements FileService {
    @Value("${baidu.oss.bucketName:xxx}")
    public String bucketName;
    @Value("${baidu.oss.domain:xxx}")
    public String domain;
    @Autowired
    private BosClient client;

    @Override
    public String upload(MultipartFile file) {
        String key = UUID.randomUUID().toString();
        try {
            client.putObject(bucketName, key, file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("【baidu】上传图片失败");
        }
        return domain + "/" + key;
    }

    @Override
    public boolean delete(String key) {
        client.deleteObject(bucketName, key);
        return true;
    }
}
