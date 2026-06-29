package com.sales.ops.dto.ips;

/**
 * @description: 供应商信息
*         "supplierConnector": "", // 供应商联系人
*         "supplierConnectorEmail": "",// 供应商联络邮箱
*
 * @author: B91717
 * @time: 2024/12/17 16:29
 */
public class IpsSupplierInfoDto {

    private String supplierConnector;

    private String supplierConnectorEmail;

    // 2025-10-20 海外发单改造，通过PSI发送，增加字段
    private Integer supplierPaymentday;

    private String supplierpartno;

    private String supplierAllocationPriority; // 指定供应商分配优先级 （0强制、1供应系统默认分配）

    public String getSupplierAllocationPriority() {
        return supplierAllocationPriority;
    }

    public void setSupplierAllocationPriority(String supplierAllocationPriority) {
        this.supplierAllocationPriority = supplierAllocationPriority;
    }

    public Integer getSupplierPaymentday() {
        return supplierPaymentday;
    }

    public void setSupplierPaymentday(Integer supplierPaymentday) {
        this.supplierPaymentday = supplierPaymentday;
    }

    public String getSupplierpartno() {
        return supplierpartno;
    }

    public void setSupplierpartno(String supplierpartno) {
        this.supplierpartno = supplierpartno;
    }

    public String getSupplierConnector() {
        return supplierConnector;
    }

    public void setSupplierConnector(String supplierConnector) {
        this.supplierConnector = supplierConnector;
    }

    public String getSupplierConnectorEmail() {
        return supplierConnectorEmail;
    }

    public void setSupplierConnectorEmail(String supplierConnectorEmail) {
        this.supplierConnectorEmail = supplierConnectorEmail;
    }
}
