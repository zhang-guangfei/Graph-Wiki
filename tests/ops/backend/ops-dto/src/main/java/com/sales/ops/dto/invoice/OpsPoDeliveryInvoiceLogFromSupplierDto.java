package com.sales.ops.dto.invoice;


import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-02-29
 */
public class OpsPoDeliveryInvoiceLogFromSupplierDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String gwInvoiceStatus;

    private Date invoiceDate;

    private Date importDate;

    private Date createTime;

    private Date actArrivalTime;

    private Long sourceId;

    private String updateUser;

    private String gwInvoiceNo;

    private String customsDeclarationNo;

    private Date shipDate;

    private String createUser;

    private String invoiceNo;

    private Integer delFlag;

    private String code;

    private Long id;

    private Date estArrivalTime;

    private Date customsDate;

    private String sourceType;

    private Date updateTime;

    private String smccode;
    // 开票主体
    private String issuerCode;

    // 生产商
    private String purchaserCode;
    // 是否关务报关标识(非关务报关写0，关务报关写1)
    private Integer isGw;
    // 到港日期
    private Date arrivalPortDate;

    private Long splitInvoiceId;

    public Long getSplitInvoiceId() {
        return splitInvoiceId;
    }

    public void setSplitInvoiceId(Long splitInvoiceId) {
        this.splitInvoiceId = splitInvoiceId;
    }

    public String getGwInvoiceStatus() {
        return gwInvoiceStatus;
    }

    public void setGwInvoiceStatus(String gwInvoiceStatus) {
        this.gwInvoiceStatus = gwInvoiceStatus;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getActArrivalTime() {
        return actArrivalTime;
    }

    public void setActArrivalTime(Date actArrivalTime) {
        this.actArrivalTime = actArrivalTime;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getGwInvoiceNo() {
        return gwInvoiceNo;
    }

    public void setGwInvoiceNo(String gwInvoiceNo) {
        this.gwInvoiceNo = gwInvoiceNo;
    }

    public String getCustomsDeclarationNo() {
        return customsDeclarationNo;
    }

    public void setCustomsDeclarationNo(String customsDeclarationNo) {
        this.customsDeclarationNo = customsDeclarationNo;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEstArrivalTime() {
        return estArrivalTime;
    }

    public void setEstArrivalTime(Date estArrivalTime) {
        this.estArrivalTime = estArrivalTime;
    }

    public Date getCustomsDate() {
        return customsDate;
    }

    public void setCustomsDate(Date customsDate) {
        this.customsDate = customsDate;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode;
    }

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    public String getPurchaserCode() {
        return purchaserCode;
    }

    public void setPurchaserCode(String purchaserCode) {
        this.purchaserCode = purchaserCode;
    }

    public Integer getIsGw() {
        return isGw;
    }

    public void setIsGw(Integer isGw) {
        this.isGw = isGw;
    }

    public Date getArrivalPortDate() {
        return arrivalPortDate;
    }

    public void setArrivalPortDate(Date arrivalPortDate) {
        this.arrivalPortDate = arrivalPortDate;
    }
}
