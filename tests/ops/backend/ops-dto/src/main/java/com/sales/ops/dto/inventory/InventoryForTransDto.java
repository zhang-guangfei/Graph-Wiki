package com.sales.ops.dto.inventory;

import java.util.Date;

/**
 * @author C02483
 * @version 1.0
 * @description: 操作Dto  调库单
 * @date 2021/10/15 13:37
 */
public class InventoryForTransDto {

    private String orderId;
    private Integer itemNo;

    /*采购标识*/
    private Boolean cgFlag;
    /*源调整信息*/
    private String  smodelno;
    private String  swarehouseCode;
    private String  sPropertyTypeCode;
    private Long sPropertyId;
    private String  scustomerNo;
    private String  sppl;
    private String  sprojectCode;
    private String  sgroupCustomerNo;
    private String  ssalesInfoNo;
    private Integer sqty;

    /*目标调整信息*/
    private String  tmodelno;
    private String  twarehouseCode;
    private String  tPropertyTypeCode;
    private Long tPropertyId;
    private String  tcustomerNo;
    private String  tppl;
    private String  tprojectCode;
    private String  tgroupCustomerNo;
    private String  tsalesInfoNo;
    private Integer tqty;
    private Date wlDdate;//指定物流交货期


    private String corderNo;
    private String cproductNo;

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo;
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo;
    }

    public Date getWlDdate() {
        return wlDdate;
    }

    public void setWlDdate(Date wlDdate) {
        this.wlDdate = wlDdate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Boolean getCgFlag() {
        return cgFlag;
    }

    public void setCgFlag(Boolean cgFlag) {
        this.cgFlag = cgFlag;
    }

    public String getSmodelno() {
        return smodelno;
    }

    public void setSmodelno(String smodelno) {
        this.smodelno = smodelno;
    }

    public String getSwarehouseCode() {
        return swarehouseCode;
    }

    public void setSwarehouseCode(String swarehouseCode) {
        this.swarehouseCode = swarehouseCode;
    }

    public String getScustomerNo() {
        return scustomerNo;
    }

    public void setScustomerNo(String scustomerNo) {
        this.scustomerNo = scustomerNo;
    }

    public String getSppl() {
        return sppl;
    }

    public void setSppl(String sppl) {
        this.sppl = sppl;
    }

    public String getSprojectCode() {
        return sprojectCode;
    }

    public void setSprojectCode(String sprojectCode) {
        this.sprojectCode = sprojectCode;
    }

    public String getSgroupCustomerNo() {
        return sgroupCustomerNo;
    }

    public void setSgroupCustomerNo(String sgroupCustomerNo) {
        this.sgroupCustomerNo = sgroupCustomerNo;
    }

    public String getSsalesInfoNo() {
        return ssalesInfoNo;
    }

    public void setSsalesInfoNo(String ssalesInfoNo) {
        this.ssalesInfoNo = ssalesInfoNo;
    }

    public Integer getSqty() {
        return sqty;
    }

    public void setSqty(Integer sqty) {
        this.sqty = sqty;
    }

    public String getTmodelno() {
        return tmodelno;
    }

    public void setTmodelno(String tmodelno) {
        this.tmodelno = tmodelno;
    }

    public String getTwarehouseCode() {
        return twarehouseCode;
    }

    public void setTwarehouseCode(String twarehouseCode) {
        this.twarehouseCode = twarehouseCode;
    }

    public String getTcustomerNo() {
        return tcustomerNo;
    }

    public void setTcustomerNo(String tcustomerNo) {
        this.tcustomerNo = tcustomerNo;
    }

    public String getTppl() {
        return tppl;
    }

    public void setTppl(String tppl) {
        this.tppl = tppl;
    }

    public String getTprojectCode() {
        return tprojectCode;
    }

    public void setTprojectCode(String tprojectCode) {
        this.tprojectCode = tprojectCode;
    }

    public String getTgroupCustomerNo() {
        return tgroupCustomerNo;
    }

    public void setTgroupCustomerNo(String tgroupCustomerNo) {
        this.tgroupCustomerNo = tgroupCustomerNo;
    }

    public String getTsalesInfoNo() {
        return tsalesInfoNo;
    }

    public void setTsalesInfoNo(String tsalesInfoNo) {
        this.tsalesInfoNo = tsalesInfoNo;
    }

    public Integer getTqty() {
        return tqty;
    }

    public void setTqty(Integer tqty) {
        this.tqty = tqty;
    }

    public String getsPropertyTypeCode() {
        return sPropertyTypeCode;
    }

    public void setsPropertyTypeCode(String sPropertyTypeCode) {
        this.sPropertyTypeCode = sPropertyTypeCode;
    }

    public String gettPropertyTypeCode() {
        return tPropertyTypeCode;
    }

    public void settPropertyTypeCode(String tPropertyTypeCode) {
        this.tPropertyTypeCode = tPropertyTypeCode;
    }

    public Long getsPropertyId() {
        return sPropertyId;
    }

    public void setsPropertyId(Long sPropertyId) {
        this.sPropertyId = sPropertyId;
    }

    public Long gettPropertyId() {
        return tPropertyId;
    }

    public void settPropertyId(Long tPropertyId) {
        this.tPropertyId = tPropertyId;
    }
}
