package cn.kcyf.tools.oss.config;

import cn.kcyf.tools.oss.model.FileType;
import cn.kcyf.tools.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * FileService工厂
 * 将各个实现类放入map
 */
@Configuration
public class OssServiceFactory {

    private Map<FileType, FileService> map = new HashMap<>();


    @Autowired
    private FileService aliyunOssServiceImpl;

    @Autowired
    private FileService qiniuOssServiceImpl;

    @Autowired
    private FileService nginxServiceImpl;

    @Autowired
    private FileService tencentCosServiceImpl;

    @Autowired
    private FileService baiduBosServiceImpl;

    @Autowired
    private FileService huaweiObsServiceImpl;


    @PostConstruct
    public void init() {
        map.put(FileType.ALIYUN, aliyunOssServiceImpl);
        map.put(FileType.QINIU, qiniuOssServiceImpl);
        map.put(FileType.NGINX, nginxServiceImpl);
        map.put(FileType.TENCENT, tencentCosServiceImpl);
        map.put(FileType.BAIDU, baiduBosServiceImpl);
        map.put(FileType.HUAWEI, huaweiObsServiceImpl);
    }

    public FileService getFileService(String fileType) {
        return getFileService(FileType.valueOf(fileType));
    }

    public FileService getFileService(FileType fileType) {
        return map.get(fileType);
    }
}
