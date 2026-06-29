package com.sales.ops.dto.flux;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 交接确认
 * @date 2022/1/26 15:37
 */
public class HandConfirm {

    private String invoice;
    //发出仓
    private String warehouseCode;
    //发出仓
    private String fromWarehouseCode;
    //目的仓
    private String toWarehouseCode;
    //承运商代码
    private String logisticsCode;
    //运单号
    private String expressCode;
    //快递公司
    private String expressCompany;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date hangdate;
    private List<HandItem> handlist;

    public HandConfirm() {
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getFromWarehouseCode() {
        return fromWarehouseCode;
    }

    public void setFromWarehouseCode(String fromWarehouseCode) {
        this.fromWarehouseCode = fromWarehouseCode;
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }

    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public Date getHangdate() {
        return hangdate;
    }

    public void setHangdate(Date hangdate) {
        this.hangdate = hangdate;
    }

    public List<HandItem> getHandlist() {
        return handlist;
    }

    public void setHandlist(List<HandItem> handlist) {
        this.handlist = handlist;
    }


}
