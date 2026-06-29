package com.sales.ops.enums;

/**
 * @author C14717
 * @version 1.0
 * @description: 转定类型
 * @date 2022/4/4 11:19
 */
public enum TrasferimentoTypeEnum {
    PO_TO_DO("0","采购转在库"),
    DO_TO_DO("1","在库转在库"),
    DO_TO_PO("2","在库转采购");

    private String status;
    private String desc;

    TrasferimentoTypeEnum(String type, String desc) {
        this.status = type;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
