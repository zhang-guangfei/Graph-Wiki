package com.smc.ops.delivery.inquiry.utils;

/**
 * @description:
 * @author: B91717
 * @time: 2025/6/6 15:55
 */

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class AbstractDateAfterFunction extends AbstractFunction {

    @Override
    public String getName() {
        return "date_after";
    }

    /**
     * 判断String类型的参数是否为日期格式
     * 2024-05-99
     * @param env
     * @return
     */
    @Override
    public AviatorObject call(Map<String, Object> env,
                              AviatorObject date1, AviatorObject date2,  AviatorObject format) {
        String pattern = format.stringValue(env);
        String dateStr1 = date1.stringValue(env);
        String dateStr2 = date2.stringValue(env);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date d1 = sdf.parse(dateStr1);
            Date d2 = sdf.parse(dateStr2);
            return AviatorBoolean.valueOf(d1.getTime() > d2.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
