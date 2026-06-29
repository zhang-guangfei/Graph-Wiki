package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 委托在库信息
 */
@Data
public class CsWarehouseVO {
    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 仓库类别 :委托在库WT，分库：SUB，中心仓（大库）：MASTER
     */
    private String warehouseType;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 邮编
     */
    private Integer postCode;

    /**
     * 收货联系人
     */
    private String linkMan;

    /**
     * 仓库地址
     */
    private String address;

    /**
     * 收货联系mail
     */
    private String mail;

    /**
     * 所属客户代码；如果是委托在库/服务备库则需输入
     */
    private String customerNo;

    /**
     * 客户负责人：如果是委托在库/服务备库则需输入
     */
    private String customerLinkman;

    /**
     * 客户负责人电话：如果是委托在库/服务备库则需输入
     */
    private String customerPhone;
    /**
     * 客户负责人mail：如果是委托在库/服务备库则需输入
     */
    private String customerMail;

    /**
     * SMC负责人
     */
    private String smcLinkman;

    /**
     * SMC负责人电话
     */
    private String smcPhone;

    /**
     * SMC负责人mail
     */
    private String smcMail;

    /**
     * 暂停出库服务:0可用，1不可用
     */
    private Integer disableFlag;

    /**
     * 组装能力;
     * BOM拆分需要组装时候使用
     * 0；不支持
     * 1：支持；
     */
    private Integer assemblyFlag;

    /**
     * 出库集约：0:不支持；1：支持
     */
    private Integer centralizeFlag;

    /**
     * 删除标识：0未删除,1删除
     */
    private Integer delflag;

    private Date createTime;

    private String createName;

    private Date modifyTime;

    private String modifier;

    /**
     * 出库集约：0:不支持；* 1：支持
     */
    private Integer purchaseFlag;

    /**
     * 采购物流中心
     */
    private String parentCode;

    /**
     * 营业所
     */
    private String deptNo;

    /**
     * 最大备库E金额
     */
    private BigDecimal maxEamount;
}
