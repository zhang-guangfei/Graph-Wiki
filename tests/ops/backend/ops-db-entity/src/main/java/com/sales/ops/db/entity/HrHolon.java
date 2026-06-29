package com.sales.ops.db.entity;

import java.io.Serializable;

public class HrHolon implements Serializable {
    private String company;

    private String companycode;

    private String benbu;

    private String deptErjibu;

    private String deptErjibucode;

    private String fnumber;

    private String daqu;

    private String fdeptname;

    private String flongnumber;

    private String ffullname;

    private String parentfnumber;

    private String parentfname;

    private String deptcode;

    private Long unitlevel;

    private String unitlevelname;

    private String psnsmcid;

    private String psnname;

    private String fuzerenposition;

    private String positionname;

    private String issaleholon;

    private String saledeptfnumber;

    private String saledeptfname;

    private static final long serialVersionUID = 1L;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode == null ? null : companycode.trim();
    }

    public String getBenbu() {
        return benbu;
    }

    public void setBenbu(String benbu) {
        this.benbu = benbu == null ? null : benbu.trim();
    }

    public String getDeptErjibu() {
        return deptErjibu;
    }

    public void setDeptErjibu(String deptErjibu) {
        this.deptErjibu = deptErjibu == null ? null : deptErjibu.trim();
    }

    public String getDeptErjibucode() {
        return deptErjibucode;
    }

    public void setDeptErjibucode(String deptErjibucode) {
        this.deptErjibucode = deptErjibucode == null ? null : deptErjibucode.trim();
    }

    public String getFnumber() {
        return fnumber;
    }

    public void setFnumber(String fnumber) {
        this.fnumber = fnumber == null ? null : fnumber.trim();
    }

    public String getDaqu() {
        return daqu;
    }

    public void setDaqu(String daqu) {
        this.daqu = daqu == null ? null : daqu.trim();
    }

    public String getFdeptname() {
        return fdeptname;
    }

    public void setFdeptname(String fdeptname) {
        this.fdeptname = fdeptname == null ? null : fdeptname.trim();
    }

    public String getFlongnumber() {
        return flongnumber;
    }

    public void setFlongnumber(String flongnumber) {
        this.flongnumber = flongnumber == null ? null : flongnumber.trim();
    }

    public String getFfullname() {
        return ffullname;
    }

    public void setFfullname(String ffullname) {
        this.ffullname = ffullname == null ? null : ffullname.trim();
    }

    public String getParentfnumber() {
        return parentfnumber;
    }

    public void setParentfnumber(String parentfnumber) {
        this.parentfnumber = parentfnumber == null ? null : parentfnumber.trim();
    }

    public String getParentfname() {
        return parentfname;
    }

    public void setParentfname(String parentfname) {
        this.parentfname = parentfname == null ? null : parentfname.trim();
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode == null ? null : deptcode.trim();
    }

    public Long getUnitlevel() {
        return unitlevel;
    }

    public void setUnitlevel(Long unitlevel) {
        this.unitlevel = unitlevel;
    }

    public String getUnitlevelname() {
        return unitlevelname;
    }

    public void setUnitlevelname(String unitlevelname) {
        this.unitlevelname = unitlevelname == null ? null : unitlevelname.trim();
    }

    public String getPsnsmcid() {
        return psnsmcid;
    }

    public void setPsnsmcid(String psnsmcid) {
        this.psnsmcid = psnsmcid == null ? null : psnsmcid.trim();
    }

    public String getPsnname() {
        return psnname;
    }

    public void setPsnname(String psnname) {
        this.psnname = psnname == null ? null : psnname.trim();
    }

    public String getFuzerenposition() {
        return fuzerenposition;
    }

    public void setFuzerenposition(String fuzerenposition) {
        this.fuzerenposition = fuzerenposition == null ? null : fuzerenposition.trim();
    }

    public String getPositionname() {
        return positionname;
    }

    public void setPositionname(String positionname) {
        this.positionname = positionname == null ? null : positionname.trim();
    }

    public String getIssaleholon() {
        return issaleholon;
    }

    public void setIssaleholon(String issaleholon) {
        this.issaleholon = issaleholon == null ? null : issaleholon.trim();
    }

    public String getSaledeptfnumber() {
        return saledeptfnumber;
    }

    public void setSaledeptfnumber(String saledeptfnumber) {
        this.saledeptfnumber = saledeptfnumber == null ? null : saledeptfnumber.trim();
    }

    public String getSaledeptfname() {
        return saledeptfname;
    }

    public void setSaledeptfname(String saledeptfname) {
        this.saledeptfname = saledeptfname == null ? null : saledeptfname.trim();
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
        HrHolon other = (HrHolon) that;
        return (this.getCompany() == null ? other.getCompany() == null : this.getCompany().equals(other.getCompany()))
            && (this.getCompanycode() == null ? other.getCompanycode() == null : this.getCompanycode().equals(other.getCompanycode()))
            && (this.getBenbu() == null ? other.getBenbu() == null : this.getBenbu().equals(other.getBenbu()))
            && (this.getDeptErjibu() == null ? other.getDeptErjibu() == null : this.getDeptErjibu().equals(other.getDeptErjibu()))
            && (this.getDeptErjibucode() == null ? other.getDeptErjibucode() == null : this.getDeptErjibucode().equals(other.getDeptErjibucode()))
            && (this.getFnumber() == null ? other.getFnumber() == null : this.getFnumber().equals(other.getFnumber()))
            && (this.getDaqu() == null ? other.getDaqu() == null : this.getDaqu().equals(other.getDaqu()))
            && (this.getFdeptname() == null ? other.getFdeptname() == null : this.getFdeptname().equals(other.getFdeptname()))
            && (this.getFlongnumber() == null ? other.getFlongnumber() == null : this.getFlongnumber().equals(other.getFlongnumber()))
            && (this.getFfullname() == null ? other.getFfullname() == null : this.getFfullname().equals(other.getFfullname()))
            && (this.getParentfnumber() == null ? other.getParentfnumber() == null : this.getParentfnumber().equals(other.getParentfnumber()))
            && (this.getParentfname() == null ? other.getParentfname() == null : this.getParentfname().equals(other.getParentfname()))
            && (this.getDeptcode() == null ? other.getDeptcode() == null : this.getDeptcode().equals(other.getDeptcode()))
            && (this.getUnitlevel() == null ? other.getUnitlevel() == null : this.getUnitlevel().equals(other.getUnitlevel()))
            && (this.getUnitlevelname() == null ? other.getUnitlevelname() == null : this.getUnitlevelname().equals(other.getUnitlevelname()))
            && (this.getPsnsmcid() == null ? other.getPsnsmcid() == null : this.getPsnsmcid().equals(other.getPsnsmcid()))
            && (this.getPsnname() == null ? other.getPsnname() == null : this.getPsnname().equals(other.getPsnname()))
            && (this.getFuzerenposition() == null ? other.getFuzerenposition() == null : this.getFuzerenposition().equals(other.getFuzerenposition()))
            && (this.getPositionname() == null ? other.getPositionname() == null : this.getPositionname().equals(other.getPositionname()))
            && (this.getIssaleholon() == null ? other.getIssaleholon() == null : this.getIssaleholon().equals(other.getIssaleholon()))
            && (this.getSaledeptfnumber() == null ? other.getSaledeptfnumber() == null : this.getSaledeptfnumber().equals(other.getSaledeptfnumber()))
            && (this.getSaledeptfname() == null ? other.getSaledeptfname() == null : this.getSaledeptfname().equals(other.getSaledeptfname()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCompany() == null) ? 0 : getCompany().hashCode());
        result = prime * result + ((getCompanycode() == null) ? 0 : getCompanycode().hashCode());
        result = prime * result + ((getBenbu() == null) ? 0 : getBenbu().hashCode());
        result = prime * result + ((getDeptErjibu() == null) ? 0 : getDeptErjibu().hashCode());
        result = prime * result + ((getDeptErjibucode() == null) ? 0 : getDeptErjibucode().hashCode());
        result = prime * result + ((getFnumber() == null) ? 0 : getFnumber().hashCode());
        result = prime * result + ((getDaqu() == null) ? 0 : getDaqu().hashCode());
        result = prime * result + ((getFdeptname() == null) ? 0 : getFdeptname().hashCode());
        result = prime * result + ((getFlongnumber() == null) ? 0 : getFlongnumber().hashCode());
        result = prime * result + ((getFfullname() == null) ? 0 : getFfullname().hashCode());
        result = prime * result + ((getParentfnumber() == null) ? 0 : getParentfnumber().hashCode());
        result = prime * result + ((getParentfname() == null) ? 0 : getParentfname().hashCode());
        result = prime * result + ((getDeptcode() == null) ? 0 : getDeptcode().hashCode());
        result = prime * result + ((getUnitlevel() == null) ? 0 : getUnitlevel().hashCode());
        result = prime * result + ((getUnitlevelname() == null) ? 0 : getUnitlevelname().hashCode());
        result = prime * result + ((getPsnsmcid() == null) ? 0 : getPsnsmcid().hashCode());
        result = prime * result + ((getPsnname() == null) ? 0 : getPsnname().hashCode());
        result = prime * result + ((getFuzerenposition() == null) ? 0 : getFuzerenposition().hashCode());
        result = prime * result + ((getPositionname() == null) ? 0 : getPositionname().hashCode());
        result = prime * result + ((getIssaleholon() == null) ? 0 : getIssaleholon().hashCode());
        result = prime * result + ((getSaledeptfnumber() == null) ? 0 : getSaledeptfnumber().hashCode());
        result = prime * result + ((getSaledeptfname() == null) ? 0 : getSaledeptfname().hashCode());
        return result;
    }
}