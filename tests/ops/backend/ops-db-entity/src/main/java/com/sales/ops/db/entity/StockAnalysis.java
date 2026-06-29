package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StockAnalysis implements Serializable {
    private Long id;

    private Integer calcid;

    private Date monthdate;

    private String warehousecode;

    private String modelno;

    private Integer avaqty;

    private Integer ordqty;

    private Integer transqty;

    private Date intime;

    private Date outtime;

    private Long propertyid;

    private String inventorytypecode;

    private String ppl;

    private String customerno;

    private String groupcustomerno;

    private String projectno;

    private String salesinfono;

    private String deptno;

    private Integer freq;

    private Integer mean;

    private BigDecimal eprice;

    private Short status;

    private Integer applyoutqty;

    private Date analysetime;

    private Date delaydate;

    private Boolean delayconsume;

    private String applypsn;

    private Date applytime;

    private Date dlvdate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCalcid() {
        return calcid;
    }

    public void setCalcid(Integer calcid) {
        this.calcid = calcid;
    }

    public Date getMonthdate() {
        return monthdate;
    }

    public void setMonthdate(Date monthdate) {
        this.monthdate = monthdate;
    }

    public String getWarehousecode() {
        return warehousecode;
    }

    public void setWarehousecode(String warehousecode) {
        this.warehousecode = warehousecode == null ? null : warehousecode.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getAvaqty() {
        return avaqty;
    }

    public void setAvaqty(Integer avaqty) {
        this.avaqty = avaqty;
    }

    public Integer getOrdqty() {
        return ordqty;
    }

    public void setOrdqty(Integer ordqty) {
        this.ordqty = ordqty;
    }

    public Integer getTransqty() {
        return transqty;
    }

    public void setTransqty(Integer transqty) {
        this.transqty = transqty;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Date getOuttime() {
        return outtime;
    }

    public void setOuttime(Date outtime) {
        this.outtime = outtime;
    }

    public Long getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(Long propertyid) {
        this.propertyid = propertyid;
    }

    public String getInventorytypecode() {
        return inventorytypecode;
    }

    public void setInventorytypecode(String inventorytypecode) {
        this.inventorytypecode = inventorytypecode == null ? null : inventorytypecode.trim();
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl == null ? null : ppl.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getGroupcustomerno() {
        return groupcustomerno;
    }

    public void setGroupcustomerno(String groupcustomerno) {
        this.groupcustomerno = groupcustomerno == null ? null : groupcustomerno.trim();
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno == null ? null : projectno.trim();
    }

    public String getSalesinfono() {
        return salesinfono;
    }

    public void setSalesinfono(String salesinfono) {
        this.salesinfono = salesinfono == null ? null : salesinfono.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
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

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getApplyoutqty() {
        return applyoutqty;
    }

    public void setApplyoutqty(Integer applyoutqty) {
        this.applyoutqty = applyoutqty;
    }

    public Date getAnalysetime() {
        return analysetime;
    }

    public void setAnalysetime(Date analysetime) {
        this.analysetime = analysetime;
    }

    public Date getDelaydate() {
        return delaydate;
    }

    public void setDelaydate(Date delaydate) {
        this.delaydate = delaydate;
    }

    public Boolean getDelayconsume() {
        return delayconsume;
    }

    public void setDelayconsume(Boolean delayconsume) {
        this.delayconsume = delayconsume;
    }

    public String getApplypsn() {
        return applypsn;
    }

    public void setApplypsn(String applypsn) {
        this.applypsn = applypsn == null ? null : applypsn.trim();
    }

    public Date getApplytime() {
        return applytime;
    }

    public void setApplytime(Date applytime) {
        this.applytime = applytime;
    }

    public Date getDlvdate() {
        return dlvdate;
    }

    public void setDlvdate(Date dlvdate) {
        this.dlvdate = dlvdate;
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
        StockAnalysis other = (StockAnalysis) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCalcid() == null ? other.getCalcid() == null : this.getCalcid().equals(other.getCalcid()))
            && (this.getMonthdate() == null ? other.getMonthdate() == null : this.getMonthdate().equals(other.getMonthdate()))
            && (this.getWarehousecode() == null ? other.getWarehousecode() == null : this.getWarehousecode().equals(other.getWarehousecode()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getAvaqty() == null ? other.getAvaqty() == null : this.getAvaqty().equals(other.getAvaqty()))
            && (this.getOrdqty() == null ? other.getOrdqty() == null : this.getOrdqty().equals(other.getOrdqty()))
            && (this.getTransqty() == null ? other.getTransqty() == null : this.getTransqty().equals(other.getTransqty()))
            && (this.getIntime() == null ? other.getIntime() == null : this.getIntime().equals(other.getIntime()))
            && (this.getOuttime() == null ? other.getOuttime() == null : this.getOuttime().equals(other.getOuttime()))
            && (this.getPropertyid() == null ? other.getPropertyid() == null : this.getPropertyid().equals(other.getPropertyid()))
            && (this.getInventorytypecode() == null ? other.getInventorytypecode() == null : this.getInventorytypecode().equals(other.getInventorytypecode()))
            && (this.getPpl() == null ? other.getPpl() == null : this.getPpl().equals(other.getPpl()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getGroupcustomerno() == null ? other.getGroupcustomerno() == null : this.getGroupcustomerno().equals(other.getGroupcustomerno()))
            && (this.getProjectno() == null ? other.getProjectno() == null : this.getProjectno().equals(other.getProjectno()))
            && (this.getSalesinfono() == null ? other.getSalesinfono() == null : this.getSalesinfono().equals(other.getSalesinfono()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getFreq() == null ? other.getFreq() == null : this.getFreq().equals(other.getFreq()))
            && (this.getMean() == null ? other.getMean() == null : this.getMean().equals(other.getMean()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getApplyoutqty() == null ? other.getApplyoutqty() == null : this.getApplyoutqty().equals(other.getApplyoutqty()))
            && (this.getAnalysetime() == null ? other.getAnalysetime() == null : this.getAnalysetime().equals(other.getAnalysetime()))
            && (this.getDelaydate() == null ? other.getDelaydate() == null : this.getDelaydate().equals(other.getDelaydate()))
            && (this.getDelayconsume() == null ? other.getDelayconsume() == null : this.getDelayconsume().equals(other.getDelayconsume()))
            && (this.getApplypsn() == null ? other.getApplypsn() == null : this.getApplypsn().equals(other.getApplypsn()))
            && (this.getApplytime() == null ? other.getApplytime() == null : this.getApplytime().equals(other.getApplytime()))
            && (this.getDlvdate() == null ? other.getDlvdate() == null : this.getDlvdate().equals(other.getDlvdate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCalcid() == null) ? 0 : getCalcid().hashCode());
        result = prime * result + ((getMonthdate() == null) ? 0 : getMonthdate().hashCode());
        result = prime * result + ((getWarehousecode() == null) ? 0 : getWarehousecode().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getAvaqty() == null) ? 0 : getAvaqty().hashCode());
        result = prime * result + ((getOrdqty() == null) ? 0 : getOrdqty().hashCode());
        result = prime * result + ((getTransqty() == null) ? 0 : getTransqty().hashCode());
        result = prime * result + ((getIntime() == null) ? 0 : getIntime().hashCode());
        result = prime * result + ((getOuttime() == null) ? 0 : getOuttime().hashCode());
        result = prime * result + ((getPropertyid() == null) ? 0 : getPropertyid().hashCode());
        result = prime * result + ((getInventorytypecode() == null) ? 0 : getInventorytypecode().hashCode());
        result = prime * result + ((getPpl() == null) ? 0 : getPpl().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getGroupcustomerno() == null) ? 0 : getGroupcustomerno().hashCode());
        result = prime * result + ((getProjectno() == null) ? 0 : getProjectno().hashCode());
        result = prime * result + ((getSalesinfono() == null) ? 0 : getSalesinfono().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getFreq() == null) ? 0 : getFreq().hashCode());
        result = prime * result + ((getMean() == null) ? 0 : getMean().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getApplyoutqty() == null) ? 0 : getApplyoutqty().hashCode());
        result = prime * result + ((getAnalysetime() == null) ? 0 : getAnalysetime().hashCode());
        result = prime * result + ((getDelaydate() == null) ? 0 : getDelaydate().hashCode());
        result = prime * result + ((getDelayconsume() == null) ? 0 : getDelayconsume().hashCode());
        result = prime * result + ((getApplypsn() == null) ? 0 : getApplypsn().hashCode());
        result = prime * result + ((getApplytime() == null) ? 0 : getApplytime().hashCode());
        result = prime * result + ((getDlvdate() == null) ? 0 : getDlvdate().hashCode());
        return result;
    }
}