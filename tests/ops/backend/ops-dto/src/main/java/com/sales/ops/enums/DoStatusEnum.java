package com.sales.ops.enums;

import java.util.Arrays;

/**
 * @author C02483
 * @version 1.0
 * @description: 物流出库状态
 * @date 2021/11/9 11:19
 */
public enum DoStatusEnum {
    INIT(0,"初始"),
    WAIT(1,"待发货"),
    PART(2,"部分发货"),
    FINISH(3,"发货完成"),
    CANCEL(4,"取消");

    private Integer status;
    private String desc;

    DoStatusEnum(Integer type, String desc) {
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


    public static DoStatusEnum parse(Integer status){
        int s = status != null ? status : 0;
        return Arrays.stream(DoStatusEnum.values()).filter(e -> e.getStatus() == s).findFirst().orElse(null);
    }
}
