package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SampleBal implements Serializable {
    private Long id;

    private String invoiceno;

    private String customerno;

    private String rorderno;

    private String modelno;

    private Integer quantity;

    private String submodelno;

    private Integer subqty;

    private String deptdesc;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal taxamount;

    private Date optdate;

    private String apptype;

    private String baltype;

    private String prodflag;

    private String prodcode;

    private String optcode;

    private String ecode;

    private String remark;

    private String deptno;

    private Date expdate;

    private String modelinchn;

    private Date indate;

    private String ordtype;

    private String username;

    private Date opttime;

    private String applycode;

    private BigDecimal priceApply;

    private Date createtime;

    private String stockcode;

    private String rcvdeptno;

    private String claimNo;

    private BigDecimal claimAmount;

    private String expressCompany;

    private String source;

    private String warehouseCode;

    private String expdetailId;

    private String userNo;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno == null ? null : rorderno.trim();
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

    public String getSubmodelno() {
        return submodelno;
    }

    public void setSubmodelno(String submodelno) {
        this.submodelno = submodelno == null ? null : submodelno.trim();
    }

    public Integer getSubqty() {
        return subqty;
    }

    public void setSubqty(Integer subqty) {
        this.subqty = subqty;
    }

    public String getDeptdesc() {
        return deptdesc;
    }

    public void setDeptdesc(String deptdesc) {
        this.deptdesc = deptdesc == null ? null : deptdesc.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    public Date getOptdate() {
        return optdate;
    }

    public void setOptdate(Date optdate) {
        this.optdate = optdate;
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype == null ? null : apptype.trim();
    }

    public String getBaltype() {
        return baltype;
    }

    public void setBaltype(String baltype) {
        this.baltype = baltype == null ? null : baltype.trim();
    }

    public String getProdflag() {
        return prodflag;
    }

    public void setProdflag(String prodflag) {
        this.prodflag = prodflag == null ? null : prodflag.trim();
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode == null ? null : prodcode.trim();
    }

    public String getOptcode() {
        return optcode;
    }

    public void setOptcode(String optcode) {
        this.optcode = optcode == null ? null : optcode.trim();
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode == null ? null : ecode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public Date getExpdate() {
        return expdate;
    }

    public void setExpdate(Date expdate) {
        this.expdate = expdate;
    }

    public String getModelinchn() {
        return modelinchn;
    }

    public void setModelinchn(String modelinchn) {
        this.modelinchn = modelinchn == null ? null : modelinchn.trim();
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype == null ? null : ordtype.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getApplycode() {
        return applycode;
    }

    public void setApplycode(String applycode) {
        this.applycode = applycode == null ? null : applycode.trim();
    }

    public BigDecimal getPriceApply() {
        return priceApply;
    }

    public void setPriceApply(BigDecimal priceApply) {
        this.priceApply = priceApply;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public String getRcvdeptno() {
        return rcvdeptno;
    }

    public void setRcvdeptno(String rcvdeptno) {
        this.rcvdeptno = rcvdeptno == null ? null : rcvdeptno.trim();
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo == null ? null : claimNo.trim();
    }

    public BigDecimal getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(BigDecimal claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getExpdetailId() {
        return expdetailId;
    }

    public void setExpdetailId(String expdetailId) {
        this.expdetailId = expdetailId == null ? null : expdetailId.trim();
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
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
        SampleBal other = (SampleBal) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getSubmodelno() == null ? other.getSubmodelno() == null : this.getSubmodelno().equals(other.getSubmodelno()))
            && (this.getSubqty() == null ? other.getSubqty() == null : this.getSubqty().equals(other.getSubqty()))
            && (this.getDeptdesc() == null ? other.getDeptdesc() == null : this.getDeptdesc().equals(other.getDeptdesc()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getTaxamount() == null ? other.getTaxamount() == null : this.getTaxamount().equals(other.getTaxamount()))
            && (this.getOptdate() == null ? other.getOptdate() == null : this.getOptdate().equals(other.getOptdate()))
            && (this.getApptype() == null ? other.getApptype() == null : this.getApptype().equals(other.getApptype()))
            && (this.getBaltype() == null ? other.getBaltype() == null : this.getBaltype().equals(other.getBaltype()))
            && (this.getProdflag() == null ? other.getProdflag() == null : this.getProdflag().equals(other.getProdflag()))
            && (this.getProdcode() == null ? other.getProdcode() == null : this.getProdcode().equals(other.getProdcode()))
            && (this.getOptcode() == null ? other.getOptcode() == null : this.getOptcode().equals(other.getOptcode()))
            && (this.getEcode() == null ? other.getEcode() == null : this.getEcode().equals(other.getEcode()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getExpdate() == null ? other.getExpdate() == null : this.getExpdate().equals(other.getExpdate()))
            && (this.getModelinchn() == null ? other.getModelinchn() == null : this.getModelinchn().equals(other.getModelinchn()))
            && (this.getIndate() == null ? other.getIndate() == null : this.getIndate().equals(other.getIndate()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()))
            && (this.getApplycode() == null ? other.getApplycode() == null : this.getApplycode().equals(other.getApplycode()))
            && (this.getPriceApply() == null ? other.getPriceApply() == null : this.getPriceApply().equals(other.getPriceApply()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getRcvdeptno() == null ? other.getRcvdeptno() == null : this.getRcvdeptno().equals(other.getRcvdeptno()))
            && (this.getClaimNo() == null ? other.getClaimNo() == null : this.getClaimNo().equals(other.getClaimNo()))
            && (this.getClaimAmount() == null ? other.getClaimAmount() == null : this.getClaimAmount().equals(other.getClaimAmount()))
            && (this.getExpressCompany() == null ? other.getExpressCompany() == null : this.getExpressCompany().equals(other.getExpressCompany()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getExpdetailId() == null ? other.getExpdetailId() == null : this.getExpdetailId().equals(other.getExpdetailId()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getSubmodelno() == null) ? 0 : getSubmodelno().hashCode());
        result = prime * result + ((getSubqty() == null) ? 0 : getSubqty().hashCode());
        result = prime * result + ((getDeptdesc() == null) ? 0 : getDeptdesc().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getTaxamount() == null) ? 0 : getTaxamount().hashCode());
        result = prime * result + ((getOptdate() == null) ? 0 : getOptdate().hashCode());
        result = prime * result + ((getApptype() == null) ? 0 : getApptype().hashCode());
        result = prime * result + ((getBaltype() == null) ? 0 : getBaltype().hashCode());
        result = prime * result + ((getProdflag() == null) ? 0 : getProdflag().hashCode());
        result = prime * result + ((getProdcode() == null) ? 0 : getProdcode().hashCode());
        result = prime * result + ((getOptcode() == null) ? 0 : getOptcode().hashCode());
        result = prime * result + ((getEcode() == null) ? 0 : getEcode().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getExpdate() == null) ? 0 : getExpdate().hashCode());
        result = prime * result + ((getModelinchn() == null) ? 0 : getModelinchn().hashCode());
        result = prime * result + ((getIndate() == null) ? 0 : getIndate().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        result = prime * result + ((getApplycode() == null) ? 0 : getApplycode().hashCode());
        result = prime * result + ((getPriceApply() == null) ? 0 : getPriceApply().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getRcvdeptno() == null) ? 0 : getRcvdeptno().hashCode());
        result = prime * result + ((getClaimNo() == null) ? 0 : getClaimNo().hashCode());
        result = prime * result + ((getClaimAmount() == null) ? 0 : getClaimAmount().hashCode());
        result = prime * result + ((getExpressCompany() == null) ? 0 : getExpressCompany().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getExpdetailId() == null) ? 0 : getExpdetailId().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        return result;
    }
}