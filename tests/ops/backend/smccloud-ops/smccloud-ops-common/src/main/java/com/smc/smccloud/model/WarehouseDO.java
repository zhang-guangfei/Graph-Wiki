package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ops_warehouse")
public class WarehouseDO {

    @TableField(value = "warehouse_code")
    private String warehouseCode;

    @TableField(value = "warehouse_type")
    private String warehouseType;

    @TableField(value = "warehouse_name")
    private String warehouseName;

    @TableField(value = "adress")
    private String adress;

    @TableField(value = "post_code")
    private String postCode;

    @TableField(value = "linkman")
    private String linkman;

    @TableField(value = "mail")
    private String mail;

    @TableField(value = "customer_no")
    private String customerNo;

    @TableField(value = "customer_linkman")
    private String customerLinkman;

    @TableField(value = "customer_phone")
    private String customerPhone;

    @TableField(value = "customer_mail")
    private String customerMail;

    @TableField(value = "smc_linkman")
    private String smcLinkman;

    @TableField(value = "smc_phone")
    private String smcPhone;

    @TableField(value = "smc_mail")
    private String smcMail;

    @TableField(value = "disable_flag")
    private String disableFlag;

    @TableField(value = "assembly_flag")
    private String assemblyFlag;

    @TableField(value = "centralize_flag")
    private String centralizeFlag;

    @TableField(value = "delflag")
    private String delflag;

    @TableField(value = "cre_time")
    private String creTime;

    @TableField(value = "creator")
    private String creator;

    @TableField(value = "modify_time")
    private String modifyTime;

    @TableField(value = "modifier")
    private String modifier;

    @TableField(value = "purchase_flag")
    private String purchaseFlag;

    @TableField(value = "parent_code")
    private String parentCode;

    @TableField(value = "rcv_linkman")
    private String rcvLinkman;

    @TableField(value = "rcv_link_tel")
    private String rcvLinkTel;

    @TableField(value = "rcv_link_email")
    private String rcvLinkEmail;

    @TableField(value = "english_address")
    private String englishAddress;

    @TableField(value = "english_linkman")
    private String englishLinkman;
// 退货能力
    @TableField(value = "returnable_flag")
    private Integer returnableFlag;
// 调库能力
    @TableField(value = "transfer_flag")
    private Integer transferFlag;
    // 补库能力
    @TableField(value = "prestock_flag")
    private Integer prestockFlag;
}
