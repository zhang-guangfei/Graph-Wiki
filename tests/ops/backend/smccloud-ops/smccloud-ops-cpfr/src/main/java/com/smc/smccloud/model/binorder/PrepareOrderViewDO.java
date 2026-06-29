package com.smc.smccloud.model.binorder;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author edp02 @Date 2022/7/16 9:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("prepare_order_view")
public class PrepareOrderViewDO implements Serializable {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "id")
    //private Long id;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "order_item")
    private String orderItem;

    @TableField(value = "split_no")
    private Integer splitNo;

    @TableField(value = "modelNo")
    private String modelNo;

    @TableField(value = "split_type")
    private String splitType;

    //@TableField(value = "item_no")
    //private Integer itemNo;

    @TableField(value = "qty")
    private Integer qty;

    @TableField(value = "qty_in")
    private Integer qtyIn;

    @TableField(value = "qty_out")
    private Integer qtyOut;
    //
    //@TableField(value = "status")
    //private String status;
    //
    //@TableField(value = "status_desc")
    //private String statusDesc;
    //
    //@TableField(value = "status_place")
    //private String statusPlace;
    //
    //@TableField(value = "stock_type")
    //private String stockType;
    //
    //@TableField(value = "stock_code")
    //private String stockCode;
    //
    //@TableField(value = "inventory_type_code")
    //private String inventoryTypeCode;
    //
    //@TableField(value = "associate_no")
    //private String associateNo;
    //
    //@TableField(value = "associate_no_item")
    //private Integer associateNoItem;
    //
    //@TableField(value = "handover_time")
    //private Date handoverTime;
    //
    //@TableField(value = "delivery_warehouse_code")
    //private String deliveryWarehouseCode;
    //
    //@TableField(value = "delflag")
    //private Integer delflag;
    //
    //@TableField(value = "version")
    //private Integer version;
    //
    //@TableField(value = "associate_split_no")
    //private Integer associateSplitNo;

    @TableField(value = "requestWarehouseId")
    private String requestWarehouseId;

    @TableField(value = "purchaseType")
    private String purchaseType;

    @TableField(value = "orderDate")
    private Date orderDate;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "qtyImport")
    private Integer qtyImport;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "stateCode")
    private String stateCode;


}
