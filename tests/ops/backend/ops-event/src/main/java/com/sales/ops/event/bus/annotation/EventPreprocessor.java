package com.sales.ops.event.bus.annotation;

import com.sales.ops.event.bus.EventCodeEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface EventPreprocessor {

    @AliasFor("value")
    EventSourceEnum[] source() default {};

    @AliasFor("source")
    EventSourceEnum[] value() default {};

    EventCodeEnum[] target() default {}; //没什么用，说明用


}