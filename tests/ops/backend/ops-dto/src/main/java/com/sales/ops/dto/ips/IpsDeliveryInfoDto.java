package com.sales.ops.dto.ips;

/**
 * @description:
 * gateNo    门号
 * zoneMark	区域
 * sortNo	分类编号，客户订单：客户代码、补库订单、货位号前10位
 * shelfNo	客户货架号
 * enduserRemarks	打印在指示上,提醒制造现场注意:1.二次电池;2.重点客户:vipcode;3.组装;4.营业所名称;5.>30的型号全型号写入
 * simpleModel	简易型号
 * receivingAddress	物流收货地址（根据物料号配置的收货工厂地址配置好，确定收货地址）
 * receivingConnector	物流收货人
 * receivingDepartmentTeleno	物流收货联系电话
 * "recDlvyDate": "",//纳期
 * "dlvyWay": "",//指定运输方式
 * "place": "",//指定纳入地
 *
 * @author: B91717
 * @time: 2024/12/17 16:29
 */
public class IpsDeliveryInfoDto {

    private String gateNo;

    private String zoneMark;

    private String sortNo;
// 2025/01/20 与客户信息中的customerShelfNo 指标重复
    private String shelfNo;

    private String enduserRemarks;

    private String simpleModel;

    private String receivingAddress;

    private String receivingConnector;

    private String receivingDepartmentTeleno;

    private String reqDlvyDate;

    private String dlvyWay;

    private String place;

    // 2025-10-20 海外发单改造，通过PSI发送，增加字段
    private String englishLinkman; // 联系人英文名（Mr Tian Xiao Ming）

    private String englishAddress; //英文的地址（17 Jian An St. Beijing Econmic & Technological Development Zone 100176 China）

    private String subCode; // BUG19186 WTSR2023001194-smccode合并方案

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getEnglishLinkman() {
        return englishLinkman;
    }

    public void setEnglishLinkman(String englishLinkman) {
        this.englishLinkman = englishLinkman;
    }

    public String getEnglishAddress() {
        return englishAddress;
    }

    public void setEnglishAddress(String englishAddress) {
        this.englishAddress = englishAddress;
    }

    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    public String getGateNo() {
        return gateNo;
    }

    public void setGateNo(String gateNo) {
        this.gateNo = gateNo;
    }

    public String getZoneMark() {
        return zoneMark;
    }

    public void setZoneMark(String zoneMark) {
        this.zoneMark = zoneMark;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getEnduserRemarks() {
        return enduserRemarks;
    }

    public void setEnduserRemarks(String enduserRemarks) {
        this.enduserRemarks = enduserRemarks;
    }

    public String getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(String simpleModel) {
        this.simpleModel = simpleModel;
    }

    public String getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public String getReceivingConnector() {
        return receivingConnector;
    }

    public void setReceivingConnector(String receivingConnector) {
        this.receivingConnector = receivingConnector;
    }

    public String getReceivingDepartmentTeleno() {
        return receivingDepartmentTeleno;
    }

    public void setReceivingDepartmentTeleno(String receivingDepartmentTeleno) {
        this.receivingDepartmentTeleno = receivingDepartmentTeleno;
    }

    public String getReqDlvyDate() {
        return reqDlvyDate;
    }

    public void setReqDlvyDate(String reqDlvyDate) {
        this.reqDlvyDate = reqDlvyDate;
    }

    public String getDlvyWay() {
        return dlvyWay;
    }

    public void setDlvyWay(String dlvyWay) {
        this.dlvyWay = dlvyWay;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
