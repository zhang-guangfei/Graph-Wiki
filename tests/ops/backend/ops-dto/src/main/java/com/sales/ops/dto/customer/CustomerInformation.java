package com.sales.ops.dto.customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CustomerInformation implements Serializable {
    // 客户代码
    private String customerNo;
    // 客户名称
    private String customerName;
    // 部门代码
    private String hrUnitId;
    // 部门名称
    private String hrUnitName;
    // 客户担当
    private String userId;
    // 代理店代码
    private String agentNo;
    // 客户类型
    private String customerType;
    // 集团内客户Id
    private String SMCGroupId;
    // 交易主体
    private String tradeSubjectId;
    // 税号
    private String taxNo;
    // 开户银行
    private String bank;
    // 账号
    private String accountNo;
    // 开票地址
    private String invoiceAddress;
    // 开票电话
    private String invoicePhoneNo;
    // 支付期限
    private Integer creditTerm;
    // 信用等级
    private String creditGrade;
    // 付款条件
    private String payTerm;
    // 信用额度
    private BigDecimal creditLimit;
    // 宽限额度
    private BigDecimal graceLimit;
    // 调整额度
    private BigDecimal adjustLimit;
    // 客户共享信息
    private List<CustomerShareAddress> customerShareInfo;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getHrUnitId() {
        return hrUnitId;
    }

    public void setHrUnitId(String hrUnitId) {
        this.hrUnitId = hrUnitId;
    }

    public String getHrUnitName() {
        return hrUnitName;
    }

    public void setHrUnitName(String hrUnitName) {
        this.hrUnitName = hrUnitName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAgentNo() {
        return agentNo;
    }

    public void setAgentNo(String agentNo) {
        this.agentNo = agentNo;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getSMCGroupId() {
        return SMCGroupId;
    }

    public void setSMCGroupId(String SMCGroupId) {
        this.SMCGroupId = SMCGroupId;
    }

    public String getTradeSubjectId() {
        return tradeSubjectId;
    }

    public void setTradeSubjectId(String tradeSubjectId) {
        this.tradeSubjectId = tradeSubjectId;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoicePhoneNo() {
        return invoicePhoneNo;
    }

    public void setInvoicePhoneNo(String invoicePhoneNo) {
        this.invoicePhoneNo = invoicePhoneNo;
    }

    public Integer getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(Integer creditTerm) {
        this.creditTerm = creditTerm;
    }

    public String getCreditGrade() {
        return creditGrade;
    }

    public void setCreditGrade(String creditGrade) {
        this.creditGrade = creditGrade;
    }

    public String getPayTerm() {
        return payTerm;
    }

    public void setPayTerm(String payTerm) {
        this.payTerm = payTerm;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getGraceLimit() {
        return graceLimit;
    }

    public void setGraceLimit(BigDecimal graceLimit) {
        this.graceLimit = graceLimit;
    }

    public BigDecimal getAdjustLimit() {
        return adjustLimit;
    }

    public void setAdjustLimit(BigDecimal adjustLimit) {
        this.adjustLimit = adjustLimit;
    }

    public List<CustomerShareAddress> getCustomerShareInfo() {
        return customerShareInfo;
    }

    public void setCustomerShareInfo(List<CustomerShareAddress> customerShareInfo) {
        this.customerShareInfo = customerShareInfo;
    }
}
