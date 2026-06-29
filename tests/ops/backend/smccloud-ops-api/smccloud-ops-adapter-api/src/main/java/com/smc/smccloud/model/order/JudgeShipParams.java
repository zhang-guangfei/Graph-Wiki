package com.smc.smccloud.model.order;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2024/12/12 8:44
 * @Descripton TODO
 */
@Data
public class JudgeShipParams {
    private String modelNo;
    private Integer qty;
    private Boolean isShip;
}
