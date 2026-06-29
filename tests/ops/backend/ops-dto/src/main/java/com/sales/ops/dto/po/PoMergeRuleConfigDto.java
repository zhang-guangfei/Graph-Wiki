package com.sales.ops.dto.po;

import java.io.Serializable;
import java.util.Date;

/**
 * bugid:17619
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/5/13 15:51
 */
public class PoMergeRuleConfigDto implements Serializable {
    private static final long serialVersionUID = -5488584893065366058L;

    private String ruleCode;

    private String ruleParam;

    private String ruleDesc;

    private String modifier;

    private Date modify_time;

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleParam() {
        return ruleParam;
    }

    public void setRuleParam(String ruleParam) {
        this.ruleParam = ruleParam;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }
}
