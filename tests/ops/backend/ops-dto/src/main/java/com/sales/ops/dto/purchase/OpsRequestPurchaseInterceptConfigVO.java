package com.sales.ops.dto.purchase;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2024/9/29 13:05
 * @Descripton TODO
 */
public class OpsRequestPurchaseInterceptConfigVO {
    private Integer id;

    private String typeid;

    private String rulekey;
    //bugid:17646 c14717 20250522
    private String ruleKey1;

    /**
     * 自定义拦截 数量
     */
    private Integer ruleKey2;

    /**
     * 是否自动出库
     */
    private Boolean autoStockOut;

    private String reason;

    private String remark;

    private Boolean enable;

    private String defaultaction;

    private String operator;

    private Date updatetime;

    private List<String> rulekeys;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getRulekey() {
        return rulekey;
    }

    public void setRulekey(String rulekey) {
        this.rulekey = rulekey;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDefaultaction() {
        return defaultaction;
    }

    public void setDefaultaction(String defaultaction) {
        this.defaultaction = defaultaction;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public List<String> getRulekeys() {
        return rulekeys;
    }

    public void setRulekeys(List<String> rulekeys) {
        this.rulekeys = rulekeys;
    }

    public String getRuleKey1() {
        return ruleKey1;
    }

    public void setRuleKey1(String ruleKey1) {
        this.ruleKey1 = ruleKey1;
    }

    public Integer getRuleKey2() {
        return ruleKey2;
    }

    public void setRuleKey2(Integer ruleKey2) {
        this.ruleKey2 = ruleKey2;
    }

    public Boolean getAutoStockOut() {
        return autoStockOut;
    }

    public void setAutoStockOut(Boolean autoStockOut) {
        this.autoStockOut = autoStockOut;
    }
}
