package com.sales.ops.dto.query;

import java.util.Date;
import java.util.List;

/**
 * @author B91717
 * @date 2022/2/11 9:19
 */
public class RequestPurchaseQO {

    private List<String> deptNos;

    private List<String> supplierids;

    private String orderno;

    private String modelno;

    private String purchasetype;

    private String supplierid;

    private String transtype;

    private String customerno;

    private  String interceptmsg;

    private  String statecode;

    private  String ordtype;

    private String corderno;

    private String purchasewarehouseid;

    // 操作者
    private String operator;

    // bug14609 WTSR2024000608-添加海外订单导出数据的新需求,增加批次号的筛选条件
    private String sendversion;

    private String poOrderNo;

    // bug14609 WTSR2024000608-添加海外订单导出数据的新需求,增加请购日期的筛选
    private Date requesTimeStart;

    private Date requesTimeEnd;

    public Date getRequesTimeStart() {
        return requesTimeStart;
    }

    public void setRequesTimeStart(Date requesTimeStart) {
        this.requesTimeStart = requesTimeStart;
    }

    public Date getRequesTimeEnd() {
        return requesTimeEnd;
    }

    public void setRequesTimeEnd(Date requesTimeEnd) {
        this.requesTimeEnd = requesTimeEnd;
    }

    public String getSendversion() {
        return sendversion;
    }

    public void setSendversion(String sendversion) {
        this.sendversion = sendversion;
    }

    public String getPoOrderNo() {
        return poOrderNo;
    }

    public void setPoOrderNo(String poOrderNo) {
        this.poOrderNo = poOrderNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<String> getSupplierids() {
        return supplierids;
    }

    public void setSupplierids(List<String> supplierids) {
        this.supplierids = supplierids;
    }

    public String getPurchasewarehouseid() {
        return purchasewarehouseid;
    }

    public void setPurchasewarehouseid(String purchasewarehouseid) {
        this.purchasewarehouseid = purchasewarehouseid;
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

    public String getInterceptmsg() {
        return interceptmsg;
    }

    public void setInterceptmsg(String interceptmsg) {
        this.interceptmsg = interceptmsg;
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

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }
}
