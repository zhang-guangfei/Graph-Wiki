package com.sales.ops.aop.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：日志注解
 * @date ：Created in 2022/1/14 9:16
 */
@Component
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String value() default "";

    boolean param() default true;

}
