package com.smc.smccloud.model.pd;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_pd_three_report_ware")
public class OpsAsPdThreeReportWareDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 盘点批次
     */
    @TableField("pd_batch_no")
    private String pdBatchNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 财务结存数量
     */
    @TableField("balance_qty")
    private Integer balanceQty;

    /**
     * 仓库代码
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField(exist = false)
    private String warehosueCodeName;

    /**
     * 样品未结转
     */
    @TableField("sample_no_over")
    private Integer sampleNoOver;

    /**
     * 已发货未推财务
     */
    @TableField("shipped_no_finance")
    private Integer shippedNoFinance;

    /**
     * 财务补偿数据
     */
    @TableField("finance_compensate")
    private Integer financeCompensate;

    /**
     * ops调拨在途
     */
    @TableField("ops_tran_onway")
    private Integer opsTranOnway;

    /**
     * ops制造在途
     */
    @TableField("ops_manu_onway")
    private Integer opsManuOnway;

    /**
     * ops退货在途
     */
    @TableField("ops_return_onway")
    private Integer opsReturnOnway;

    /**
     * ops采购未入
     */
    @TableField("ops_purchase_no_in")
    private Integer opsPurchaseNoIn;

    /**
     * ops调拨未入
     */
    @TableField("ops_tran_no_in")
    private Integer opsTranNoIn;

    /**
     * ops退货未入
     */
    @TableField("ops_return_no_in")
    private Integer opsReturnNoIn;

    /**
     * ops库存数量
     */
    @TableField("ops_inventroy_qty")
    private Integer opsInventroyQty;

    /**
     * ops合计数量
     */
    @TableField("ops_sum_qty")
    private Integer opsSumQty;

    @TableField("wms_borrow")
    private Integer wmsBorrow;

    @TableField("wms_tran_onway")
    private Integer wmsTranOnway;

    @TableField("wms_manu_onway")
    private Integer wmsManuOnway;

    @TableField("wms_return_onway")
    private Integer wmsReturnOnway;

    @TableField("wms_purchase_no_in")
    private Integer wmsPurchaseNoIn;

    @TableField("wms_tran_no_in")
    private Integer wmsTranNoIn;

    @TableField("wms_return_no_in")
    private Integer wmsReturnNoIn;

    /**
     * WMS实盘数量
     */
    @TableField("wms_inventory_act")
    private Integer wmsInventoryAct;

    /**
     * WMS过渡库位
     */
    @TableField("wms_inventory_tra")
    private Integer wmsInventoryTra;

    /**
     * WMS寄售数量
     */
    @TableField("wms_inventory_consignment")
    private Integer wmsInventoryConsignment;

    /**
     * WMS集约货位
     */
    @TableField("wms_inventory_intensive")
    private Integer wmsInventoryIntensive;

    @TableField("wms_sum_qty")
    private Integer wmsSumQty;

    @TableField("last_initial_quantity")
    private int lastInitialQuantity;

    // wms组换在途
    @TableField("wms_zh_zt")
    private int wmsZhZt;
    // wms组换到货未入
    @TableField("wms_zh_dhwr")
    private int wmsZhDhwr;
    // ops组换在途
    @TableField("ops_zh_zt")
    private int opsZhZt;
    // ops组换到货未入
    @TableField("ops_zh_dhwr")
    private int opsZhDhwr;

    @TableField("eprice")
    private BigDecimal eprice;

}
