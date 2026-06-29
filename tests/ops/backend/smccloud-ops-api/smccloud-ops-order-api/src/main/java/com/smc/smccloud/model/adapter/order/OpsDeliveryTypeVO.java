package com.smc.smccloud.model.adapter.order;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2024/8/13 14:39
 * @Descripton TODO
 */
@Data
public class OpsDeliveryTypeVO {


    private String deliveryPriorityHigh;

    private Integer id;

    private Integer status;

    private String deliveryPriorityLow;

    private String deliveryPriorityLowCode;

    private String deliveryPriorityHighCode;

    private Boolean disabledSingleChange;

}
