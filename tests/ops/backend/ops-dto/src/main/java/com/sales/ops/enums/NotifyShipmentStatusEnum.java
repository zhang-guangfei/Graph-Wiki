package com.sales.ops.enums;

/**
 * @author ：C14717
 * @version: $
 * @description：通知发货主表状态
 * @date ：Created in 2024/12/16 10:19
 */
public enum NotifyShipmentStatusEnum {
    INIT(0,"分配初始"),
    PART(1,"分配部分"),
    FINISH(2,"分配完成"),;

    private Integer code;
    private String desc;

    NotifyShipmentStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
