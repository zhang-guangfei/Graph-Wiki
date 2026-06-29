package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class TblWorkdayYear implements Serializable {
    private Date workdayDate;

    private Date optdate;

    private String username;

    private String optflag;

    private String optflagJp;

    private String optflagCn;

    private static final long serialVersionUID = 1L;

    public Date getWorkdayDate() {
        return workdayDate;
    }

    public void setWorkdayDate(Date workdayDate) {
        this.workdayDate = workdayDate;
    }

    public Date getOptdate() {
        return optdate;
    }

    public void setOptdate(Date optdate) {
        this.optdate = optdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getOptflag() {
        return optflag;
    }

    public void setOptflag(String optflag) {
        this.optflag = optflag == null ? null : optflag.trim();
    }

    public String getOptflagJp() {
        return optflagJp;
    }

    public void setOptflagJp(String optflagJp) {
        this.optflagJp = optflagJp == null ? null : optflagJp.trim();
    }

    public String getOptflagCn() {
        return optflagCn;
    }

    public void setOptflagCn(String optflagCn) {
        this.optflagCn = optflagCn == null ? null : optflagCn.trim();
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
        TblWorkdayYear other = (TblWorkdayYear) that;
        return (this.getWorkdayDate() == null ? other.getWorkdayDate() == null : this.getWorkdayDate().equals(other.getWorkdayDate()))
            && (this.getOptdate() == null ? other.getOptdate() == null : this.getOptdate().equals(other.getOptdate()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getOptflag() == null ? other.getOptflag() == null : this.getOptflag().equals(other.getOptflag()))
            && (this.getOptflagJp() == null ? other.getOptflagJp() == null : this.getOptflagJp().equals(other.getOptflagJp()))
            && (this.getOptflagCn() == null ? other.getOptflagCn() == null : this.getOptflagCn().equals(other.getOptflagCn()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWorkdayDate() == null) ? 0 : getWorkdayDate().hashCode());
        result = prime * result + ((getOptdate() == null) ? 0 : getOptdate().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getOptflag() == null) ? 0 : getOptflag().hashCode());
        result = prime * result + ((getOptflagJp() == null) ? 0 : getOptflagJp().hashCode());
        result = prime * result + ((getOptflagCn() == null) ? 0 : getOptflagCn().hashCode());
        return result;
    }
}