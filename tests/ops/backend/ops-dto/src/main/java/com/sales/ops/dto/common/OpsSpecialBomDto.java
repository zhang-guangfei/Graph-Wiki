package com.sales.ops.dto.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $ bugid:17799
 * @description：
 * @date ：Created in 2025/6/13 10:10
 */
public class OpsSpecialBomDto implements Serializable {
    private static final long serialVersionUID = 7066224788218536532L;

    private String customerCode; // 客户代码
    private String modelNo;

    private Boolean bomType;  // true表示'S'(拆分), false表示'N'(不拆分)
    private Long bomId;
    private Boolean priorityComplete;  // true表示'1'(是), false表示'0'(否)
    private Boolean delFlag;  // true表示'1'(有效), false表示'0'(无效)
    private Date createTime;
    private Date updateTime;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Boolean getBomType() {
        return bomType;
    }

    public void setBomType(Boolean bomType) {
        this.bomType = bomType;
    }

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public Boolean getPriorityComplete() {
        return priorityComplete;
    }

    public void setPriorityComplete(Boolean priorityComplete) {
        this.priorityComplete = priorityComplete;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
