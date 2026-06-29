package com.sales.ops.dto.invoice;

import java.math.BigDecimal;
import java.util.Date;

public class OpsImpInvoiceDetailVO {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * imp_invoice_master.id
     */
    private Long invoiceId;

    /**
     * 发票号
     */
    private String invoiceNo;

    private String pono;

    /**
     * 采购项号
     */
    private Integer poitemno;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单项号
     */
    private Integer itemNo;

    private Integer splitItemNo;

    private String fullOrderNo;
    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 币制
     */
    private String currency;

    /**
     * 出港日期
     */
    private Date shipDate;

    /**
     * 运输方式
     */
    private String shipMethod;

    /**
     * 箱号
     */
    private String caseNo;

    /**
     * 条码
     */
    private String barcode;

    private String enName;

    /***
     * 重量
     */
    private Double weight;

    private String supplierCode;

    private Date createTime;

    private Date updateTime;

    /**
     * 订单类型
     */
    private String orderType;

    private String remark;

    private String remarkii;

    private Integer status;

    private String productCode;

    private String rohsCode;

    /**
     * 原产国
     */
    private String origin;

    private String fromCode;

    private String shelfCode;

    private String createUser;

    private String updateUser;

    /**
     * 导入的原始单号+项号
     */
    public String impOrderNo;

    /**
     * 关税
     */
    public  BigDecimal customsFee;

    /**
     * 来源id
     */
    public Long fromId;

    public String impModelNo;

    public String OverseaInvoiceNo;

    /**
     * 无商业价值
     */
    public String nonCommercial;


    private String purchaseType;

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono;
    }

    public Integer getPoitemno() {
        return poitemno;
    }

    public void setPoitemno(Integer poitemno) {
        this.poitemno = poitemno;
    }

    public String getRemarkii() {
        return remarkii;
    }

    public void setRemarkii(String remarkii) {
        this.remarkii = remarkii;
    }

    public String getRohsCode() {
        return rohsCode;
    }

    public void setRohsCode(String rohsCode) {
        this.rohsCode = rohsCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getSplitItemNo() {
        return splitItemNo;
    }

    public void setSplitItemNo(Integer splitItemNo) {
        this.splitItemNo = splitItemNo;
    }

    public String getFullOrderNo() {
        return fullOrderNo;
    }

    public void setFullOrderNo(String fullOrderNo) {
        this.fullOrderNo = fullOrderNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getShelfCode() {
        return shelfCode;
    }

    public void setShelfCode(String shelfCode) {
        this.shelfCode = shelfCode;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getImpOrderNo() {
        return impOrderNo;
    }

    public void setImpOrderNo(String impOrderNo) {
        this.impOrderNo = impOrderNo;
    }

    public BigDecimal getCustomsFee() {
        return customsFee;
    }

    public void setCustomsFee(BigDecimal customsFee) {
        this.customsFee = customsFee;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getImpModelNo() {
        return impModelNo;
    }

    public void setImpModelNo(String impModelNo) {
        this.impModelNo = impModelNo;
    }

    public String getOverseaInvoiceNo() {
        return OverseaInvoiceNo;
    }

    public void setOverseaInvoiceNo(String overseaInvoiceNo) {
        OverseaInvoiceNo = overseaInvoiceNo;
    }

    public String getNonCommercial() {
        return nonCommercial;
    }

    public void setNonCommercial(String nonCommercial) {
        this.nonCommercial = nonCommercial;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }
}
