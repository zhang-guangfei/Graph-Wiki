package com.sales.ops.enums;

/**
 * 物流出库状态
 *
 * @author C12961
 * @date 2022/3/19 13:25
 */
public enum WmStatusEnum {

    SENT_TASK(0, "SENT_TASK", "已下发"),
    GROUP_BATCH(1, "GROUP_BATCH", "已组波次"),
    PICK(2, "PICK", "拣货完成"),
    PACKAGE(3, "PACKAGE", "包装完成"),
    DELIVERED(4, "DELIVERED", "装箱完成"),
    COLLECTING(5, "COLLECTING", "待揽收"),
    ;

    private Integer code;
    private String status;
    private String desc;

    WmStatusEnum(Integer code, String status, String desc) {
        this.status = status;
        this.desc = desc;
        this.code = code;
    }


    public  boolean before(Integer wmsStatus){
        if (wmsStatus == null) {
            return true;
        }
        return wmsStatus < this.code;
    }



    public static OrderStatusDetailEnum getStatusDesc(String status) {
        for (WmStatusEnum value : WmStatusEnum.values()) {
            if (value.getStatus().equals(status)) {
                if (value == PICK) {
                    return OrderStatusDetailEnum.WM_PICK;
                }
                if (value == PACKAGE) {
                    return OrderStatusDetailEnum.WM_PACKAGE;
                }
                if (value == COLLECTING) {
                    return OrderStatusDetailEnum.WM_COLLECTING;
                }
                if (value == DELIVERED) {
                    return OrderStatusDetailEnum.WM_DELIVERED;
                }
            }
        }
        return null;
    }


    public static OrderStatusDetailEnum getDBStatusDesc(String status) {
        for (WmStatusEnum value : WmStatusEnum.values()) {
            if (value.getStatus().equals(status)) {
                if (value == PICK) {
                    return OrderStatusDetailEnum.DB_PICK;
                }
                if (value == PACKAGE) {
                    return OrderStatusDetailEnum.DB_PACKAGE;
                }
                if (value == COLLECTING) {
                    return OrderStatusDetailEnum.DB_COLLECTING;
                }
                if (value == DELIVERED) {
                    return OrderStatusDetailEnum.DB_DELIVERED;
                }
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
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

    public static WmStatusEnum parse(String status) {
        for (WmStatusEnum statusEnum : WmStatusEnum.values()) {
            if (statusEnum.getStatus().equals(status)) {
                return statusEnum;
            }
        }
        return null;
    }
}
