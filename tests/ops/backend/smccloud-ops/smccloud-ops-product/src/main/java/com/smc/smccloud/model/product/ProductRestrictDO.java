package com.smc.smccloud.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = false)
@TableName("product_restrict")
public class ProductRestrictDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 绝对禁止销售品
     */
    @TableField(exist = false)
    private String typeAsp;

    /**
     * 需重新特注品
     */
    @TableField(exist = false)
    private String typePnc;

    /**
     * 客户贩卖限制品
     */
    @TableField(exist = false)
    private String typeSGroup;

    /**
     * 最低售价限制品
     */
    @TableField(exist = false)
    private String typeSPrice;

    /**
     * 行业贩卖限制品
     */
    @TableField(exist = false)
    private String typeSRoute;

    /**
     * 95012订货权限
     */
    @TableField(exist = false)
    private String auth95012;

    /**
     * 说明
     */
    @TableField("desc")
    private String desc;

    /**
     * 逻辑删除  0否  1是
     */
    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField(exist = false)
    private String updatedUser;

    @TableId(value = "ModelNo", type = IdType.AUTO)
    private String ModelNo;

    @TableField(exist = false)
    private String CustomerNo;

    @TableField(exist = false)
    private String createdUser;

    @TableField(exist = false)
    private String updatedDate;

    @TableField(exist = false)
    private String createdDate;

    @TableField(exist = false)
    private String industryCode;


}
