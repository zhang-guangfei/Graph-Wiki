package com.smc.smccloud.model.prestock;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2024/12/6 13:06
 * @Descripton TODO
 */
@Data
public class FindAvgQtyRecordsVO {
    private Long inventoryLogId;
    private int avgQty;
    private String orderNo;
}
