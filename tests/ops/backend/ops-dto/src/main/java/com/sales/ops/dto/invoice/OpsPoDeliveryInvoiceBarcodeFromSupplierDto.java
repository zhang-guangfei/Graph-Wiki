package com.sales.ops.dto.invoice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-02-22
 */
public class OpsPoDeliveryInvoiceBarcodeFromSupplierDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String createUser;

    private Long sourceId;

    private String hsCode;

    private String receiveWarehouseId;

    private Integer quantity;

    private Long id;

    private String caseNo;

    private Long pid;

    private String snCode;

    private String pono;

    private Date businessExecTime;

    private BigDecimal netWeight;

    private Integer delFlag;

    private Date deliveryTime;

    private String modelNo;

    private String barcode;

    private Date shipTime;

    private Date updateTime;

    private String supplierOrderNo;

    private String transtypeCode;

    private String updateUser;

    private String supplierLineItemNo;

    private Date createTime;

    private Date supplierSystemExecTime;

    private Integer lineItem;

    private String sourceType;

    private String roshCode;

    private String subCode;

    /**
     * origin
     * product_code
     * shelf_code
     * imp_order_no
     */
    // 原产国
    private String origin;
    private String productCode;
    // 货架号
    private String shelfCode;
    // 原始导入订单号+‘-’+项号
    private String impOrderNo;
    private Long fromId;

    // 生产商代码
    private String manufacturerCode;

    private String supplierCode;

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public String getReceiveWarehouseId() {
        return receiveWarehouseId;
    }

    public void setReceiveWarehouseId(String receiveWarehouseId) {
        this.receiveWarehouseId = receiveWarehouseId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono;
    }

    public Date getBusinessExecTime() {
        return businessExecTime;
    }

    public void setBusinessExecTime(Date businessExecTime) {
        this.businessExecTime = businessExecTime;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo;
    }

    public String getTranstypeCode() {
        return transtypeCode;
    }

    public void setTranstypeCode(String transtypeCode) {
        this.transtypeCode = transtypeCode;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getSupplierLineItemNo() {
        return supplierLineItemNo;
    }

    public void setSupplierLineItemNo(String supplierLineItemNo) {
        this.supplierLineItemNo = supplierLineItemNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSupplierSystemExecTime() {
        return supplierSystemExecTime;
    }

    public void setSupplierSystemExecTime(Date supplierSystemExecTime) {
        this.supplierSystemExecTime = supplierSystemExecTime;
    }

    public Integer getLineItem() {
        return lineItem;
    }

    public void setLineItem(Integer lineItem) {
        this.lineItem = lineItem;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getRoshCode() {
        return roshCode;
    }

    public void setRoshCode(String roshCode) {
        this.roshCode = roshCode;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getShelfCode() {
        return shelfCode;
    }

    public void setShelfCode(String shelfCode) {
        this.shelfCode = shelfCode;
    }

    public String getImpOrderNo() {
        return impOrderNo;
    }

    public void setImpOrderNo(String impOrderNo) {
        this.impOrderNo = impOrderNo;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
}
