package com.smc.smccloud.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author lyc
 * @Date 2021/12/22 14:33
 * @Descripton 实现了ApplicationContextAware ，这个类就可以获取到所有引用对象的bean
 */

@Component
public final class ContextUtil implements ApplicationContextAware {

    protected static ApplicationContext applicationContext ;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (applicationContext == null) {
            applicationContext = arg0;
        }
    }
    public static Object getBean(String name) {
        // name表示其他要注入的注解name名
        return applicationContext.getBean(name);
    }
    /**
     * 拿到ApplicationContext对象实例后就可以手动获取Bean的注入实例对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}