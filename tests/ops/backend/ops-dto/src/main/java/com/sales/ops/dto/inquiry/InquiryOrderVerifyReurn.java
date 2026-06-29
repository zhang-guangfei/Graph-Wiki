package com.sales.ops.dto.inquiry;

import java.io.Serializable;
import java.util.List;

/**
 * 订单催促校验，返回信息集合
 * @author B91717
 */
public class InquiryOrderVerifyReurn implements Serializable {
    private static final long serialVersionUID = 1L;
    private InquiryOrderMasterDto inquiryOrderMaster; // 主单

    private List<InquiryOrderDetailDto> inquiryOrderDetails; //子项

    public InquiryOrderMasterDto getInquiryOrderMaster() {
        return inquiryOrderMaster;
    }

    public void setInquiryOrderMaster(InquiryOrderMasterDto inquiryOrderMaster) {
        this.inquiryOrderMaster = inquiryOrderMaster;
    }

    public List<InquiryOrderDetailDto> getInquiryOrderDetails() {
        return inquiryOrderDetails;
    }

    public void setInquiryOrderDetails(List<InquiryOrderDetailDto> inquiryOrderDetails) {
        this.inquiryOrderDetails = inquiryOrderDetails;
    }
}
