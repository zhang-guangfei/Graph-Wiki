package com.smc.smccloud.model.order;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/10/24 15:10
 * @Descripton TODO
 */
@Data
public class DlvEntireVO {

    private String orderNo;

    // 是否随发分批
    private Boolean isDlvEntire;

    private String customerNo;

    private String userNo;

    private String dlvEntire;
}
