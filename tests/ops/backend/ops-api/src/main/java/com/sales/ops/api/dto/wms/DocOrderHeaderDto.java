package com.sales.ops.api.dto.wms;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：发运、加工 do pco to wms 4.6 销售/发运订单/加工单/盘点差异调账下发
 * @date ：Created in 2021/10/28 17:12
 */
public class DocOrderHeaderDto implements Serializable {

    private static final long serialVersionUID = -1912799296536811721L;

    private List<DocOrderDetailsDto> details;

    private String warehouseId;//仓库ID TRUE String(20)
    private String customerId;//货主ID TRUE String(50)
    private String orderType;//订单类型 TRUE String(20)
    private String docNo;//上游订单号 TRUE String(20)
    private String soReferenceA;//参考编号A FALSE String(50)
    private String soReferenceB;//参考编号B FALSE String(50)
    private String soReferenceC;//参考编号C FALSE String(50)
    private String soReferenceD;//参考编号D FALSE String(50)
    private String priority;//订单优先级 FALSE String(1)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;//订单创建时间 FALSE datetime
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expectedShipmentTime1;//预期发货时间 FALSE datetime 物流交货时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date requiredDeliveryTime;//要求交货时间 FALSE datetime hope
    private String deliveryNo;//快递单号 FALSE String(30)
    private String consigneeId;//收货人代码 TRUE String(50)
    private String consigneeName;//收货人名称 TRUE String(200)
    private String consigneeContact;//收货人联系人 FALSE String(200)
    private String consigneeAddress1;//收货人地址1 TRUE String(200)
    private String consigneeAddress2;//收货人地址2 FALSE String(50)
    private String consigneeAddress3;//收货人地址3 FALSE String(50)
    private String consigneeCountry;//收货人国家代码 FALSE String(2)
    private String consigneeProvince;//收货人省份 FALSE String(50)
    private String consigneeCity;//收货人城市 FALSE String(50)
    private String consigneeDistrict;//收货人区 FALSE String(50)
    private String consigneeStreet;//收货人街道 FALSE String(50)
    private String consigneeMail;//收货人邮箱 FALSE String(50)
    private String consigneeTel1;//收货人手机号 FALSE String(50)
    private String consigneeTel2;//收货人固话 FALSE String(50)
    private String consigneeZip;//收货人邮编 FALSE String(50)
    private String carrierId;//承运人代码 FALSE String(30)
    private String carrierName;//承运人名称 FALSE String(200)
    private String carrierFax;//承运人传真 FALSE String(50)
    private String carrierMail;//承运人邮箱 FALSE String(50)
    private String issuePartyId;//下单方代码 FALSE varchar(30)
    private String issuePartyName;//下单方名称 FALSE varchar(200)
    private String channel;//渠道 FALSE String(20)
    private String hedi01;//EDI信息01 FALSE String(200)
    private String hedi02;//EDI信息02 FALSE String(200)
    private String hedi03;//EDI信息03 FALSE String(200)
    private String hedi04;//EDI信息04 FALSE String(200)
    private String hedi05;//EDI信息05 FALSE String(200)
    private String hedi06;//EDI信息06 FALSE String(200)
    private String hedi07;//EDI信息07 FALSE String(200)
    private String hedi08;//EDI信息08 FALSE String(200)
    private String hedi09;//EDI信息09 FALSE decimal(18, 8)
    private String hedi10;//EDI信息10 FALSE decimal(18, 8)
    private String hedi11;//EDI信息11
    private String hedi12;//EDI信息12
    private String hedi16; //客户合同号
    private String hedi15; //KT 加工单标识
    private String hedi18; // 需拆数出货的预约订单 通知发货订单
    private String invoicePrintFlag;//是否打印发票 FALSE String(1)
    private String route;//线路 FALSE String(10)
    private String stop;//站点 FALSE String(10)
    private String userDefine1;// 加工单传doid 用户自定义1 FALSE String(200)
    private String userDefine2;//出库区分 用户自定义2 FALSE String(200)
    private String userDefine3;//供应商 用户自定义3 FALSE String(200)
    private String userDefine4;//用户自定义4 FALSE String(200)
    private String userDefine5;//用户自定义5 FALSE String(200)
    private String userDefine6;//用户自定义6 FALSE String(200)
    private String userDefine7;//用户自定义7 FALSE String(500)
    private String userDefine8;//用户自定义8 FALSE String(500)
    private String userDefine9;//用户自定义9 FALSE decimal(18,8)
    private String userDefine10;//用户自定义10 FALSE decimal(18,8)
    private String notes;//备注 FALSE String(500)
    private String crossdockFlag;//越库标记 FALSE String(1)

    public List<DocOrderDetailsDto> getDetails() {
        return details;
    }

    public void setDetails(List<DocOrderDetailsDto> details) {
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getSoReferenceA() {
        return soReferenceA;
    }

    public void setSoReferenceA(String soReferenceA) {
        this.soReferenceA = soReferenceA;
    }

    public String getSoReferenceB() {
        return soReferenceB;
    }

    public void setSoReferenceB(String soReferenceB) {
        this.soReferenceB = soReferenceB;
    }

    public String getSoReferenceC() {
        return soReferenceC;
    }

    public void setSoReferenceC(String soReferenceC) {
        this.soReferenceC = soReferenceC;
    }

    public String getSoReferenceD() {
        return soReferenceD;
    }

    public void setSoReferenceD(String soReferenceD) {
        this.soReferenceD = soReferenceD;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getExpectedShipmentTime1() {
        return expectedShipmentTime1;
    }

    public void setExpectedShipmentTime1(Date expectedShipmentTime1) {
        this.expectedShipmentTime1 = expectedShipmentTime1;
    }

    public Date getRequiredDeliveryTime() {
        return requiredDeliveryTime;
    }

    public void setRequiredDeliveryTime(Date requiredDeliveryTime) {
        this.requiredDeliveryTime = requiredDeliveryTime;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(String consigneeId) {
        this.consigneeId = consigneeId;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeContact() {
        return consigneeContact;
    }

    public void setConsigneeContact(String consigneeContact) {
        this.consigneeContact = consigneeContact;
    }

    public String getConsigneeAddress1() {
        return consigneeAddress1;
    }

    public void setConsigneeAddress1(String consigneeAddress1) {
        this.consigneeAddress1 = consigneeAddress1;
    }

    public String getConsigneeAddress2() {
        return consigneeAddress2;
    }

    public void setConsigneeAddress2(String consigneeAddress2) {
        this.consigneeAddress2 = consigneeAddress2;
    }

    public String getConsigneeAddress3() {
        return consigneeAddress3;
    }

    public void setConsigneeAddress3(String consigneeAddress3) {
        this.consigneeAddress3 = consigneeAddress3;
    }

    public String getConsigneeCountry() {
        return consigneeCountry;
    }

    public void setConsigneeCountry(String consigneeCountry) {
        this.consigneeCountry = consigneeCountry;
    }

    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getConsigneeDistrict() {
        return consigneeDistrict;
    }

    public void setConsigneeDistrict(String consigneeDistrict) {
        this.consigneeDistrict = consigneeDistrict;
    }

    public String getConsigneeStreet() {
        return consigneeStreet;
    }

    public void setConsigneeStreet(String consigneeStreet) {
        this.consigneeStreet = consigneeStreet;
    }

    public String getConsigneeMail() {
        return consigneeMail;
    }

    public void setConsigneeMail(String consigneeMail) {
        this.consigneeMail = consigneeMail;
    }

    public String getConsigneeTel1() {
        return consigneeTel1;
    }

    public void setConsigneeTel1(String consigneeTel1) {
        this.consigneeTel1 = consigneeTel1;
    }

    public String getConsigneeTel2() {
        return consigneeTel2;
    }

    public void setConsigneeTel2(String consigneeTel2) {
        this.consigneeTel2 = consigneeTel2;
    }

    public String getConsigneeZip() {
        return consigneeZip;
    }

    public void setConsigneeZip(String consigneeZip) {
        this.consigneeZip = consigneeZip;
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

    public String getCarrierFax() {
        return carrierFax;
    }

    public void setCarrierFax(String carrierFax) {
        this.carrierFax = carrierFax;
    }

    public String getCarrierMail() {
        return carrierMail;
    }

    public void setCarrierMail(String carrierMail) {
        this.carrierMail = carrierMail;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getInvoicePrintFlag() {
        return invoicePrintFlag;
    }

    public void setInvoicePrintFlag(String invoicePrintFlag) {
        this.invoicePrintFlag = invoicePrintFlag;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
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

    public String getHedi11() {
        return hedi11;
    }

    public void setHedi11(String hedi11) {
        this.hedi11 = hedi11;
    }

    public String getHedi12() {
        return hedi12;
    }

    public void setHedi12(String hedi12) {
        this.hedi12 = hedi12;
    }

    public String getHedi16() {
        return hedi16;
    }

    public void setHedi16(String hedi16) {
        this.hedi16 = hedi16;
    }

    public String getHedi15() {
        return hedi15;
    }

    public void setHedi15(String hedi15) {
        this.hedi15 = hedi15;
    }

    public String getHedi18() {
        return hedi18;
    }

    public void setHedi18(String hedi18) {
        this.hedi18 = hedi18;
    }
}
