package com.sales.ops.dto.easyexcel.delivery;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * bugid: 11007
 * @version: 1.0$
 * @description：发送barcode实体
 * @date ：Created in 2023/6/7 14:50
 */
public class BarSendExcelDto implements Serializable {
    private static final long serialVersionUID = 1775048478256668977L;

    @ExcelProperty("单号")
    private String orderFno;
    @ExcelProperty("型号")
    private String modelNo;
    @ExcelProperty("数量")
    private Integer quantity;
    @ExcelProperty("外箱")
    private String barcodeW;
    @ExcelProperty("内箱")
    private String barcodeN;
    @ExcelProperty("客户")
    private String customerNo;
    @ExcelProperty("user_no")
    private String userNo;
    @ExcelProperty("user名称")
    private String userName;
    @ExcelProperty("发货时间")
    private Date shipDate;
    @ExcelProperty("发运单号")
    private String expressNo;

    @ExcelProperty("承运商")
    private String expressCompany;
    @ExcelProperty("仓库")
    private String warehouseCode;
    @ExcelProperty("PO号")
    private String corderNo;
    @ExcelProperty("客户物料号")
    private String cproductNo;
    @ExcelProperty("营业所")
    private String deptName;
    @ExcelProperty("收货地址")
    private String address;
    @ExcelProperty("收货人")
    private String linkman;

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBarcodeW() {
        return barcodeW;
    }

    public void setBarcodeW(String barcodeW) {
        this.barcodeW = barcodeW;
    }

    public String getBarcodeN() {
        return barcodeN;
    }

    public void setBarcodeN(String barcodeN) {
        this.barcodeN = barcodeN;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
}
