package com.sales.ops.dto.inquiry;

import java.io.Serializable;

/**
 * 门户催促校验，返回信息的集合
 * 2024-09-25
 * @author B91717
 */
public class InquirySalesApplyReurn implements Serializable {
    private static final long serialVersionUID = 1L;

    private String inquiryType; //催促类别，订单催促：1，销售催促：0

    private InquiryApplyVerifyReurn inquiryApplyVerifyReurn; // 采购催促返回信息

    private InquiryOrderReurn InquiryOrderReurn; // 订单催促返回信息

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public InquiryApplyVerifyReurn getInquiryApplyVerifyReurn() {
        return inquiryApplyVerifyReurn;
    }

    public void setInquiryApplyVerifyReurn(InquiryApplyVerifyReurn inquiryApplyVerifyReurn) {
        this.inquiryApplyVerifyReurn = inquiryApplyVerifyReurn;
    }

    public com.sales.ops.dto.inquiry.InquiryOrderReurn getInquiryOrderReurn() {
        return InquiryOrderReurn;
    }

    public void setInquiryOrderReurn(com.sales.ops.dto.inquiry.InquiryOrderReurn inquiryOrderReurn) {
        InquiryOrderReurn = inquiryOrderReurn;
    }
}
