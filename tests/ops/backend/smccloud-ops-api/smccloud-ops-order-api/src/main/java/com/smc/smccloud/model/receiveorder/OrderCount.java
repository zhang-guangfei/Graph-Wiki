package com.smc.smccloud.model.receiveorder;

import lombok.Data;

@Data
public class OrderCount {
    private Integer orderSalesCount;
    private Integer processingOrderCount;
    private Integer notHandleOrderCount;
    private Integer exceptionHandCount;
}
