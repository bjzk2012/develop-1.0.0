package cn.kcyf.tools.ueditor.upload;

import cn.kcyf.tools.ueditor.PathFormat;
import cn.kcyf.tools.ueditor.UeditorConfigKit;
import cn.kcyf.tools.ueditor.define.AppInfo;
import cn.kcyf.tools.ueditor.define.BaseState;
import cn.kcyf.tools.ueditor.define.FileType;
import cn.kcyf.tools.ueditor.define.State;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class BinaryUploader {

	public static final State save(HttpServletRequest request, Map<String, Object> conf) {
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}

		try {
            MultipartFile file = ((StandardMultipartHttpServletRequest) request).getFile(conf.get("fieldName").toString());

			if (file == null) {
				return new BaseState(false, 7);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			String rootPath = (String) conf.get("rootPath");

			State storageState = UeditorConfigKit.getFileManager().saveFile(file.getInputStream(), rootPath, savePath, maxSize);

			if (storageState.isSuccess()) {
				storageState.putInfo("url", UeditorConfigKit.getFileManager().getBasePath() + PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
