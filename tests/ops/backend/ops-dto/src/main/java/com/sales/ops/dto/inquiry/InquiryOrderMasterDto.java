package com.sales.ops.dto.inquiry;

import com.sales.ops.db.entity.OpsInquiryApply;

import java.io.Serializable;

/**
 * 订单催促校验，主项Dto
 * @author B91717
 */
public class InquiryOrderMasterDto extends OpsInquiryApply implements Serializable {
    /**
     * 是否可催促
     */
    private Boolean canPress;
    /**
     * 返回不可催促原因
     */
    private String checkFailureMsg;

    private Integer inquiryCount; // 返回当前订单的催促次数

    private String  orderStatusDesc; // 当前订单状态翻译

    // 门户新增时，返回的催促状态
    private String status;
    // 门户新增时,催促状态描述
    private String statusDescription;

    // 门户新增催促结果描述字段
    private String inquiryResultDescription; //当返回的纳期>期望纳期时 就是超预期，反之为预期内
    /**
     * 库存代码
     */
    private String stockCode;

    /**
     * 库存类别
     */
    private String stockType;

    private String stockDesc;



    private String userno;

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
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

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
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

    public String getInquiryResultDescription() {
        return inquiryResultDescription;
    }

    public void setInquiryResultDescription(String inquiryResultDescription) {
        this.inquiryResultDescription = inquiryResultDescription;
    }

    public String getStockDesc() {
        return stockDesc;
    }

    public void setStockDesc(String stockDesc) {
        this.stockDesc = stockDesc;
    }
}
