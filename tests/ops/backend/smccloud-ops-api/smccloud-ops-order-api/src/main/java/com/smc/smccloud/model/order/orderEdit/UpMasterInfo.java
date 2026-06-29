package com.smc.smccloud.model.order.orderEdit;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/11/8 10:07
 * @Descripton 变更订单主体信息实体
 */
@Data
public class UpMasterInfo {


    private String loginUserId;

    // 订单号(主单号)
    private String orderNo;

    // 集约方式 0:按地址集约;1:按订单集约;2:按用户集约
    private String intensiveNo;

    // 出货方式
    // 0:随发
    // 1:货齐-单仓
    // 2:随发-分批
    // 3:货齐-多仓
    private String deliveryEntireNo;

}
