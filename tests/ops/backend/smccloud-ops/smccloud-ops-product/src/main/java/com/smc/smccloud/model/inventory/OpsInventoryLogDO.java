package com.smc.smccloud.model.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "ops_inventory_log")
public class OpsInventoryLogDO {

    @TableId(value = "inventory_log_id", type = IdType.AUTO)
    private Long inventoryLogId;

    @TableField(value = "inventory_id")
    private Long inventoryId;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "voucher_code")
    private String voucherCode;

    @TableField(value = "voucher_type")
    private String voucherType;

    @TableField(value = "order_no")
    private String orderNo;

    @TableField(value = "item_no")
    private Integer itemNo;

    @TableField(value = "modelno")
    private String modelno;

    @TableField(value = "invoice_no")
    private String invoiceNo;

    @TableField(value = "warehouse_code")
    private String warehouseCode;

    @TableField(value = "version")
    private Integer version;

    @TableField(value = "delflag")
    private Integer delflag;

    @TableField(value = "cre_time")
    private Date creTime;

    @TableField(value = "creator")
    private String creator;

    @TableField(value = "modify_time")
    private Date modifyTime;

    @TableField(value = "modifier")
    private String modifier;

    @TableField(value = "type")
    private Integer type;

}
