package com.sales.ops.dto.flux;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：4.9 收货人地址变更实体
 * @date ：Created in 2021/12/21 10:16
 */
public class ChangeConsigneeAddressDto implements Serializable {

    private static final long serialVersionUID = -9053130576919845688L;

    private String warehouseId;
    private String customerId;
    private String orderNo;
    private String address; //总地址

    private String province;//省 代码
    private String city;//市 代码
    private String district;//区 代码
    private String address2; //详细地址
    private String priority;// 是否特发 1 特发
    private String hedi07;//1：直发客户，2：直发营业所，3：营业所/客户自提
    private String carrierId;//制定承运商
    private String consigneeTel1;//收货人手机号
    private String consigneeTel2;//收货人固话
    private String consigneeZip;//收货人邮编
    private String consigneeContact;//收货联系人

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getHedi07() {
        return hedi07;
    }

    public void setHedi07(String hedi07) {
        this.hedi07 = hedi07;
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

    public String getConsigneeContact() {
        return consigneeContact;
    }

    public void setConsigneeContact(String consigneeContact) {
        this.consigneeContact = consigneeContact;
    }
}
