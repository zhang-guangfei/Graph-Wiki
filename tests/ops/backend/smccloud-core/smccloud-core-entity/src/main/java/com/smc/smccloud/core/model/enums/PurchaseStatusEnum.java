package com.smc.smccloud.core.model.enums;


/**
 * @Author lyc
 * @Date 2022/4/24 9:59
 * @Descripton 请购单状态
 */
public enum PurchaseStatusEnum {

    waitHandle("0","待处理"),
    process("1","处理中"),
    waitPurchase("2","待采购"),
    alreadySend("3","已发送"),
    intercept("4","拦截"),
    shikomiIntercept("5","SHIKOMI拦截"),
    alreadyFinish("6","已完成"),
    cancel("9","取消");

    private String code;
    private String codeName;

    PurchaseStatusEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getName (String code) {
        for (PurchaseStatusEnum item : PurchaseStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.codeName;
            }
        }
        return null;
    }

    public static String getCodeByName (String name) {
        for (PurchaseStatusEnum item : PurchaseStatusEnum.values()) {
            if (item.getCodeName().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
}
