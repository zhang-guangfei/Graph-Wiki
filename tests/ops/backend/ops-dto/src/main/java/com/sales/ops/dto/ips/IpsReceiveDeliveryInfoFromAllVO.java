package com.sales.ops.dto.ips;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author smc
 * @since 2024-12-25
 */
public class IpsReceiveDeliveryInfoFromAllVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 采购单元即采购方
     */

    private String purchaseUnitCode;

    /**
     * SMCcode
     */

    private String smccode;

    /**
     * 收货地址编码
     */

    private String receivingAddressCode;

    /**
     * 业务系统源id，OPS，PMS，IPS、AIMS、JP_AS400、O_P新采购系统、ThreeC 海外三国
     */

    private String sourceSystem;

    /**
     * 采购单号
     */

    private String orderNo;

    /**
     * 采购项号
     */

    private String orderItem;

    /**
     * 交付状态，建议参考日本
     */

    private String identificationCode;

    /**
     * 计划交付批次号
     */

    private String deliveryPlanBatchNo;

    /**
     * 单据处理说明
     */

    private String orderStatusDescription;

    /**
     * 型号
     */

    private String model;

    /**
     * 采购数量
     */

    private Double quantity;

    /**
     * 残数
     */

    private Double ordRemainingQty;

    /**
     * 完纳标识：0未完纳；1已完纳
     */

    private String isChecked;

    /**
     * 发运数量
     */

    private Double shippedQty;

    /**
     * 供应商接单号
     */

    private String supplierSalesOrderNo;

    /**
     * 供应商接单项号
     */

    private String supplierSalesOrderItem;

    /**
     * 供应商操作时间
     */

    private Date supplierOperateTime;

    /**
     * 是否分纳发货
     */

    private String isSplitShip;

    /**
     * 供应商出库区分
     */

    private String supplierExpinvCode;

    /**
     * 供应商出库区分说明
     */

    private String supplierExpinvRemark;

    /**
     * 拟定发货方式
     */

    private String promiseShippingMethod;

    /**
     * 实际供应商
     */

    private String factSupplier;

    /**
     * 实际生产商
     */

    private String factManufacturer;

    /**
     * 生产商接单号
     */

    private String factManufacturerOrder;

    /**
     * 生产商项号
     */

    private String factManufacturerItem;

    /**
     * 供应商预计完工日,2025-02-06修改为字符串类型
     */

    private String promiseDateFromSupplierH;

    /**
     * 供应商预计入库日,2025-02-06修改为字符串类型
     */

    private String promiseDateFromSupplierW;

    /**
     * 供应商预计发运日,2025-02-06修改为字符串类型
     */

    private String promiseDateFromSupplierA;

    /**
     * 供应商预计送达日
     */

    private String promiseDateFromSupplierArrived;

    /**
     * 供应商返信理由分类码
     */

    private String reasonCodeFromSupplier;

    /**
     * 供应商返信理由
     */

    private String reasonFromSupplier;

    /**
     * 计划交付批次号
     */

    private String deliveryFactBatchNo;

    /**
     * 供应商实际完工日
     */

    private String factDateFromSupplierH;

    /**
     * 供应商实际入库日
     */

    private String factDateFromSupplierW;

    /**
     * 供应商实际发运日
     */

    private String factDateFromSupplierA;

    /**
     * 供应商实际送达日
     */

    private String factDateFromSupplierArrived;

    /**
     * 实际发货方式
     */

    private String shippingMethod;

    /**
     * 承运商
     */

    private String expressCompany;

    /**
     * 运单号
     */

    private String expressNo;

    /**
     * 运输方式
     */

    private String transType;

    /**
     * 形式发票号
     */

    private String invoiceNo;

    /**
     * 含税单价
     */

    private Double price;

    /**
     * 税率
     */

    private Double taxRate;

    /**
     * 未税单价
     */

    private Double untaxPrice;

    /**
     * 箱号
     */

    private String skidNo;

    /**
     * barcode
     */

    private String barcode;

    /**
     * 重量
     */

    private Double weightkg;

    /**
     * 备注
     */

    private String remark;

    /**
     * 附加信息
     */

    private String addtionInfo;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 创建者
     */

    private String createUser;

    /**
     * 删除标识：0正常；1删除
     */

    private Integer delflag;

    /**
     * 发票批次号
     */

    private String invoiceBatchNo;


    public String getPurchaseUnitCode() {
        return purchaseUnitCode;
    }

    public void setPurchaseUnitCode(String purchaseUnitCode) {
        this.purchaseUnitCode = purchaseUnitCode;
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode;
    }

    public String getReceivingAddressCode() {
        return receivingAddressCode;
    }

    public void setReceivingAddressCode(String receivingAddressCode) {
        this.receivingAddressCode = receivingAddressCode;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public String getDeliveryPlanBatchNo() {
        return deliveryPlanBatchNo;
    }

    public void setDeliveryPlanBatchNo(String deliveryPlanBatchNo) {
        this.deliveryPlanBatchNo = deliveryPlanBatchNo;
    }

    public String getOrderStatusDescription() {
        return orderStatusDescription;
    }

    public void setOrderStatusDescription(String orderStatusDescription) {
        this.orderStatusDescription = orderStatusDescription;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getOrdRemainingQty() {
        return ordRemainingQty;
    }

    public void setOrdRemainingQty(Double ordRemainingQty) {
        this.ordRemainingQty = ordRemainingQty;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public Double getShippedQty() {
        return shippedQty;
    }

    public void setShippedQty(Double shippedQty) {
        this.shippedQty = shippedQty;
    }

    public String getSupplierSalesOrderNo() {
        return supplierSalesOrderNo;
    }

    public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
        this.supplierSalesOrderNo = supplierSalesOrderNo;
    }

    public String getSupplierSalesOrderItem() {
        return supplierSalesOrderItem;
    }

    public void setSupplierSalesOrderItem(String supplierSalesOrderItem) {
        this.supplierSalesOrderItem = supplierSalesOrderItem;
    }

    public Date getSupplierOperateTime() {
        return supplierOperateTime;
    }

    public void setSupplierOperateTime(Date supplierOperateTime) {
        this.supplierOperateTime = supplierOperateTime;
    }

    public String getIsSplitShip() {
        return isSplitShip;
    }

    public void setIsSplitShip(String isSplitShip) {
        this.isSplitShip = isSplitShip;
    }

    public String getSupplierExpinvCode() {
        return supplierExpinvCode;
    }

    public void setSupplierExpinvCode(String supplierExpinvCode) {
        this.supplierExpinvCode = supplierExpinvCode;
    }

    public String getSupplierExpinvRemark() {
        return supplierExpinvRemark;
    }

    public void setSupplierExpinvRemark(String supplierExpinvRemark) {
        this.supplierExpinvRemark = supplierExpinvRemark;
    }

    public String getPromiseShippingMethod() {
        return promiseShippingMethod;
    }

    public void setPromiseShippingMethod(String promiseShippingMethod) {
        this.promiseShippingMethod = promiseShippingMethod;
    }

    public String getFactSupplier() {
        return factSupplier;
    }

    public void setFactSupplier(String factSupplier) {
        this.factSupplier = factSupplier;
    }

    public String getFactManufacturer() {
        return factManufacturer;
    }

    public void setFactManufacturer(String factManufacturer) {
        this.factManufacturer = factManufacturer;
    }

    public String getFactManufacturerOrder() {
        return factManufacturerOrder;
    }

    public void setFactManufacturerOrder(String factManufacturerOrder) {
        this.factManufacturerOrder = factManufacturerOrder;
    }

    public String getFactManufacturerItem() {
        return factManufacturerItem;
    }

    public void setFactManufacturerItem(String factManufacturerItem) {
        this.factManufacturerItem = factManufacturerItem;
    }

    public String getPromiseDateFromSupplierH() {
        return promiseDateFromSupplierH;
    }

    public void setPromiseDateFromSupplierH(String promiseDateFromSupplierH) {
        this.promiseDateFromSupplierH = promiseDateFromSupplierH;
    }

    public String getPromiseDateFromSupplierW() {
        return promiseDateFromSupplierW;
    }

    public void setPromiseDateFromSupplierW(String promiseDateFromSupplierW) {
        this.promiseDateFromSupplierW = promiseDateFromSupplierW;
    }

    public String getPromiseDateFromSupplierA() {
        return promiseDateFromSupplierA;
    }

    public void setPromiseDateFromSupplierA(String promiseDateFromSupplierA) {
        this.promiseDateFromSupplierA = promiseDateFromSupplierA;
    }

    public String getPromiseDateFromSupplierArrived() {
        return promiseDateFromSupplierArrived;
    }

    public void setPromiseDateFromSupplierArrived(String promiseDateFromSupplierArrived) {
        this.promiseDateFromSupplierArrived = promiseDateFromSupplierArrived;
    }

    public String getReasonCodeFromSupplier() {
        return reasonCodeFromSupplier;
    }

    public void setReasonCodeFromSupplier(String reasonCodeFromSupplier) {
        this.reasonCodeFromSupplier = reasonCodeFromSupplier;
    }

    public String getReasonFromSupplier() {
        return reasonFromSupplier;
    }

    public void setReasonFromSupplier(String reasonFromSupplier) {
        this.reasonFromSupplier = reasonFromSupplier;
    }

    public String getDeliveryFactBatchNo() {
        return deliveryFactBatchNo;
    }

    public void setDeliveryFactBatchNo(String deliveryFactBatchNo) {
        this.deliveryFactBatchNo = deliveryFactBatchNo;
    }

    public String getFactDateFromSupplierH() {
        return factDateFromSupplierH;
    }

    public void setFactDateFromSupplierH(String factDateFromSupplierH) {
        this.factDateFromSupplierH = factDateFromSupplierH;
    }

    public String getFactDateFromSupplierW() {
        return factDateFromSupplierW;
    }

    public void setFactDateFromSupplierW(String factDateFromSupplierW) {
        this.factDateFromSupplierW = factDateFromSupplierW;
    }

    public String getFactDateFromSupplierA() {
        return factDateFromSupplierA;
    }

    public void setFactDateFromSupplierA(String factDateFromSupplierA) {
        this.factDateFromSupplierA = factDateFromSupplierA;
    }

    public String getFactDateFromSupplierArrived() {
        return factDateFromSupplierArrived;
    }

    public void setFactDateFromSupplierArrived(String factDateFromSupplierArrived) {
        this.factDateFromSupplierArrived = factDateFromSupplierArrived;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getUntaxPrice() {
        return untaxPrice;
    }

    public void setUntaxPrice(Double untaxPrice) {
        this.untaxPrice = untaxPrice;
    }

    public String getSkidNo() {
        return skidNo;
    }

    public void setSkidNo(String skidNo) {
        this.skidNo = skidNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getWeightkg() {
        return weightkg;
    }

    public void setWeightkg(Double weightkg) {
        this.weightkg = weightkg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddtionInfo() {
        return addtionInfo;
    }

    public void setAddtionInfo(String addtionInfo) {
        this.addtionInfo = addtionInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getInvoiceBatchNo() {
        return invoiceBatchNo;
    }

    public void setInvoiceBatchNo(String invoiceBatchNo) {
        this.invoiceBatchNo = invoiceBatchNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IpsReceiveDeliveryInfoFromAllVO that = (IpsReceiveDeliveryInfoFromAllVO) o;
        return Objects.equals(purchaseUnitCode, that.purchaseUnitCode) && Objects.equals(smccode, that.smccode) && Objects.equals(receivingAddressCode, that.receivingAddressCode) && Objects.equals(sourceSystem, that.sourceSystem) && Objects.equals(orderNo, that.orderNo) && Objects.equals(orderItem, that.orderItem) && Objects.equals(identificationCode, that.identificationCode) && Objects.equals(deliveryPlanBatchNo, that.deliveryPlanBatchNo) && Objects.equals(orderStatusDescription, that.orderStatusDescription) && Objects.equals(model, that.model) && Objects.equals(quantity, that.quantity) && Objects.equals(ordRemainingQty, that.ordRemainingQty) && Objects.equals(isChecked, that.isChecked) && Objects.equals(shippedQty, that.shippedQty) && Objects.equals(supplierSalesOrderNo, that.supplierSalesOrderNo) && Objects.equals(supplierSalesOrderItem, that.supplierSalesOrderItem) && Objects.equals(supplierOperateTime, that.supplierOperateTime) && Objects.equals(isSplitShip, that.isSplitShip) && Objects.equals(supplierExpinvCode, that.supplierExpinvCode) && Objects.equals(supplierExpinvRemark, that.supplierExpinvRemark) && Objects.equals(promiseShippingMethod, that.promiseShippingMethod) && Objects.equals(factSupplier, that.factSupplier) && Objects.equals(factManufacturer, that.factManufacturer) && Objects.equals(factManufacturerOrder, that.factManufacturerOrder) && Objects.equals(factManufacturerItem, that.factManufacturerItem) && Objects.equals(promiseDateFromSupplierH, that.promiseDateFromSupplierH) && Objects.equals(promiseDateFromSupplierW, that.promiseDateFromSupplierW) && Objects.equals(promiseDateFromSupplierA, that.promiseDateFromSupplierA) && Objects.equals(promiseDateFromSupplierArrived, that.promiseDateFromSupplierArrived) && Objects.equals(reasonCodeFromSupplier, that.reasonCodeFromSupplier) && Objects.equals(reasonFromSupplier, that.reasonFromSupplier) && Objects.equals(deliveryFactBatchNo, that.deliveryFactBatchNo) && Objects.equals(factDateFromSupplierH, that.factDateFromSupplierH) && Objects.equals(factDateFromSupplierW, that.factDateFromSupplierW) && Objects.equals(factDateFromSupplierA, that.factDateFromSupplierA) && Objects.equals(factDateFromSupplierArrived, that.factDateFromSupplierArrived) && Objects.equals(shippingMethod, that.shippingMethod) && Objects.equals(expressCompany, that.expressCompany) && Objects.equals(expressNo, that.expressNo) && Objects.equals(transType, that.transType) && Objects.equals(invoiceNo, that.invoiceNo) && Objects.equals(price, that.price) && Objects.equals(taxRate, that.taxRate) && Objects.equals(untaxPrice, that.untaxPrice) && Objects.equals(skidNo, that.skidNo) && Objects.equals(barcode, that.barcode) && Objects.equals(weightkg, that.weightkg) && Objects.equals(remark, that.remark) && Objects.equals(addtionInfo, that.addtionInfo) && Objects.equals(createTime, that.createTime) && Objects.equals(createUser, that.createUser) && Objects.equals(delflag, that.delflag) && Objects.equals(invoiceBatchNo, that.invoiceBatchNo) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseUnitCode, smccode, receivingAddressCode, sourceSystem, orderNo, orderItem, identificationCode, deliveryPlanBatchNo, orderStatusDescription, model, quantity, ordRemainingQty, isChecked, shippedQty, supplierSalesOrderNo, supplierSalesOrderItem, supplierOperateTime, isSplitShip, supplierExpinvCode, supplierExpinvRemark, promiseShippingMethod, factSupplier, factManufacturer, factManufacturerOrder, factManufacturerItem, promiseDateFromSupplierH, promiseDateFromSupplierW, promiseDateFromSupplierA, promiseDateFromSupplierArrived, reasonCodeFromSupplier, reasonFromSupplier, deliveryFactBatchNo, factDateFromSupplierH, factDateFromSupplierW, factDateFromSupplierA, factDateFromSupplierArrived, shippingMethod, expressCompany, expressNo, transType, invoiceNo, price, taxRate, untaxPrice, skidNo, barcode, weightkg, remark, addtionInfo, createTime, createUser, delflag, invoiceBatchNo, id);
    }
}
