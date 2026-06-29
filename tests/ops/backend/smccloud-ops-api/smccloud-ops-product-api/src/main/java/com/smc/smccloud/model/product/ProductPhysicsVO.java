package com.smc.smccloud.model.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author edp04
 * @title: ProductPhysicsVO
 * @date 2022/10/13 08:47
 */
@Data
public class ProductPhysicsVO {

    private String modelNo;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private BigDecimal weight;

    private BigDecimal netweight;

    private BigDecimal afterPackagingLength;

    private BigDecimal afterPackagingWidth;

    private BigDecimal afterPackagingHeight;

    private BigDecimal cylDiameter;

    private BigDecimal cylminlen;

    private BigDecimal cylmaxlen;

    private BigDecimal mSizeMin;

    private BigDecimal mSizeMax;

    private String midSize;

    private Boolean isDeleted;

    private Date createdDate;

    private String createdUser;

    private Date updatedDate;

    private String updatedUser;

    private Date modifyDate;

    private String modifier;

}
