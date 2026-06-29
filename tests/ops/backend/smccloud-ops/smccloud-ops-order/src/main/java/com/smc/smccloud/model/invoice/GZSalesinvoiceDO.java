package com.smc.smccloud.model.invoice;

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
 * @Author edp02 @Date 2022/6/7 15:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tmp_sales_invoice_cng")
public class GZSalesinvoiceDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("InvoiceNo")
    private String invoiceNo;

    @TableField("Impdate")
    private Date impdate;

    @TableField("Ecode")
    private String ecode;

    @TableField("OrderNo")
    private String orderNo;

    @TableField("ModelNo")
    private String modelNo;

    @TableField("Quantity")
    private Integer quantity;

    @TableField("Price")
    private BigDecimal price;

    @TableField("Amount")
    private BigDecimal amount;

    @TableField("OptCode")
    private String OptCode;

    @TableField("OptDate")
    private Date optDate;

    @TableField("StockNo")
    private String stockNo;

    @TableField("InsertTime")
    private Date insertTime;

    @TableField("ExtractTime")
    private Date extractTime;

    @TableField("taxamount")
    private BigDecimal taxamount;

    @TableField("DeliveryNo")
    private String deliveryNo;
}
