package com.sales.ops.dto.inquiry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：B91717
 * 催促校验枚举类
 */
public enum InquiryReturnErrorMsgEnum {
    As400CANCEL("0", "CANCELLED ON ABOVE DATE"),
    As400DISPATCHED("1", "DISPATCHED FROM FACTORY ON ABOVE DATE"),
    As400ERROR("2", "EEE"),

    MANUCANCEL("3", "已取消"),

    MANUDELIVERY("4", "制造物流已出库");



    private String code;
    private String message;

    InquiryReturnErrorMsgEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getType() {
        return code;
    }
    public String getDesc() {
        return message;
    }
    public static InquiryReturnErrorMsgEnum getType(String type) {
        for (InquiryReturnErrorMsgEnum typeEnum : values()) {
            if (typeEnum.code.equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }


    public static List<String> AS400ErrorMsg() {
        List<String> list = new ArrayList<String>();
        list.add(As400CANCEL.getDesc());
        list.add(As400DISPATCHED.getDesc());
        return list;
    }

    public static List<String> MANUErrorMsg() {
        List<String> list = new ArrayList<String>();
        list.add(MANUCANCEL.getDesc());
        list.add(MANUDELIVERY.getDesc());
        return list;
    }


}
