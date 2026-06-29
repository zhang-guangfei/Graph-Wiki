package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2024-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rcvmaster")
public class RcvmasterDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接单号
     */
    @TableId(value = "rorder_no", type = IdType.AUTO)
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
    private Date ROrdDate;

    /**
     * [出货方式]	0:随发	
;1:货齐-单仓	
;2:随发-分批;	3:货齐-多仓
     */
    @TableField("dlv_entire")
    private String dlvEntire;

    /**
     * 交货方式名称 编码： 1：直发客户，2：直发营业所，3：营业所/客户自提【废弃】
     */
    @TableField("DlvSite")
    private String DlvSite;

    /**
     * 运费负担; [0:SMC负担;1:客户负担;2:渠道负担]  20220825变更
     */
    @TableField("TransFee")
    private String TransFee;

    /**
     * 项目代码
     */
    @TableField("PrjCode")
    private String PrjCode;

    /**
     * 集约(分包)方式:0:按地址集约;1:按订单集约;2:按用户集约
     */
    @TableField("DlvType")
    private String DlvType;

    /**
     * 外勤
     */
    @TableField("Employee")
    private String Employee;

    /**
     * 内勤
     */
    @TableField("EmployeeNo")
    private String EmployeeNo;

    /**
     * 处理时间
     */
    @TableField("OptTime")
    private Date OptTime;

    /**
     * 操作担当
     */
    @TableField("Operator")
    private String Operator;

    /**
     * 原接单号，门户过来单据则为合同订单号
     */
    @TableField("ORorderNo")
    private String ORorderNo;

    /**
     * 合同代码
     */
    @TableField("ContractNo")
    private String ContractNo;

    /**
     * 报价单号
     */
    @TableField("QuotationNo")
    private String QuotationNo;

    /**
     * 客户采购单号
     */
    @TableField("PurchaseNO")
    private String PurchaseNO;

    /**
     * 营业所代码
     */
    @TableField("dept_No")
    private String deptNo;

    /**
     * HL编码
     */
    @TableField("HLCode")
    private String HLCode;

    /**
     * 收货地所在营业所代码
     */
    @TableField("delivery_dept_no")
    private String deliveryDeptNo;

    /**
     * 订单的SMC交易主体
     */
    @TableField("trade_companyId")
    private String tradeCompanyid;

    @TableField("update_time")
    private Date updateTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("id")
    private Long id;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_user")
    private String createUser;


}
