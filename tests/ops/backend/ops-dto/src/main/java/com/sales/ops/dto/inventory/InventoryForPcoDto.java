package com.sales.ops.dto.inventory;

import java.io.Serializable;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 加工单
 * @date 2021/10/15 13:41
 */
public class InventoryForPcoDto implements Serializable {

    private String orderId;
    private String warehouseCode;

    private String modelno;
    private String customerNo;
    private String ppl;
    private String projectCode;
    private String groupCustomerNo;
    private Integer qty;

    private List<InventoryForAdjustDto> submodels;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public List<InventoryForAdjustDto> getSubmodels() {
        return submodels;
    }

    public void setSubmodels(List<InventoryForAdjustDto> submodels) {
        this.submodels = submodels;
    }
}
