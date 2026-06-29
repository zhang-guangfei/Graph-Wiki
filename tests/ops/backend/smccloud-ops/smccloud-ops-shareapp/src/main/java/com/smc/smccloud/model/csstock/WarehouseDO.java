package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 寄售库房清单
 *
 * @author smc
 * @since 2021-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_warehouse")
public class WarehouseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库代码
     */
    @TableId(value = "warehouse_code")
    private String warehouseCode;

    /**
     * 仓库类别 :委托在库WT，分库：SUB，中心仓（大库）：MASTER
     */
    @TableField("warehouse_type")
    private String warehouseType;

    /**
     * 仓库名称
     */
    @TableField("warehouse_name")
    private String warehouseName;

    /**
     * 邮编
     */
    @TableField("post_code")
    private Integer postCode;

    /**
     * 收货联系人
     */
    @TableField("linkman")
    private String linkMan;

    /**
     * 仓库地址
     */
    @TableField("adress")
    private String address;

    /**
     * 收货联系mail
     */
    @TableField("mail")
    private String mail;

    /**
     * 所属客户代码；如果是委托在库/服务备库则需输入
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * 客户负责人：如果是委托在库/服务备库则需输入
     */
    @TableField("customer_linkman")
    private String customerLinkman;

    /**
     * 客户负责人电话：如果是委托在库/服务备库则需输入
     */
    @TableField("customer_phone")
    private String customerPhone;
    /**
     * 客户负责人mail：如果是委托在库/服务备库则需输入
     */
    @TableField("customer_mail")
    private String customerMail;

    /**
     * SMC负责人
     */
    @TableField("smc_linkman")
    private String smcLinkman;

    /**
     * SMC负责人电话
     */
    @TableField("smc_phone")
    private String smcPhone;

    /**
     * SMC负责人mail
     */
    @TableField("smc_mail")
    private String smcMail;

    /**
     * 暂停出库服务:0可用，1不可用
     */
    @TableField("disable_flag")
    private Integer disableFlag;

    /**
     * 组装能力;
     * BOM拆分需要组装时候使用
     * 0；不支持
     * 1：支持；
     */
    @TableField("assembly_flag")
    private Integer assemblyFlag;

    /**
     * 出库集约：0:不支持；1：支持
     */
    @TableField("centralize_flag")
    private Integer centralizeFlag;

    /**
     * 删除标识：0未删除,1删除
     */
    @TableField("delflag")
    private Integer delflag;

    @TableField("cre_time")
    private Date creTime;

    @TableField("creator")
    private String creator;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("modifier")
    private String modifier;

    /**
     * 出库集约：0:不支持；* 1：支持
     */
    @TableField("purchase_flag")
    private Integer purchaseFlag;

    /**
     * 采购物流中心
     */
    @TableField("parent_code")
    private String parentCode;
    /**
     * 营业所
     */
    @TableField("dept_no")
    private String deptNo;

    @TableField("max_eamount")
    private BigDecimal maxEamount;

    @TableField("link_phone")
    private String linkPhone;

    @TableField("link_mobile")
    private String linkMobile;

    @TableField(value = "rcv_linkman")
    private String rcvLinkman;

    @TableField(value = "rcv_link_tel")
    private String rcvLinkTel;

    @TableField(value = "rcv_link_email")
    private String rcvLinkEmail;

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
