package com.sales.ops.serviceimpl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2023/6/10 14:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InvLog {
    String apiName() default "";//api名称
}
