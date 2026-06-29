package com.sales.ops.db.entity;

import java.io.Serializable;

public class WGroupcustomers implements Serializable {
    private String groupid;

    private String groupname;

    private String vipcode;

    private static final long serialVersionUID = 1L;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public String getVipcode() {
        return vipcode;
    }

    public void setVipcode(String vipcode) {
        this.vipcode = vipcode == null ? null : vipcode.trim();
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
        WGroupcustomers other = (WGroupcustomers) that;
        return (this.getGroupid() == null ? other.getGroupid() == null : this.getGroupid().equals(other.getGroupid()))
            && (this.getGroupname() == null ? other.getGroupname() == null : this.getGroupname().equals(other.getGroupname()))
            && (this.getVipcode() == null ? other.getVipcode() == null : this.getVipcode().equals(other.getVipcode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGroupid() == null) ? 0 : getGroupid().hashCode());
        result = prime * result + ((getGroupname() == null) ? 0 : getGroupname().hashCode());
        result = prime * result + ((getVipcode() == null) ? 0 : getVipcode().hashCode());
        return result;
    }
}