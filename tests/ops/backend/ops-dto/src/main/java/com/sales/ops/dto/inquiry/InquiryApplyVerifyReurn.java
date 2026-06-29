package com.sales.ops.dto.inquiry;

import com.sales.ops.db.entity.OpsInquiryApply;

import java.io.Serializable;

/**
 * 采购催促校验，返回信息集合
 *
 * @author B91717
 */
public class InquiryApplyVerifyReurn extends OpsInquiryApply implements Serializable {
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

    // 门户新增催促结果描述字段
    private String inquiryResultDescription; //当返回的纳期>期望纳期时 就是超预期，反之为预期内

    private Integer itemno; // 2024-03-26 门户申请时，拆分为门户的子项号

    // 新增前端 特殊返信纳期显示
    private String dlvmoddateStr;;

    public String getDlvmoddateStr() {
        return dlvmoddateStr;
    }

    public void setDlvmoddateStr(String dlvmoddateStr) {
        this.dlvmoddateStr = dlvmoddateStr;
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public String getInquiryResultDescription() {
        return inquiryResultDescription;
    }

    public void setInquiryResultDescription(String inquiryResultDescription) {
        this.inquiryResultDescription = inquiryResultDescription;
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

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public Integer getInquiryCount() {
        return inquiryCount;
    }

    public void setInquiryCount(Integer inquiryCount) {
        this.inquiryCount = inquiryCount;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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
}
