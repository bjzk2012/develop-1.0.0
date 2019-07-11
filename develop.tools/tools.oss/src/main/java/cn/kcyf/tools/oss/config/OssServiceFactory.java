package cn.kcyf.tools.oss.config;

import cn.kcyf.tools.oss.model.OssType;
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

    private Map<OssType, FileService> map = new HashMap<>();


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
        map.put(OssType.ALIYUN, aliyunOssServiceImpl);
        map.put(OssType.QINIU, qiniuOssServiceImpl);
        map.put(OssType.NGINX, nginxServiceImpl);
        map.put(OssType.TENCENT, tencentCosServiceImpl);
        map.put(OssType.BAIDU, baiduBosServiceImpl);
        map.put(OssType.HUAWEI, huaweiObsServiceImpl);
    }

    public FileService getFileService(String fileType) {
        return getFileService(OssType.valueOf(fileType));
    }

    public FileService getFileService(OssType fileType) {
        return map.get(fileType);
    }
}
