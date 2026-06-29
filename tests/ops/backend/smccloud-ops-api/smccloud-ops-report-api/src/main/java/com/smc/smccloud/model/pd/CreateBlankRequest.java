package com.smc.smccloud.model.pd;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/6/15 15:08
 * @Descripton TODO
 */
@Data
public class CreateBlankRequest {
    private int masterWarehouse;
    private int subWarehouse;
    private int wtWarehouse;
    private int arriveNotINWarehouse;
}
