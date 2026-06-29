package com.smc.smccloud.model.productPrice;

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
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product_price")
public class ProductPriceDO implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableField("ModelNo")
    private String modelNo;

    @TableField(value = "MinQuantity")
    private Integer minQuantity;

    @TableField("EPrice")
    private BigDecimal eprice;

    @TableField("EPriceJp")
    private BigDecimal epriceJP;

    @TableField("EpricePra")
    private BigDecimal epricePra;
    /**
     * 限制最低销售价格(不含税)
     */
    @TableField("LowestPrice")
    private BigDecimal lowestPrice;

    /**
     * 进口FOB价
     */
    @TableField("ImportFobPrice")
    private BigDecimal importFobPrice;

    /**
     * 出口FOB价
     */
    @TableField("ExportFobPrice")
    private BigDecimal exportFobPrice;

    @TableField(exist = false)
    private BigDecimal spriceRMB;

    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("ImportFobPriceOriginal")
    private BigDecimal importFobPriceOriginal;

    @TableField("ImportCurrencyId")
    private String importCurrencyId;

    @TableField(exist = false)
    private Date updateTime;


}
