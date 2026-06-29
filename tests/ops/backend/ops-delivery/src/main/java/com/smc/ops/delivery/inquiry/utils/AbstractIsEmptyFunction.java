package com.smc.ops.delivery.inquiry.utils;

/**
 * @description:
 * @author: B91717
 * @time: 2025/6/6 15:55
 */

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Collection;
import java.util.Map;

public class AbstractIsEmptyFunction extends AbstractFunction {

    @Override
    public String getName() {
        return "isEmpty";
    }

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        Object value = arg1.getValue(env);
        return AviatorBoolean.valueOf(
                value == null ||
                        (value instanceof String && ((String)value).isEmpty()) ||
                        (value instanceof Collection && ((Collection<?>)value).isEmpty())
        );
    }
}
