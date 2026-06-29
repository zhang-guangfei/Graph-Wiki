package com.smc.smccloud.model.cost;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-03-03 14:26
 * Description:
 */
@Data
@TableName("Impdata_Adjust")
public class ImpdataAdjustDO {

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    @TableField("ImpDate")
    private Date impDate;

    @TableField("InvoiceNo")
    private String invoiceNo;

    @TableField("OwnerCompanyId")
    private String ownerCompanyId;

    @TableField("InvDesc")
    private String invDesc;

    @TableField("Ecode")
    private String eCode;

    @TableField("OrderNo")
    private String orderNo;

    @TableField("CustomerNo")
    private String customerNo;

    @TableField("ModelNo")
    private String modelNo;

    @TableField("Quantity")
    private Integer quantity;

    @TableField("Price")
    private BigDecimal price;

    @TableField("Amount")
    private BigDecimal amount;

    @TableField("OptCode")
    private String optCode;

    @TableField("DataSource")
    private String dataSource;

    /**
     * 仓库代码
     */
    @TableField("StockCode")
    private String stockCode;

}
