package com.smc.smccloud.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author edp04
 * @title: ProductPhysicsDO
 * @date 2022/10/13 08:39
 */
@Data
@TableName("product_physics")
public class ProductPhysicsDO {

    @TableField("ModelNo")
    private String modelNo;

    @TableField("length")
    private BigDecimal length;

    @TableField("width")
    private BigDecimal width;

    @TableField("height")
    private BigDecimal height;

    @TableField("weight")
    private BigDecimal weight;

    @TableField("netweight")
    private BigDecimal netweight;

    @TableField("AfterPackaging_length")
    private BigDecimal afterPackagingLength;

    @TableField("AfterPackaging_width")
    private BigDecimal afterPackagingWidth;

    @TableField("AfterPackaging_height")
    private BigDecimal afterPackagingHeight;

    @TableField("CylDiameter")
    private BigDecimal cylDiameter;

    @TableField(exist = false)
    private BigDecimal cylminlen;

    @TableField(exist = false)
    private BigDecimal cylmaxlen;

    @TableField(exist = false)
    private BigDecimal mSizeMin;

    @TableField(exist = false)
    private BigDecimal mSizeMax;

    @TableField(exist = false)
    private String midSize;

    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField(exist = false)
    private Date createdDate;

    @TableField(exist = false)
    private String createdUser;

    @TableField(exist = false)
    private Date updatedDate;

    @TableField(exist = false)
    private String updatedUser;

    @TableField(exist = false)
    private Date modifyDate;

    @TableField(exist = false)
    private String modifier;

}
