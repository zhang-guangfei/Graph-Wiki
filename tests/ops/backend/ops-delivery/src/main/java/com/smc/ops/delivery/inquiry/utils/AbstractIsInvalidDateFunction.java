package com.smc.ops.delivery.inquiry.utils;

/**
 * @description:
 * @author: B91717
 * @time: 2025/6/6 15:55
 */

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.sales.ops.dto.util.DateUtil;

import java.util.Map;

public class AbstractIsInvalidDateFunction extends AbstractFunction {

    @Override
    public String getName() {
        return "isInvalidDate";
    }

    /**
     * 判断String类型的参数是否为日期格式
     * 2024-05-99
     * @param env
     * @param arg1
     * @return
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        try {
            String dateStr = arg1.stringValue(env);
            boolean isValid = true; // 默认返回无效
            if (DateUtil.isValidDateTimeOrDate(dateStr)) { // 校验是否为标准的日期格式
                isValid = false;
            } else if (dateStr.length() == 8 && dateStr.startsWith("2") && !dateStr.endsWith("9999")) { // 校验是否为标准的日期格式
//                && !dateStr.endsWith("99") && !dateStr.startsWith("0")
                isValid = false;
            }
            return AviatorBoolean.valueOf(isValid); // 返回是否无效日期
        } catch (Exception e) {
            return AviatorBoolean.valueOf(true); // 返回无效日期
        }
    }
}
