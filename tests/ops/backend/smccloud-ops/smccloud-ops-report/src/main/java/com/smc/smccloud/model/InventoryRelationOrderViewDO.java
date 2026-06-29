package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("inventory_relation_order_view")
public class InventoryRelationOrderViewDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ass_num")
    private Integer assNum;

    @TableField("order_id")
    private String orderId;

    @TableField("do_qty")
    private Integer doQty;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("dlv_entire")
    private String dlvEntire;

    @TableField("sales_info_no")
    private String salesInfoNo;

    @TableField("inv_qty")
    private Integer invQty;

    @TableField("dept_code")
    private String deptCode;

    @TableField("inv_pre_qty")
    private Integer invPreQty;

    @TableField("order_item")
    private String orderItem;

    @TableField("ppl")
    private String ppl;

    @TableField("customer")
    private String customer;

    @TableField("gather_warehouse_code")
    private String gatherWarehouseCode;

    @TableField("warehouse_type")
    private String warehouseType;

    @TableField("inventory_status")
    private String inventoryStatus;

    @TableField("user_no")
    private String userNo;

    @TableField("wl_date")
    private String wlDate;

    @TableField("customer_no")
    private String customerNo;

    @TableField("project_code")
    private String projectCode;

    @TableField("do_use_qty")
    private Integer doUseQty;

    @TableField("inventory_type_code")
    private String inventoryTypeCode;

    @TableField("do_source")
    private String doSource;

    @TableField("dept_no")
    private String deptNo;

    @TableField("modelno")
    private String modelno;

    @TableField("group_customer_no")
    private String groupCustomerNo;

    @TableField("do_type")
    private String doType;

    @TableField("inventory_id")
    private Long inventoryId;

    @TableField("hope_date")
    private String hopeDate;

    @TableField("num")
    private Integer num;

    @TableField("do_id")
    private String doId;


}
