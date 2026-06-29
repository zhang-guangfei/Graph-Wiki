package com.sales.ops.enums;

/**
 * @author B91717
 * @date 2023/4/27
 * @apiNote
 * 采购删单异常类
 */
public enum PurchaseErrorEnum {
    DELETE("1","采购删单");
//    DELETE("8","删除");


    PurchaseErrorEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;
    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
