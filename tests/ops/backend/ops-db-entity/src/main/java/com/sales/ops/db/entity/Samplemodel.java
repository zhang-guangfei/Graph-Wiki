package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class Samplemodel implements Serializable {
    private String samplemodelno;

    private String modelno;

    private String deptjp;

    private Date upddate;

    private String modelseir;

    private String modeltype;

    private static final long serialVersionUID = 1L;

    public String getSamplemodelno() {
        return samplemodelno;
    }

    public void setSamplemodelno(String samplemodelno) {
        this.samplemodelno = samplemodelno == null ? null : samplemodelno.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getDeptjp() {
        return deptjp;
    }

    public void setDeptjp(String deptjp) {
        this.deptjp = deptjp == null ? null : deptjp.trim();
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public String getModelseir() {
        return modelseir;
    }

    public void setModelseir(String modelseir) {
        this.modelseir = modelseir == null ? null : modelseir.trim();
    }

    public String getModeltype() {
        return modeltype;
    }

    public void setModeltype(String modeltype) {
        this.modeltype = modeltype == null ? null : modeltype.trim();
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
        Samplemodel other = (Samplemodel) that;
        return (this.getSamplemodelno() == null ? other.getSamplemodelno() == null : this.getSamplemodelno().equals(other.getSamplemodelno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getDeptjp() == null ? other.getDeptjp() == null : this.getDeptjp().equals(other.getDeptjp()))
            && (this.getUpddate() == null ? other.getUpddate() == null : this.getUpddate().equals(other.getUpddate()))
            && (this.getModelseir() == null ? other.getModelseir() == null : this.getModelseir().equals(other.getModelseir()))
            && (this.getModeltype() == null ? other.getModeltype() == null : this.getModeltype().equals(other.getModeltype()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSamplemodelno() == null) ? 0 : getSamplemodelno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getDeptjp() == null) ? 0 : getDeptjp().hashCode());
        result = prime * result + ((getUpddate() == null) ? 0 : getUpddate().hashCode());
        result = prime * result + ((getModelseir() == null) ? 0 : getModelseir().hashCode());
        result = prime * result + ((getModeltype() == null) ? 0 : getModeltype().hashCode());
        return result;
    }
}