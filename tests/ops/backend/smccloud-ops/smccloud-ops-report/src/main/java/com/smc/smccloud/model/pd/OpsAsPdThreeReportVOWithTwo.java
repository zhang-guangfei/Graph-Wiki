package com.smc.smccloud.model.pd;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lyc
 * @Date 2023/12/20 14:00
 * @Descripton TODO
 */
@Data
public class OpsAsPdThreeReportVOWithTwo {
    private static final long serialVersionUID = 1L;


    /**
     * 型号
     */
    @ExcelProperty("型号")
    private String modelNo;

    /**
     * 备注
     */
    @ExcelProperty("差异确认")
    private String remark;

    /**
     * 财务结存数量
     */
    @ExcelIgnore
    @ExcelProperty("财务结存")
    private int balanceQty;

    /**
     * wms合计库存(物流盘点)
     */
    @ExcelProperty("物流盘点")
    private int wmsSumQty;

    /**
     * ops合计库存(业务账本数)
     */
    @ExcelProperty("业务账本数")
    private int opsSumQty;


    /**
     * 样品未结转
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("样品未结转")
    private int sampleNoOver;

    /**
     * 已出库未推财务
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("已出库未推财务")
    private int shippedNoFinance;

    /**
     * 财务补偿
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("财务补偿")
    private int financeCompensate;

    /**
     * wms线下借库数
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("借库数")
    private int wmsBorrow;


    /**
     * ops调拨在途
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("ops调拨在途")
    private int opsTranOnway;

    /**
     * ops制造在途
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("ops制造在途")
    private int opsManuOnway;


    /**
     * ops库存数据
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("ops库存数据")
    private int opsInventroyQty;

    /**
     * ops采购到货未入
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("ops采购到货未入")
    private int opsPurchaseNoIn;


    /**
     * ops退货到货未入
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("ops退货到货未入")
    private int opsReturnNoIn;

    /**
     * ops调拨到货未入
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("ops调拨到货未入")
    private int opsTranNoIn;

    /**
     * ops退货在途
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("ops退货在途")
    private int opsReturnOnway;


    /**
     * wms到货未入
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("wms到货未入")
    private int wmsTranNoIn;


    /**
     * wms调拨在途
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("wms调拨在途")
    private int wmsTranOnway;


    /**
     * wms退货在途
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("wms退货在途")
    private int wmsReturnOnway;

    /**
     * wms制造在途
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("wms制造在途")
    private int wmsManuOnway;

    /**
     * wms采购到货未入
     */
    @ExcelIgnore
    private int wmsPurchaseNoIn;

    @ExcelIgnore
    private int wmsReturnNoIn;

    /**
     * wms账簿数
     */
    @ExcelIgnore
    private int wmsInventoryQty;


    /**
     * 物流-财务
     */
    @HeadStyle(fillForegroundColor = 52)
    @ExcelProperty("物流-财务")
    private int wlCw;

    /**
     * 业务-财务
     */
    @HeadStyle(fillForegroundColor = 52)
    @ExcelProperty("业务-财务")
    private int ywCw;

    /**
     * 物流-业务
     */
    @HeadStyle(fillForegroundColor = 52)
    @ExcelProperty("物流-业务")
    private int wlYw;

    /**
     * 北京业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("北京业务数")
    private int kbjYwQty;

    /**
     * 上海业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("上海业务数")
    private int kshYwQty;

    /**
     * 广州业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("广州业务数")
    private int kgzYwQty;

    /**
     * 常州业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("常州业务数")
    private int sczYwQty;

    /**
     * 天津业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("天津业务数")
    private int ktjYwQty;

    /**
     * 武汉业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("武汉业务数")
    private int swhYwQty;

    /**
     * 济南业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("济南业务数")
    private int sjnYwQty;

    /**
     * 沈阳业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("沈阳业务数")
    private int ssyYwQty;

    /**
     * 太原业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("太原业务数")
    private int styYwQty;

    /**
     * 潍坊业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("潍坊业务数")
    private int swfYwQty;

    /**
     * 成都业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("成都业务数")
    private int scdYwQty;

    /**
     * 寄售业务数
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("寄售业务数")
    private int wtYwQty;

    /**
     * 北京盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("北京盘点数")
    private int kbjQty;

    /**
     * 上海盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("上海盘点数")
    private int kshQty;

    /**
     * 广州盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("广州盘点数")
    private int kgzQty;

    /**
     * 常州盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("常州盘点数")
    private int sczQty;

    /**
     * 天津盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("天津盘点数")
    private int ktjQty;

    /**
     * 武汉盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("武汉盘点数")
    private int swhQty;

    /**
     * 济南盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("济南盘点数")
    private int sjnQty;

    /**
     * 沈阳盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("沈阳盘点数")
    private int ssyQty;

    /**
     * 太原盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("太原盘点数")
    private int styQty;

    /**
     * 潍坊盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("潍坊盘点数")
    private int swfQty;

    /**
     * 成都盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("成都盘点数")
    private int scdQty;

    /**
     * 寄售盘点数
     */
    @HeadStyle(fillForegroundColor = 34)
    @ExcelProperty("寄售盘点数")
    private int wtQty;

    /**
     * 北京物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("北京物流-业务")
    private int kbjwlYwQty;

    /**
     * 上海物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("上海物流-业务")
    private int kshwlYwQty;

    /**
     * 广州物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("广州物流-业务")
    private int kgzwlYwQty;

    /**
     * 常州物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("常州物流-业务")
    private int sczwlYwQty;

    /**
     * 天津物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("天津物流-业务")
    private int ktjwlYwQty;

    /**
     * 武汉物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("武汉物流-业务")
    private int swhwlYwQty;

    /**
     * 济南物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("济南物流-业务")
    private int sjnwlYwQty;

    /**
     * 沈阳物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("沈阳物流-业务")
    private int ssywlYwQty;

    /**
     * 太原物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("太原物流-业务")
    private int stywlYwQty;

    /**
     * 潍坊物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("潍坊物流-业务")
    private int swfwlYwQty;

    /**
     * 成都物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("成都物流-业务")
    private int scdwlYwQty;

    /**
     * 寄售物流-业务
     */
    @HeadStyle(fillForegroundColor = 40)
    @ExcelProperty("寄售物流-业务")
    private int wtwlYwQty;

    /**
     * e价
     */
    @HeadStyle(fillForegroundColor = 42)
    @ExcelProperty("e价")
    private BigDecimal eprice;

    /**
     * 盘点批次号
     */
    @ExcelIgnore
    private String pdBatchNo;
}
