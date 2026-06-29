package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShikomiBj implements Serializable {
    private Integer id;

    private String applyno;

    private Date applydate;

    private String answerno;

    private String modelno;

    private Integer quantity;

    private Date orddate;

    private Integer assedate;

    private Integer residueno;

    private String pid;

    private Integer qtyordpre;

    private String optstate;

    private Integer qtywarning;

    private Integer qtynoord;

    private String remark;

    private BigDecimal priceLot;

    private Integer partprepareDays;

    private String descriptionRemark;

    private String customerno;

    private String deptno;

    private String indcode;

    private String shikomiClass;

    private String shikomiClassCode;

    private String username;

    private Date opttime;

    private Integer isremind;

    private String applicantname;

    private String applicantemail;

    private String approvername;

    private String approveremail;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyno() {
        return applyno;
    }

    public void setApplyno(String applyno) {
        this.applyno = applyno == null ? null : applyno.trim();
    }

    public Date getApplydate() {
        return applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    public String getAnswerno() {
        return answerno;
    }

    public void setAnswerno(String answerno) {
        this.answerno = answerno == null ? null : answerno.trim();
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

    public Date getOrddate() {
        return orddate;
    }

    public void setOrddate(Date orddate) {
        this.orddate = orddate;
    }

    public Integer getAssedate() {
        return assedate;
    }

    public void setAssedate(Integer assedate) {
        this.assedate = assedate;
    }

    public Integer getResidueno() {
        return residueno;
    }

    public void setResidueno(Integer residueno) {
        this.residueno = residueno;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Integer getQtyordpre() {
        return qtyordpre;
    }

    public void setQtyordpre(Integer qtyordpre) {
        this.qtyordpre = qtyordpre;
    }

    public String getOptstate() {
        return optstate;
    }

    public void setOptstate(String optstate) {
        this.optstate = optstate == null ? null : optstate.trim();
    }

    public Integer getQtywarning() {
        return qtywarning;
    }

    public void setQtywarning(Integer qtywarning) {
        this.qtywarning = qtywarning;
    }

    public Integer getQtynoord() {
        return qtynoord;
    }

    public void setQtynoord(Integer qtynoord) {
        this.qtynoord = qtynoord;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getPriceLot() {
        return priceLot;
    }

    public void setPriceLot(BigDecimal priceLot) {
        this.priceLot = priceLot;
    }

    public Integer getPartprepareDays() {
        return partprepareDays;
    }

    public void setPartprepareDays(Integer partprepareDays) {
        this.partprepareDays = partprepareDays;
    }

    public String getDescriptionRemark() {
        return descriptionRemark;
    }

    public void setDescriptionRemark(String descriptionRemark) {
        this.descriptionRemark = descriptionRemark == null ? null : descriptionRemark.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getIndcode() {
        return indcode;
    }

    public void setIndcode(String indcode) {
        this.indcode = indcode == null ? null : indcode.trim();
    }

    public String getShikomiClass() {
        return shikomiClass;
    }

    public void setShikomiClass(String shikomiClass) {
        this.shikomiClass = shikomiClass == null ? null : shikomiClass.trim();
    }

    public String getShikomiClassCode() {
        return shikomiClassCode;
    }

    public void setShikomiClassCode(String shikomiClassCode) {
        this.shikomiClassCode = shikomiClassCode == null ? null : shikomiClassCode.trim();
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

    public Integer getIsremind() {
        return isremind;
    }

    public void setIsremind(Integer isremind) {
        this.isremind = isremind;
    }

    public String getApplicantname() {
        return applicantname;
    }

    public void setApplicantname(String applicantname) {
        this.applicantname = applicantname == null ? null : applicantname.trim();
    }

    public String getApplicantemail() {
        return applicantemail;
    }

    public void setApplicantemail(String applicantemail) {
        this.applicantemail = applicantemail == null ? null : applicantemail.trim();
    }

    public String getApprovername() {
        return approvername;
    }

    public void setApprovername(String approvername) {
        this.approvername = approvername == null ? null : approvername.trim();
    }

    public String getApproveremail() {
        return approveremail;
    }

    public void setApproveremail(String approveremail) {
        this.approveremail = approveremail == null ? null : approveremail.trim();
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
        ShikomiBj other = (ShikomiBj) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplyno() == null ? other.getApplyno() == null : this.getApplyno().equals(other.getApplyno()))
            && (this.getApplydate() == null ? other.getApplydate() == null : this.getApplydate().equals(other.getApplydate()))
            && (this.getAnswerno() == null ? other.getAnswerno() == null : this.getAnswerno().equals(other.getAnswerno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getOrddate() == null ? other.getOrddate() == null : this.getOrddate().equals(other.getOrddate()))
            && (this.getAssedate() == null ? other.getAssedate() == null : this.getAssedate().equals(other.getAssedate()))
            && (this.getResidueno() == null ? other.getResidueno() == null : this.getResidueno().equals(other.getResidueno()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getQtyordpre() == null ? other.getQtyordpre() == null : this.getQtyordpre().equals(other.getQtyordpre()))
            && (this.getOptstate() == null ? other.getOptstate() == null : this.getOptstate().equals(other.getOptstate()))
            && (this.getQtywarning() == null ? other.getQtywarning() == null : this.getQtywarning().equals(other.getQtywarning()))
            && (this.getQtynoord() == null ? other.getQtynoord() == null : this.getQtynoord().equals(other.getQtynoord()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getPriceLot() == null ? other.getPriceLot() == null : this.getPriceLot().equals(other.getPriceLot()))
            && (this.getPartprepareDays() == null ? other.getPartprepareDays() == null : this.getPartprepareDays().equals(other.getPartprepareDays()))
            && (this.getDescriptionRemark() == null ? other.getDescriptionRemark() == null : this.getDescriptionRemark().equals(other.getDescriptionRemark()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getIndcode() == null ? other.getIndcode() == null : this.getIndcode().equals(other.getIndcode()))
            && (this.getShikomiClass() == null ? other.getShikomiClass() == null : this.getShikomiClass().equals(other.getShikomiClass()))
            && (this.getShikomiClassCode() == null ? other.getShikomiClassCode() == null : this.getShikomiClassCode().equals(other.getShikomiClassCode()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()))
            && (this.getIsremind() == null ? other.getIsremind() == null : this.getIsremind().equals(other.getIsremind()))
            && (this.getApplicantname() == null ? other.getApplicantname() == null : this.getApplicantname().equals(other.getApplicantname()))
            && (this.getApplicantemail() == null ? other.getApplicantemail() == null : this.getApplicantemail().equals(other.getApplicantemail()))
            && (this.getApprovername() == null ? other.getApprovername() == null : this.getApprovername().equals(other.getApprovername()))
            && (this.getApproveremail() == null ? other.getApproveremail() == null : this.getApproveremail().equals(other.getApproveremail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplyno() == null) ? 0 : getApplyno().hashCode());
        result = prime * result + ((getApplydate() == null) ? 0 : getApplydate().hashCode());
        result = prime * result + ((getAnswerno() == null) ? 0 : getAnswerno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getOrddate() == null) ? 0 : getOrddate().hashCode());
        result = prime * result + ((getAssedate() == null) ? 0 : getAssedate().hashCode());
        result = prime * result + ((getResidueno() == null) ? 0 : getResidueno().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getQtyordpre() == null) ? 0 : getQtyordpre().hashCode());
        result = prime * result + ((getOptstate() == null) ? 0 : getOptstate().hashCode());
        result = prime * result + ((getQtywarning() == null) ? 0 : getQtywarning().hashCode());
        result = prime * result + ((getQtynoord() == null) ? 0 : getQtynoord().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getPriceLot() == null) ? 0 : getPriceLot().hashCode());
        result = prime * result + ((getPartprepareDays() == null) ? 0 : getPartprepareDays().hashCode());
        result = prime * result + ((getDescriptionRemark() == null) ? 0 : getDescriptionRemark().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getIndcode() == null) ? 0 : getIndcode().hashCode());
        result = prime * result + ((getShikomiClass() == null) ? 0 : getShikomiClass().hashCode());
        result = prime * result + ((getShikomiClassCode() == null) ? 0 : getShikomiClassCode().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        result = prime * result + ((getIsremind() == null) ? 0 : getIsremind().hashCode());
        result = prime * result + ((getApplicantname() == null) ? 0 : getApplicantname().hashCode());
        result = prime * result + ((getApplicantemail() == null) ? 0 : getApplicantemail().hashCode());
        result = prime * result + ((getApprovername() == null) ? 0 : getApprovername().hashCode());
        result = prime * result + ((getApproveremail() == null) ? 0 : getApproveremail().hashCode());
        return result;
    }
}