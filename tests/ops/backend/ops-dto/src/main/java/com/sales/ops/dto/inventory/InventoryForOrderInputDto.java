package com.sales.ops.dto.inventory;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 库存查询输入对象
 * @date 2021/10/25 19:23
 */
public class InventoryForOrderInputDto {

    private String inventoryStatus;//库存状态: 正常在库:N   采购在途:T1   转运在途:T2  调拨在途:T3  生产中P， 到货未入库:W    限定X
    private String warehouseCode;//仓库号
    private List<String> modelno;//型号：必填
    private String inventoryTypeCode;//在库类型
    private String customerNo;//客户号
    private String ppl;//PPL代码
    private String projectCode;//项目号
    private String groupCustomerNo;//客户群代码
    private String orderno;//采购单号

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }



    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public List<String> getModelno() {
        return modelno;
    }

    public void setModelno(List<String> modelno) {
        this.modelno = modelno;
    }
}
