package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsRoBarcode implements Serializable {
    private Long id;

    private String roId;

    private Integer roItem;

    private String invoiceno;

    private String warehouseCode;

    private String barcode;

    private String packageCode;

    private String modelno;

    private Integer qty;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String sn;

    private Integer scan;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Date scantime;

    private Integer num;

    private Integer assNum;

    private Long invoiceid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId == null ? null : roId.trim();
    }

    public Integer getRoItem() {
        return roItem;
    }

    public void setRoItem(Integer roItem) {
        this.roItem = roItem;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode == null ? null : packageCode.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Integer getScan() {
        return scan;
    }

    public void setScan(Integer scan) {
        this.scan = scan;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getScantime() {
        return scantime;
    }

    public void setScantime(Date scantime) {
        this.scantime = scantime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAssNum() {
        return assNum;
    }

    public void setAssNum(Integer assNum) {
        this.assNum = assNum;
    }

    public Long getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Long invoiceid) {
        this.invoiceid = invoiceid;
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
        OpsRoBarcode other = (OpsRoBarcode) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoId() == null ? other.getRoId() == null : this.getRoId().equals(other.getRoId()))
            && (this.getRoItem() == null ? other.getRoItem() == null : this.getRoItem().equals(other.getRoItem()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getBarcode() == null ? other.getBarcode() == null : this.getBarcode().equals(other.getBarcode()))
            && (this.getPackageCode() == null ? other.getPackageCode() == null : this.getPackageCode().equals(other.getPackageCode()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getSn() == null ? other.getSn() == null : this.getSn().equals(other.getSn()))
            && (this.getScan() == null ? other.getScan() == null : this.getScan().equals(other.getScan()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getScantime() == null ? other.getScantime() == null : this.getScantime().equals(other.getScantime()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getAssNum() == null ? other.getAssNum() == null : this.getAssNum().equals(other.getAssNum()))
            && (this.getInvoiceid() == null ? other.getInvoiceid() == null : this.getInvoiceid().equals(other.getInvoiceid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoId() == null) ? 0 : getRoId().hashCode());
        result = prime * result + ((getRoItem() == null) ? 0 : getRoItem().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getBarcode() == null) ? 0 : getBarcode().hashCode());
        result = prime * result + ((getPackageCode() == null) ? 0 : getPackageCode().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getSn() == null) ? 0 : getSn().hashCode());
        result = prime * result + ((getScan() == null) ? 0 : getScan().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getScantime() == null) ? 0 : getScantime().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getAssNum() == null) ? 0 : getAssNum().hashCode());
        result = prime * result + ((getInvoiceid() == null) ? 0 : getInvoiceid().hashCode());
        return result;
    }
}