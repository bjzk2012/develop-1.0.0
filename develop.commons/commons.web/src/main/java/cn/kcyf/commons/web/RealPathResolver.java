package cn.kcyf.commons.web;

/**
 * 绝对路径提供类
 *
 * @author Tom
 */
public interface RealPathResolver {
    /**
     * 获得绝对路径
     *
     * @param path
     * @return
     */
    String get(String path);
}
