package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2021/12/31 11:49
 * @description：代码中单独对库存粒度的判断时会用到的枚举
 */
public enum InventoryMatchConfigEnum {
    MASTER_GM("MASTER_GM", "物流中心在库"),
    ;


    private String code;
    private String desc;

    InventoryMatchConfigEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
