package com.smc.smccloud.core.utils;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class JsonValidatorUtil {
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 解析JSON并校验
     */
    public static <T> T parseAndValidate(String json, Class<T> clazz) {
        T object = JSONObject.parseObject(json, clazz);
        validate(object);
        return object;
    }

    /**
     * 校验对象
     */
    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getPropertyPath())
                        .append(": ")
                        .append(violation.getMessage())
                        .append("; ");
            }
            throw new IllegalArgumentException("参数校验失败: " + sb.toString());
        }
    }
}
