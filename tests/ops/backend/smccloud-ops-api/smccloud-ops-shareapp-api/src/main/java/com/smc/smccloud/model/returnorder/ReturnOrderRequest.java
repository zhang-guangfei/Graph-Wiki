package com.smc.smccloud.model.returnorder;

import lombok.Data;

@Data
public class ReturnOrderRequest {

    private String applyNo;

    private String orderNo;

    private String modelNo;

    private Integer[] status;

    // 申请时间
    private String applyTimeStart;
    private String applyTimeEnd;

    // 收货时间
    private String receiveTimeStart;
    private String receiveTimeEnd;

    // 入库时间
    private String inTimeStart;
    private String inTimeEnd;

    private String requestWarehouseCode;

}
