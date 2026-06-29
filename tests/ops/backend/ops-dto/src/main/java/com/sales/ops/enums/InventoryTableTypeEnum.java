package com.sales.ops.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 在途在库表
 * @auther C12961
 * @date 2021/11/18 8:10
 */
public enum InventoryTableTypeEnum {
    EMPTY("","无"),
    NORMAL("N","物流在库表"),
    TRANS("T","在途表") ;


    private String type;
    private String desc;

    InventoryTableTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static InventoryTableTypeEnum getEnumByType(String type){
        for (InventoryTableTypeEnum tableEnum : InventoryTableTypeEnum.values()) {
            if(tableEnum.getType().equals(type)){
                return tableEnum;
            }
        }
        return null;
    }

    public static boolean isNormal(String type){
        return StringUtils.equals(type, InventoryTableTypeEnum.NORMAL.getType());
    }



}
