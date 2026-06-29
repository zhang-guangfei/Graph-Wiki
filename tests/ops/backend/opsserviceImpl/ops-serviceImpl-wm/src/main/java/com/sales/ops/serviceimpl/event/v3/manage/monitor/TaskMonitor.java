package com.sales.ops.serviceimpl.event.v3.manage.monitor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface TaskMonitor {

    String value() default "";

    String type() default "info";
}