package cn.kcyf.commons.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量保存Spring ApplicationContext,可在任意代码中取出ApplicaitonContext.
 *
 * @author tom
 */
public class SpringContextHolder implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("上下文未注入");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkApplicationContext();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }
}
