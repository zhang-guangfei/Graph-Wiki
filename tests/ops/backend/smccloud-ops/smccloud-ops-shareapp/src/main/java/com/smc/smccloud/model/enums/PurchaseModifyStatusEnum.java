package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;


public enum PurchaseModifyStatusEnum {
    waitHand(0,"待处理"),
    handing(1,"处理中"),
    finish(5,"成功"),
    bnbg(7,"不能变更"),
    notHand(8,"暂不处理"),
    cancel(9,"取消");

    private Integer code;
    private String codeName;

    PurchaseModifyStatusEnum(Integer code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public Integer getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }


    public static String getCodeNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (PurchaseModifyStatusEnum item : PurchaseModifyStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
