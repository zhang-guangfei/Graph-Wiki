package com.smc.smccloud.model.borrowstock;

import lombok.Data;

import java.util.Date;

@Data
public class BrwDetailVO {

    private String modelNo;

    private Integer quantity;

    private Integer optStatus;

    private String remark;

    private Date shipDate;

    private Integer returnQty;

    /**
     * 已出库数量
     */
    private Integer expQty;

    private Integer itemId;

    private String warehouseCode;
}
