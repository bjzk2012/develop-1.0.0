package cn.kcyf.tools.oss.service.impl;

import cn.kcyf.tools.oss.service.FileService;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 阿里云oss存储文件
 */
@Service("aliyunOssServiceImpl")
public class AliyunOssServiceImpl implements FileService {

    @Autowired
    private OSSClient ossClient;

    @Value("${aliyun.oss.bucketName:platform-wx}")
    private String bucketName;
    @Value("${aliyun.oss.domain:https://platform-wx.oss-cn-beijing.aliyuncs.com}")
    private String domain;

    @Override
    public String upload(MultipartFile file) {
        try {
            ossClient.putObject(bucketName, file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            return "";
        }
        return domain + "/" + file.getOriginalFilename();
    }

    @Override
    public boolean delete(String path) {
        ossClient.deleteObject(bucketName, path.replace(domain + "/", ""));
        return true;
    }

}
