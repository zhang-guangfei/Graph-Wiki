package com.sales.ops.enums;

/**
 * @author C14717
 * @version 1.0
 * @description: 加工 1等待加工、2加工中、3加工完成
 * @date 2022/03/22 11:19
 */
public enum PcoStatusEnum {
    INIT(0,"初始"),
    WAIT(1,"拣货完成"),
    PART(2,"加工中"),
    FINISH(3,"加工完成");


    private Integer status;
    private String desc;

    PcoStatusEnum(Integer type, String desc) {
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
