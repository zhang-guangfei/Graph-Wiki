package com.sales.ops.dto.inquiry;

import com.sales.ops.db.entity.OpsInquiryApply;

import java.io.Serializable;

/**
 * 订单催促校验，子项明细返回Dto
 * @author B91717
 */
public class InquiryOrderDetailDto extends OpsInquiryApply implements Serializable {
    /**
     * 是否可催促
     */
    private Boolean canPress;

    /**
     * 返回不可催促原因
     */
    private String checkFailureMsg;

    private String supplierId;

    private Integer inquiryCount; // 返回当前订单的催促次数

    private String  orderStatusDesc; // 当前订单状态翻译,即采购单状态的翻译

    // 门户新增催促结果描述字段
    private String inquiryResultDescription; //当返回的纳期>期望纳期时 就是超预期，反之为预期内

    private String rorderSplitno; // 带订单拆分单号的订单拆分项号，可能与采购的项号不一致，所以单独设置字段

    private String stockType;

    private String stockCode;

    private String inventoryTypeCode;

    private String inventoryDesc; // 前端显示的状态信息

    private String assignStatus; // 分配处理的状态

    // 新增前端 特殊返信纳期显示
    private String dlvmoddateStr;;

    public String getDlvmoddateStr() {
        return dlvmoddateStr;
    }

    public void setDlvmoddateStr(String dlvmoddateStr) {
        this.dlvmoddateStr = dlvmoddateStr;
    }


    public String getInventoryDesc() {
        return inventoryDesc;
    }

    public void setInventoryDesc(String inventoryDesc) {
        this.inventoryDesc = inventoryDesc;
    }

    public String getRorderSplitno() {
        return rorderSplitno;
    }

    public void setRorderSplitno(String rorderSplitno) {
        this.rorderSplitno = rorderSplitno;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }
    public String getInquiryResultDescription() {
        return inquiryResultDescription;
    }

    public void setInquiryResultDescription(String inquiryResultDescription) {
        this.inquiryResultDescription = inquiryResultDescription;
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
}
