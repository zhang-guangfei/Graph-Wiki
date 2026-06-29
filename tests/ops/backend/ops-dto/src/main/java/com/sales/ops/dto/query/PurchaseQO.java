package com.sales.ops.dto.query;

import java.util.Date;
import java.util.List;

/**
 * @author B91717
 * @date 2022/2/11 9:19
 */
public class PurchaseQO {

    private List<String> deptNos;

    private String orderno;

    private String modelno;

    private String purchasetype;

    private String statecode;

    private String customerno;

    private Date purchaseDateStart;

    private Date purchaseDateEnd;

    private String supplierid;

    private  String ordtype;

    private String corderno;

    private String receivewarehouseid;

    private String sendversion; //bug14609 新增批次号筛选条件

    public String getSendversion() {
        return sendversion;
    }

    public void setSendversion(String sendversion) {
        this.sendversion = sendversion;
    }

    public String getReceivewarehouseid() {
        return receivewarehouseid;
    }

    public void setReceivewarehouseid(String receivewarehouseid) {
        this.receivewarehouseid = receivewarehouseid;
    }

    public String getCorderno() {
        return corderno;
    }

    public void setCorderno(String corderno) {
        this.corderno = corderno;
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public Date getPurchaseDateStart() {
        return purchaseDateStart;
    }

    public void setPurchaseDateStart(Date purchaseDateStart) {
        this.purchaseDateStart = purchaseDateStart;
    }

    public Date getPurchaseDateEnd() {
        return purchaseDateEnd;
    }

    public void setPurchaseDateEnd(Date purchaseDateEnd) {
        this.purchaseDateEnd = purchaseDateEnd;
    }

    public List<String> getDeptNos() {
        return deptNos;
    }

    public void setDeptNos(List<String> deptNos) {
        this.deptNos = deptNos;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getPurchasetype() {
        return purchasetype;
    }

    public void setPurchasetype(String purchasetype) {
        this.purchasetype = purchasetype;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }
}
