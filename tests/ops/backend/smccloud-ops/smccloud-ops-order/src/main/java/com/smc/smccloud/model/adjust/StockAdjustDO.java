package com.smc.smccloud.model.adjust;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "stock_adjust")
public class StockAdjustDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "invoiceNo")
    private String invoiceNo;

    @TableField(value = "fullOrderNo")
    private String fullOrderNo;

    @TableField(value = "orderNo")
    private String orderNo;

    @TableField(value = "itemNo")
    private Integer itemNo;

    @TableField(value = "splitItemNo")
    private Integer splitItemNo;

    @TableField(value = "modelNo")
    private String modelNo;

    @TableField(value = "quantity")
    private Integer quantity;

    @TableField(value = "adjustType")
    private Integer adjustType;

    @TableField(value = "optCode")
    private Integer optCode;

    @TableField(value = "createTime")
    private Date createTime;

    @TableField(value = "updateTime")
    private Date updateTime;

    @TableField(value = "createUser")
    private String createUser;

    @TableField(value = "updateUser")
    private String updateUser;

    @TableField(value = "price")
    private BigDecimal price;

    @TableField(value = "amount")
    private BigDecimal amount;

    @TableField(value = "warehouseCode")
    private String warehouseCode;

    @TableField(value = "pplNo")
    private String pplNo;

    @TableField(value = "projectNo")
    private String projectNo;

    @TableField(value = "customerNo")
    private String customerNo;

    @TableField(value = "groupCustomerNo")
    private String groupCustomerNo;

    @TableField(value = "inventoryTypeCode")
    private String inventoryTypeCode;

    @TableField(value = "inventoryPropertyId")
    private Long inventoryPropertyId;

    @TableField(value = "reason")
    private String reason;

    @TableField(value = "adjustDate")
    private Date adjustDate;

    @TableField(value = "supplierCode")
    private String supplierCode;

    @TableField(value = "exchangeRate")
    private BigDecimal exchangeRate;

    @TableField(value = "currency")
    private String currency;

    @TableField(value = "resultMsg")
    private String resultMsg;

    @TableField(exist = false)
    private String invoiceId;
}
