package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ImpInvoiceDetailTemp implements Serializable {
    private Long id;

    private Long invoiceId;

    private String invoiceNo;

    private String pono;

    private Integer poitemno;

    private String orderNo;

    private Integer splitItemNo;

    private Integer itemNo;

    private String fullOrderNo;

    private String modelNo;

    private Integer quantity;

    private BigDecimal price;

    private String currency;

    private Date shipDate;

    private String shipMethod;

    private String caseNo;

    private String barcode;

    private String enName;

    private BigDecimal weight;

    private String supplierCode;

    private Date createTime;

    private Date updateTime;

    private String orderType;

    private String remark;

    private String remarkii;

    private Short status;

    private String productCode;

    private String rohsCode;

    private String origin;

    private String fromCode;

    private String shelfCode;

    private String updateUser;

    private String createUser;

    private String impOrderNo;

    private BigDecimal customsFee;

    private Long fromId;

    private String impModelNo;

    private String overseaInvoiceNo;

    private String noncommercial;

    private BigDecimal exciseTax;

    private BigDecimal otherFee;

    private BigDecimal transFee;

    private String poWarehousecode;

    private BigDecimal vatFee;

    private String snCode;

    private BigDecimal amount;

    private static final long serialVersionUID = 1L;

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
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono == null ? null : pono.trim();
    }

    public Integer getPoitemno() {
        return poitemno;
    }

    public void setPoitemno(Integer poitemno) {
        this.poitemno = poitemno;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getSplitItemNo() {
        return splitItemNo;
    }

    public void setSplitItemNo(Integer splitItemNo) {
        this.splitItemNo = splitItemNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getFullOrderNo() {
        return fullOrderNo;
    }

    public void setFullOrderNo(String fullOrderNo) {
        this.fullOrderNo = fullOrderNo == null ? null : fullOrderNo.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
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
        this.currency = currency == null ? null : currency.trim();
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
        this.shipMethod = shipMethod == null ? null : shipMethod.trim();
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo == null ? null : caseNo.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
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
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRemarkii() {
        return remarkii;
    }

    public void setRemarkii(String remarkii) {
        this.remarkii = remarkii == null ? null : remarkii.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getRohsCode() {
        return rohsCode;
    }

    public void setRohsCode(String rohsCode) {
        this.rohsCode = rohsCode == null ? null : rohsCode.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode == null ? null : fromCode.trim();
    }

    public String getShelfCode() {
        return shelfCode;
    }

    public void setShelfCode(String shelfCode) {
        this.shelfCode = shelfCode == null ? null : shelfCode.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getImpOrderNo() {
        return impOrderNo;
    }

    public void setImpOrderNo(String impOrderNo) {
        this.impOrderNo = impOrderNo == null ? null : impOrderNo.trim();
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
        this.impModelNo = impModelNo == null ? null : impModelNo.trim();
    }

    public String getOverseaInvoiceNo() {
        return overseaInvoiceNo;
    }

    public void setOverseaInvoiceNo(String overseaInvoiceNo) {
        this.overseaInvoiceNo = overseaInvoiceNo == null ? null : overseaInvoiceNo.trim();
    }

    public String getNoncommercial() {
        return noncommercial;
    }

    public void setNoncommercial(String noncommercial) {
        this.noncommercial = noncommercial == null ? null : noncommercial.trim();
    }

    public BigDecimal getExciseTax() {
        return exciseTax;
    }

    public void setExciseTax(BigDecimal exciseTax) {
        this.exciseTax = exciseTax;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public String getPoWarehousecode() {
        return poWarehousecode;
    }

    public void setPoWarehousecode(String poWarehousecode) {
        this.poWarehousecode = poWarehousecode == null ? null : poWarehousecode.trim();
    }

    public BigDecimal getVatFee() {
        return vatFee;
    }

    public void setVatFee(BigDecimal vatFee) {
        this.vatFee = vatFee;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode == null ? null : snCode.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ImpInvoiceDetailTemp other = (ImpInvoiceDetailTemp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getPono() == null ? other.getPono() == null : this.getPono().equals(other.getPono()))
            && (this.getPoitemno() == null ? other.getPoitemno() == null : this.getPoitemno().equals(other.getPoitemno()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getSplitItemNo() == null ? other.getSplitItemNo() == null : this.getSplitItemNo().equals(other.getSplitItemNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getFullOrderNo() == null ? other.getFullOrderNo() == null : this.getFullOrderNo().equals(other.getFullOrderNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getShipDate() == null ? other.getShipDate() == null : this.getShipDate().equals(other.getShipDate()))
            && (this.getShipMethod() == null ? other.getShipMethod() == null : this.getShipMethod().equals(other.getShipMethod()))
            && (this.getCaseNo() == null ? other.getCaseNo() == null : this.getCaseNo().equals(other.getCaseNo()))
            && (this.getBarcode() == null ? other.getBarcode() == null : this.getBarcode().equals(other.getBarcode()))
            && (this.getEnName() == null ? other.getEnName() == null : this.getEnName().equals(other.getEnName()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getSupplierCode() == null ? other.getSupplierCode() == null : this.getSupplierCode().equals(other.getSupplierCode()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getRemarkii() == null ? other.getRemarkii() == null : this.getRemarkii().equals(other.getRemarkii()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getProductCode() == null ? other.getProductCode() == null : this.getProductCode().equals(other.getProductCode()))
            && (this.getRohsCode() == null ? other.getRohsCode() == null : this.getRohsCode().equals(other.getRohsCode()))
            && (this.getOrigin() == null ? other.getOrigin() == null : this.getOrigin().equals(other.getOrigin()))
            && (this.getFromCode() == null ? other.getFromCode() == null : this.getFromCode().equals(other.getFromCode()))
            && (this.getShelfCode() == null ? other.getShelfCode() == null : this.getShelfCode().equals(other.getShelfCode()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getImpOrderNo() == null ? other.getImpOrderNo() == null : this.getImpOrderNo().equals(other.getImpOrderNo()))
            && (this.getCustomsFee() == null ? other.getCustomsFee() == null : this.getCustomsFee().equals(other.getCustomsFee()))
            && (this.getFromId() == null ? other.getFromId() == null : this.getFromId().equals(other.getFromId()))
            && (this.getImpModelNo() == null ? other.getImpModelNo() == null : this.getImpModelNo().equals(other.getImpModelNo()))
            && (this.getOverseaInvoiceNo() == null ? other.getOverseaInvoiceNo() == null : this.getOverseaInvoiceNo().equals(other.getOverseaInvoiceNo()))
            && (this.getNoncommercial() == null ? other.getNoncommercial() == null : this.getNoncommercial().equals(other.getNoncommercial()))
            && (this.getExciseTax() == null ? other.getExciseTax() == null : this.getExciseTax().equals(other.getExciseTax()))
            && (this.getOtherFee() == null ? other.getOtherFee() == null : this.getOtherFee().equals(other.getOtherFee()))
            && (this.getTransFee() == null ? other.getTransFee() == null : this.getTransFee().equals(other.getTransFee()))
            && (this.getPoWarehousecode() == null ? other.getPoWarehousecode() == null : this.getPoWarehousecode().equals(other.getPoWarehousecode()))
            && (this.getVatFee() == null ? other.getVatFee() == null : this.getVatFee().equals(other.getVatFee()))
            && (this.getSnCode() == null ? other.getSnCode() == null : this.getSnCode().equals(other.getSnCode()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getPono() == null) ? 0 : getPono().hashCode());
        result = prime * result + ((getPoitemno() == null) ? 0 : getPoitemno().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getSplitItemNo() == null) ? 0 : getSplitItemNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getFullOrderNo() == null) ? 0 : getFullOrderNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getShipDate() == null) ? 0 : getShipDate().hashCode());
        result = prime * result + ((getShipMethod() == null) ? 0 : getShipMethod().hashCode());
        result = prime * result + ((getCaseNo() == null) ? 0 : getCaseNo().hashCode());
        result = prime * result + ((getBarcode() == null) ? 0 : getBarcode().hashCode());
        result = prime * result + ((getEnName() == null) ? 0 : getEnName().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getSupplierCode() == null) ? 0 : getSupplierCode().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getRemarkii() == null) ? 0 : getRemarkii().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getProductCode() == null) ? 0 : getProductCode().hashCode());
        result = prime * result + ((getRohsCode() == null) ? 0 : getRohsCode().hashCode());
        result = prime * result + ((getOrigin() == null) ? 0 : getOrigin().hashCode());
        result = prime * result + ((getFromCode() == null) ? 0 : getFromCode().hashCode());
        result = prime * result + ((getShelfCode() == null) ? 0 : getShelfCode().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getImpOrderNo() == null) ? 0 : getImpOrderNo().hashCode());
        result = prime * result + ((getCustomsFee() == null) ? 0 : getCustomsFee().hashCode());
        result = prime * result + ((getFromId() == null) ? 0 : getFromId().hashCode());
        result = prime * result + ((getImpModelNo() == null) ? 0 : getImpModelNo().hashCode());
        result = prime * result + ((getOverseaInvoiceNo() == null) ? 0 : getOverseaInvoiceNo().hashCode());
        result = prime * result + ((getNoncommercial() == null) ? 0 : getNoncommercial().hashCode());
        result = prime * result + ((getExciseTax() == null) ? 0 : getExciseTax().hashCode());
        result = prime * result + ((getOtherFee() == null) ? 0 : getOtherFee().hashCode());
        result = prime * result + ((getTransFee() == null) ? 0 : getTransFee().hashCode());
        result = prime * result + ((getPoWarehousecode() == null) ? 0 : getPoWarehousecode().hashCode());
        result = prime * result + ((getVatFee() == null) ? 0 : getVatFee().hashCode());
        result = prime * result + ((getSnCode() == null) ? 0 : getSnCode().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        return result;
    }
}