package com.smc.ops.delivery.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Component
public class SpringContextUtil implements ApplicationContextAware, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(SpringContextUtil.class);
    private static ApplicationContext applicationContext;

    public SpringContextUtil() {
    }

    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;

    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static Object getBean(String name, Class cla) throws BeansException {
        return applicationContext.getBean(name, cla);
    }

    public static String getMessage(String code) {
        return applicationContext.getMessage(code, null, Locale.CHINA);
    }

    public static String getMessage(String code, Object[] params) {
        return applicationContext.getMessage(code, params, Locale.CHINA);
    }

    public static void clearHolder() {
        if (log.isDebugEnabled()) {
            log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        }

        applicationContext = null;
    }

    public static void publishEvent(ApplicationEvent event) {
        if (applicationContext != null) {
            applicationContext.publishEvent(event);
        }
    }

    public void destroy() throws Exception {
        clearHolder();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
