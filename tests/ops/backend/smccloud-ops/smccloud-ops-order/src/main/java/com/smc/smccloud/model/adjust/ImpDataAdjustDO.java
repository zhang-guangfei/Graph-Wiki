//package com.smc.smccloud.model.adjust;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.Data;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
///**
// * @author edp04
// * @title: ImpDataAdjustDO
// * @date 2022/10/18 10:10
// */
//@Data
//@TableName("Impdata_Adjust")
//public class ImpDataAdjustDO {
//
//    @TableId(value = "Id", type = IdType.AUTO)
//    private Long id;
//
//    @TableField("ImpDate")
//    private Date impDate;
//
//    @TableField("InvoiceNo")
//    private String invoiceNo;
//
//    @TableField("InvDesc")
//    private String invDesc;
//
//    @TableField("Ecode")
//    private String eCode;
//
//    @TableField("OrderNo")
//    private String orderNo;
//
//    @TableField("CustomerNo")
//    private String customerNo;
//
//    @TableField("ModelNo")
//    private String modelNo;
//
//    @TableField("Quantity")
//    private Integer quantity;
//
//    @TableField("Price")
//    private BigDecimal price;
//
//    @TableField("Amount")
//    private BigDecimal amount;
//
//    @TableField("OptCode")
//    private String optCode;
//
//    @TableField("DataSource")
//    private String dataSource;
//
//    /**
//     * 仓库代码
//     */
//    @TableField("StockCode")
//    private String stockCode;
//
//}