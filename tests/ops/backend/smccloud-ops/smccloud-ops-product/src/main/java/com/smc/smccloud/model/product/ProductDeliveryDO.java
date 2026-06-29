package com.smc.smccloud.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-01-21 11:33
 * Description:
 */
@Data
@TableName("product_delivery")
public class ProductDeliveryDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("modelNo")
    private String modelNo;

    @TableField("orgCountryId")
    private String orgCountryId;

    @TableField("supplyId")
    private String supplyId;

    @TableField("stdDlvDay")
    private Integer stdDlvDay;

    @TableField("stdDlvDateMaxNumber")
    private Integer stdDlvDateMaxNumber;

    @TableField("maxProdQty")
    private Integer maxProdQty;

    @TableField("enableMaxProdQty")
    private Boolean enableMaxProdQty;

    @TableField("isPrimary")
    private Boolean isPrimary;

    @TableField("supplierPartNo")
    private String supplierPartNo;

    @TableField(exist = false)
    private Date updateTime;

    @TableField("resultSource")
    private String resultSource;

    @TableField("resultSourceDesc")
    private String resultSourceDesc;

    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("priority")
    private int priority;







}
