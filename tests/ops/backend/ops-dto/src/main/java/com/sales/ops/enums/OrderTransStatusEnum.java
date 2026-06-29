package com.sales.ops.enums;

/**
 * 主动调拨单的状态枚举类
 * @author C12961
 * @date 2022/3/21 16:42
 */
public enum OrderTransStatusEnum {


    DB_FAIL(-1, "处理失败"),
    DB_INIT(0, "未处理"),
    DB_WAIT(1, "待出库"),
    DB_OUTING(2, "出库中"),
    DB_OUTED(3, "已出库"),
    DB_SIGNIN(4, "已签收"),
    DB_CONFIRM(5, "到货确认"),
    DB_FINISHED(6, "已完成"),
    ;


    private Integer status;
    private String desc;

    OrderTransStatusEnum(Integer status, String desc) {
        this.status = status;
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

    public static OrderTransStatusEnum getByStatus(Integer status) {
        for (OrderTransStatusEnum statusEnum : values()) {
            if (statusEnum.getStatus().equals(status)) {
                return statusEnum;
            }
        }
        return null;
    }
}
