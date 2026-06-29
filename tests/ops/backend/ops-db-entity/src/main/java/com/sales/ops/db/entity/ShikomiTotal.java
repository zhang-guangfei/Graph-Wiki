package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class ShikomiTotal extends ShikomiTotalKey implements Serializable {
    private Long id;

    private String modelno;

    private Integer lastqty;

    private Integer applyqty;

    private Integer remainqty;

    private Integer maxqty;

    private Integer qtyordpre;

    private String serialmodel;

    private Short status;

    private String customerno;

    private String answertext;

    private String remark;

    private Date registdate;

    private Date revisedate;

    private String classtype;

    private String classcode;

    private String subsidiarycode;

    private String companycode;

    private String branchcode;

    private Date dlvdate;

    private String deptno;

    private Date applytime;

    private Integer orderqty;

    private Integer cancelqty;

    private String modeltype;

    private Date updatetime;

    private Date createtime;

    private String createuser;

    private String updateuser;

    private String applypsnname;

    private BigDecimal eprice;

    private Integer qtywarning;

    private String applicantno;

    private String applicantname;

    private String applicantemail;

    private String approverno;

    private String approvername;

    private String approveremail;

    private Integer assedays;

    private Integer qtynoord;

    private BigDecimal priceLot;

    private Integer lotqty;

    private String indcode;

    private Integer partpreparedays;

    private String applyno;

    private String rohs;

    private Integer qtypo;

    private Integer qtyonhand;

    private Short inspectstatus;

    private Date inspectsendtime;

    private Date inspectanswertime;

    private String inspectanswerpsnname;

    private String inspectanswerpsnno;

    private Short inspectdaily;

    private Short inspecttype;

    private String inspectanswertext;

    private Date planusedate;

    private String pplno;

    private String projectno;

    private Integer avgordqty;

    private Date lastorddate;

    private Short inspectapplytype;

    private String inspectapproverno;

    private String inspectapprovername;

    private Date inspectapprovertime;

    private Integer modelcount;

    private Integer inspectqty;

    private String inspectapplyno;

    private String iswarning;

    private byte[] warehousecode;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getLastqty() {
        return lastqty;
    }

    public void setLastqty(Integer lastqty) {
        this.lastqty = lastqty;
    }

    public Integer getApplyqty() {
        return applyqty;
    }

    public void setApplyqty(Integer applyqty) {
        this.applyqty = applyqty;
    }

    public Integer getRemainqty() {
        return remainqty;
    }

    public void setRemainqty(Integer remainqty) {
        this.remainqty = remainqty;
    }

    public Integer getMaxqty() {
        return maxqty;
    }

    public void setMaxqty(Integer maxqty) {
        this.maxqty = maxqty;
    }

    public Integer getQtyordpre() {
        return qtyordpre;
    }

    public void setQtyordpre(Integer qtyordpre) {
        this.qtyordpre = qtyordpre;
    }

    public String getSerialmodel() {
        return serialmodel;
    }

    public void setSerialmodel(String serialmodel) {
        this.serialmodel = serialmodel == null ? null : serialmodel.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getAnswertext() {
        return answertext;
    }

    public void setAnswertext(String answertext) {
        this.answertext = answertext == null ? null : answertext.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getRegistdate() {
        return registdate;
    }

    public void setRegistdate(Date registdate) {
        this.registdate = registdate;
    }

    public Date getRevisedate() {
        return revisedate;
    }

    public void setRevisedate(Date revisedate) {
        this.revisedate = revisedate;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype == null ? null : classtype.trim();
    }

    public String getClasscode() {
        return classcode;
    }

    public void setClasscode(String classcode) {
        this.classcode = classcode == null ? null : classcode.trim();
    }

    public String getSubsidiarycode() {
        return subsidiarycode;
    }

    public void setSubsidiarycode(String subsidiarycode) {
        this.subsidiarycode = subsidiarycode == null ? null : subsidiarycode.trim();
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode == null ? null : companycode.trim();
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode == null ? null : branchcode.trim();
    }

    public Date getDlvdate() {
        return dlvdate;
    }

    public void setDlvdate(Date dlvdate) {
        this.dlvdate = dlvdate;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public Date getApplytime() {
        return applytime;
    }

    public void setApplytime(Date applytime) {
        this.applytime = applytime;
    }

    public Integer getOrderqty() {
        return orderqty;
    }

    public void setOrderqty(Integer orderqty) {
        this.orderqty = orderqty;
    }

    public Integer getCancelqty() {
        return cancelqty;
    }

    public void setCancelqty(Integer cancelqty) {
        this.cancelqty = cancelqty;
    }

    public String getModeltype() {
        return modeltype;
    }

    public void setModeltype(String modeltype) {
        this.modeltype = modeltype == null ? null : modeltype.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public String getApplypsnname() {
        return applypsnname;
    }

    public void setApplypsnname(String applypsnname) {
        this.applypsnname = applypsnname == null ? null : applypsnname.trim();
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public Integer getQtywarning() {
        return qtywarning;
    }

    public void setQtywarning(Integer qtywarning) {
        this.qtywarning = qtywarning;
    }

    public String getApplicantno() {
        return applicantno;
    }

    public void setApplicantno(String applicantno) {
        this.applicantno = applicantno == null ? null : applicantno.trim();
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

    public String getApproverno() {
        return approverno;
    }

    public void setApproverno(String approverno) {
        this.approverno = approverno == null ? null : approverno.trim();
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

    public Integer getAssedays() {
        return assedays;
    }

    public void setAssedays(Integer assedays) {
        this.assedays = assedays;
    }

    public Integer getQtynoord() {
        return qtynoord;
    }

    public void setQtynoord(Integer qtynoord) {
        this.qtynoord = qtynoord;
    }

    public BigDecimal getPriceLot() {
        return priceLot;
    }

    public void setPriceLot(BigDecimal priceLot) {
        this.priceLot = priceLot;
    }

    public Integer getLotqty() {
        return lotqty;
    }

    public void setLotqty(Integer lotqty) {
        this.lotqty = lotqty;
    }

    public String getIndcode() {
        return indcode;
    }

    public void setIndcode(String indcode) {
        this.indcode = indcode == null ? null : indcode.trim();
    }

    public Integer getPartpreparedays() {
        return partpreparedays;
    }

    public void setPartpreparedays(Integer partpreparedays) {
        this.partpreparedays = partpreparedays;
    }

    public String getApplyno() {
        return applyno;
    }

    public void setApplyno(String applyno) {
        this.applyno = applyno == null ? null : applyno.trim();
    }

    public String getRohs() {
        return rohs;
    }

    public void setRohs(String rohs) {
        this.rohs = rohs == null ? null : rohs.trim();
    }

    public Integer getQtypo() {
        return qtypo;
    }

    public void setQtypo(Integer qtypo) {
        this.qtypo = qtypo;
    }

    public Integer getQtyonhand() {
        return qtyonhand;
    }

    public void setQtyonhand(Integer qtyonhand) {
        this.qtyonhand = qtyonhand;
    }

    public Short getInspectstatus() {
        return inspectstatus;
    }

    public void setInspectstatus(Short inspectstatus) {
        this.inspectstatus = inspectstatus;
    }

    public Date getInspectsendtime() {
        return inspectsendtime;
    }

    public void setInspectsendtime(Date inspectsendtime) {
        this.inspectsendtime = inspectsendtime;
    }

    public Date getInspectanswertime() {
        return inspectanswertime;
    }

    public void setInspectanswertime(Date inspectanswertime) {
        this.inspectanswertime = inspectanswertime;
    }

    public String getInspectanswerpsnname() {
        return inspectanswerpsnname;
    }

    public void setInspectanswerpsnname(String inspectanswerpsnname) {
        this.inspectanswerpsnname = inspectanswerpsnname == null ? null : inspectanswerpsnname.trim();
    }

    public String getInspectanswerpsnno() {
        return inspectanswerpsnno;
    }

    public void setInspectanswerpsnno(String inspectanswerpsnno) {
        this.inspectanswerpsnno = inspectanswerpsnno == null ? null : inspectanswerpsnno.trim();
    }

    public Short getInspectdaily() {
        return inspectdaily;
    }

    public void setInspectdaily(Short inspectdaily) {
        this.inspectdaily = inspectdaily;
    }

    public Short getInspecttype() {
        return inspecttype;
    }

    public void setInspecttype(Short inspecttype) {
        this.inspecttype = inspecttype;
    }

    public String getInspectanswertext() {
        return inspectanswertext;
    }

    public void setInspectanswertext(String inspectanswertext) {
        this.inspectanswertext = inspectanswertext == null ? null : inspectanswertext.trim();
    }

    public Date getPlanusedate() {
        return planusedate;
    }

    public void setPlanusedate(Date planusedate) {
        this.planusedate = planusedate;
    }

    public String getPplno() {
        return pplno;
    }

    public void setPplno(String pplno) {
        this.pplno = pplno == null ? null : pplno.trim();
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno == null ? null : projectno.trim();
    }

    public Integer getAvgordqty() {
        return avgordqty;
    }

    public void setAvgordqty(Integer avgordqty) {
        this.avgordqty = avgordqty;
    }

    public Date getLastorddate() {
        return lastorddate;
    }

    public void setLastorddate(Date lastorddate) {
        this.lastorddate = lastorddate;
    }

    public Short getInspectapplytype() {
        return inspectapplytype;
    }

    public void setInspectapplytype(Short inspectapplytype) {
        this.inspectapplytype = inspectapplytype;
    }

    public String getInspectapproverno() {
        return inspectapproverno;
    }

    public void setInspectapproverno(String inspectapproverno) {
        this.inspectapproverno = inspectapproverno == null ? null : inspectapproverno.trim();
    }

    public String getInspectapprovername() {
        return inspectapprovername;
    }

    public void setInspectapprovername(String inspectapprovername) {
        this.inspectapprovername = inspectapprovername == null ? null : inspectapprovername.trim();
    }

    public Date getInspectapprovertime() {
        return inspectapprovertime;
    }

    public void setInspectapprovertime(Date inspectapprovertime) {
        this.inspectapprovertime = inspectapprovertime;
    }

    public Integer getModelcount() {
        return modelcount;
    }

    public void setModelcount(Integer modelcount) {
        this.modelcount = modelcount;
    }

    public Integer getInspectqty() {
        return inspectqty;
    }

    public void setInspectqty(Integer inspectqty) {
        this.inspectqty = inspectqty;
    }

    public String getInspectapplyno() {
        return inspectapplyno;
    }

    public void setInspectapplyno(String inspectapplyno) {
        this.inspectapplyno = inspectapplyno == null ? null : inspectapplyno.trim();
    }

    public String getIswarning() {
        return iswarning;
    }

    public void setIswarning(String iswarning) {
        this.iswarning = iswarning == null ? null : iswarning.trim();
    }

    public byte[] getWarehousecode() {
        return warehousecode;
    }

    public void setWarehousecode(byte[] warehousecode) {
        this.warehousecode = warehousecode;
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
        ShikomiTotal other = (ShikomiTotal) that;
        return (this.getShikomino() == null ? other.getShikomino() == null : this.getShikomino().equals(other.getShikomino()))
            && (this.getSuppliercode() == null ? other.getSuppliercode() == null : this.getSuppliercode().equals(other.getSuppliercode()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getLastqty() == null ? other.getLastqty() == null : this.getLastqty().equals(other.getLastqty()))
            && (this.getApplyqty() == null ? other.getApplyqty() == null : this.getApplyqty().equals(other.getApplyqty()))
            && (this.getRemainqty() == null ? other.getRemainqty() == null : this.getRemainqty().equals(other.getRemainqty()))
            && (this.getMaxqty() == null ? other.getMaxqty() == null : this.getMaxqty().equals(other.getMaxqty()))
            && (this.getQtyordpre() == null ? other.getQtyordpre() == null : this.getQtyordpre().equals(other.getQtyordpre()))
            && (this.getSerialmodel() == null ? other.getSerialmodel() == null : this.getSerialmodel().equals(other.getSerialmodel()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getAnswertext() == null ? other.getAnswertext() == null : this.getAnswertext().equals(other.getAnswertext()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getRegistdate() == null ? other.getRegistdate() == null : this.getRegistdate().equals(other.getRegistdate()))
            && (this.getRevisedate() == null ? other.getRevisedate() == null : this.getRevisedate().equals(other.getRevisedate()))
            && (this.getClasstype() == null ? other.getClasstype() == null : this.getClasstype().equals(other.getClasstype()))
            && (this.getClasscode() == null ? other.getClasscode() == null : this.getClasscode().equals(other.getClasscode()))
            && (this.getSubsidiarycode() == null ? other.getSubsidiarycode() == null : this.getSubsidiarycode().equals(other.getSubsidiarycode()))
            && (this.getCompanycode() == null ? other.getCompanycode() == null : this.getCompanycode().equals(other.getCompanycode()))
            && (this.getBranchcode() == null ? other.getBranchcode() == null : this.getBranchcode().equals(other.getBranchcode()))
            && (this.getDlvdate() == null ? other.getDlvdate() == null : this.getDlvdate().equals(other.getDlvdate()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getApplytime() == null ? other.getApplytime() == null : this.getApplytime().equals(other.getApplytime()))
            && (this.getOrderqty() == null ? other.getOrderqty() == null : this.getOrderqty().equals(other.getOrderqty()))
            && (this.getCancelqty() == null ? other.getCancelqty() == null : this.getCancelqty().equals(other.getCancelqty()))
            && (this.getModeltype() == null ? other.getModeltype() == null : this.getModeltype().equals(other.getModeltype()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()))
            && (this.getApplypsnname() == null ? other.getApplypsnname() == null : this.getApplypsnname().equals(other.getApplypsnname()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getQtywarning() == null ? other.getQtywarning() == null : this.getQtywarning().equals(other.getQtywarning()))
            && (this.getApplicantno() == null ? other.getApplicantno() == null : this.getApplicantno().equals(other.getApplicantno()))
            && (this.getApplicantname() == null ? other.getApplicantname() == null : this.getApplicantname().equals(other.getApplicantname()))
            && (this.getApplicantemail() == null ? other.getApplicantemail() == null : this.getApplicantemail().equals(other.getApplicantemail()))
            && (this.getApproverno() == null ? other.getApproverno() == null : this.getApproverno().equals(other.getApproverno()))
            && (this.getApprovername() == null ? other.getApprovername() == null : this.getApprovername().equals(other.getApprovername()))
            && (this.getApproveremail() == null ? other.getApproveremail() == null : this.getApproveremail().equals(other.getApproveremail()))
            && (this.getAssedays() == null ? other.getAssedays() == null : this.getAssedays().equals(other.getAssedays()))
            && (this.getQtynoord() == null ? other.getQtynoord() == null : this.getQtynoord().equals(other.getQtynoord()))
            && (this.getPriceLot() == null ? other.getPriceLot() == null : this.getPriceLot().equals(other.getPriceLot()))
            && (this.getLotqty() == null ? other.getLotqty() == null : this.getLotqty().equals(other.getLotqty()))
            && (this.getIndcode() == null ? other.getIndcode() == null : this.getIndcode().equals(other.getIndcode()))
            && (this.getPartpreparedays() == null ? other.getPartpreparedays() == null : this.getPartpreparedays().equals(other.getPartpreparedays()))
            && (this.getApplyno() == null ? other.getApplyno() == null : this.getApplyno().equals(other.getApplyno()))
            && (this.getRohs() == null ? other.getRohs() == null : this.getRohs().equals(other.getRohs()))
            && (this.getQtypo() == null ? other.getQtypo() == null : this.getQtypo().equals(other.getQtypo()))
            && (this.getQtyonhand() == null ? other.getQtyonhand() == null : this.getQtyonhand().equals(other.getQtyonhand()))
            && (this.getInspectstatus() == null ? other.getInspectstatus() == null : this.getInspectstatus().equals(other.getInspectstatus()))
            && (this.getInspectsendtime() == null ? other.getInspectsendtime() == null : this.getInspectsendtime().equals(other.getInspectsendtime()))
            && (this.getInspectanswertime() == null ? other.getInspectanswertime() == null : this.getInspectanswertime().equals(other.getInspectanswertime()))
            && (this.getInspectanswerpsnname() == null ? other.getInspectanswerpsnname() == null : this.getInspectanswerpsnname().equals(other.getInspectanswerpsnname()))
            && (this.getInspectanswerpsnno() == null ? other.getInspectanswerpsnno() == null : this.getInspectanswerpsnno().equals(other.getInspectanswerpsnno()))
            && (this.getInspectdaily() == null ? other.getInspectdaily() == null : this.getInspectdaily().equals(other.getInspectdaily()))
            && (this.getInspecttype() == null ? other.getInspecttype() == null : this.getInspecttype().equals(other.getInspecttype()))
            && (this.getInspectanswertext() == null ? other.getInspectanswertext() == null : this.getInspectanswertext().equals(other.getInspectanswertext()))
            && (this.getPlanusedate() == null ? other.getPlanusedate() == null : this.getPlanusedate().equals(other.getPlanusedate()))
            && (this.getPplno() == null ? other.getPplno() == null : this.getPplno().equals(other.getPplno()))
            && (this.getProjectno() == null ? other.getProjectno() == null : this.getProjectno().equals(other.getProjectno()))
            && (this.getAvgordqty() == null ? other.getAvgordqty() == null : this.getAvgordqty().equals(other.getAvgordqty()))
            && (this.getLastorddate() == null ? other.getLastorddate() == null : this.getLastorddate().equals(other.getLastorddate()))
            && (this.getInspectapplytype() == null ? other.getInspectapplytype() == null : this.getInspectapplytype().equals(other.getInspectapplytype()))
            && (this.getInspectapproverno() == null ? other.getInspectapproverno() == null : this.getInspectapproverno().equals(other.getInspectapproverno()))
            && (this.getInspectapprovername() == null ? other.getInspectapprovername() == null : this.getInspectapprovername().equals(other.getInspectapprovername()))
            && (this.getInspectapprovertime() == null ? other.getInspectapprovertime() == null : this.getInspectapprovertime().equals(other.getInspectapprovertime()))
            && (this.getModelcount() == null ? other.getModelcount() == null : this.getModelcount().equals(other.getModelcount()))
            && (this.getInspectqty() == null ? other.getInspectqty() == null : this.getInspectqty().equals(other.getInspectqty()))
            && (this.getInspectapplyno() == null ? other.getInspectapplyno() == null : this.getInspectapplyno().equals(other.getInspectapplyno()))
            && (this.getIswarning() == null ? other.getIswarning() == null : this.getIswarning().equals(other.getIswarning()))
            && (Arrays.equals(this.getWarehousecode(), other.getWarehousecode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShikomino() == null) ? 0 : getShikomino().hashCode());
        result = prime * result + ((getSuppliercode() == null) ? 0 : getSuppliercode().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getLastqty() == null) ? 0 : getLastqty().hashCode());
        result = prime * result + ((getApplyqty() == null) ? 0 : getApplyqty().hashCode());
        result = prime * result + ((getRemainqty() == null) ? 0 : getRemainqty().hashCode());
        result = prime * result + ((getMaxqty() == null) ? 0 : getMaxqty().hashCode());
        result = prime * result + ((getQtyordpre() == null) ? 0 : getQtyordpre().hashCode());
        result = prime * result + ((getSerialmodel() == null) ? 0 : getSerialmodel().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getAnswertext() == null) ? 0 : getAnswertext().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getRegistdate() == null) ? 0 : getRegistdate().hashCode());
        result = prime * result + ((getRevisedate() == null) ? 0 : getRevisedate().hashCode());
        result = prime * result + ((getClasstype() == null) ? 0 : getClasstype().hashCode());
        result = prime * result + ((getClasscode() == null) ? 0 : getClasscode().hashCode());
        result = prime * result + ((getSubsidiarycode() == null) ? 0 : getSubsidiarycode().hashCode());
        result = prime * result + ((getCompanycode() == null) ? 0 : getCompanycode().hashCode());
        result = prime * result + ((getBranchcode() == null) ? 0 : getBranchcode().hashCode());
        result = prime * result + ((getDlvdate() == null) ? 0 : getDlvdate().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getApplytime() == null) ? 0 : getApplytime().hashCode());
        result = prime * result + ((getOrderqty() == null) ? 0 : getOrderqty().hashCode());
        result = prime * result + ((getCancelqty() == null) ? 0 : getCancelqty().hashCode());
        result = prime * result + ((getModeltype() == null) ? 0 : getModeltype().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        result = prime * result + ((getApplypsnname() == null) ? 0 : getApplypsnname().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getQtywarning() == null) ? 0 : getQtywarning().hashCode());
        result = prime * result + ((getApplicantno() == null) ? 0 : getApplicantno().hashCode());
        result = prime * result + ((getApplicantname() == null) ? 0 : getApplicantname().hashCode());
        result = prime * result + ((getApplicantemail() == null) ? 0 : getApplicantemail().hashCode());
        result = prime * result + ((getApproverno() == null) ? 0 : getApproverno().hashCode());
        result = prime * result + ((getApprovername() == null) ? 0 : getApprovername().hashCode());
        result = prime * result + ((getApproveremail() == null) ? 0 : getApproveremail().hashCode());
        result = prime * result + ((getAssedays() == null) ? 0 : getAssedays().hashCode());
        result = prime * result + ((getQtynoord() == null) ? 0 : getQtynoord().hashCode());
        result = prime * result + ((getPriceLot() == null) ? 0 : getPriceLot().hashCode());
        result = prime * result + ((getLotqty() == null) ? 0 : getLotqty().hashCode());
        result = prime * result + ((getIndcode() == null) ? 0 : getIndcode().hashCode());
        result = prime * result + ((getPartpreparedays() == null) ? 0 : getPartpreparedays().hashCode());
        result = prime * result + ((getApplyno() == null) ? 0 : getApplyno().hashCode());
        result = prime * result + ((getRohs() == null) ? 0 : getRohs().hashCode());
        result = prime * result + ((getQtypo() == null) ? 0 : getQtypo().hashCode());
        result = prime * result + ((getQtyonhand() == null) ? 0 : getQtyonhand().hashCode());
        result = prime * result + ((getInspectstatus() == null) ? 0 : getInspectstatus().hashCode());
        result = prime * result + ((getInspectsendtime() == null) ? 0 : getInspectsendtime().hashCode());
        result = prime * result + ((getInspectanswertime() == null) ? 0 : getInspectanswertime().hashCode());
        result = prime * result + ((getInspectanswerpsnname() == null) ? 0 : getInspectanswerpsnname().hashCode());
        result = prime * result + ((getInspectanswerpsnno() == null) ? 0 : getInspectanswerpsnno().hashCode());
        result = prime * result + ((getInspectdaily() == null) ? 0 : getInspectdaily().hashCode());
        result = prime * result + ((getInspecttype() == null) ? 0 : getInspecttype().hashCode());
        result = prime * result + ((getInspectanswertext() == null) ? 0 : getInspectanswertext().hashCode());
        result = prime * result + ((getPlanusedate() == null) ? 0 : getPlanusedate().hashCode());
        result = prime * result + ((getPplno() == null) ? 0 : getPplno().hashCode());
        result = prime * result + ((getProjectno() == null) ? 0 : getProjectno().hashCode());
        result = prime * result + ((getAvgordqty() == null) ? 0 : getAvgordqty().hashCode());
        result = prime * result + ((getLastorddate() == null) ? 0 : getLastorddate().hashCode());
        result = prime * result + ((getInspectapplytype() == null) ? 0 : getInspectapplytype().hashCode());
        result = prime * result + ((getInspectapproverno() == null) ? 0 : getInspectapproverno().hashCode());
        result = prime * result + ((getInspectapprovername() == null) ? 0 : getInspectapprovername().hashCode());
        result = prime * result + ((getInspectapprovertime() == null) ? 0 : getInspectapprovertime().hashCode());
        result = prime * result + ((getModelcount() == null) ? 0 : getModelcount().hashCode());
        result = prime * result + ((getInspectqty() == null) ? 0 : getInspectqty().hashCode());
        result = prime * result + ((getInspectapplyno() == null) ? 0 : getInspectapplyno().hashCode());
        result = prime * result + ((getIswarning() == null) ? 0 : getIswarning().hashCode());
        result = prime * result + (Arrays.hashCode(getWarehousecode()));
        return result;
    }
}