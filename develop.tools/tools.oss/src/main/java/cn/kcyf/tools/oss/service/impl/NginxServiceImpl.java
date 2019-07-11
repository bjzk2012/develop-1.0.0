package cn.kcyf.tools.oss.service.impl;

import cn.kcyf.tools.oss.service.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * nginx
 * TODO 未测试
 */
@Service("nginxServiceImpl")
public class NginxServiceImpl implements FileService {
    @Value("${nginx.oss.uploadPath:xxx}")
    private String uploadPath;
    @Value("${nginx.oss.downPath:xxx}")
    private String downPath;

    @Override
    public String upload(MultipartFile file) {
        String key = UUID.randomUUID().toString();
        String f = uploadPath + key;
        f = f.substring(0, f.lastIndexOf("/"));
        File localFile = new File(f);
        try {
            if (!localFile.exists() && !localFile.isDirectory()) {
                localFile.mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(uploadPath + key));
        } catch (Exception e) {
            throw new RuntimeException("【nginx】文件上传发生错误", e);
        }
        return downPath + file.getOriginalFilename();
    }

    @Override
    public boolean delete(String path) {
        try {
            FileUtils.forceDelete(new File(uploadPath + path));
        } catch (IOException e) {
            throw new RuntimeException("【nginx】文件删除发生错误", e);
        }
        return true;
    }

}
