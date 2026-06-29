package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/9/17 17:00
 * @Descripton TODO
 */
public enum  ReturnOrderStatusEnum {
    edit("1","编辑中"),
    approval("2","审批中"),
    waitRcv("3","待收货"),
    refuseRcv("4","包裹拒收"),
    alreadyRcv("5","仓库已收货"),
    finishRcv("6","完成退货"),
    shth("7","收货退回"),
    cancelReturn("9","取消")
    ;

    private String code;
    private String codeName;

    ReturnOrderStatusEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }


    public static String getCodeNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        for (ReturnOrderStatusEnum item : ReturnOrderStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return "";
    }

}
