package com.smc.smccloud.model.salesinvoice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author edp04
 * @title: SalesImpAgentDO
 * @date 2022/07/16 11:16
 */
@Data
@TableName("SalesImp_agent")
public class SalesImpAgentDO {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("Impdate")
    private Date impdate;

    @TableField("InvoiceNo")
    private String invoiceNo;

    @TableField("ECode")
    private Integer eCode;

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
    private Integer optCode;

    @TableField("OptDate")
    private Date optDate;

    @TableField("AmountJP")
    private BigDecimal amountJP;

    @TableField("StockCode")
    private Integer stockCode;

    @TableField("ProcessCode")
    private String processCode;

    @TableField("Stateflag")
    private String stateflag;

    @TableField("DataSource")
    private String dataSource;

    @TableField("Remark")
    private String remark;

    @TableField("WarehouseCode")
    private String warehouseCode;

    @TableField("CustomerNo")
    private String customerNo;

}
