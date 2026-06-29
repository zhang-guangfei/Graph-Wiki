package com.smc.smccloud.model.inventory;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-01-25   库存供应商
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InventorySupplierVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 在库数
     */
    private Integer quantity;

    /**
     * 可组装数量
     */
    private Integer quantityAssembly;

    /**
     * 可生产数量
     */
    private Integer quantityProduce;

    @NotEmpty
    private String supplierId;

    @NotEmpty
    private String modelNo;

    private Date updateTime;

    private String inventoryClass;//在库类别 WH F1 F2


}
