package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-04-25 15:32
 * Description:
 */
@Data
@TableName("ops_customer")
public class CustomerDO {

    /**
     * 客户代码
     */
    @TableId(value = "customer_no", type = IdType.INPUT)
    private String customerNo;

    /**
     * 客户名称(中文)
     */
    @TableField("name")
    private String name;

    /**
     * 客户名称(英文)
     */
    @TableField("ename")
    private String ename;

    /**
     * 营业所代码
     */
    @TableField("HRUnitId")
    private String HRUnitId;

    /**
     * 客户担当
     */
    @TableField("PSNSMCID")
    private String PSNSMCID;

    /**
     * 代理商代码
     */
    @TableField("AgentNo")
    private String agentNo;

    /**
     * 客户类型
     */
    @TableField("CustomerType")
    private String customerType;

    /**
     *
     */
    @TableField("SMCGroupId")
    private String SMCGroupId;

    /**
     * 交易主体
     */
    @TableField("tradeSubjectId")
    private String tradeSubjectId;

    /**
     * 税号
     */
    @TableField("TaxNo")
    private String taxNo;

    /**
     * 开户银行
     */
    @TableField("bank")
    private String bank;

    /**
     * 开户银行账号
     */
    @TableField("AccountNo")
    private String accountNo;

    /**
     * 开票地址
     */
    @TableField("InvoiceAddress")
    private String invoiceAddress;

    /**
     * 开票联系人
     */
    @TableField("InvoicePhoneNo")
    private String invoicePhoneNo;

    /**
     * 开票类型
     */
    @TableField("InvoiceType")
    private String invoiceType;

    /**
     * 删除
     */
    @TableField("delflag")
    private String delFlag;

    @TableField("cre_time")
    private String creTime;

    @TableField("creator")
    private String creator;

    @TableField("modify_time")
    private String modifyTime;

    @TableField("modifier")
    private String modifier;

    @TableField("CstmGrade")
    private String cstmGrade;

    @TableField("VIPCode_Cn")
    private String vipCodeCn;

    @TableField("customerLevel")
    private String customerLevel;

    @TableField("HLCode")
    private String hlCode;

}
