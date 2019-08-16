package cn.kcyf.tools.ueditor.manager;

import cn.kcyf.commons.utils.FtpUtils;
import cn.kcyf.tools.ueditor.define.BaseState;
import cn.kcyf.tools.ueditor.define.State;

import java.io.InputStream;
import java.util.Map;

public class FtpFileManager extends AbstractFileManager {
    public FtpFileManager(FtpUtils ftpUtils) {
        this.ftpUtils = ftpUtils;
    }

    private FtpUtils ftpUtils;

    @Override
    public State list(Map<String, Object> conf, int start) {
        return null;
    }

    @Override
    public State saveFile(byte[] data, String rootPath, String savePath) {
        return null;
    }

    @Override
    public State saveFile(InputStream is, String rootPath, String savePath, long maxSize) {
        int lastIndex = savePath.lastIndexOf("/");
        String path = savePath.substring(1, lastIndex);
        String fileName = savePath.substring(lastIndex + 1);
        ftpUtils.upload(ftpUtils.path + path, fileName, is);

        State state = new BaseState(true);
        state.putInfo("size", 0);
        state.putInfo("title", savePath);
        return state;
    }

    @Override
    public State saveFile(InputStream is, String rootPath, String savePath) {
        return saveFile(is, rootPath, savePath, 0);
    }

    public String getBasePath() {
        return ftpUtils.domain + ftpUtils.path;
    }
}
