package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/6/1 9:50
 * @Descripton TODO
 */
public enum  OrderModifyEnum {
    edit("1","编辑"),
    waitHand("2","待处理"),
    noPass("4","不通过"),
    handing("5","处理中"),
    finish("6","完成"),
    bnbg("7","不能变更"),
    notHand("8","供应商确认中"),
    cancel("9","取消")
    ;

    private String code;
    private String codeName;

    OrderModifyEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }


    public static String getCodeNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (OrderModifyEnum item : OrderModifyEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
