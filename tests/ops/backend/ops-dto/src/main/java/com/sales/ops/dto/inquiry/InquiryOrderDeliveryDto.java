package com.sales.ops.dto.inquiry;

import java.util.List;

/**
 * @description:
 * @author: B91717
 * @time: 2024/10/9 13:35
 */
public class InquiryOrderDeliveryDto {
    private String orderNo;

    private String hopeDeliveryDate;

    private List<InquiryOrderDetailDto> inquiryOrderDetails;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHopeDeliveryDate() {
        return hopeDeliveryDate;
    }

    public void setHopeDeliveryDate(String hopeDeliveryDate) {
        this.hopeDeliveryDate = hopeDeliveryDate;
    }

    public List<InquiryOrderDetailDto> getInquiryOrderDetails() {
        return inquiryOrderDetails;
    }

    public void setInquiryOrderDetails(List<InquiryOrderDetailDto> inquiryOrderDetails) {
        this.inquiryOrderDetails = inquiryOrderDetails;
    }
}
