package com.sales.ops.dto.inquiry;

import com.sales.ops.dto.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 采购异常返信字典，用于程序校验
 */
public enum InquiryDlvDateEnum {

    TWO222222("22/22/22", "型号确认中订单正转交正确部门"),
    THREE333333("33/33/33","正在日本研发中心组装"),
    FOUR444444("44/44/44", "紧急停止供货"),
    FIVE555555("55/55/55", "部品定向其他工厂,无确切交货期"),
    SIX666666("66/66/66", "部品定向外协,无确切交货期"),
    SEVEN777777("77/77/77", "加工完毕日期不确定,正在确认中"),
    EIGHT888888("88/88/88", "日本制造部正在向日本研发中心要图纸"),
    DLVDATE4444("4444-04-04", "紧急停止供货"),
    NINE999999("99/99/99","订单异常,请登录AS400查看异常原因并及时进行处理"),
    NINE990000("99/00/00", "要提供样式书"),
    DLVDATE9999("9999-09-09", "订单异常,请登录AS400查看异常原因并及时进行处理"),

    DLVDATE9900("9900-09-09", "要提供样式书"),

    NEWTWO222222("222222", "型号确认中订单正转交正确部门"),
    NEWTHREE333333("333333","正在日本研发中心组装"),
    NEWDLVDATE4444("444444", "紧急停止供货"),
    NEWFIVE555555("555555", "部品定向其他工厂,无确切交货期"),
    NEWSIX666666("666666", "部品定向外协,无确切交货期"),
    NEWSEVEN777777("777777", "加工完毕日期不确定,正在确认中"),
    NEWEIGHT888888("888888", "日本制造部正在向日本研发中心要图纸"),
    NEWDLVDATE9999("999999", "订单异常,请登录AS400查看异常原因并及时进行处理"),
    NEWDLVDATE9900("990000", "要提供样式书");


    private String type;
    private String desc;

    InquiryDlvDateEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static InquiryDlvDateEnum parse(String type) {
        return Arrays.stream(InquiryDlvDateEnum.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }

    public static String convertDlvDate(Date date) {
        if (date == null) {
            return null;
        }
        String result = null;
        String dateStr = DateUtil.dateToString(date);
        switch (dateStr) {
            case "8888-08-08":
                result = EIGHT888888.getType();
                break;
            case "2222-02-02":
                result = TWO222222.getType();
                break;
            case "3333-03-03":
                result = THREE333333.getType();
                break;
            case "5555-05-05":
                result = FIVE555555.getType();
                break;
            case "6666-06-06":
                result = SIX666666.getType();
                break;
            case "7777-07-07":
                result = SEVEN777777.getType();
                break;
            case "4444-04-04":
                result = NEWDLVDATE4444.getType();
                break;
            case "9999-09-09":
                result = NEWDLVDATE9999.getType();
                break;
            case "9900-09-09":
                result = NEWDLVDATE9900.getType();
                break;
            default:
                if (dateStr.startsWith("99") && !dateStr.equals("9999-12-31")) {
                    String year = dateStr.substring(0, 4);
                    String month = dateStr.substring(5, 7);
                    String day = dateStr.substring(8, 10);
                    year = year.replace("99", "20");
                    result = year + "-" + month + "-" + "99";
                } else {
                    // 9924-02-29
                    result = dateStr;
                }
                break;
        }
        return result;
    }

    // 校验是否为日期格式
    public static Boolean dateTimeValue(String date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        boolean isValid = true;
        try {
            // 创建日期时间格式化对象
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            // 将用户输入的字符串解析为LocalDateTime对象
            LocalDate dateTime = LocalDate.parse(date, formatter);
            System.out.println(dateTime);
        } catch (DateTimeParseException e) {
            // 解析失败，说明输入的字符串不符合指定的日期时间格式
            isValid = false;
        }
        return isValid;
    }

    public static Boolean dateValue(String date) {
        String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};
        boolean isValid = false;
        for (String pattern : patterns) {
            try {
                // 创建日期时间格式化对象
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                // 尝试将用户输入的字符串解析为LocalDateTime或LocalDate对象
                LocalDate dateOnly = LocalDate.parse(date, formatter);
                System.out.println(dateOnly);
                isValid = true;
                break; // 一旦解析成功，跳出循环
            } catch (DateTimeParseException e) {
                // 解析失败，继续尝试下一个格式
            }
        }
        return isValid;
    }
    public static List<String> errorDlvdateList() {
        List<String> list = new ArrayList<String>();
        list.add(NEWDLVDATE4444.getType());
        list.add(NEWDLVDATE9999.getType());
        list.add(NEWDLVDATE9900.getType());
        return list;
    }

    public static List<String> specialDateList() {
        List<String> list = new ArrayList<String>();
        list.add(NEWTWO222222.getType());
        list.add(NEWTHREE333333.getType());
        list.add(NEWDLVDATE4444.getType());
        list.add(NEWFIVE555555.getType());
        list.add(NEWSIX666666.getType());
        list.add(NEWSEVEN777777.getType());
        list.add(NEWEIGHT888888.getType());
        list.add(NEWDLVDATE9900.getType());
        list.add(NEWDLVDATE9999.getType());
        return list;
    }

//    public static void main(String[] args) {
//         System.out.println("\"2024-04-10\"解析结果：" + dateValue("2024-04-10"));
//         System.out.println("\"241101\"解析结果：" + dateValue("241101"));
//         System.out.println("\"2024-02-20 10:00:02.630\"解析结果："+ dateValue("2024-02-20 10:00:02.630"));
//         System.out.println("\"2024-02-20 10:00:02\"\"解析结果：" + dateValue("2024-02-20 10:00:02"));
//    }

}
