package com.smc.smccloud.model.binorder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bin_order_detail")
public class BinOrderDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 最终订货数量
     */
    @TableField("confirm_qty")
    private Integer confirmQty;

    /**
     * 采购数量
     */
    @TableField("po_qty")
    private Integer poQty;

    /**
     * 调拨数量
     */
    @TableField("trans_qty")
    private Integer transQty;

    /**
     * 0-采购填(K,B,U),1-调拨-9-拆分数量
     */
    @TableField("order_type")
    private String orderType;

    /**
     * 订向库房
     */
    @TableField("order_stock_code")
    private String orderStockCode;

    /**
     * 通用在库数量
     */
    @TableField("stock_qty")
    private Integer stockQty;


    /**
     * 自动化bin数量
     */
    @TableField("qtybin_all")
    private Integer qtybinAll;

    /**
     * 自动化bin数
     */
    @TableField("bincell_all")
    private Integer bincellAll;

    /**
     * 与bin应该备库的数量差
     */
    @TableField("bin_diff_qty")
    private Integer binDiffQty;

    /**
     * 相差的bin个数
     */
    @TableField("bin_diff_num")
    private Double binDiffNum;


    /**
     * 最小包装单位
     */
    @TableField("min_pack_qty")
    private Integer minPackQty;

    /**
     * 标准交货期
     */
    @TableField("std_lead_time")
    private Integer stdLeadTime;

    /**
     * 建议备库月数
     */
    @TableField("recomm_months")
    private Integer recommMonths;


    /**
     * 型号分类,气缸、阀、小物等
     */
    @TableField("model_class")
    private String modelClass;

    @TableField("eprice")
    private BigDecimal eprice;

    @TableField("update_time")
    private Date updateTime;

    @TableField("box_qty")
    private Integer boxQty;

    @TableField("trans_type")
    private String transType;



    @TableField("remark")
    private String remark;

    @TableField("months")
    private Double months;

    @TableField("order_no")
    private String orderNo;

    @TableField("update_user")
    private String updateUser;

    /**
     * 月用量
     */
    @TableField("mean")
    private Double mean;

    /**
     *  频率
     */
    @TableField("freq")
    private Integer freq;


    @TableField("bin_class")
    private String binClass;


    @TableField("calc_type")
    private String calcType;

    @TableField("qtybin")
    private Integer qtybin;



    @TableField("ording_qty")
    private Integer ordingQty;

    @TableField("app_id")
    private Integer appId;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField("stock_months")
    private Double stockMonths;



    @TableField("status")
    private Integer status;

    @TableField("order_qty")
    private Integer orderQty;
    @TableField("bincell")
    private Integer bincell;



    @TableField("model_no")
    private String modelNo;

    @TableField("calc_id")
    private Long calcId;

    @TableField("box_no")
    private String boxNo;

    @TableField("dlv_date")
    private Date dlvDate;

    @TableField("weight")
    private Double weight;

    @TableField("spec_stock_qty")
    private Integer specStockQty;


    @TableField("spec_ording_qty")
    private Integer specOrdingQty;

    @TableField("warehouse_code")
    private String warehouseCode;


    @TableField("create_time")
    private Date createTime;



    @TableField("supplier_code")
    private String supplierCode;

    @TableField("create_user")
    private String createUser;

    @TableField("customers")
    private Integer customers;

    @TableField("po_type")
    private String poType;

    @TableField("direct_purchase")
    private Integer directpurchase;

    @TableField("property_id")
    private Long propertyId;

    @TableField(value = "ppl")
    private String ppl;  //ppl代码

    @TableField(value = "project_no")
    private String projectNo;  //项目号

    @TableField(value = "inventory_type_code")
    private String inventoryTypeCode;  //库存类型代码

    @TableField(value = "group_customer_no")
    private String groupCustomerNo;  //客户群号

    @TableField(value = "customerNo")
    private String customerNo;  //客户代码

    @TableField(value = "stock_qty_all")
    private Integer stockQtyAll;  //总在库

    @TableField(value = "mean_all")
    private Integer meanAll;  //总月均

    @TableField(value = "stock_months_all")
    private BigDecimal stockMonthsAll;  //总月均

    @TableField(value = "main_supplier_code")
    private String mainSupplierCode;  //原产地

    @TableField(value = "set_order_type")
    private String setOrderType;  //订单类型

    @TableField(value = "last_dlvdate")
    private Date lastDlvdate;  //最后交货期


    @TableField(value = "pre_qty")
    private Integer preQty;  //被预约数

    @TableField(value = "replenishment_method")
    private Integer replenishmentMethod;

    @TableField(value = "center_flag")
    private Integer centerFlag;

    @TableField(value = "center_warehouse")
    private String centerWarehouse;

    @TableField(value = "client")
    private String client;

    //json格式字符串
    @TableField(value = "excess_qty")
    private String excessQty;
    //删除字段
    //@TableField("end_date")
    //private Date endDate;

    //@TableField(value = "app_item_no")
    //private Integer appItemNo;  //最后交货期
    //
    //@TableField("upd_no")
    //private Integer updNo;
    ////有用vo
    //@TableField("stDevVal")
    //private Double stdevval;
    //
    //@TableField("ecode")
    //private String ecode;
    //
    //
    //@TableField("order_point")
    //private Integer orderPoint;
    //
    ////有用vo
    //@TableField("curMthSalesQty")
    //private Integer curmthsalesqty;
    //@TableField("avg_diff")
    //private Double avgDiff;
    //@TableField("model_spec_flag")
    //private Integer modelSpecFlag;
    //
    ///**
    // * 变动系数
    // */
    //@TableField("diff_val")
    //private Double diffVal;
    ///**
    // * 最小起订量
    // */
    ////有用
    //@TableField("min_order_qty")
    //private Integer minOrderQty;
    ///**
    // * 型号属性-1:标准品, 2:简易特注品,3:一般特注品, 4: 集成型号, 5:维修费
    // */
    //@TableField("model_type")
    //private String modelType;
    //
    ////有用
    //@TableField("max_stock_qty")
    //private Integer maxStockQty;
    //@TableField("max_dlvdate")
    //private Date maxDlvdate;
    //@TableField("avg_val")
    //private Double avgVal;
}
