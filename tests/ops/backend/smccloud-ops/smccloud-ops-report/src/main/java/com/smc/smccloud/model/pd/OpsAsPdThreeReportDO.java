package com.smc.smccloud.model.pd;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author smc
 * @since 2023-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_pd_three_report")
public class OpsAsPdThreeReportDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 盘点批次号
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
    private int balanceQty;

    /**
     * 样品未结转
     */
    @TableField("sample_no_over")
    private int sampleNoOver;

    /**
     * 已出库未推财务
     */
    @TableField("shipped_no_finance")
    private int shippedNoFinance;

    /**
     * 财务补偿
     */
    @TableField("finance_compensate")
    private int financeCompensate;

    /**
     * ops调拨在途
     */
    @TableField("ops_tran_onway")
    private int opsTranOnway;

    /**
     * ops制造在途
     */
    @TableField("ops_manu_onway")
    private int opsManuOnway;

    /**
     * ops退货在途
     */
    @TableField("ops_return_onway")
    private int opsReturnOnway;

    /**
     * ops采购到货未入
     */
    @TableField("ops_purchase_no_in")
    private int opsPurchaseNoIn;

    /**
     * ops调拨到货未入
     */
    @TableField("ops_tran_no_in")
    private int opsTranNoIn;

    /**
     * ops退货到货未入
     */
    @TableField("ops_return_no_in")
    private int opsReturnNoIn;

    /**
     * ops库存数据
     */
    @TableField("ops_inventroy_qty")
    private int opsInventroyQty;

    /**
     * ops合计库存(业务账本数)
     */
    @TableField("ops_sum_qty")
    private int opsSumQty;

    /**
     * wms线下借库数
     */
    @TableField("wms_borrow")
    private int wmsBorrow;

    /**
     * wms调拨在途
     */
    @TableField("wms_tran_onway")
    private int wmsTranOnway;

    /**
     * wms制造在途
     */
    @TableField("wms_manu_onway")
    private int wmsManuOnway;

    /**
     * wms退货在途
     */
    @TableField("wms_return_onway")
    private int wmsReturnOnway;

    /**
     * wms采购到货未入
     */
    @TableField("wms_purchase_no_in")
    private int wmsPurchaseNoIn;

    /**
     * wms到货未入
     */
    @TableField("wms_tran_no_in")
    private int wmsTranNoIn;

    @TableField("wms_return_no_in")
    private int wmsReturnNoIn;

    /**
     * wms账簿数
     */
    @TableField("wms_inventory_qty")
    private int wmsInventoryQty;

    /**
     * wms合计库存(物流盘点)
     */
    @TableField("wms_sum_qty")
    private int wmsSumQty;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 物流-财务
     */
    @TableField("wl_cw")
    private int wlCw;

    /**
     * 业务-财务
     */
    @TableField("yw_cw")
    private int ywCw;

    /**
     * 物流-业务
     */
    @TableField("wl_yw")
    private int wlYw;

    /**
     * 北京业务数
     */
    @TableField("kbj_yw_qty")
    private int kbjYwQty;

    /**
     * 上海业务数
     */
    @TableField("ksh_yw_qty")
    private int kshYwQty;

    /**
     * 广州业务数
     */
    @TableField("kgz_yw_qty")
    private int kgzYwQty;

    /**
     * 常州业务数
     */
    @TableField("scz_yw_qty")
    private int sczYwQty;

    /**
     * 武汉业务数
     */
    @TableField("swh_yw_qty")
    private int swhYwQty;

    /**
     * 济南业务数
     */
    @TableField("sjn_yw_qty")
    private int sjnYwQty;

    /**
     * 沈阳业务数
     */
    @TableField("ssy_yw_qty")
    private int ssyYwQty;

    /**
     * 太原业务数
     */
    @TableField("sty_yw_qty")
    private int styYwQty;

    /**
     * 潍坊业务数
     */
    @TableField("swf_yw_qty")
    private int swfYwQty;

    /**
     * 成都业务数
     */
    @TableField("scd_yw_qty")
    private int scdYwQty;

    /**
     * 寄售业务数
     */
    @TableField("wt_yw_qty")
    private int wtYwQty;

    /**
     * 北京盘点数
     */
    @TableField("kbj_qty")
    private int kbjQty;

    /**
     * 上海盘点数
     */
    @TableField("ksh_qty")
    private int kshQty;

    /**
     * 广州盘点数
     */
    @TableField("kgz_qty")
    private int kgzQty;

    /**
     * 常州盘点数
     */
    @TableField("scz_qty")
    private int sczQty;

    /**
     * 武汉盘点数
     */
    @TableField("swh_qty")
    private int swhQty;

    /**
     * 济南盘点数
     */
    @TableField("sjn_qty")
    private int sjnQty;

    /**
     * 太原盘点数
     */
    @TableField("sty_qty")
    private int styQty;

    /**
     * 潍坊盘点数
     */
    @TableField("swf_qty")
    private int swfQty;

    /**
     * 成都盘点数
     */
    @TableField("scd_qty")
    private int scdQty;

    /**
     * 寄售盘点数
     */
    @TableField("wt_qty")
    private int wtQty;

    /**
     * 北京物流-业务
     */
    @TableField("kbjwl_yw_qty")
    private int kbjwlYwQty;

    /**
     * 上海物流-业务
     */
    @TableField("kshwl_yw_qty")
    private int kshwlYwQty;

    /**
     * 广州物流-业务
     */
    @TableField("kgzwl_yw_qty")
    private int kgzwlYwQty;

    /**
     * 常州物流-业务
     */
    @TableField("sczwl_yw_qty")
    private int sczwlYwQty;

    /**
     * 武汉物流-业务
     */
    @TableField("swhwl_yw_qty")
    private int swhwlYwQty;

    /**
     * 济南物流-业务
     */
    @TableField("sjnwl_yw_qty")
    private int sjnwlYwQty;

    /**
     * 沈阳物流-业务
     */
    @TableField("ssywl_yw_qty")
    private int ssywlYwQty;

    /**
     * 太原物流-业务
     */
    @TableField("stywl_yw_qty")
    private int stywlYwQty;

    /**
     * 潍坊物流-业务
     */
    @TableField("swfwl_yw_qty")
    private int swfwlYwQty;

    /**
     * 成都物流-业务
     */
    @TableField("scdwl_yw_qty")
    private int scdwlYwQty;

    /**
     * 寄售物流-业务
     */
    @TableField("wtwl_yw_qty")
    private int wtwlYwQty;

    /**
     * e价
     */
    @TableField("eprice")
    private BigDecimal eprice;

    /**
     * 沈阳盘点数
     */
    @TableField("ssy_qty")
    private int ssyQty;

    /**
     * 天津业务数
     */
    @TableField("ktj_yw_qty")
    private int ktjYwQty;

    /**
     * 天津盘点数
     */
    @TableField("ktj_qty")
    private int ktjQty;

    /**
     * 天津物流-业务
     */
    @TableField("ktjwl_yw_qty")
    private int ktjwlYwQty;

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

    // 库存期初
    @TableField(exist = false)
    private int lastInitialQuantity;
}
