package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductSupplierConfig extends ProductSupplierConfigKey implements Serializable {
    private Integer maxprodqty;

    private Boolean enablemaxprodqty;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getMaxprodqty() {
        return maxprodqty;
    }

    public void setMaxprodqty(Integer maxprodqty) {
        this.maxprodqty = maxprodqty;
    }

    public Boolean getEnablemaxprodqty() {
        return enablemaxprodqty;
    }

    public void setEnablemaxprodqty(Boolean enablemaxprodqty) {
        this.enablemaxprodqty = enablemaxprodqty;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}