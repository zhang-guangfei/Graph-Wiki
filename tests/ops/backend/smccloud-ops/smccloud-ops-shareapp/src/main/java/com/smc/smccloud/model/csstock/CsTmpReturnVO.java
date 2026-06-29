package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * @author edp04
 * @title: CsTmpReturnVO
 * @date 2022/05/30 13:52
 */
@Data
public class CsTmpReturnVO {

    private String deptNo;

    private String agentNo;

    private Integer models;

    private String warehouseCode;

    private Integer returnQty;

    private Integer overTimeQty;
}
