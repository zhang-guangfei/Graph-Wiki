package com.smc.smccloud.model.product;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-07-08
 */
@Data
public class ProductRestrictVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 绝对禁止销售品
     */
    private String typeAsp;

    /**
     * 需重新特注品
     */
    private String typePnc;

    /**
     * 客户贩卖限制品
     */
    private String typeSGroup;

    /**
     * 最低售价限制品
     */
    private String typeSPrice;

    /**
     * 行业贩卖限制品
     */
    private String typeSRoute;

    /**
     * 95012订货权限
     */
    private String auth95012;

    /**
     * 说明
     */
    private String desc;

    /**
     * 逻辑删除  0否  1是
     */
    private Boolean isDeleted;

    private String updatedUser;

    private String ModelNo;

    private String CustomerNo;

    private String createdUser;

    private String updatedDate;

    private String createdDate;

    private String industryCode;


}
