package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Supplierinvoice implements Serializable {
    private Integer id;

    private Integer gzid;

    private String invoiceno;

    private String suppliercode;

    private String orderno;

    private Date shipdate;

    private String modelno;

    private Integer quantity;

    private String caseno;

    private String barcode;

    private BigDecimal price;

    private Short status;

    private String origincode;

    private String origincountry;

    private String unit;

    private String noref;

    private String productcode;

    private String transtype;

    private String remark;

    private Date imptime;

    private BigDecimal weight;

    private String modelenname;

    private String ordertype;

    private BigDecimal tariffrate;

    private String cnname;

    private Date updtime;

    private String jphscode;

    private String cnhscode;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGzid() {
        return gzid;
    }

    public void setGzid(Integer gzid) {
        this.gzid = gzid;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getSuppliercode() {
        return suppliercode;
    }

    public void setSuppliercode(String suppliercode) {
        this.suppliercode = suppliercode == null ? null : suppliercode.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Date getShipdate() {
        return shipdate;
    }

    public void setShipdate(Date shipdate) {
        this.shipdate = shipdate;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno == null ? null : caseno.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getOrigincode() {
        return origincode;
    }

    public void setOrigincode(String origincode) {
        this.origincode = origincode == null ? null : origincode.trim();
    }

    public String getOrigincountry() {
        return origincountry;
    }

    public void setOrigincountry(String origincountry) {
        this.origincountry = origincountry == null ? null : origincountry.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getNoref() {
        return noref;
    }

    public void setNoref(String noref) {
        this.noref = noref == null ? null : noref.trim();
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode == null ? null : productcode.trim();
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype == null ? null : transtype.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getImptime() {
        return imptime;
    }

    public void setImptime(Date imptime) {
        this.imptime = imptime;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getModelenname() {
        return modelenname;
    }

    public void setModelenname(String modelenname) {
        this.modelenname = modelenname == null ? null : modelenname.trim();
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype == null ? null : ordertype.trim();
    }

    public BigDecimal getTariffrate() {
        return tariffrate;
    }

    public void setTariffrate(BigDecimal tariffrate) {
        this.tariffrate = tariffrate;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname == null ? null : cnname.trim();
    }

    public Date getUpdtime() {
        return updtime;
    }

    public void setUpdtime(Date updtime) {
        this.updtime = updtime;
    }

    public String getJphscode() {
        return jphscode;
    }

    public void setJphscode(String jphscode) {
        this.jphscode = jphscode == null ? null : jphscode.trim();
    }

    public String getCnhscode() {
        return cnhscode;
    }

    public void setCnhscode(String cnhscode) {
        this.cnhscode = cnhscode == null ? null : cnhscode.trim();
    }
}