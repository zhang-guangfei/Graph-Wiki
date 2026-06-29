package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2023/8/28 13:23
 * @Descripton TODO
 */
public enum  PurchaseOrderStatusEnum2 {
    /**
     * 0:待处理
     * ;1:已发送
     * ;2:已接单;
     * 3:运输中;
     * 5:已完成;
     * 6:强制完纳;
     * 7:转订删除;
     * 8:数据异常;
     * 9:删除
     */
     waitHand("0","待处理"),
    alreadySend("1","已发送"),
    alreadyRcv("2","已接单"),
    tranIng("3","运输中"),
    finish("5","已完成"),
    qiangzhiwanna("6","强制完纳"),
    zddel("7","转订删除"),
    errorData("8","数据异常"),
    del("9","删除");

    private String code;
    private String codeName;

    PurchaseOrderStatusEnum2(String code, String codeName) {
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

    public static String getNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (PurchaseOrderStatusEnum2 item : PurchaseOrderStatusEnum2.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return code;
    }
}
