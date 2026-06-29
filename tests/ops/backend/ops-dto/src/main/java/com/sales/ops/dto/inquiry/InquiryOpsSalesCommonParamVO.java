package com.sales.ops.dto.inquiry;

/**
 * @Author lyc
 * @Date 2023/8/2 11:10
 * @Descripton TODO
 */
public class InquiryOpsSalesCommonParamVO {
    private Object data;
    private String batchNo;
    private String businessCode;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
