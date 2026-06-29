package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ShikomiInspection implements Serializable {
    private Long id;

    private Long shikomiid;

    private String shikomino;

    private String modelno;

    private Short inspecttype;

    private Integer qtywarning;

    private Date createtime;

    private String createuser;

    private Short warnqtyoptcode;

    private Short demandqtyoptcode;

    private String answertext;

    private Short cancelqtyoptcode;

    private Short opentoworld;

    private Short delaytocancel;

    private String applyno;

    private Short applytype;

    private Integer applyqty;

    private String reason;

    private Date applydate;

    private String applicantno;

    private Integer remainqty;

    private Integer cancelqty;

    private Integer customerqty;

    private Date retentiondurationdate;

    private String expirationhandle;

    private Integer repairqty;

    private Short inspectstatus;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShikomiid() {
        return shikomiid;
    }

    public void setShikomiid(Long shikomiid) {
        this.shikomiid = shikomiid;
    }

    public String getShikomino() {
        return shikomino;
    }

    public void setShikomino(String shikomino) {
        this.shikomino = shikomino == null ? null : shikomino.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Short getInspecttype() {
        return inspecttype;
    }

    public void setInspecttype(Short inspecttype) {
        this.inspecttype = inspecttype;
    }

    public Integer getQtywarning() {
        return qtywarning;
    }

    public void setQtywarning(Integer qtywarning) {
        this.qtywarning = qtywarning;
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

    public Short getWarnqtyoptcode() {
        return warnqtyoptcode;
    }

    public void setWarnqtyoptcode(Short warnqtyoptcode) {
        this.warnqtyoptcode = warnqtyoptcode;
    }

    public Short getDemandqtyoptcode() {
        return demandqtyoptcode;
    }

    public void setDemandqtyoptcode(Short demandqtyoptcode) {
        this.demandqtyoptcode = demandqtyoptcode;
    }

    public String getAnswertext() {
        return answertext;
    }

    public void setAnswertext(String answertext) {
        this.answertext = answertext == null ? null : answertext.trim();
    }

    public Short getCancelqtyoptcode() {
        return cancelqtyoptcode;
    }

    public void setCancelqtyoptcode(Short cancelqtyoptcode) {
        this.cancelqtyoptcode = cancelqtyoptcode;
    }

    public Short getOpentoworld() {
        return opentoworld;
    }

    public void setOpentoworld(Short opentoworld) {
        this.opentoworld = opentoworld;
    }

    public Short getDelaytocancel() {
        return delaytocancel;
    }

    public void setDelaytocancel(Short delaytocancel) {
        this.delaytocancel = delaytocancel;
    }

    public String getApplyno() {
        return applyno;
    }

    public void setApplyno(String applyno) {
        this.applyno = applyno == null ? null : applyno.trim();
    }

    public Short getApplytype() {
        return applytype;
    }

    public void setApplytype(Short applytype) {
        this.applytype = applytype;
    }

    public Integer getApplyqty() {
        return applyqty;
    }

    public void setApplyqty(Integer applyqty) {
        this.applyqty = applyqty;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getApplydate() {
        return applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    public String getApplicantno() {
        return applicantno;
    }

    public void setApplicantno(String applicantno) {
        this.applicantno = applicantno == null ? null : applicantno.trim();
    }

    public Integer getRemainqty() {
        return remainqty;
    }

    public void setRemainqty(Integer remainqty) {
        this.remainqty = remainqty;
    }

    public Integer getCancelqty() {
        return cancelqty;
    }

    public void setCancelqty(Integer cancelqty) {
        this.cancelqty = cancelqty;
    }

    public Integer getCustomerqty() {
        return customerqty;
    }

    public void setCustomerqty(Integer customerqty) {
        this.customerqty = customerqty;
    }

    public Date getRetentiondurationdate() {
        return retentiondurationdate;
    }

    public void setRetentiondurationdate(Date retentiondurationdate) {
        this.retentiondurationdate = retentiondurationdate;
    }

    public String getExpirationhandle() {
        return expirationhandle;
    }

    public void setExpirationhandle(String expirationhandle) {
        this.expirationhandle = expirationhandle == null ? null : expirationhandle.trim();
    }

    public Integer getRepairqty() {
        return repairqty;
    }

    public void setRepairqty(Integer repairqty) {
        this.repairqty = repairqty;
    }

    public Short getInspectstatus() {
        return inspectstatus;
    }

    public void setInspectstatus(Short inspectstatus) {
        this.inspectstatus = inspectstatus;
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
        ShikomiInspection other = (ShikomiInspection) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getShikomiid() == null ? other.getShikomiid() == null : this.getShikomiid().equals(other.getShikomiid()))
            && (this.getShikomino() == null ? other.getShikomino() == null : this.getShikomino().equals(other.getShikomino()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getInspecttype() == null ? other.getInspecttype() == null : this.getInspecttype().equals(other.getInspecttype()))
            && (this.getQtywarning() == null ? other.getQtywarning() == null : this.getQtywarning().equals(other.getQtywarning()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getWarnqtyoptcode() == null ? other.getWarnqtyoptcode() == null : this.getWarnqtyoptcode().equals(other.getWarnqtyoptcode()))
            && (this.getDemandqtyoptcode() == null ? other.getDemandqtyoptcode() == null : this.getDemandqtyoptcode().equals(other.getDemandqtyoptcode()))
            && (this.getAnswertext() == null ? other.getAnswertext() == null : this.getAnswertext().equals(other.getAnswertext()))
            && (this.getCancelqtyoptcode() == null ? other.getCancelqtyoptcode() == null : this.getCancelqtyoptcode().equals(other.getCancelqtyoptcode()))
            && (this.getOpentoworld() == null ? other.getOpentoworld() == null : this.getOpentoworld().equals(other.getOpentoworld()))
            && (this.getDelaytocancel() == null ? other.getDelaytocancel() == null : this.getDelaytocancel().equals(other.getDelaytocancel()))
            && (this.getApplyno() == null ? other.getApplyno() == null : this.getApplyno().equals(other.getApplyno()))
            && (this.getApplytype() == null ? other.getApplytype() == null : this.getApplytype().equals(other.getApplytype()))
            && (this.getApplyqty() == null ? other.getApplyqty() == null : this.getApplyqty().equals(other.getApplyqty()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getApplydate() == null ? other.getApplydate() == null : this.getApplydate().equals(other.getApplydate()))
            && (this.getApplicantno() == null ? other.getApplicantno() == null : this.getApplicantno().equals(other.getApplicantno()))
            && (this.getRemainqty() == null ? other.getRemainqty() == null : this.getRemainqty().equals(other.getRemainqty()))
            && (this.getCancelqty() == null ? other.getCancelqty() == null : this.getCancelqty().equals(other.getCancelqty()))
            && (this.getCustomerqty() == null ? other.getCustomerqty() == null : this.getCustomerqty().equals(other.getCustomerqty()))
            && (this.getRetentiondurationdate() == null ? other.getRetentiondurationdate() == null : this.getRetentiondurationdate().equals(other.getRetentiondurationdate()))
            && (this.getExpirationhandle() == null ? other.getExpirationhandle() == null : this.getExpirationhandle().equals(other.getExpirationhandle()))
            && (this.getRepairqty() == null ? other.getRepairqty() == null : this.getRepairqty().equals(other.getRepairqty()))
            && (this.getInspectstatus() == null ? other.getInspectstatus() == null : this.getInspectstatus().equals(other.getInspectstatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getShikomiid() == null) ? 0 : getShikomiid().hashCode());
        result = prime * result + ((getShikomino() == null) ? 0 : getShikomino().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getInspecttype() == null) ? 0 : getInspecttype().hashCode());
        result = prime * result + ((getQtywarning() == null) ? 0 : getQtywarning().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getWarnqtyoptcode() == null) ? 0 : getWarnqtyoptcode().hashCode());
        result = prime * result + ((getDemandqtyoptcode() == null) ? 0 : getDemandqtyoptcode().hashCode());
        result = prime * result + ((getAnswertext() == null) ? 0 : getAnswertext().hashCode());
        result = prime * result + ((getCancelqtyoptcode() == null) ? 0 : getCancelqtyoptcode().hashCode());
        result = prime * result + ((getOpentoworld() == null) ? 0 : getOpentoworld().hashCode());
        result = prime * result + ((getDelaytocancel() == null) ? 0 : getDelaytocancel().hashCode());
        result = prime * result + ((getApplyno() == null) ? 0 : getApplyno().hashCode());
        result = prime * result + ((getApplytype() == null) ? 0 : getApplytype().hashCode());
        result = prime * result + ((getApplyqty() == null) ? 0 : getApplyqty().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getApplydate() == null) ? 0 : getApplydate().hashCode());
        result = prime * result + ((getApplicantno() == null) ? 0 : getApplicantno().hashCode());
        result = prime * result + ((getRemainqty() == null) ? 0 : getRemainqty().hashCode());
        result = prime * result + ((getCancelqty() == null) ? 0 : getCancelqty().hashCode());
        result = prime * result + ((getCustomerqty() == null) ? 0 : getCustomerqty().hashCode());
        result = prime * result + ((getRetentiondurationdate() == null) ? 0 : getRetentiondurationdate().hashCode());
        result = prime * result + ((getExpirationhandle() == null) ? 0 : getExpirationhandle().hashCode());
        result = prime * result + ((getRepairqty() == null) ? 0 : getRepairqty().hashCode());
        result = prime * result + ((getInspectstatus() == null) ? 0 : getInspectstatus().hashCode());
        return result;
    }
}