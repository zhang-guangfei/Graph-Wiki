package com.sales.ops.dto.inquiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sales.ops.db.entity.OpsInquiryDetail;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单催促校验，子项明细返回Dto
 * @author B91717
 */
public class InquiryOrderDetailReturnDto extends OpsInquiryDetail implements Serializable {
    /**
     * 是否可催促
     */
    private Boolean canPress;

    /**
     * 返回不可催促原因
     */
    private String checkFailureMsg;

    private Integer inquiryCount; // 返回当前订单的催促次数

    private String  orderStatusDesc; // 当前订单状态翻译,即采购单状态的翻译

    // 门户新增催促结果描述字段
    private String inquiryResultDescription; //当返回的纳期>期望纳期时 就是超预期，反之为预期内

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date estimatedDeliveryDay; // 订单预计送达客户日

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date expectedDeliveryTime; // 订单工厂预计发货日

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

    public String getInquiryResultDescription() {
        return inquiryResultDescription;
    }

    public void setInquiryResultDescription(String inquiryResultDescription) {
        this.inquiryResultDescription = inquiryResultDescription;
    }

    public Date getEstimatedDeliveryDay() {
        return estimatedDeliveryDay;
    }

    public void setEstimatedDeliveryDay(Date estimatedDeliveryDay) {
        this.estimatedDeliveryDay = estimatedDeliveryDay;
    }

    public Date getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Date expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }
}
