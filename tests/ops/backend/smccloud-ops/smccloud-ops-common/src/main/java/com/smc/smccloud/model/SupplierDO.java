package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("supplier")
public class SupplierDO {

    @TableField("id")
    private String id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "companyId")
    private String companyId;

//    @TableField(value = "CountryCode")
//    private String countryCode;   字段已删除

    @TableField(value = "stdDeliveryDay")
    private String stdDeliveryDay;

    @TableField(value = "enableStdDeliveryDay")
    private Integer enableStdDeliveryDay;

    @TableField(value = "enableInventory")
    private Integer enableInventory;

    @TableField(value = "fcost_code")
    private String fcostcode;

    @TableField(value = "fstDeliveryDay")
    private Integer fstDeliveryDay;

    @TableField(value = "fstTransTypeId")
    private String fstTransTypeId;

    @TableField(value = "shipDeliveryDay")
    private Integer shipDeliveryDay;

    @TableField(value = "stdProductManuDay")
    private Integer stdProductManuDay;

    @TableField(value = "nstdProductManuDay")
    private Integer nstdProductManuDay;

    @TableField(value = "fullName")
    private String fullName;

    @TableField(value = "TransCurrency")
    private String TransCurrency;

    @TableField(value = "paymentDay")
    private Integer paymentDay;

    @TableField(value = "updateTime",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(value = "updator")
    private String updator;

//    @TableField(value = "exchangeRate")
//    private BigDecimal exchangeRate;


    @TableField(value = "isAutoSend")
    private Integer isAutoSend;

    @TableField(value = "email")
    private String email;

    // 显示排序字段(数值越大越往后)
    @TableField("sort")
    private Integer sort;

}
