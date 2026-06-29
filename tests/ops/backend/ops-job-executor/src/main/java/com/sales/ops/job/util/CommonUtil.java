package com.sales.ops.job.util;

import java.util.regex.Pattern;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2022/1/19 12:26
 */
public class CommonUtil {

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
