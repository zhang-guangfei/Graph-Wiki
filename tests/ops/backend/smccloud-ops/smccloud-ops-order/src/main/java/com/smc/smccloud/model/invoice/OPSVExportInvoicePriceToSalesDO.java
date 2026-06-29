package com.smc.smccloud.model.invoice;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author edp02 @Date 2022/5/16 8:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("OPS_V_ExportInvoicePriceToSales")
public class OPSVExportInvoicePriceToSalesDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("增值税发票号")
    private String invoiceNo;

    @TableField("型号")
    private String modelNo;

    @TableField("数量")
    private Integer quantity;

    @TableField("品名")
    private String brandName;

    @TableField("单价")
    private BigDecimal price;

    @TableField("总价")
    private BigDecimal amount;

    @TableField("订单号")
    private String orderNo;

    @TableField("订单项号")
    private String itemNo;

    @TableField("出口日期")
    private Date expDate;

    @TableField("开票主体")
    private String invfrom;

    @TableField("税率")
    private BigDecimal taxRate;

    @TableField("发票号")
    private String billNo;

    @TableField("开票日期")
    private Date invoiceDate;


}
