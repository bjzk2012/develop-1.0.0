package cn.kcyf.tools.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件service
*/
public interface FileService {

	String upload(MultipartFile file);

	boolean delete(String path);

}
