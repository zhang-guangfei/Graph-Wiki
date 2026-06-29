package com.sales.ops.db.entity;

import java.io.Serializable;

public class PrepareOrderKey implements Serializable {
    private String rorderno;

    private String prepareNo;

    private Integer prepareType;

    private static final long serialVersionUID = 1L;

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno == null ? null : rorderno.trim();
    }

    public String getPrepareNo() {
        return prepareNo;
    }

    public void setPrepareNo(String prepareNo) {
        this.prepareNo = prepareNo == null ? null : prepareNo.trim();
    }

    public Integer getPrepareType() {
        return prepareType;
    }

    public void setPrepareType(Integer prepareType) {
        this.prepareType = prepareType;
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
        PrepareOrderKey other = (PrepareOrderKey) that;
        return (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getPrepareNo() == null ? other.getPrepareNo() == null : this.getPrepareNo().equals(other.getPrepareNo()))
            && (this.getPrepareType() == null ? other.getPrepareType() == null : this.getPrepareType().equals(other.getPrepareType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getPrepareNo() == null) ? 0 : getPrepareNo().hashCode());
        result = prime * result + ((getPrepareType() == null) ? 0 : getPrepareType().hashCode());
        return result;
    }
}