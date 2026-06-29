package com.sales.ops.enums;

/**
 * @author C02483
 * @version 1.0
 * @description: 物流出库状态
 * @date 2021/11/9 11:19
 */
public enum OrderChangeDBWmsEnum {
    DEL_SUCCESS(1,"wms删除调拨单成功"),
    DEL_FAILURE(2,"wms已作业不能删除调拨单"),
    NOT_HAVE(3,"还没有下发wms");

    private Integer status;
    private String desc;

    OrderChangeDBWmsEnum(Integer type, String desc) {
        this.status = type;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
