package com.smc.smccloud.model.Inventory;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("inventory_supplier")
public class InventorySupplierDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 在库数
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 可组装数量
     */
    @TableField("quantityAssembly")
    private Integer quantityAssembly;

    /**
     * 可生产数量
     */
    @TableField("quantityProduce")
    private Integer quantityProduce;

    @TableId(value = "supplierId")
    private String supplierId;

    @TableField("modelNo")
    private String modelNo;

    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
