package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Slowmovingmodel implements Serializable {
    private Integer id;

    private String modelno;

    private Integer quantity;

    private Date lastindate;

    private Date lasoutdate;

    private String remark;

    private Date createtime;

    private String createuser;

    private Date updatetime;

    private String updateuser;

    private Integer status;

    private BigDecimal eprice;

    private BigDecimal lotprice;

    private String warehousecode;

    private String supplier;

    private Integer designtypeid;

    private String productname;

    private Integer leftqty;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getLastindate() {
        return lastindate;
    }

    public void setLastindate(Date lastindate) {
        this.lastindate = lastindate;
    }

    public Date getLasoutdate() {
        return lasoutdate;
    }

    public void setLasoutdate(Date lasoutdate) {
        this.lasoutdate = lasoutdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public BigDecimal getLotprice() {
        return lotprice;
    }

    public void setLotprice(BigDecimal lotprice) {
        this.lotprice = lotprice;
    }

    public String getWarehousecode() {
        return warehousecode;
    }

    public void setWarehousecode(String warehousecode) {
        this.warehousecode = warehousecode == null ? null : warehousecode.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public Integer getDesigntypeid() {
        return designtypeid;
    }

    public void setDesigntypeid(Integer designtypeid) {
        this.designtypeid = designtypeid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public Integer getLeftqty() {
        return leftqty;
    }

    public void setLeftqty(Integer leftqty) {
        this.leftqty = leftqty;
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
        Slowmovingmodel other = (Slowmovingmodel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getLastindate() == null ? other.getLastindate() == null : this.getLastindate().equals(other.getLastindate()))
            && (this.getLasoutdate() == null ? other.getLasoutdate() == null : this.getLasoutdate().equals(other.getLasoutdate()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getLotprice() == null ? other.getLotprice() == null : this.getLotprice().equals(other.getLotprice()))
            && (this.getWarehousecode() == null ? other.getWarehousecode() == null : this.getWarehousecode().equals(other.getWarehousecode()))
            && (this.getSupplier() == null ? other.getSupplier() == null : this.getSupplier().equals(other.getSupplier()))
            && (this.getDesigntypeid() == null ? other.getDesigntypeid() == null : this.getDesigntypeid().equals(other.getDesigntypeid()))
            && (this.getProductname() == null ? other.getProductname() == null : this.getProductname().equals(other.getProductname()))
            && (this.getLeftqty() == null ? other.getLeftqty() == null : this.getLeftqty().equals(other.getLeftqty()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getLastindate() == null) ? 0 : getLastindate().hashCode());
        result = prime * result + ((getLasoutdate() == null) ? 0 : getLasoutdate().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getLotprice() == null) ? 0 : getLotprice().hashCode());
        result = prime * result + ((getWarehousecode() == null) ? 0 : getWarehousecode().hashCode());
        result = prime * result + ((getSupplier() == null) ? 0 : getSupplier().hashCode());
        result = prime * result + ((getDesigntypeid() == null) ? 0 : getDesigntypeid().hashCode());
        result = prime * result + ((getProductname() == null) ? 0 : getProductname().hashCode());
        result = prime * result + ((getLeftqty() == null) ? 0 : getLeftqty().hashCode());
        return result;
    }
}