package com.sales.ops.dto.inquiry;

import java.io.Serializable;

/**
 * INQA,新增时的集合
 *
 * @author B91717
 */
public class InquiryOrderApplyAddDto implements Serializable {
    private InquiryApplyAddParam inquiryApplyAddParam;

    private InquiryOrderVerifyReurn inquiryOrderVerifyReurn;

    public InquiryApplyAddParam getInquiryApplyAddParam() {
        return inquiryApplyAddParam;
    }

    public InquiryOrderVerifyReurn getInquiryOrderVerifyReurn() {
        return inquiryOrderVerifyReurn;
    }

    public void setInquiryOrderVerifyReurn(InquiryOrderVerifyReurn inquiryOrderVerifyReurn) {
        this.inquiryOrderVerifyReurn = inquiryOrderVerifyReurn;
    }

    public void setInquiryApplyAddParam(InquiryApplyAddParam inquiryApplyAddParam) {
        this.inquiryApplyAddParam = inquiryApplyAddParam;
    }


}
