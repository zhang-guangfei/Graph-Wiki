package com.smc.smccloud.model.enums;

/**
 * @author wuweidong
 * @create 2023/11/21 14:52
 * @description
 */
public enum PreStockProcessTypeEnum {

    purchase("1", "采购"),
    binPurchase("2", "BIN采购"),
    prepareBin("3", "预约Bin在途"),
    trans("4", "调库"),
    transDiff("5", "异仓调拨"),
    cancel("9", "取消"),
    prepareOrder("6", "准备订单")
            ;

    private String code;
    private String codeName;

    PreStockProcessTypeEnum(String code, String codeName) {
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
        for (PreStockProcessTypeEnum item : PreStockProcessTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
