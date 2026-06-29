package com.sales.ops.api.dto.tms;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description： 7.1 承运商信息回传
 * @date ：Created in 2021/12/27 16:14
 */
public class CarrierInfoDto implements Serializable {

    private static final long serialVersionUID = -5316472481840408324L;

    private String carrierId;    //承运商ID
    private String carrierName;    //承运商名称
    private String carrierType;    //承运人类型
    private String shortName;    //简称
    private String tel1;    //电话1
    private String tel2;    //电话2
    private String invoiceType;    //发票类型
    private String startTime;    //合作开始时间
    private String endTime;    //合作结束时间
    private String address;    //地址
    private String contact;    //联系人
    private String Fax;    //传真
    private String zipCode;    //邮编
    private String emailAddress;    //邮箱
    private String branchId;    //机构
    private String offeringType;    //运输方式
    private String serviceType;    //服务类型
    private String leadTime;    //运输时长
    private String province;    //省份
    private String city;    //城市
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;    //新增时间
    private String addWho;    //新增人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;    //编辑时间
    private String editWho;    //编辑人
    private String udf04;    //自定义04
    private String udf05;    //自定义05
    private String udf06;    //自定义06
    private String udf07;    //自定义07
    private String udf08;    //自定义08
    private String udf09;    //自定义09
    private String udf10;    //自定义10

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getOfferingType() {
        return offeringType;
    }

    public void setOfferingType(String offeringType) {
        this.offeringType = offeringType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddWho() {
        return addWho;
    }

    public void setAddWho(String addWho) {
        this.addWho = addWho;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEditWho() {
        return editWho;
    }

    public void setEditWho(String editWho) {
        this.editWho = editWho;
    }

    public String getUdf04() {
        return udf04;
    }

    public void setUdf04(String udf04) {
        this.udf04 = udf04;
    }

    public String getUdf05() {
        return udf05;
    }

    public void setUdf05(String udf05) {
        this.udf05 = udf05;
    }

    public String getUdf06() {
        return udf06;
    }

    public void setUdf06(String udf06) {
        this.udf06 = udf06;
    }

    public String getUdf07() {
        return udf07;
    }

    public void setUdf07(String udf07) {
        this.udf07 = udf07;
    }

    public String getUdf08() {
        return udf08;
    }

    public void setUdf08(String udf08) {
        this.udf08 = udf08;
    }

    public String getUdf09() {
        return udf09;
    }

    public void setUdf09(String udf09) {
        this.udf09 = udf09;
    }

    public String getUdf10() {
        return udf10;
    }

    public void setUdf10(String udf10) {
        this.udf10 = udf10;
    }
}
