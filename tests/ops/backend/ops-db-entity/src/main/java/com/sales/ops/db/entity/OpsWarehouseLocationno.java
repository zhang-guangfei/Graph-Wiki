package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsWarehouseLocationno implements Serializable {
    private Long id;

    private String modelno;

    private String warehouseid;

    private String locationno;

    private Date updatetime;

    private Date inserttime;

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

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid == null ? null : warehouseid.trim();
    }

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno == null ? null : locationno.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
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
        OpsWarehouseLocationno other = (OpsWarehouseLocationno) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getWarehouseid() == null ? other.getWarehouseid() == null : this.getWarehouseid().equals(other.getWarehouseid()))
            && (this.getLocationno() == null ? other.getLocationno() == null : this.getLocationno().equals(other.getLocationno()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getWarehouseid() == null) ? 0 : getWarehouseid().hashCode());
        result = prime * result + ((getLocationno() == null) ? 0 : getLocationno().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        return result;
    }
}