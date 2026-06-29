package com.smc.smccloud.model.sampleorder;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/11/9 16:32
 * @Descripton TODO
 */
@Data
public class CheckRcvQtyVO {

    private String orderFno;

    private int applyBalQty;

    private int orderQty;

    private boolean balFlag;

    private Long sampleBalId;

}
