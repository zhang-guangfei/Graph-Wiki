package com.smc.smccloud.log.annotation;

import java.lang.annotation.*;

/**
 * @Author lyc
 * @Date 2023/5/29 9:12
 * @Descripton TODO
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    /**
     * 描述
     *
     * @return {String}
     */
    String value();
}
