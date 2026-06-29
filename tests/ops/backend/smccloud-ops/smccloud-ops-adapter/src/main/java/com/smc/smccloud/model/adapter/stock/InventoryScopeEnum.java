package com.smc.smccloud.model.adapter.stock;

public enum InventoryScopeEnum {

    NORMAL("N", "在库库存"),
    MOVE("M", "在途库存"),
    SUPPLIER("S", "供应商库存"),
    ;


    private String code;
    private String desc;

    InventoryScopeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static InventoryScopeEnum getEnumByType(String type) {
        for (InventoryScopeEnum inventoryScopeEnum : InventoryScopeEnum.values()) {
            if (inventoryScopeEnum.getCode().equals(type)) {
                return inventoryScopeEnum;
            }
        }
        return null;
    }
}