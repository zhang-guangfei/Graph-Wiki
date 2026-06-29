package com.smc.smccloud.model;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/8/4 19:37
 * @Descripton TODO
 */
@Data
public class CalTransDayEntity {
    private String salesBranchId;
    private String warehouseCode;
    private String priority;
    private Integer deliveryDay;
    private String warehouseType;
}
