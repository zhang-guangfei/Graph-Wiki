package com.smc.smccloud.model.trans;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "trans_order")
public class TransOrderDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "trans_no")
    private String transNo;

    @TableField(value = "trans_type")
    private Integer transType;

    @TableField(value = "item_no")
    private Integer itemNo;

    @TableField(value = "model_no")
    private String modelNo;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "from_no")
    private String fromNo;

    @TableField(value = "from_id")
    private Long fromId;

    @TableField(value = "from_type")
    private Integer fromType;

    @TableField(value = "from_inventory_property_id")
    private Long fromInventoryPropertyId;

    @TableField(value = "from_warehouse_code")
    private String fromWarehouseCode;

    @TableField(value = "from_inventory_type_code")
    private String fromInventoryTypeCode;

    @TableField(value = "from_ppl")
    private String fromPpl;

    @TableField(value = "from_project_code")
    private String fromProjectCode;

    @TableField(value = "from_group_customer_no")
    private String fromGroupCustomerNo;

    @TableField(value = "from_sales_info_no")
    private String fromSalesInfoNo;

    @TableField(value = "from_customer_no")
    private String fromCustomerNo;

    @TableField(value = "to_inventory_property_id")
    private Long toInventoryPropertyId;

    @TableField(value = "to_warehouse_code")
    private String toWarehouseCode;

    @TableField(value = "to_inventory_type_code")
    private String toInventoryTypeCode;

    @TableField(value = "to_ppl")
    private String toPpl;

    @TableField(value = "to_project_code")
    private String toProjectCode;

    @TableField(value = "to_group_customer_no")
    private String toGroupCustomerNo;

    @TableField(value = "to_sales_info_no")
    private String toSalesInfoNo;

    @TableField(value = "to_customer_no")
    private String toCustomerNo;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "create_user")
    private String createUser;

    @TableField(value = "update_user")
    private String updateUser;

    @TableField(value = "wms_dlv_date")
    private Date wmsDlvDate;//指定物流交货期

    @TableField("finish_time")
    private Date finishTime;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("ship_qty")
    private Integer shipQty;

    @TableField("ship_date")
    private Date shipDate;
}
