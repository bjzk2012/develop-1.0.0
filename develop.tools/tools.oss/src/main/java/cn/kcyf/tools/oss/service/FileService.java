package cn.kcyf.tools.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件service 目前仅支持阿里云oss,七牛云
*/
public interface FileService {

	String upload(MultipartFile file);

	boolean delete(String path);

}
