package com.sales.ops.common.until;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/10/27 10:41
 */
public class StringMailUtil {

    public static boolean  verifyMail(String mailAddress){
        String regex = "^[A-Za-z0-9._]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        //定义Pattern对象
        Pattern pattern=Pattern.compile(regex);
        //匹配对象
        Matcher matcher=pattern.matcher(mailAddress);
        //bool函数判断输入是否正确
        return matcher.matches();

    }
}
