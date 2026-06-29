package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Bindata extends BindataKey implements Serializable {
    private Long id;

    private Integer qtybin;

    private Integer bincell;

    private String casetype;

    private String prodtype;

    private Date createtime;

    private Date updatetime;

    private String positionno;

    private String meshtype;

    private Integer incaseqty;

    private String adjustable;

    private Short delflag;

    private Short lastdelflag;

    private String bintype;

    private Integer safeqty;

    private Integer freq;

    private Integer mean;

    private String setlevel;

    private String newlevel;

    private Integer newfreq;

    private Integer newmean;

    private Integer setfreq;

    private String poType;

    private String suppliercode;

    private String ordertype;

    private String prodseri;

    private String staterange;

    private Integer minpackageqty;

    private Short directpurchase;

    private Short autorepl;

    private Short directdelivery;

    private BigDecimal eprice;

    private String ecode;

    private String modelseries;

    private String ppl;

    private String projectNo;

    private String inventoryTypeCode;

    private String groupCustomerNo;

    private String origin;

    private Date logindate;

    private Integer replqty;

    private Integer errormodel;

    private String setsuppliercode;

    private Integer applyqty;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQtybin() {
        return qtybin;
    }

    public void setQtybin(Integer qtybin) {
        this.qtybin = qtybin;
    }

    public Integer getBincell() {
        return bincell;
    }

    public void setBincell(Integer bincell) {
        this.bincell = bincell;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype == null ? null : casetype.trim();
    }

    public String getProdtype() {
        return prodtype;
    }

    public void setProdtype(String prodtype) {
        this.prodtype = prodtype == null ? null : prodtype.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPositionno() {
        return positionno;
    }

    public void setPositionno(String positionno) {
        this.positionno = positionno == null ? null : positionno.trim();
    }

    public String getMeshtype() {
        return meshtype;
    }

    public void setMeshtype(String meshtype) {
        this.meshtype = meshtype == null ? null : meshtype.trim();
    }

    public Integer getIncaseqty() {
        return incaseqty;
    }

    public void setIncaseqty(Integer incaseqty) {
        this.incaseqty = incaseqty;
    }

    public String getAdjustable() {
        return adjustable;
    }

    public void setAdjustable(String adjustable) {
        this.adjustable = adjustable == null ? null : adjustable.trim();
    }

    public Short getDelflag() {
        return delflag;
    }

    public void setDelflag(Short delflag) {
        this.delflag = delflag;
    }

    public Short getLastdelflag() {
        return lastdelflag;
    }

    public void setLastdelflag(Short lastdelflag) {
        this.lastdelflag = lastdelflag;
    }

    public String getBintype() {
        return bintype;
    }

    public void setBintype(String bintype) {
        this.bintype = bintype == null ? null : bintype.trim();
    }

    public Integer getSafeqty() {
        return safeqty;
    }

    public void setSafeqty(Integer safeqty) {
        this.safeqty = safeqty;
    }

    public Integer getFreq() {
        return freq;
    }

    public void setFreq(Integer freq) {
        this.freq = freq;
    }

    public Integer getMean() {
        return mean;
    }

    public void setMean(Integer mean) {
        this.mean = mean;
    }

    public String getSetlevel() {
        return setlevel;
    }

    public void setSetlevel(String setlevel) {
        this.setlevel = setlevel == null ? null : setlevel.trim();
    }

    public String getNewlevel() {
        return newlevel;
    }

    public void setNewlevel(String newlevel) {
        this.newlevel = newlevel == null ? null : newlevel.trim();
    }

    public Integer getNewfreq() {
        return newfreq;
    }

    public void setNewfreq(Integer newfreq) {
        this.newfreq = newfreq;
    }

    public Integer getNewmean() {
        return newmean;
    }

    public void setNewmean(Integer newmean) {
        this.newmean = newmean;
    }

    public Integer getSetfreq() {
        return setfreq;
    }

    public void setSetfreq(Integer setfreq) {
        this.setfreq = setfreq;
    }

    public String getPoType() {
        return poType;
    }

    public void setPoType(String poType) {
        this.poType = poType == null ? null : poType.trim();
    }

    public String getSuppliercode() {
        return suppliercode;
    }

    public void setSuppliercode(String suppliercode) {
        this.suppliercode = suppliercode == null ? null : suppliercode.trim();
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype == null ? null : ordertype.trim();
    }

    public String getProdseri() {
        return prodseri;
    }

    public void setProdseri(String prodseri) {
        this.prodseri = prodseri == null ? null : prodseri.trim();
    }

    public String getStaterange() {
        return staterange;
    }

    public void setStaterange(String staterange) {
        this.staterange = staterange == null ? null : staterange.trim();
    }

    public Integer getMinpackageqty() {
        return minpackageqty;
    }

    public void setMinpackageqty(Integer minpackageqty) {
        this.minpackageqty = minpackageqty;
    }

    public Short getDirectpurchase() {
        return directpurchase;
    }

    public void setDirectpurchase(Short directpurchase) {
        this.directpurchase = directpurchase;
    }

    public Short getAutorepl() {
        return autorepl;
    }

    public void setAutorepl(Short autorepl) {
        this.autorepl = autorepl;
    }

    public Short getDirectdelivery() {
        return directdelivery;
    }

    public void setDirectdelivery(Short directdelivery) {
        this.directdelivery = directdelivery;
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode == null ? null : ecode.trim();
    }

    public String getModelseries() {
        return modelseries;
    }

    public void setModelseries(String modelseries) {
        this.modelseries = modelseries == null ? null : modelseries.trim();
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl == null ? null : ppl.trim();
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode == null ? null : inventoryTypeCode.trim();
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo == null ? null : groupCustomerNo.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public Date getLogindate() {
        return logindate;
    }

    public void setLogindate(Date logindate) {
        this.logindate = logindate;
    }

    public Integer getReplqty() {
        return replqty;
    }

    public void setReplqty(Integer replqty) {
        this.replqty = replqty;
    }

    public Integer getErrormodel() {
        return errormodel;
    }

    public void setErrormodel(Integer errormodel) {
        this.errormodel = errormodel;
    }

    public String getSetsuppliercode() {
        return setsuppliercode;
    }

    public void setSetsuppliercode(String setsuppliercode) {
        this.setsuppliercode = setsuppliercode == null ? null : setsuppliercode.trim();
    }

    public Integer getApplyqty() {
        return applyqty;
    }

    public void setApplyqty(Integer applyqty) {
        this.applyqty = applyqty;
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
        Bindata other = (Bindata) that;
        return (this.getStocktype() == null ? other.getStocktype() == null : this.getStocktype().equals(other.getStocktype()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getPropertyId() == null ? other.getPropertyId() == null : this.getPropertyId().equals(other.getPropertyId()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getQtybin() == null ? other.getQtybin() == null : this.getQtybin().equals(other.getQtybin()))
            && (this.getBincell() == null ? other.getBincell() == null : this.getBincell().equals(other.getBincell()))
            && (this.getCasetype() == null ? other.getCasetype() == null : this.getCasetype().equals(other.getCasetype()))
            && (this.getProdtype() == null ? other.getProdtype() == null : this.getProdtype().equals(other.getProdtype()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getPositionno() == null ? other.getPositionno() == null : this.getPositionno().equals(other.getPositionno()))
            && (this.getMeshtype() == null ? other.getMeshtype() == null : this.getMeshtype().equals(other.getMeshtype()))
            && (this.getIncaseqty() == null ? other.getIncaseqty() == null : this.getIncaseqty().equals(other.getIncaseqty()))
            && (this.getAdjustable() == null ? other.getAdjustable() == null : this.getAdjustable().equals(other.getAdjustable()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getLastdelflag() == null ? other.getLastdelflag() == null : this.getLastdelflag().equals(other.getLastdelflag()))
            && (this.getBintype() == null ? other.getBintype() == null : this.getBintype().equals(other.getBintype()))
            && (this.getSafeqty() == null ? other.getSafeqty() == null : this.getSafeqty().equals(other.getSafeqty()))
            && (this.getFreq() == null ? other.getFreq() == null : this.getFreq().equals(other.getFreq()))
            && (this.getMean() == null ? other.getMean() == null : this.getMean().equals(other.getMean()))
            && (this.getSetlevel() == null ? other.getSetlevel() == null : this.getSetlevel().equals(other.getSetlevel()))
            && (this.getNewlevel() == null ? other.getNewlevel() == null : this.getNewlevel().equals(other.getNewlevel()))
            && (this.getNewfreq() == null ? other.getNewfreq() == null : this.getNewfreq().equals(other.getNewfreq()))
            && (this.getNewmean() == null ? other.getNewmean() == null : this.getNewmean().equals(other.getNewmean()))
            && (this.getSetfreq() == null ? other.getSetfreq() == null : this.getSetfreq().equals(other.getSetfreq()))
            && (this.getPoType() == null ? other.getPoType() == null : this.getPoType().equals(other.getPoType()))
            && (this.getSuppliercode() == null ? other.getSuppliercode() == null : this.getSuppliercode().equals(other.getSuppliercode()))
            && (this.getOrdertype() == null ? other.getOrdertype() == null : this.getOrdertype().equals(other.getOrdertype()))
            && (this.getProdseri() == null ? other.getProdseri() == null : this.getProdseri().equals(other.getProdseri()))
            && (this.getStaterange() == null ? other.getStaterange() == null : this.getStaterange().equals(other.getStaterange()))
            && (this.getMinpackageqty() == null ? other.getMinpackageqty() == null : this.getMinpackageqty().equals(other.getMinpackageqty()))
            && (this.getDirectpurchase() == null ? other.getDirectpurchase() == null : this.getDirectpurchase().equals(other.getDirectpurchase()))
            && (this.getAutorepl() == null ? other.getAutorepl() == null : this.getAutorepl().equals(other.getAutorepl()))
            && (this.getDirectdelivery() == null ? other.getDirectdelivery() == null : this.getDirectdelivery().equals(other.getDirectdelivery()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getEcode() == null ? other.getEcode() == null : this.getEcode().equals(other.getEcode()))
            && (this.getModelseries() == null ? other.getModelseries() == null : this.getModelseries().equals(other.getModelseries()))
            && (this.getPpl() == null ? other.getPpl() == null : this.getPpl().equals(other.getPpl()))
            && (this.getProjectNo() == null ? other.getProjectNo() == null : this.getProjectNo().equals(other.getProjectNo()))
            && (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getGroupCustomerNo() == null ? other.getGroupCustomerNo() == null : this.getGroupCustomerNo().equals(other.getGroupCustomerNo()))
            && (this.getOrigin() == null ? other.getOrigin() == null : this.getOrigin().equals(other.getOrigin()))
            && (this.getLogindate() == null ? other.getLogindate() == null : this.getLogindate().equals(other.getLogindate()))
            && (this.getReplqty() == null ? other.getReplqty() == null : this.getReplqty().equals(other.getReplqty()))
            && (this.getErrormodel() == null ? other.getErrormodel() == null : this.getErrormodel().equals(other.getErrormodel()))
            && (this.getSetsuppliercode() == null ? other.getSetsuppliercode() == null : this.getSetsuppliercode().equals(other.getSetsuppliercode()))
            && (this.getApplyqty() == null ? other.getApplyqty() == null : this.getApplyqty().equals(other.getApplyqty()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStocktype() == null) ? 0 : getStocktype().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getPropertyId() == null) ? 0 : getPropertyId().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getQtybin() == null) ? 0 : getQtybin().hashCode());
        result = prime * result + ((getBincell() == null) ? 0 : getBincell().hashCode());
        result = prime * result + ((getCasetype() == null) ? 0 : getCasetype().hashCode());
        result = prime * result + ((getProdtype() == null) ? 0 : getProdtype().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getPositionno() == null) ? 0 : getPositionno().hashCode());
        result = prime * result + ((getMeshtype() == null) ? 0 : getMeshtype().hashCode());
        result = prime * result + ((getIncaseqty() == null) ? 0 : getIncaseqty().hashCode());
        result = prime * result + ((getAdjustable() == null) ? 0 : getAdjustable().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getLastdelflag() == null) ? 0 : getLastdelflag().hashCode());
        result = prime * result + ((getBintype() == null) ? 0 : getBintype().hashCode());
        result = prime * result + ((getSafeqty() == null) ? 0 : getSafeqty().hashCode());
        result = prime * result + ((getFreq() == null) ? 0 : getFreq().hashCode());
        result = prime * result + ((getMean() == null) ? 0 : getMean().hashCode());
        result = prime * result + ((getSetlevel() == null) ? 0 : getSetlevel().hashCode());
        result = prime * result + ((getNewlevel() == null) ? 0 : getNewlevel().hashCode());
        result = prime * result + ((getNewfreq() == null) ? 0 : getNewfreq().hashCode());
        result = prime * result + ((getNewmean() == null) ? 0 : getNewmean().hashCode());
        result = prime * result + ((getSetfreq() == null) ? 0 : getSetfreq().hashCode());
        result = prime * result + ((getPoType() == null) ? 0 : getPoType().hashCode());
        result = prime * result + ((getSuppliercode() == null) ? 0 : getSuppliercode().hashCode());
        result = prime * result + ((getOrdertype() == null) ? 0 : getOrdertype().hashCode());
        result = prime * result + ((getProdseri() == null) ? 0 : getProdseri().hashCode());
        result = prime * result + ((getStaterange() == null) ? 0 : getStaterange().hashCode());
        result = prime * result + ((getMinpackageqty() == null) ? 0 : getMinpackageqty().hashCode());
        result = prime * result + ((getDirectpurchase() == null) ? 0 : getDirectpurchase().hashCode());
        result = prime * result + ((getAutorepl() == null) ? 0 : getAutorepl().hashCode());
        result = prime * result + ((getDirectdelivery() == null) ? 0 : getDirectdelivery().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getEcode() == null) ? 0 : getEcode().hashCode());
        result = prime * result + ((getModelseries() == null) ? 0 : getModelseries().hashCode());
        result = prime * result + ((getPpl() == null) ? 0 : getPpl().hashCode());
        result = prime * result + ((getProjectNo() == null) ? 0 : getProjectNo().hashCode());
        result = prime * result + ((getInventoryTypeCode() == null) ? 0 : getInventoryTypeCode().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getOrigin() == null) ? 0 : getOrigin().hashCode());
        result = prime * result + ((getLogindate() == null) ? 0 : getLogindate().hashCode());
        result = prime * result + ((getReplqty() == null) ? 0 : getReplqty().hashCode());
        result = prime * result + ((getErrormodel() == null) ? 0 : getErrormodel().hashCode());
        result = prime * result + ((getSetsuppliercode() == null) ? 0 : getSetsuppliercode().hashCode());
        result = prime * result + ((getApplyqty() == null) ? 0 : getApplyqty().hashCode());
        return result;
    }
}