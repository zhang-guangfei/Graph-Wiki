package com.sales.ops.serviceimpl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retryable {
    int maxRetries() default 3; // 最大重试次数，默认为3
    long retryInterval() default 1000; // 重试间隔，默认为1000毫秒
}