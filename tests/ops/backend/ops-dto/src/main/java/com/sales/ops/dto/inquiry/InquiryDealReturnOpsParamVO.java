package com.sales.ops.dto.inquiry;

/**
 * @Description: 回调门户传参实体
 * @Author B98075
 * @Date 2023/08/10
 */
public class InquiryDealReturnOpsParamVO {

    private Integer applyType;

    private InquiryDealReturnOpsParam dealReturnOpsParam;

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public InquiryDealReturnOpsParam getDealReturnOpsParam() {
        return dealReturnOpsParam;
    }

    public void setDealReturnOpsParam(InquiryDealReturnOpsParam dealReturnOpsParam) {
        this.dealReturnOpsParam = dealReturnOpsParam;
    }
}
