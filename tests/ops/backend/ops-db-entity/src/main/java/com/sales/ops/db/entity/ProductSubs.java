package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductSubs implements Serializable {
    private String modelno;

    private String substitute;

    private String remark;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getSubstitute() {
        return substitute;
    }

    public void setSubstitute(String substitute) {
        this.substitute = substitute == null ? null : substitute.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}