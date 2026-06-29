package com.sales.ops.dto.purchase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/12/1 10:45
 */
public class ImpDataDto implements Serializable {

    private Long id;

    private Long invoiceid;

    private String invoiceno;

    private String pono;

    private Integer lineitem;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String modelno;

    private Integer quantity;

    private String barcode;

    private String caseno;

    private String supplierid;

    private String greencode;

    private String receivewarehouseid;

    private Date receivedate;

    private Date imptime;

    private String statecode;

    private Date inserttime;

    private Date updatetime;

    private Date supplierexpdate;

    private String operate;

    private BigDecimal weight;

    private String transferStatus;

    private String transferCarried;

    private String transferExpressCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Long invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono;
    }

    public Integer getLineitem() {
        return lineitem;
    }

    public void setLineitem(Integer lineitem) {
        this.lineitem = lineitem;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getGreencode() {
        return greencode;
    }

    public void setGreencode(String greencode) {
        this.greencode = greencode;
    }

    public String getReceivewarehouseid() {
        return receivewarehouseid;
    }

    public void setReceivewarehouseid(String receivewarehouseid) {
        this.receivewarehouseid = receivewarehouseid;
    }

    public Date getReceivedate() {
        return receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

    public Date getImptime() {
        return imptime;
    }

    public void setImptime(Date imptime) {
        this.imptime = imptime;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getSupplierexpdate() {
        return supplierexpdate;
    }

    public void setSupplierexpdate(Date supplierexpdate) {
        this.supplierexpdate = supplierexpdate;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getTransferCarried() {
        return transferCarried;
    }

    public void setTransferCarried(String transferCarried) {
        this.transferCarried = transferCarried;
    }

    public String getTransferExpressCode() {
        return transferExpressCode;
    }

    public void setTransferExpressCode(String transferExpressCode) {
        this.transferExpressCode = transferExpressCode;
    }
}
