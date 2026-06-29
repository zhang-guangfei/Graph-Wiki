package com.smc.smccloud.model.orderlog;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class OrderLogVO {

    public OrderLogVO() {
    }

    private Long id;

    /**
     * 订单号
     */
    @NotNull
    private String orderNo;

    /**
     * 操作类型
     */
    @NotNull
    private Integer optType;

    /**
     * 操作说明
     */
    private String description;

    /**
     * 操作时间
     */
    private Date optTime;

    /**
     * 操作人姓名
     */
    private String optUserName;

    /**
     * 操作人id
     */
    private String optUserId;

    /**
     * 操作者IP
     */
    private String ip;

    /**
     * 写入时间
     */
    private Date createTime;
}
