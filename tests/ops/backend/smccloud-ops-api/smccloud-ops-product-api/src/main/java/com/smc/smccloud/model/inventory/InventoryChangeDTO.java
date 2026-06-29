package com.smc.smccloud.model.inventory;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Author: B90034
 * Date: 2022-02-24 09:02
 * Description: 库存变更实体
 */
@Data
public class InventoryChangeDTO {

    /**
     * 1-入库
     * 2-预约库存
     * 3-扣库存
     * 4-取消预约库存
     */
    @NotNull
    private Integer changeType;

    /**
     * 变更数量
     */
    @NotNull
    private Integer quantity;

    /**
     * 型号
     */
    @NotBlank
    private String modelNo;

    /**
     * 仓库代码
     */
    @NotBlank
    private String warehouseCode;

    /**
     * inventoryPropertyId
     */
    @NotNull
    private Long propertyId;

    /**
     * 库存状态[正常在库:N, 限定:X]
     */
    @NotBlank
    private String inventoryStatus;

    /**
     * 质量状态: 0-良品, 1-不良品, 2-未检品
     */
    @NotBlank
    private String qaStatus;

    private String optUser;

    private String fromNo;

    private Integer fromId;

    private String fromType;
}
