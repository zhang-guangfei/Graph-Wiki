package com.sales.ops.dto.ips;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @description: IPS从ips_receive_order_all_original_info原始表接入pms_order_maste采购主表的转换DTO
 * @author: B91717
 */

public class IpsReceiveOrderContentAddDto {

    private String sourceSystem; // 来源系统

    private String purchaseUnitCode; // 采购单元

    private String supplierSystem; // 供应商系统

    private String smccode; // SMCCode（根据接入单据信息和smccode转换配置表转换smccode）

    private String receivingWarehouse; // 收货仓编码（由业务系统提供）

    private String plantmarkAddress; // 厂内地址,对应master表的request_address

    private String receivingAddress; // 收货地址

    private String receivingConnector; // 收货人

    private String receivingDepartmentTeleNo; // 收货人联系电话

    private String sourceOrderNo; // 原始单号

    private String sourceItemNo; // 原始项号

    private String isMandatorySupplier; // 是否强制指定供应商,0不强制，1执行强制指定供应商,一期默认值为1

    private String expSupplierNo; // 期望供应商代码（转igps内部的供应商代码）

    private String materialNo; // 物料号

    private String modelNo; // 采购型号

    private String modelName; // 品名

    private BigDecimal qty;

    private String unit;

    private Date requestTime; // 订货日期

    private Date specifiedDeliveryDate; // 指定货期

    private String specifiedDeliveryWay; // 指定运输方式，0海运，1空运，3陆运，4快船，5铁路

    private String specifiedDeliveryCarrier; // 指定承运商，0日通

    private String purchaseOrderType; // 采购单类型，A销售订单、B先行在库订单、 K BIN补库订单、U加急订单

    private String purchaseBusinessType; // 采购业务类型，0公司间采购订单，1委外加工采购单

    private String purchaseEntity; // 采购主体（指交易主体的买方主体），指集团内的采购主体的编码

    private String requestDepartmentNo; // 请购部门编码

    private String requestDepartmentName; // 请购部门名称

    private String requestPersonName; // 请购担当姓名

    private String requestPersonNo; // 请购担当ID

    private String requestPersonTele; // 请购担当联系电话

    private String requestPersonEmail; // 请购担当邮箱

    private String contentInfo; // 采购附件信息

    private String requestAddress;

    private String orderMasterNum;

    private String receivingAddressCode;  // 收货地址编码

    private String orderType;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getPurchaseUnitCode() {
        return purchaseUnitCode;
    }

    public void setPurchaseUnitCode(String purchaseUnitCode) {
        this.purchaseUnitCode = purchaseUnitCode;
    }

    public String getSupplierSystem() {
        return supplierSystem;
    }

    public void setSupplierSystem(String supplierSystem) {
        this.supplierSystem = supplierSystem;
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode;
    }

    public String getReceivingWarehouse() {
        return receivingWarehouse;
    }

    public void setReceivingWarehouse(String receivingWarehouse) {
        this.receivingWarehouse = receivingWarehouse;
    }

    public String getPlantmarkAddress() {
        return plantmarkAddress;
    }

    public void setPlantmarkAddress(String plantmarkAddress) {
        this.plantmarkAddress = plantmarkAddress;
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

    public String getReceivingDepartmentTeleNo() {
        return receivingDepartmentTeleNo;
    }

    public void setReceivingDepartmentTeleNo(String receivingDepartmentTeleNo) {
        this.receivingDepartmentTeleNo = receivingDepartmentTeleNo;
    }

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    public String getSourceItemNo() {
        return sourceItemNo;
    }

    public void setSourceItemNo(String sourceItemNo) {
        this.sourceItemNo = sourceItemNo;
    }

    public String getIsMandatorySupplier() {
        return isMandatorySupplier;
    }

    public void setIsMandatorySupplier(String isMandatorySupplier) {
        this.isMandatorySupplier = isMandatorySupplier;
    }

    public String getExpSupplierNo() {
        return expSupplierNo;
    }

    public void setExpSupplierNo(String expSupplierNo) {
        this.expSupplierNo = expSupplierNo;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getSpecifiedDeliveryDate() {
        return specifiedDeliveryDate;
    }

    public void setSpecifiedDeliveryDate(Date specifiedDeliveryDate) {
        this.specifiedDeliveryDate = specifiedDeliveryDate;
    }

    public String getSpecifiedDeliveryWay() {
        return specifiedDeliveryWay;
    }

    public void setSpecifiedDeliveryWay(String specifiedDeliveryWay) {
        this.specifiedDeliveryWay = specifiedDeliveryWay;
    }

    public String getSpecifiedDeliveryCarrier() {
        return specifiedDeliveryCarrier;
    }

    public void setSpecifiedDeliveryCarrier(String specifiedDeliveryCarrier) {
        this.specifiedDeliveryCarrier = specifiedDeliveryCarrier;
    }

    public String getPurchaseOrderType() {
        return purchaseOrderType;
    }

    public void setPurchaseOrderType(String purchaseOrderType) {
        this.purchaseOrderType = purchaseOrderType;
    }

    public String getPurchaseBusinessType() {
        return purchaseBusinessType;
    }

    public void setPurchaseBusinessType(String purchaseBusinessType) {
        this.purchaseBusinessType = purchaseBusinessType;
    }

    public String getPurchaseEntity() {
        return purchaseEntity;
    }

    public void setPurchaseEntity(String purchaseEntity) {
        this.purchaseEntity = purchaseEntity;
    }

    public String getRequestDepartmentNo() {
        return requestDepartmentNo;
    }

    public void setRequestDepartmentNo(String requestDepartmentNo) {
        this.requestDepartmentNo = requestDepartmentNo;
    }

    public String getRequestDepartmentName() {
        return requestDepartmentName;
    }

    public void setRequestDepartmentName(String requestDepartmentName) {
        this.requestDepartmentName = requestDepartmentName;
    }

    public String getRequestPersonName() {
        return requestPersonName;
    }

    public void setRequestPersonName(String requestPersonName) {
        this.requestPersonName = requestPersonName;
    }

    public String getRequestPersonNo() {
        return requestPersonNo;
    }

    public void setRequestPersonNo(String requestPersonNo) {
        this.requestPersonNo = requestPersonNo;
    }

    public String getRequestPersonTele() {
        return requestPersonTele;
    }

    public void setRequestPersonTele(String requestPersonTele) {
        this.requestPersonTele = requestPersonTele;
    }

    public String getRequestPersonEmail() {
        return requestPersonEmail;
    }

    public void setRequestPersonEmail(String requestPersonEmail) {
        this.requestPersonEmail = requestPersonEmail;
    }

    public String getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }

    public String getOrderMasterNum() {
        return orderMasterNum;
    }

    public void setOrderMasterNum(String orderMasterNum) {
        this.orderMasterNum = orderMasterNum;
    }

    public String getReceivingAddressCode() {
        return receivingAddressCode;
    }

    public void setReceivingAddressCode(String receivingAddressCode) {
        this.receivingAddressCode = receivingAddressCode;
    }
}
