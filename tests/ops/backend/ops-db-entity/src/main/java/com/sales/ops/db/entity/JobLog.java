package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class JobLog implements Serializable {
    private String jobname;

    private Date lasttime;

    private String lastresult;

    private static final long serialVersionUID = 1L;

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname == null ? null : jobname.trim();
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getLastresult() {
        return lastresult;
    }

    public void setLastresult(String lastresult) {
        this.lastresult = lastresult == null ? null : lastresult.trim();
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
        JobLog other = (JobLog) that;
        return (this.getJobname() == null ? other.getJobname() == null : this.getJobname().equals(other.getJobname()))
            && (this.getLasttime() == null ? other.getLasttime() == null : this.getLasttime().equals(other.getLasttime()))
            && (this.getLastresult() == null ? other.getLastresult() == null : this.getLastresult().equals(other.getLastresult()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getJobname() == null) ? 0 : getJobname().hashCode());
        result = prime * result + ((getLasttime() == null) ? 0 : getLasttime().hashCode());
        result = prime * result + ((getLastresult() == null) ? 0 : getLastresult().hashCode());
        return result;
    }
}