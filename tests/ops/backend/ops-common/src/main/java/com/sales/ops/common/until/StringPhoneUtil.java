package com.sales.ops.common.until;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2022/8/11 11:08
 */
public class StringPhoneUtil {

    /**
     * 过滤掉手机号中的特殊字符：空格，‘-’等等字符。
     *
     * @param phone : "158-8922-2222"  , "158 8955 6336"
     * @return :phone : "15889222222"  , "15889556336"
     */
    public static String replaceSpaceStr2(String phone) {
        if (StringUtils.isBlank(phone)){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int length = phone.length();
        boolean firstSplit = true;
        for (int i = 0; i < length; i++) {
            String subStr = phone.substring(i, i + 1);
            if (isNumeric(subStr)) {  //如果这个字符是数字，则保存在sb中。
                sb.append(subStr);
            } else {
                if(!"-".equals(subStr)){
                    sb.append(";");
                    firstSplit = false;
                }else if("-".equals(subStr)){
                    sb.append(subStr);
                }
            }
        }
        return sb.toString();
    }
    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) { //可以正常使用

        Pattern pattern = Pattern.compile("[0-9]*");

        return pattern.matcher(str).matches();

    }


    public static boolean  isMobil(String phone){
        String regex2="1[3-9][0-9]\\d{8}";

        //定义Pattern对象
        Pattern pattern2=Pattern.compile(regex2);
        //匹配对象
        Matcher matcher2=pattern2.matcher(phone);
        //bool函数判断输入是否正确
        return matcher2.matches();
    }

    public static boolean isPhone(String phone){
        //
        String regex2="^1\\d{10}$|^(0\\d{2,3}-?|\\(0\\d{2,3}\\))?[1-9]\\d{4,7}(-\\d{1,8})?$";
        //定义Pattern对象
        Pattern pattern2=Pattern.compile(regex2);
        //匹配对象
        Matcher matcher2=pattern2.matcher(phone);
        //bool函数判断输入是否正确
        return matcher2.matches();
    }

}
