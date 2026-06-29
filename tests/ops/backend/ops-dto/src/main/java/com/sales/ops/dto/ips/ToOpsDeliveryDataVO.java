package com.sales.ops.dto.ips;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author smc
 * @since 2024-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ToOpsDeliveryDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 客户代码
     */
    private String CustomerCode;

    /**
     * 采购单号
     */
    private String OrderNo;

    /**
     * 采购项号
     */
    private String OrderItem;

    /**
     * 交付状态，建议参考日本
     */
    private String IdentificationCode;

    /**
     * 单据处理说明
     */
    private String orderStatusDescription;

    /**
     * 型号
     */
    private String Model;

    /**
     * 采购数量
     */
    private Integer Quantity;

    /**
     * 残数
     */
    private Integer OrdRemainingQty;

    /**
     * 发运数量
     */
    private Integer ShippedQty;

    /**
     * 供应商接单号
     */
    private String supplierSalesOrderNo;

    /**
     * 供应商接单项号
     */
    private String supplierSalesOrderItem;

    /**
     * 供应商出库区分
     */
    private String supplierExpinvCode;

    /**
     * 供应商出库区分说明
     */
    private String supplierExpinvremark;

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
     * 供应商预计完工日
     */
    private Date promisedatefrmsupplierH;

    /**
     * 供应商预计入库日
     */
    private Date promisedatefrmsupplierW;

    /**
     * 供应商预计发运日
     */
    private Date promisedatefrmsupplierA;

    /**
     * 供应商预计送达日
     */
    private Date promisedatefrmsupplierArrived;

    /**
     * 供应商返信理由分类码
     */
    private String ReasonCodeFrmSupplier;

    /**
     * 供应商返信理由
     */
    private String ReasonFrmSupplier;

    /**
     * 供应商实际完工日
     */
    private Date factdatefrmsupplierH;

    /**
     * 供应商实际入库日
     */
    private Date factdatefrmsupplierW;

    /**
     * 供应商实际发运日
     */
    private Date factdatefrmsupplierA;

    /**
     * 供应商实际送达日
     */
    private Date factdatefrmsupplierArrived;

    /**
     * 实际发货方式
     */
    private String ShippingMethod;

    /**
     * 发票号
     */
    private String InvoiceNo;

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
    private String TransType;

    /**
     * 箱号
     */
    private Double SkidNo;

    /**
     * barcode
     */
    private String barcode;

    /**
     * 重量
     */
    private Double WeightKG;

    /**
     * 含税单价
     */
    private Double price;

    /**
     * 未税单价
     */
    private Double noTaxPrice;

    /**
     * 含税金额
     */
    private Double amount;

    /**
     * 未税金额
     */
    private Double noTaxAmount;

    /**
     * 税率
     */
    private Double tax;

    private Date createTime;

    private String createUser;

    private Integer delflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getOrderItem() {
        return OrderItem;
    }

    public void setOrderItem(String orderItem) {
        OrderItem = orderItem;
    }

    public String getIdentificationCode() {
        return IdentificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        IdentificationCode = identificationCode;
    }

    public String getOrderStatusDescription() {
        return orderStatusDescription;
    }

    public void setOrderStatusDescription(String orderStatusDescription) {
        this.orderStatusDescription = orderStatusDescription;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Integer getOrdRemainingQty() {
        return OrdRemainingQty;
    }

    public void setOrdRemainingQty(Integer ordRemainingQty) {
        OrdRemainingQty = ordRemainingQty;
    }

    public Integer getShippedQty() {
        return ShippedQty;
    }

    public void setShippedQty(Integer shippedQty) {
        ShippedQty = shippedQty;
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

    public String getSupplierExpinvCode() {
        return supplierExpinvCode;
    }

    public void setSupplierExpinvCode(String supplierExpinvCode) {
        this.supplierExpinvCode = supplierExpinvCode;
    }

    public String getSupplierExpinvremark() {
        return supplierExpinvremark;
    }

    public void setSupplierExpinvremark(String supplierExpinvremark) {
        this.supplierExpinvremark = supplierExpinvremark;
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

    public Date getPromisedatefrmsupplierH() {
        return promisedatefrmsupplierH;
    }

    public void setPromisedatefrmsupplierH(Date promisedatefrmsupplierH) {
        this.promisedatefrmsupplierH = promisedatefrmsupplierH;
    }

    public Date getPromisedatefrmsupplierW() {
        return promisedatefrmsupplierW;
    }

    public void setPromisedatefrmsupplierW(Date promisedatefrmsupplierW) {
        this.promisedatefrmsupplierW = promisedatefrmsupplierW;
    }

    public Date getPromisedatefrmsupplierA() {
        return promisedatefrmsupplierA;
    }

    public void setPromisedatefrmsupplierA(Date promisedatefrmsupplierA) {
        this.promisedatefrmsupplierA = promisedatefrmsupplierA;
    }

    public Date getPromisedatefrmsupplierArrived() {
        return promisedatefrmsupplierArrived;
    }

    public void setPromisedatefrmsupplierArrived(Date promisedatefrmsupplierArrived) {
        this.promisedatefrmsupplierArrived = promisedatefrmsupplierArrived;
    }

    public String getReasonCodeFrmSupplier() {
        return ReasonCodeFrmSupplier;
    }

    public void setReasonCodeFrmSupplier(String reasonCodeFrmSupplier) {
        ReasonCodeFrmSupplier = reasonCodeFrmSupplier;
    }

    public String getReasonFrmSupplier() {
        return ReasonFrmSupplier;
    }

    public void setReasonFrmSupplier(String reasonFrmSupplier) {
        ReasonFrmSupplier = reasonFrmSupplier;
    }

    public Date getFactdatefrmsupplierH() {
        return factdatefrmsupplierH;
    }

    public void setFactdatefrmsupplierH(Date factdatefrmsupplierH) {
        this.factdatefrmsupplierH = factdatefrmsupplierH;
    }

    public Date getFactdatefrmsupplierW() {
        return factdatefrmsupplierW;
    }

    public void setFactdatefrmsupplierW(Date factdatefrmsupplierW) {
        this.factdatefrmsupplierW = factdatefrmsupplierW;
    }

    public Date getFactdatefrmsupplierA() {
        return factdatefrmsupplierA;
    }

    public void setFactdatefrmsupplierA(Date factdatefrmsupplierA) {
        this.factdatefrmsupplierA = factdatefrmsupplierA;
    }

    public Date getFactdatefrmsupplierArrived() {
        return factdatefrmsupplierArrived;
    }

    public void setFactdatefrmsupplierArrived(Date factdatefrmsupplierArrived) {
        this.factdatefrmsupplierArrived = factdatefrmsupplierArrived;
    }

    public String getShippingMethod() {
        return ShippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        ShippingMethod = shippingMethod;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
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
        return TransType;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }

    public Double getSkidNo() {
        return SkidNo;
    }

    public void setSkidNo(Double skidNo) {
        SkidNo = skidNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getWeightKG() {
        return WeightKG;
    }

    public void setWeightKG(Double weightKG) {
        WeightKG = weightKG;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNoTaxPrice() {
        return noTaxPrice;
    }

    public void setNoTaxPrice(Double noTaxPrice) {
        this.noTaxPrice = noTaxPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getNoTaxAmount() {
        return noTaxAmount;
    }

    public void setNoTaxAmount(Double noTaxAmount) {
        this.noTaxAmount = noTaxAmount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
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
}
