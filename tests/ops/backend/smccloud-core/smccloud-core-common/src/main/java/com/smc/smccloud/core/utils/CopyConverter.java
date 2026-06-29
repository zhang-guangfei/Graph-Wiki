package com.smc.smccloud.core.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.cglib.core.Converter;
import org.springframework.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CopyConverter implements Converter {

    private Object targetObject;

    @Override
    public Object convert(Object value, Class targetClass, Object context) {
        try {
            if (ObjectUtils.isEmpty(value)) {
                String name = StringUtils.substring(ObjectUtils.nullSafeToString(context), 3);
                String fiedName = StringUtils.uncapitalize(name);
                return FieldUtils.readField(targetObject, fiedName, true);
            }

        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
        }

        return value;
    }

    public CopyConverter(Object targetObject) {
        this.targetObject = targetObject;
    }
}
