package com.smc.smccloud.model.delivery;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2024/3/29 10:44
 * @Descripton TODO
 */
@Data
public class CanUseQtyAndIsBinVO {

    private String modelNo;

    private Integer qty;

    // 是否BIN品
    private boolean isBin = false;

    // 可用库存
    private Integer canuseQuantity = 0;
}
