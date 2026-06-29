package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2024-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("trans_order")
public class TransOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 1-调库 2组换 3 采购
     */
    @TableField("trans_type")
    private Integer transType;

    /**
     * 0-初始化 ，	1-出库中 ，	2-出库中，	3-已出库，	4-到货待处理，	5-到货确认，	6-已入库 7采购中 9 取消
     */
    @TableField("status")
    private Integer status;

    /**
     * 来源单号
     */
    @TableField("from_no")
    private String fromNo;

    /**
     * 来源id
     */
    @TableField("from_id")
    private Long fromId;

    /**
     * 来源类型 1-bin补库，2-调库申请，3先行在库申请，4委托在库，5先行在库预决算
     */
    @TableField("from_type")
    private Integer fromType;

    /**
     * 库存分类：顾客在库，战略在库、通用在库
     */
    @TableField("from_inventory_type_code")
    private String fromInventoryTypeCode;

    /**
     * PPL代码
     */
    @TableField("from_ppl")
    private String fromPpl;

    /**
     * 项目号
     */
    @TableField("from_project_code")
    private String fromProjectCode;

    /**
     * 客户群代码
     */
    @TableField("from_group_customer_no")
    private String fromGroupCustomerNo;

    /**
     * 营业情报号
     */
    @TableField("from_sales_info_no")
    private String fromSalesInfoNo;

    /**
     * 库存分类：顾客在库，战略在库、通用在库
     */
    @TableField("to_inventory_type_code")
    private String toInventoryTypeCode;

    /**
     * PPL代码
     */
    @TableField("to_ppl")
    private String toPpl;

    /**
     * 项目号
     */
    @TableField("to_project_code")
    private String toProjectCode;

    /**
     * 客户群代码
     */
    @TableField("to_group_customer_no")
    private String toGroupCustomerNo;

    /**
     * 营业情报号
     */
    @TableField("to_sales_info_no")
    private String toSalesInfoNo;

    /**
     * 入库数量
     */
    @TableField("in_quantity")
    private Integer inQuantity;

    /**
     * 调拨单希望物流发货日期
     */
    @TableField("wms_dlv_date")
    private Date wmsDlvDate;

    /**
     * 完成时间
     */
    @TableField("finish_time")
    private Date finishTime;

    /**
     * 发票id
     */
    @TableField("invoice_id")
    private Long invoiceId;

    /**
     * 是否必须回到目标仓库	1：异仓调拨，必须回到目标仓	0：同仓调库	
     */
    @TableField("trans_flag")
    private Boolean transFlag;

    /**
     * 调出库存ID
     */
    @TableField("from_inventory_id")
    private Long fromInventoryId;

    /**
     * 来源关联单号
     */
    @TableField("from_associate_no")
    private String fromAssociateNo;

    @TableField("ship_date")
    private Date shipDate;

    @TableField("to_inventory_property_id")
    private Long toInventoryPropertyId;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("from_inventory_property_id")
    private Long fromInventoryPropertyId;

    @TableField("model_no")
    private String modelNo;

    @TableField("to_customer_no")
    private String toCustomerNo;

    @TableField("quantity")
    private Integer quantity;

    @TableField("from_associate_no_item")
    private Integer fromAssociateNoItem;

    @TableField("trans_no")
    private String transNo;

    @TableField("to_warehouse_code")
    private String toWarehouseCode;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("item_no")
    private Integer itemNo;

    @TableField("from_associate_no_split")
    private Integer fromAssociateNoSplit;

    @TableField("from_customer_no")
    private String fromCustomerNo;

    @TableField("create_user")
    private String createUser;

    @TableField("from_warehouse_code")
    private String fromWarehouseCode;

    @TableField("ship_qty")
    private Integer shipQty;


}
