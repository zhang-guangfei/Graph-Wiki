package com.smc.smccloud.model.inventory;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @TableId(value = "supplierId")
    private String supplierId;

    @TableField("modelNo")
    private String modelNo;

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

    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("quantityPrepare")
    private Integer quantityPrepare;

    @TableField("binflag")
    private Integer binflag;

    @TableField("inventory_class")
    private String inventoryClass;//在库类别 WH F1 F2

}
