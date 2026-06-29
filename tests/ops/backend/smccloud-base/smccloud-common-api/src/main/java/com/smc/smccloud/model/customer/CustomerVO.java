package com.smc.smccloud.model.customer;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author smc
 * @since 2022-01-12
 */
@Data
public class CustomerVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 客户名称(中文)
     */
    private String name;

    /**
     * 客户名称(英文)
     */
    private String ename;

    /**
     * 营业所代码
     */
    private String HRUnitId;

    /**
     * 客户担当
     */
    private String PSNSMCID;

    /**
     * 代理商代码
     */
    private String agentNo;

    /**
     * 客户类型
     */
    private String customerType;

    /**
     *
     */
    private String SMCGroupId;

    /**
     * 交易主体
     */
    private String tradeSubjectId;

    /**
     * 税号
     */
    private String taxNo;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 开户银行账号
     */
    private String accountNo;

    /**
     * 开票地址
     */
    private String invoiceAddress;

    /**
     * 开票联系人
     */
    private String invoicePhoneNo;

    /**
     * 开票类型
     */
    private String invoiceType;

    /**
     * 删除
     */
    private String delFlag;

    private String creTime;

    private String creator;

    private String modifyTime;

    private String modifier;

    private String cstmGrade;

    private String vipCodeCn;

    private String customerLevel;

    private String hlCode;

}
