package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class TblProductinterceptrule implements Serializable {
    private Integer id;

    private String name;

    private Integer status;

    private Integer applytype;

    private String modelno;

    private String matchmodelno;

    private Short modeltype;

    private String supplierid;

    private String origin;

    private String warehousecode;

    private String allowcustomernos;

    private Integer minqty;

    private Integer maxqty;

    private Integer actiontype;

    private String towarehousecode;

    private String tosupplierid;

    private String remark;

    private String updateuser;

    private String createuser;

    private Date createtime;

    private Date updatetime;

    private Short restartflag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApplytype() {
        return applytype;
    }

    public void setApplytype(Integer applytype) {
        this.applytype = applytype;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getMatchmodelno() {
        return matchmodelno;
    }

    public void setMatchmodelno(String matchmodelno) {
        this.matchmodelno = matchmodelno == null ? null : matchmodelno.trim();
    }

    public Short getModeltype() {
        return modeltype;
    }

    public void setModeltype(Short modeltype) {
        this.modeltype = modeltype;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getWarehousecode() {
        return warehousecode;
    }

    public void setWarehousecode(String warehousecode) {
        this.warehousecode = warehousecode == null ? null : warehousecode.trim();
    }

    public String getAllowcustomernos() {
        return allowcustomernos;
    }

    public void setAllowcustomernos(String allowcustomernos) {
        this.allowcustomernos = allowcustomernos == null ? null : allowcustomernos.trim();
    }

    public Integer getMinqty() {
        return minqty;
    }

    public void setMinqty(Integer minqty) {
        this.minqty = minqty;
    }

    public Integer getMaxqty() {
        return maxqty;
    }

    public void setMaxqty(Integer maxqty) {
        this.maxqty = maxqty;
    }

    public Integer getActiontype() {
        return actiontype;
    }

    public void setActiontype(Integer actiontype) {
        this.actiontype = actiontype;
    }

    public String getTowarehousecode() {
        return towarehousecode;
    }

    public void setTowarehousecode(String towarehousecode) {
        this.towarehousecode = towarehousecode == null ? null : towarehousecode.trim();
    }

    public String getTosupplierid() {
        return tosupplierid;
    }

    public void setTosupplierid(String tosupplierid) {
        this.tosupplierid = tosupplierid == null ? null : tosupplierid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
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

    public Short getRestartflag() {
        return restartflag;
    }

    public void setRestartflag(Short restartflag) {
        this.restartflag = restartflag;
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
        TblProductinterceptrule other = (TblProductinterceptrule) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getApplytype() == null ? other.getApplytype() == null : this.getApplytype().equals(other.getApplytype()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getMatchmodelno() == null ? other.getMatchmodelno() == null : this.getMatchmodelno().equals(other.getMatchmodelno()))
            && (this.getModeltype() == null ? other.getModeltype() == null : this.getModeltype().equals(other.getModeltype()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getOrigin() == null ? other.getOrigin() == null : this.getOrigin().equals(other.getOrigin()))
            && (this.getWarehousecode() == null ? other.getWarehousecode() == null : this.getWarehousecode().equals(other.getWarehousecode()))
            && (this.getAllowcustomernos() == null ? other.getAllowcustomernos() == null : this.getAllowcustomernos().equals(other.getAllowcustomernos()))
            && (this.getMinqty() == null ? other.getMinqty() == null : this.getMinqty().equals(other.getMinqty()))
            && (this.getMaxqty() == null ? other.getMaxqty() == null : this.getMaxqty().equals(other.getMaxqty()))
            && (this.getActiontype() == null ? other.getActiontype() == null : this.getActiontype().equals(other.getActiontype()))
            && (this.getTowarehousecode() == null ? other.getTowarehousecode() == null : this.getTowarehousecode().equals(other.getTowarehousecode()))
            && (this.getTosupplierid() == null ? other.getTosupplierid() == null : this.getTosupplierid().equals(other.getTosupplierid()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getRestartflag() == null ? other.getRestartflag() == null : this.getRestartflag().equals(other.getRestartflag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getApplytype() == null) ? 0 : getApplytype().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getMatchmodelno() == null) ? 0 : getMatchmodelno().hashCode());
        result = prime * result + ((getModeltype() == null) ? 0 : getModeltype().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getOrigin() == null) ? 0 : getOrigin().hashCode());
        result = prime * result + ((getWarehousecode() == null) ? 0 : getWarehousecode().hashCode());
        result = prime * result + ((getAllowcustomernos() == null) ? 0 : getAllowcustomernos().hashCode());
        result = prime * result + ((getMinqty() == null) ? 0 : getMinqty().hashCode());
        result = prime * result + ((getMaxqty() == null) ? 0 : getMaxqty().hashCode());
        result = prime * result + ((getActiontype() == null) ? 0 : getActiontype().hashCode());
        result = prime * result + ((getTowarehousecode() == null) ? 0 : getTowarehousecode().hashCode());
        result = prime * result + ((getTosupplierid() == null) ? 0 : getTosupplierid().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getRestartflag() == null) ? 0 : getRestartflag().hashCode());
        return result;
    }
}