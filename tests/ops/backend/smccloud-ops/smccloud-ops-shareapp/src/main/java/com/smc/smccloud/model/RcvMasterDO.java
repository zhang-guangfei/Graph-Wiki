package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author smc
 * @since 2021-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rcvmaster")
public class RcvMasterDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接单号
     */
    @TableField(value = "rorder_no")
    private String rorderNo;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * 用户代码
     */
    @TableField("user_no")
    private String userNo;

    /**
     * 最终用户
     */
    @TableField("end_user")
    private String endUser;

    /**
     * 接单日期
     */
    @TableField("ROrdDate")
    private Date rordDate;

    /**
     * 货齐出货
     */
    @TableField("dlv_entire")
    private String dlvEntire;

    /**
     * 交货地点
     */
    @TableField("DlvSite")
    private String dlvSite;

    /**
     * 运费负担
     */
    @TableField("TransFee")
    private String transFee;

    /**
     * 国内运输方式
     */
//    @TableField("TransChannel")
//    private String transChannel;

    /**
     * 项目代码
     */
    @TableField("PrjCode")
    private String prjCode;

    /**
     * 原接单号 （合同订单号）
     */
    @TableField("ORorderNo")
    private String orOrderNo;

    /**
     * 出货方式
     */
    @TableField("DlvType")
    private String dlvType;

    /**
     * 外勤
     */
    @TableField("Employee")
    private String employee;

    /**
     * 内勤
     */
    @TableField("EmployeeNo")
    private String employeeNo;

    /**
     * 处理时间
     */
    @TableField("OptTime")
    private Date optTime;

    /**
     * 操作担当
     */
    @TableField("Operator")
    private String operator;

    /**
     * 营业所代码
     */
    @TableField("dept_No")
    private String deptNo;

    /**
     * 合同号
     */
    @TableField("ContractNo")
    private String contractNo;

    /**
     * 报价单号
     */
    @TableField("QuotationNo")
    private String quotationNo;

    /**
     * 客户采购单号
     */
    @TableField("PurchaseNO")
    private String purchaseNo;

    /**
     * HL编码
     */
    @TableField("HLCode")
    private String hlCode;

    /**
     * 订单的SMC交易主体
     */
    @TableField("trade_companyId")
    private String tradeCompanyId;

    @TableField("create_user")
    private String createUser;

//    @TableField("corder_no")
//    private String corderNo;

    /**
     * 收货地所在营业所代码
     */
    @TableField("delivery_dept_no")
    private String deliveryDeptNo;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("update_time")
    private Date updateTime;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_user",fill = FieldFill.INSERT)
    private String updateUser;


}
