package cn.kcyf.tools.ueditor;


import cn.kcyf.tools.ueditor.manager.AbstractFileManager;
import cn.kcyf.tools.ueditor.manager.DefaultFileManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Ueditor 配置工具类，非线程安全，请全局配置
 */
public class UeditorConfigKit {

	protected static AbstractFileManager fileManager;

    public static void setFileManager(AbstractFileManager fileManager) {
        UeditorConfigKit.fileManager = fileManager;
    }

    public static AbstractFileManager getFileManager() {
		return UeditorConfigKit.fileManager;
	}
	
}
