package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CsModelInfoVO {

    //    是否拆分 --
    private Boolean isSplit;
    //    近半年接单总和 --
    private Integer sumRcvOrderNum;
    //    订货频率 --
    private Integer freq;
    //    客户计数 --
    private Integer customersCount;
    //    月均数量 --
    private BigDecimal avgQtyCount;
    //    现有在库数 --
    private Integer onHourseNum;
    //    预约数量 --
    private Integer appointmentNum;
    //    在途数量 --
    private Integer onWayNum;
    //    补库数量 --
    private Integer replQty;
    //    保有月数 --
    private Integer months;
    //    是否BIN品 --
    private Boolean isBinData;
    // 专备数量（按照用户编码查询用户的专备有效在库数，和在途数，返回格式为：在库数；在途数(此在途数跟上面的在途数量不是一个值)）
    private Integer specialQuantity;
    private String modelNo;

    //  最小包装单位
    private Integer minPackageUnit;

    // 含税单价(基准价)
    private BigDecimal unitPricewithTax;
    // 金额
    private BigDecimal amount;

    // 逾期数量
    private Integer overTimeQty;

   /* //    是否LOT价
    private Boolean isLotPrice;

    private String customer;
    // 库房代码
    private String stockCode;

    private String modelNo;
    // 设置备备库数值
    private Integer initStockQty;
    // 单位规格数量
    private Integer initUnitQty;
    // 采购中数量
    private Integer ordingQty;
    // 在库数
    private Integer onhandQty;
    // 待收货数量
    private Integer transQty;
    // 总数量
    private Integer totalQty;
    // E价
    private BigDecimal eprice;

    // e金额
    private BigDecimal eamount;*/

}
