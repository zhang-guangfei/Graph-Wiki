package com.smc.smccloud.model.pd;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lyc
 * @Date 2024/11/16 9:18
 * @Descripton TODO
 */
@Data
public class OpsAsPdThreeReportWareExportVO {
    /**
     * 盘点批次
     */
    @ExcelProperty("批次日期")
    private String pdBatchNo;

    /**
     * 仓库代码
     */
    @ExcelIgnore
    private String warehouseCode;

    @ExcelProperty("仓库名称")
    private String warehosueCodeName;

    /**
     * 型号
     */
    @ExcelProperty("型号")
    private String modelNo;

    /**
     * 财务结存数量
     */
    @ExcelProperty("财务结存")
    private Integer balanceQty;

    /**
     * 样品未结转
     */
    @ExcelProperty("样品未结转")
    private Integer sampleNoOver;

    /**
     * 已发货未推财务
     */
    @ExcelProperty("已发货未推财务")
    private Integer shippedNoFinance;

    /**
     * 财务补偿数据
     */
    @ExcelProperty("财务补偿数据")
    private Integer financeCompensate;

    /**
     * ops调拨在途
     */
    @ExcelProperty("ops调拨在途")
    private Integer opsTranOnway;

    /**
     * ops制造在途
     */
    @ExcelProperty("ops制造在途")
    private Integer opsManuOnway;

    /**
     * ops退货在途
     */
    @ExcelProperty("ops退货在途")
    private Integer opsReturnOnway;

    /**
     * ops采购未入
     */
    @ExcelProperty("ops采购未入")
    private Integer opsPurchaseNoIn;

    /**
     * ops调拨未入
     */
    @ExcelProperty("ops调拨未入")
    private Integer opsTranNoIn;

    /**
     * ops退货未入
     */
    @ExcelProperty("ops退货未入")
    private Integer opsReturnNoIn;

    /**
     * ops库存数量
     */
    @ExcelProperty("ops库存数量")
    private Integer opsInventroyQty;

    // ops组换在途
    @ExcelProperty("ops组换在途")
    private int opsZhZt;

    // ops组换到货未入
    @ExcelProperty("ops组换到货未入")
    private int opsZhDhwr;

    /**
     * ops合计数量
     */
    @ExcelProperty("ops合计数量")
    private Integer opsSumQty;

    @ExcelProperty("wms线下借库")
    private Integer wmsBorrow;

    @ExcelProperty("wms调拨在途")
    private Integer wmsTranOnway;

    @ExcelProperty("wms制造在途")
    private Integer wmsManuOnway;

    @ExcelProperty("wms退货在途")
    private Integer wmsReturnOnway;

    // wms组换在途
    @ExcelProperty("wms组换在途")
    private int wmsZhZt;

    @ExcelProperty("wms采购未入")
    private Integer wmsPurchaseNoIn;

    @ExcelProperty("wms调拨未入")
    private Integer wmsTranNoIn;

    @ExcelProperty("wms退货未入")
    private Integer wmsReturnNoIn;

    // wms组换到货未入
    @ExcelProperty("wms组换到货未入")
    private int wmsZhDhwr;

    /**
     * wms集约待交接
     */
    @ExcelProperty("wms集约待交接")
    private Integer wmsInventoryIntensive;

    /**
     * WMS实盘数量
     */
    @ExcelProperty("wms库存数量")
    private Integer wmsInventoryAct;

    /**
     * WMS合计数量
     */
    @ExcelProperty("wms合计数量")
    private Integer wmsSumQty;

    @ExcelProperty("上期盘点期初数")
    private int lastInitialQuantity;

    @ExcelProperty("E价")
    private BigDecimal eprice;


}
