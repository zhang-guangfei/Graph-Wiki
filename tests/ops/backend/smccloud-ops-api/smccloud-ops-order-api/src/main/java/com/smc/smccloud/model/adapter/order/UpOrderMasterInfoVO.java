package com.smc.smccloud.model.adapter.order;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/8/13 13:14
 * @Descripton TODO
 */
@Data
public class UpOrderMasterInfoVO {

    // 是否可更改出货方式
    private Boolean canChangeDeliveryModel = false;

    private List<OpsDeliveryTypeVO> deliveryTypeVOList;



}
