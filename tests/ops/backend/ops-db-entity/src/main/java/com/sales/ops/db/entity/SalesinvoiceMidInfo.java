package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SalesinvoiceMidInfo implements Serializable {
    private Integer id;

    private String orderno;

    private String customerno;

    private String userno;

    private String deptno;

    private String modelno;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal feerate;

    private String tradecompanyid;

    private String type;

    private String remark;

    private Date inserttime;

    private String isnew;

    private String applyno;

    private Integer applynoItem;

    private Integer canbackquantity;

    private String tocustomerstock;

    private String neworderno;

    private String rorderNo;

    private Integer rorderItem;

    private String stateCode;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFeerate() {
        return feerate;
    }

    public void setFeerate(BigDecimal feerate) {
        this.feerate = feerate;
    }

    public String getTradecompanyid() {
        return tradecompanyid;
    }

    public void setTradecompanyid(String tradecompanyid) {
        this.tradecompanyid = tradecompanyid == null ? null : tradecompanyid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew == null ? null : isnew.trim();
    }

    public String getApplyno() {
        return applyno;
    }

    public void setApplyno(String applyno) {
        this.applyno = applyno == null ? null : applyno.trim();
    }

    public Integer getApplynoItem() {
        return applynoItem;
    }

    public void setApplynoItem(Integer applynoItem) {
        this.applynoItem = applynoItem;
    }

    public Integer getCanbackquantity() {
        return canbackquantity;
    }

    public void setCanbackquantity(Integer canbackquantity) {
        this.canbackquantity = canbackquantity;
    }

    public String getTocustomerstock() {
        return tocustomerstock;
    }

    public void setTocustomerstock(String tocustomerstock) {
        this.tocustomerstock = tocustomerstock == null ? null : tocustomerstock.trim();
    }

    public String getNeworderno() {
        return neworderno;
    }

    public void setNeworderno(String neworderno) {
        this.neworderno = neworderno == null ? null : neworderno.trim();
    }

    public String getRorderNo() {
        return rorderNo;
    }

    public void setRorderNo(String rorderNo) {
        this.rorderNo = rorderNo == null ? null : rorderNo.trim();
    }

    public Integer getRorderItem() {
        return rorderItem;
    }

    public void setRorderItem(Integer rorderItem) {
        this.rorderItem = rorderItem;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode == null ? null : stateCode.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
        SalesinvoiceMidInfo other = (SalesinvoiceMidInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getFeerate() == null ? other.getFeerate() == null : this.getFeerate().equals(other.getFeerate()))
            && (this.getTradecompanyid() == null ? other.getTradecompanyid() == null : this.getTradecompanyid().equals(other.getTradecompanyid()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()))
            && (this.getIsnew() == null ? other.getIsnew() == null : this.getIsnew().equals(other.getIsnew()))
            && (this.getApplyno() == null ? other.getApplyno() == null : this.getApplyno().equals(other.getApplyno()))
            && (this.getApplynoItem() == null ? other.getApplynoItem() == null : this.getApplynoItem().equals(other.getApplynoItem()))
            && (this.getCanbackquantity() == null ? other.getCanbackquantity() == null : this.getCanbackquantity().equals(other.getCanbackquantity()))
            && (this.getTocustomerstock() == null ? other.getTocustomerstock() == null : this.getTocustomerstock().equals(other.getTocustomerstock()))
            && (this.getNeworderno() == null ? other.getNeworderno() == null : this.getNeworderno().equals(other.getNeworderno()))
            && (this.getRorderNo() == null ? other.getRorderNo() == null : this.getRorderNo().equals(other.getRorderNo()))
            && (this.getRorderItem() == null ? other.getRorderItem() == null : this.getRorderItem().equals(other.getRorderItem()))
            && (this.getStateCode() == null ? other.getStateCode() == null : this.getStateCode().equals(other.getStateCode()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getFeerate() == null) ? 0 : getFeerate().hashCode());
        result = prime * result + ((getTradecompanyid() == null) ? 0 : getTradecompanyid().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        result = prime * result + ((getIsnew() == null) ? 0 : getIsnew().hashCode());
        result = prime * result + ((getApplyno() == null) ? 0 : getApplyno().hashCode());
        result = prime * result + ((getApplynoItem() == null) ? 0 : getApplynoItem().hashCode());
        result = prime * result + ((getCanbackquantity() == null) ? 0 : getCanbackquantity().hashCode());
        result = prime * result + ((getTocustomerstock() == null) ? 0 : getTocustomerstock().hashCode());
        result = prime * result + ((getNeworderno() == null) ? 0 : getNeworderno().hashCode());
        result = prime * result + ((getRorderNo() == null) ? 0 : getRorderNo().hashCode());
        result = prime * result + ((getRorderItem() == null) ? 0 : getRorderItem().hashCode());
        result = prime * result + ((getStateCode() == null) ? 0 : getStateCode().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }
}