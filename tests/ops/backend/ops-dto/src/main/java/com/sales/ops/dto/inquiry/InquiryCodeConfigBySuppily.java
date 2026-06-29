package com.sales.ops.dto.inquiry;

import com.sales.ops.db.entity.OpsInquiryCodeConfig;

import java.util.List;

/**
 * @description: 根据不同供应商返回可选的催促原因配置
 * @author: B91717
 * @time: 2024/5/9 13:01
 */
public class InquiryCodeConfigBySuppily {

    private String supplierCode; // 供应商

    private List<OpsInquiryCodeConfig> opsInquiryCodeConfigs;

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public List<OpsInquiryCodeConfig> getOpsInquiryCodeConfigs() {
        return opsInquiryCodeConfigs;
    }

    public void setOpsInquiryCodeConfigs(List<OpsInquiryCodeConfig> opsInquiryCodeConfigs) {
        this.opsInquiryCodeConfigs = opsInquiryCodeConfigs;
    }
}
