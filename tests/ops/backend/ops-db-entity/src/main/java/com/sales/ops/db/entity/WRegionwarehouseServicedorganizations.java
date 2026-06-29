package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class WRegionwarehouseServicedorganizations implements Serializable {
    private String warehouseid;

    private String orgid;

    private Date updatetime;

    private String expinvSwitch;

    private static final long serialVersionUID = 1L;

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid == null ? null : warehouseid.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getExpinvSwitch() {
        return expinvSwitch;
    }

    public void setExpinvSwitch(String expinvSwitch) {
        this.expinvSwitch = expinvSwitch == null ? null : expinvSwitch.trim();
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
        WRegionwarehouseServicedorganizations other = (WRegionwarehouseServicedorganizations) that;
        return (this.getWarehouseid() == null ? other.getWarehouseid() == null : this.getWarehouseid().equals(other.getWarehouseid()))
            && (this.getOrgid() == null ? other.getOrgid() == null : this.getOrgid().equals(other.getOrgid()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getExpinvSwitch() == null ? other.getExpinvSwitch() == null : this.getExpinvSwitch().equals(other.getExpinvSwitch()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWarehouseid() == null) ? 0 : getWarehouseid().hashCode());
        result = prime * result + ((getOrgid() == null) ? 0 : getOrgid().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getExpinvSwitch() == null) ? 0 : getExpinvSwitch().hashCode());
        return result;
    }
}