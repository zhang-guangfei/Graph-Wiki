package com.smc.smccloud.model.VSalesManuorder;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: OpsVDeliveryAnswerToSalesDO
 * @date 2022/04/26 15:40
 */

@Data
@TableName("Manufacture.dbo.OPS_V_DeliveryAnswerToSales")
public class OpsVDeliveryAnswerToSalesDO {

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 发行日期
     */
    private Date shipDate;

    /**
     * 营业纳期
     */
    private Date dlvDate;

    /**
     * 变更纳期
     */
    private Date changeDlvDate;

    /**
     * 入库号
     */
    private String inStockCode;

    /**
     * 出库日期
     */
    private Date expDate;

    /**
     * 依赖号
     */
    private String dependencyCode;

    private String remark;

    /**
     * 发票号
     */
    private String invoiceNo;

    private String customerNo;

    /**
     * 装箱日期
     */
    private Date packDate;

    /**
     * 工厂出口日期
     */
    private Date ExportDate;

    /**
     * 出货状态
     */
    private String expStatus;

    private String orderType;

    private String holon;

    /**
     * 返信备注
     */
    private String replayRemark;

    private String orderNo;

    private String itemNo;

}
