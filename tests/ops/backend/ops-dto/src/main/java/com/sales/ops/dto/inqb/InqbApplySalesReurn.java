package com.sales.ops.dto.inqb;

import java.io.Serializable;

/**
 * 采购催促校验，返回信息集合
 *
 * @author B91717
 */
public class InqbApplySalesReurn  implements Serializable {
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

    private String supplierId;

    private Integer inquiryCount; // 返回当前订单的催促次数

    private String  orderStatusDesc; // 当前订单状态翻译

    // 门户新增时，返回的催促状态
    private String status;
    // 门户新增时,催促状态描述
    private String statusDescription;

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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getInquiryCount() {
        return inquiryCount;
    }

    public void setInquiryCount(Integer inquiryCount) {
        this.inquiryCount = inquiryCount;
    }

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
