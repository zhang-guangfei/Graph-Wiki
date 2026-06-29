package com.smc.ops.delivery.inquiry.utils;

import com.googlecode.aviator.AviatorEvaluator;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: B91717
 * @time: 2025/6/10 10:46
 */
@Configuration
public class AviatorConfig {

    @PostConstruct
    public void registerCustomFunctions() {
        AviatorEvaluator.addFunction(new AbstractDateAfterFunction());
        AviatorEvaluator.addFunction(new AbstractIsInvalidDateFunction());
        AviatorEvaluator.addFunction(new AbstractIsEmptyFunction());
        // 可继续注册其他自定义函数
    }
}
