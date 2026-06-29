package com.smc.smccloud.model.VSalesManuorder;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/5/27 12:01
 * @Descripton TODO
 */
@Data
// @TableName("Manufacture.dbo.OPS_V_ImpInvoiceStatusFrmCMS")
public class ImpInvoiceStatusFrmCMSDO {

    // [厂别]

    // [发票号]
    private String invoiceNo;
    // [订单号]
    private String orderNo;
    // [订单项号]
    private Integer itemNo;
    // [原始发票号]
    private String oldInvoiceNo;
    // [客户代码]
    private String customerNo;
    // [型号]
    private String modelNo;
    // [数量合计]

    // [运输方式]

    // [操作时间]

    // [发票确认时间]

    // [发送中海时间]

    // [预到港日期]
    private Date preArricalDate;
    // [预到货日期]
    private Date esArriveDate;
    // [发货日期]
    private Date sendOrderDate;
    // [实际到港日期]
    private Date realyArricalDate;
    // [申报日期]
    private Date customsDate;
    // [到厂日期]
    private Date arriveFactoryDate;

    // [到货确认时间]

    // [入库确认时间]

    // [单据内部编号]

    // [更新时间]
    private Date updateTime;


}
