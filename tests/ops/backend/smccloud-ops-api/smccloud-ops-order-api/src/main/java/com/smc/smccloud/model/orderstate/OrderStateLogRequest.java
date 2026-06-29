package com.smc.smccloud.model.orderstate;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/10/26 11:42
 * @Descripton TODO
 */
@Data
public class OrderStateLogRequest {

    private String orderNo;

    private String orderId;

    private String createTimeStart;

    private String createTimeEnd;

}
