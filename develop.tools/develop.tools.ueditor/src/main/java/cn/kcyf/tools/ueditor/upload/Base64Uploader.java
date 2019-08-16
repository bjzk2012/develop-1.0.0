package cn.kcyf.tools.ueditor.upload;

import cn.kcyf.tools.ueditor.PathFormat;
import cn.kcyf.tools.ueditor.UeditorConfigKit;
import cn.kcyf.tools.ueditor.define.AppInfo;
import cn.kcyf.tools.ueditor.define.BaseState;
import cn.kcyf.tools.ueditor.define.FileType;
import cn.kcyf.tools.ueditor.define.State;
import com.alibaba.fastjson.util.Base64;

import java.util.Map;

public final class Base64Uploader {

    public static State save(String content, Map<String, Object> conf) {
        byte[] data = decode(content);

        long maxSize = ((Long) conf.get("maxSize")).longValue();

        if (!validSize(data, maxSize)) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }

        String suffix = FileType.getSuffix("JPG");

        String savePath = PathFormat.parse((String) conf.get("savePath"),
                (String) conf.get("filename"));

        savePath = savePath + suffix;
        String rootPath = (String) conf.get("rootPath");

        State storageState = UeditorConfigKit.getFileManager().saveFile(data, rootPath, savePath);

        if (storageState.isSuccess()) {
            storageState.putInfo("url", UeditorConfigKit.getFileManager().getBasePath() + PathFormat.format(savePath));
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", "");
        }
        return storageState;
    }

    private static byte[] decode(String content) {
        return Base64.decodeFast(content);
    }

    private static boolean validSize(byte[] data, long length) {
        return data.length <= length;
    }

}