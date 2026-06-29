package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ShikomiModel extends ShikomiModelKey implements Serializable {
    private Long id;

    private String serialmodel;

    private Date updatetime;

    private Date createtime;

    private String createuser;

    private String updateuser;

    private String modeltype;

    private String mainmodelflag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialmodel() {
        return serialmodel;
    }

    public void setSerialmodel(String serialmodel) {
        this.serialmodel = serialmodel == null ? null : serialmodel.trim();
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

    public String getModeltype() {
        return modeltype;
    }

    public void setModeltype(String modeltype) {
        this.modeltype = modeltype == null ? null : modeltype.trim();
    }

    public String getMainmodelflag() {
        return mainmodelflag;
    }

    public void setMainmodelflag(String mainmodelflag) {
        this.mainmodelflag = mainmodelflag == null ? null : mainmodelflag.trim();
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
        ShikomiModel other = (ShikomiModel) that;
        return (this.getShikomino() == null ? other.getShikomino() == null : this.getShikomino().equals(other.getShikomino()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSerialmodel() == null ? other.getSerialmodel() == null : this.getSerialmodel().equals(other.getSerialmodel()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()))
            && (this.getModeltype() == null ? other.getModeltype() == null : this.getModeltype().equals(other.getModeltype()))
            && (this.getMainmodelflag() == null ? other.getMainmodelflag() == null : this.getMainmodelflag().equals(other.getMainmodelflag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShikomino() == null) ? 0 : getShikomino().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSerialmodel() == null) ? 0 : getSerialmodel().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        result = prime * result + ((getModeltype() == null) ? 0 : getModeltype().hashCode());
        result = prime * result + ((getMainmodelflag() == null) ? 0 : getMainmodelflag().hashCode());
        return result;
    }
}