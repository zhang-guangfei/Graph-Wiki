package com.sales.ops.dto.inquiry;

import java.io.Serializable;

/**
 * INQA,新增时的集合
 *
 * @author B91717
 */
public class InquiryApplyAddDto implements Serializable {
    private InquiryApplyAddParam inquiryApplyAddParam;

    private InquiryApplyVerifyReurn inquiryApplyVerifyReurn;

    public InquiryApplyAddParam getInquiryApplyAddParam() {
        return inquiryApplyAddParam;
    }

    public void setInquiryApplyAddParam(InquiryApplyAddParam inquiryApplyAddParam) {
        this.inquiryApplyAddParam = inquiryApplyAddParam;
    }

    public InquiryApplyVerifyReurn getInquiryApplyVerifyReurn() {
        return inquiryApplyVerifyReurn;
    }

    public void setInquiryApplyVerifyReurn(InquiryApplyVerifyReurn inquiryApplyVerifyReurn) {
        this.inquiryApplyVerifyReurn = inquiryApplyVerifyReurn;
    }
}
