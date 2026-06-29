package com.smc.smccloud.model.orderstate;

import lombok.Data;

import java.util.Date;

@Data
public class OrderStateDetailVO {
    /**
     * 状态类型
     */
    private Integer stateCode;

    /**
     * 变更次数
     */
    private Integer changeTimes;

    /**
     * 状态说明
     */
    private String stateDes;

    /**
     * 状态的最早时间
     */
    private Date minDate;

    /**
     * 状态的最晚时间
     */
    private Date maxDate;

    /**
     * 状态来源
     */
    private String provider;

    private String delayDay;

    private Date createTime;

    private String remark;

    private Long id;

    private Date stateDate;

    private String orderNo;

    private Date updateTime;

    private Date firstDate;

    private String dateName;
}
