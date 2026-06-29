package com.sales.ops.dto.inquiry;

import com.sales.ops.db.entity.OpsInquiryApply;

/**
 * 新增催促申请DTO
 * @author B91717
 */
public class InquiryAddDto extends OpsInquiryApply {
    /**
     * 是否可催促
     */
    private Boolean canPress;

    /**
     * 返回不可催促原因
     */
    private String checkFailureMsg;

    // 任务号
    private String taskNo;

    // 分配催促模块
    private String fptype;

    // 版本号
    private Integer version;

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getFptype() {
        return fptype;
    }

    public void setFptype(String fptype) {
        this.fptype = fptype;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getCanPress() {
        return canPress;
    }

    public void setCanPress(Boolean canPress) {
        this.canPress = canPress;
    }

    public String getCheckFailureMsg() {
        return checkFailureMsg;
    }

    public void setCheckFailureMsg(String checkFailureMsg) {
        this.checkFailureMsg = checkFailureMsg;
    }
}
