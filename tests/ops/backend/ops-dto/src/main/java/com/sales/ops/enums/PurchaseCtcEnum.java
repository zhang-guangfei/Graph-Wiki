package com.sales.ops.enums;

/**
 * @author B91717
 * @date 2022/10/25
 * @apiNote
 * CTC更新状态，或者完成状态枚举类
 */
public enum PurchaseCtcEnum {
    FINISH("7","完成"),
    DELETE("8","删除");


    PurchaseCtcEnum(String type, String desc) {
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
