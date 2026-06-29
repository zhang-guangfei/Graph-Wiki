package com.sales.ops.dto.inquiry;

import com.sales.ops.db.entity.OpsInquiryCodeConfig;

/**
 * @description: 配置原因带参数的封装
 * @author: B91717
 * @time: 2024/6/25 13:01
 */
public class InquiryCodeConfigByParam extends OpsInquiryCodeConfig{

    private String supplierId;

    private String supplierName;

    private String paramName;

    private String paramType;

    private String dataLength;

    private Boolean isFixedLength;

    private String otherTypeInfo;

    private String paramRemark;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }


    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public Boolean getFixedLength() {
        return isFixedLength;
    }

    public void setFixedLength(Boolean fixedLength) {
        isFixedLength = fixedLength;
    }

    public String getOtherTypeInfo() {
        return otherTypeInfo;
    }

    public void setOtherTypeInfo(String otherTypeInfo) {
        this.otherTypeInfo = otherTypeInfo;
    }

    public String getParamRemark() {
        return paramRemark;
    }

    public void setParamRemark(String paramRemark) {
        this.paramRemark = paramRemark;
    }
}
