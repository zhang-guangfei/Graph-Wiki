package com.sales.ops.db.entity;

import java.io.Serializable;

public class OpsCoordinateinfo implements Serializable {
    private Long id;

    private String massage;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage == null ? null : massage.trim();
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
        OpsCoordinateinfo other = (OpsCoordinateinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMassage() == null ? other.getMassage() == null : this.getMassage().equals(other.getMassage()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMassage() == null) ? 0 : getMassage().hashCode());
        return result;
    }
}