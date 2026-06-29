package com.sales.ops.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：日志注解
 * @date ：Created in 2022/1/14 9:16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String apiName() default "";//api名称
    String type() default "";// 出库，入库，加工
}
