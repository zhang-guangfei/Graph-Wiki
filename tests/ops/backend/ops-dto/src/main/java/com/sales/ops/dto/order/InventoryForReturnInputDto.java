package com.sales.ops.dto.order;

/**
 * 退货接口参数
 * @author C12961
 * @date 2022/1/18 9:58
 */
public class InventoryForReturnInputDto {
    //必填项
    //客户单号
    private String orderId;
    private String orderItem;
    //发票号
    private String invoiceNo;
    //仓库代码
    private String warehouseCode;
    //客户号
    private String customerNo;
    //退货原因
    private String reason;
    //数量
    private Integer qty;
    //型号
    private String modelno;
    //DK:大库\ZB:专备
    private String warehouseType;


    //选填项
    //承运商
    private String carried;
    //来货运单号
    private String expressCode;
    //用户代码
    private String userNo;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}
