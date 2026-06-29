package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ShikomiCustomerlist implements Serializable {
    private String shikomino;

    private String customerno;

    private Date updatetime;

    private String username;

    private static final long serialVersionUID = 1L;

    public String getShikomino() {
        return shikomino;
    }

    public void setShikomino(String shikomino) {
        this.shikomino = shikomino == null ? null : shikomino.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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
        ShikomiCustomerlist other = (ShikomiCustomerlist) that;
        return (this.getShikomino() == null ? other.getShikomino() == null : this.getShikomino().equals(other.getShikomino()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShikomino() == null) ? 0 : getShikomino().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        return result;
    }
}