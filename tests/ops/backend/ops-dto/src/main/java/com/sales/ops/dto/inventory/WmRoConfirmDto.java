package com.sales.ops.dto.inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sales.ops.dto.util.UserDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：收货单收货确认参数
 * @date ：Created in 2021/10/20 12:04
 */
public class WmRoConfirmDto implements Serializable {

    private static final long serialVersionUID = 2293516764085310567L;

    /**
     * 物流收货指令号
     */
    private String receiveOrderCode;

    /**
     * 仓库ID
     */
    private String warehouseCode;
    /**
     * WMS单号(唯一码，用于判断富勒请求)
     */
    private String wmsOrderCode;
    /**
     * 收货编码 箱码 barcode 或托号
     */
    private String receiveCode;

    /**
     * 收货方式(1扫箱/2扫托)
     */
    private String scanType;
    /**
     * 实收数量
     */
    private String qty;
    /**
     * 质量状态 ZP=正品, CC=残次, JS=机损, XS=箱损, 默认为ZP
     */
    private String inventoryType;
    /**
     * //消息ID，幂等性校验用
     */
//    private String msgId;

    private UserDto userDto;
    /**
     * 型號
     */
    private String modelNo;

    /**
     * 收货人
     */
    private String username;

    /**
     * 收货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date receiveTime;

    private String invoiceNo;

    private Long invoiceId;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getReceiveOrderCode() {
        return receiveOrderCode;
    }

    public void setReceiveOrderCode(String receiveOrderCode) {
        this.receiveOrderCode = receiveOrderCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWmsOrderCode() {
        return wmsOrderCode;
    }

    public void setWmsOrderCode(String wmsOrderCode) {
        this.wmsOrderCode = wmsOrderCode;
    }


    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}
