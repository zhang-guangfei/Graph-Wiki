package com.sales.ops.api.dto.wms;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：Ro_TO_WMS 接口文档4.3
 * @date ：Created in 2021/10/28 16:14
 */
public class DocAsnHeaderDto implements Serializable {
    private static final long serialVersionUID = 7172466405057644707L;

    private List<DocAsnDetailsDto> details;

    private String warehouseId;// 所属仓库 必填
    private String customerId;//  货主 id true
    private String asnType;//订单类型 true
    private String docNo;//上游来源订单 true
    private String asnReferenceA; //ASN参考信息 false
    private String asnReferenceB; //ASN参考信息 false
    private String asnReferenceC; //ASN参考信息 false
    private String asnReferenceD; //ASN参考信息 false
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date asnCreationTime;//ASN创建时间 false
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectedArriveTime1;//预期到货时间段从 false
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectedArriveTime2;//预期到货时间段到 false

    private String supplierId; //供应商ID false
    private String supplierName; //供应商名称 false
    private String supplierAddress1; //供应商地址 false
    private String supplierAddress2; //供应商地址 false
    private String supplierAddress3; //供应商地址 false
    private String supplierAddress4; //供应商地址 false
    private String supplierCountry;//供应商国家 false
    private String supplierProvince;//供应商省份 false
    private String supplierCity;//供应商城市 false
    private String supplierDistrict;//供应商区/镇 false
    private String supplierStreet;//供应商街道 false
    private String supplierContact; //供应商联系人 false
    private String supplierFax;// 供应商传真 false
    private String supplierMail;//供应商电子邮件 false
    private String supplierTel1; //供应商移动电话 false
    private String supplierTel2;//供应商拱顶电话 false

    private String supplierZip;//供应商邮政编码；false
    private String carrierId;//承运人Id false
    private String carrierName;//承运人名称 false
    private String issuePartyId;//下单F方ID false
    private String issuePartyName;//下单方名称；false

    private String countryOfDestination;//目的国 false
    private String countryOfOrigin;//原产国 false
    private String followUp;//业务担当 false
    private String hedi01;//EDI相关信息 false
    private String hedi02;//EDI相关信息 false
    private String hedi03;//EDI相关信息 false
    private String hedi04;//EDI相关信息 false
    private String hedi05;//EDI相关信息 false
    private String hedi06;//EDI相关信息 false
    private String hedi07;//EDI相关信息 false
    private String hedi08;//EDI相关信息 false
    private String hedi09;//EDI相关信息 false
    private String hedi10;//EDI相关信息 false
    private String placeOfDischarge;//卸货地 false
    private String placeOfLoading; //装货地 false
    private String placeOfDelivery;//交货地 false
    private String priority;//优先级；false
    private String userDefine1; //用户自定义1 false
    private String userDefine2; //用户自定义1 false
    private String userDefine3; //用户自定义1 false
    private String userDefine4; //用户自定义1 false
    private String userDefine5; //用户自定义1 false
    private String userDefine6; //用户自定义1 false
    private String userDefine7; //用户自定义1 false
    private String userDefine8; //用户自定义1 false
    private String userDefine9; //用户自定义1 false
    private String userDefine10; //用户自定义1 false
    private String notes; //备注 false
    private String crossdockFlag;//越库标记 false


    public List<DocAsnDetailsDto> getDetails() {
        return details;
    }

    public void setDetails(List<DocAsnDetailsDto> details) {
        this.details = details;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAsnType() {
        return asnType;
    }

    public void setAsnType(String asnType) {
        this.asnType = asnType;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getAsnReferenceA() {
        return asnReferenceA;
    }

    public void setAsnReferenceA(String asnReferenceA) {
        this.asnReferenceA = asnReferenceA;
    }

    public String getAsnReferenceB() {
        return asnReferenceB;
    }

    public void setAsnReferenceB(String asnReferenceB) {
        this.asnReferenceB = asnReferenceB;
    }

    public String getAsnReferenceC() {
        return asnReferenceC;
    }

    public void setAsnReferenceC(String asnReferenceC) {
        this.asnReferenceC = asnReferenceC;
    }

    public String getAsnReferenceD() {
        return asnReferenceD;
    }

    public void setAsnReferenceD(String asnReferenceD) {
        this.asnReferenceD = asnReferenceD;
    }

    public Date getAsnCreationTime() {
        return asnCreationTime;
    }

    public void setAsnCreationTime(Date asnCreationTime) {
        this.asnCreationTime = asnCreationTime;
    }

    public Date getExpectedArriveTime1() {
        return expectedArriveTime1;
    }

    public void setExpectedArriveTime1(Date expectedArriveTime1) {
        this.expectedArriveTime1 = expectedArriveTime1;
    }

    public Date getExpectedArriveTime2() {
        return expectedArriveTime2;
    }

    public void setExpectedArriveTime2(Date expectedArriveTime2) {
        this.expectedArriveTime2 = expectedArriveTime2;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress1() {
        return supplierAddress1;
    }

    public void setSupplierAddress1(String supplierAddress1) {
        this.supplierAddress1 = supplierAddress1;
    }

    public String getSupplierAddress2() {
        return supplierAddress2;
    }

    public void setSupplierAddress2(String supplierAddress2) {
        this.supplierAddress2 = supplierAddress2;
    }

    public String getSupplierAddress3() {
        return supplierAddress3;
    }

    public void setSupplierAddress3(String supplierAddress3) {
        this.supplierAddress3 = supplierAddress3;
    }

    public String getSupplierAddress4() {
        return supplierAddress4;
    }

    public void setSupplierAddress4(String supplierAddress4) {
        this.supplierAddress4 = supplierAddress4;
    }

    public String getSupplierCountry() {
        return supplierCountry;
    }

    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }

    public String getSupplierProvince() {
        return supplierProvince;
    }

    public void setSupplierProvince(String supplierProvince) {
        this.supplierProvince = supplierProvince;
    }

    public String getSupplierCity() {
        return supplierCity;
    }

    public void setSupplierCity(String supplierCity) {
        this.supplierCity = supplierCity;
    }

    public String getSupplierDistrict() {
        return supplierDistrict;
    }

    public void setSupplierDistrict(String supplierDistrict) {
        this.supplierDistrict = supplierDistrict;
    }

    public String getSupplierStreet() {
        return supplierStreet;
    }

    public void setSupplierStreet(String supplierStreet) {
        this.supplierStreet = supplierStreet;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierFax() {
        return supplierFax;
    }

    public void setSupplierFax(String supplierFax) {
        this.supplierFax = supplierFax;
    }

    public String getSupplierMail() {
        return supplierMail;
    }

    public void setSupplierMail(String supplierMail) {
        this.supplierMail = supplierMail;
    }

    public String getSupplierTel1() {
        return supplierTel1;
    }

    public void setSupplierTel1(String supplierTel1) {
        this.supplierTel1 = supplierTel1;
    }

    public String getSupplierTel2() {
        return supplierTel2;
    }

    public void setSupplierTel2(String supplierTel2) {
        this.supplierTel2 = supplierTel2;
    }

    public String getSupplierZip() {
        return supplierZip;
    }

    public void setSupplierZip(String supplierZip) {
        this.supplierZip = supplierZip;
    }

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

    public String getIssuePartyId() {
        return issuePartyId;
    }

    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId;
    }

    public String getIssuePartyName() {
        return issuePartyName;
    }

    public void setIssuePartyName(String issuePartyName) {
        this.issuePartyName = issuePartyName;
    }

    public String getCountryOfDestination() {
        return countryOfDestination;
    }

    public void setCountryOfDestination(String countryOfDestination) {
        this.countryOfDestination = countryOfDestination;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getFollowUp() {
        return followUp;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    public String getHedi01() {
        return hedi01;
    }

    public void setHedi01(String hedi01) {
        this.hedi01 = hedi01;
    }

    public String getHedi02() {
        return hedi02;
    }

    public void setHedi02(String hedi02) {
        this.hedi02 = hedi02;
    }

    public String getHedi03() {
        return hedi03;
    }

    public void setHedi03(String hedi03) {
        this.hedi03 = hedi03;
    }

    public String getHedi04() {
        return hedi04;
    }

    public void setHedi04(String hedi04) {
        this.hedi04 = hedi04;
    }

    public String getHedi05() {
        return hedi05;
    }

    public void setHedi05(String hedi05) {
        this.hedi05 = hedi05;
    }

    public String getHedi06() {
        return hedi06;
    }

    public void setHedi06(String hedi06) {
        this.hedi06 = hedi06;
    }

    public String getHedi07() {
        return hedi07;
    }

    public void setHedi07(String hedi07) {
        this.hedi07 = hedi07;
    }

    public String getHedi08() {
        return hedi08;
    }

    public void setHedi08(String hedi08) {
        this.hedi08 = hedi08;
    }

    public String getHedi09() {
        return hedi09;
    }

    public void setHedi09(String hedi09) {
        this.hedi09 = hedi09;
    }

    public String getHedi10() {
        return hedi10;
    }

    public void setHedi10(String hedi10) {
        this.hedi10 = hedi10;
    }

    public String getPlaceOfDischarge() {
        return placeOfDischarge;
    }

    public void setPlaceOfDischarge(String placeOfDischarge) {
        this.placeOfDischarge = placeOfDischarge;
    }

    public String getPlaceOfLoading() {
        return placeOfLoading;
    }

    public void setPlaceOfLoading(String placeOfLoading) {
        this.placeOfLoading = placeOfLoading;
    }

    public String getPlaceOfDelivery() {
        return placeOfDelivery;
    }

    public void setPlaceOfDelivery(String placeOfDelivery) {
        this.placeOfDelivery = placeOfDelivery;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUserDefine1() {
        return userDefine1;
    }

    public void setUserDefine1(String userDefine1) {
        this.userDefine1 = userDefine1;
    }

    public String getUserDefine2() {
        return userDefine2;
    }

    public void setUserDefine2(String userDefine2) {
        this.userDefine2 = userDefine2;
    }

    public String getUserDefine3() {
        return userDefine3;
    }

    public void setUserDefine3(String userDefine3) {
        this.userDefine3 = userDefine3;
    }

    public String getUserDefine4() {
        return userDefine4;
    }

    public void setUserDefine4(String userDefine4) {
        this.userDefine4 = userDefine4;
    }

    public String getUserDefine5() {
        return userDefine5;
    }

    public void setUserDefine5(String userDefine5) {
        this.userDefine5 = userDefine5;
    }

    public String getUserDefine6() {
        return userDefine6;
    }

    public void setUserDefine6(String userDefine6) {
        this.userDefine6 = userDefine6;
    }

    public String getUserDefine7() {
        return userDefine7;
    }

    public void setUserDefine7(String userDefine7) {
        this.userDefine7 = userDefine7;
    }

    public String getUserDefine8() {
        return userDefine8;
    }

    public void setUserDefine8(String userDefine8) {
        this.userDefine8 = userDefine8;
    }

    public String getUserDefine9() {
        return userDefine9;
    }

    public void setUserDefine9(String userDefine9) {
        this.userDefine9 = userDefine9;
    }

    public String getUserDefine10() {
        return userDefine10;
    }

    public void setUserDefine10(String userDefine10) {
        this.userDefine10 = userDefine10;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCrossdockFlag() {
        return crossdockFlag;
    }

    public void setCrossdockFlag(String crossdockFlag) {
        this.crossdockFlag = crossdockFlag;
    }
}
