package com.sales.ops.dto.purchase;

import java.util.Date;

/**
 * @author C12961
 * @date 2022/6/22 9:55
 */
public class DlvModDateDto {
    private Date dlvModDate;
    private Date updateTime;
    private String reasonremark;

    private String dlvModDateStr;

    public String getDlvModDateStr() {
        return dlvModDateStr;
    }

    public void setDlvModDateStr(String dlvModDateStr) {
        this.dlvModDateStr = dlvModDateStr;
    }

    public Date getDlvModDate() {
        return dlvModDate;
    }

    public void setDlvModDate(Date dlvModDate) {
        this.dlvModDate = dlvModDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReasonremark() {
        return reasonremark;
    }

    public void setReasonremark(String reasonremark) {
        this.reasonremark = reasonremark;
    }
}
