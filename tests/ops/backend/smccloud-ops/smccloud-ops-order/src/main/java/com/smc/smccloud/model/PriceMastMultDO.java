package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author edp04
 * @title: PriceMastMultDO
 * @date 2022/10/24 10:10
 */
@Data
@TableName(value = "PriceMast_Mult")
public class PriceMastMultDO {

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "ECode")
    private String eCode;

    @TableField(value = "ModelNo")
    private String modelNo;

    @TableField(value = "Quantity")
    private String quantity;

    @TableField(value = "EPriceJp")
    private BigDecimal ePriceJp;

    @TableField(value = "EPriceRMB")
    private BigDecimal ePriceRMB;

    @TableField(value = "SPriceRMB")
    private Integer sPriceRMB;

    @TableField(value = "FobPrice")
    private BigDecimal fobPrice;

    @TableField(value = "UpdDate")
    private Date updDate;

    @TableField(value = "MinQuantity")
    private Integer minQuantity;

    @TableField(value = "MaxQuantity")
    private Integer maxQuantity;

    @TableField(value = "EPrice_Base")
    private BigDecimal ePriceBase;

    @TableField(value = "UpdName")
    private String updName;

    @TableField(value = "LoginDate")
    private Date loginDate;

    @TableField(value = "price_lowest")
    private BigDecimal priceLowest;

    @TableField(value = "Used_Id")
    private String usedId;

    @TableField(value = "CTCProduct")
    private String cTCProduct;

    @TableField(value = "UPriceRMB")
    private BigDecimal uPriceRMB;

    @TableField(value = "StdDlvDate")
    private Integer stdDlvDate;

    @TableField(value = "updateTime")
    private Date updateTime;

}
