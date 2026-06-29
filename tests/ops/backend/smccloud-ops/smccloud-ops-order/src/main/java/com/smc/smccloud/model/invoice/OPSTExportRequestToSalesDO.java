package com.smc.smccloud.model.invoice;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author edp02 @Date 2022/4/7 12:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("OPS_T_ExportRequestToSales")
public class OPSTExportRequestToSalesDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("现品票")
    private String billNo;

    @TableField("型号")
    private String modelNo;

    @TableField("客户代码")
    private String customerNo;

    @TableField("订单数量")
    private Integer orderQty;

    @TableField("数量")
    private Integer quantity;

    @TableField("入库号")
    private String impNo;

    @TableField("依赖号")
    private String orderNo;

    @TableField("发票号")
    private String fapiaoNo;

    @TableField("拍号")
    private String paiNo;

    @TableField("InvoiceNo")
    private String invoiceNo;

    @TableField("InvNo")
    private String invNo;

    @TableField("ExecuteTime")
    private Date executeTime;

    @TableField("unitWeight")
    private Double unitWeight;

    @TableField("unitPrice")
    private BigDecimal unitPrice;

    @TableField("SN_CODE")
    private String snCODE;

    @TableField("Rosh")
    private String rosh;

    @TableField("出库日期")
    private Date expDate;

    @TableField("开票主体")
    private String invfrom;

    @TableField(exist = false)
    private BigDecimal amount;

    @TableField("贸易方式")
    private String saleType;
}
