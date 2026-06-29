package com.sales.ops.db.entity;

import java.io.Serializable;

public class YyLongmodelexchange implements Serializable {
    private Integer id;

    private String yymodel;

    private String modelno;

    private String model;

    private String modelcopy;

    private String otherrules;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYymodel() {
        return yymodel;
    }

    public void setYymodel(String yymodel) {
        this.yymodel = yymodel == null ? null : yymodel.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getModelcopy() {
        return modelcopy;
    }

    public void setModelcopy(String modelcopy) {
        this.modelcopy = modelcopy == null ? null : modelcopy.trim();
    }

    public String getOtherrules() {
        return otherrules;
    }

    public void setOtherrules(String otherrules) {
        this.otherrules = otherrules == null ? null : otherrules.trim();
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
        YyLongmodelexchange other = (YyLongmodelexchange) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getYymodel() == null ? other.getYymodel() == null : this.getYymodel().equals(other.getYymodel()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getModelcopy() == null ? other.getModelcopy() == null : this.getModelcopy().equals(other.getModelcopy()))
            && (this.getOtherrules() == null ? other.getOtherrules() == null : this.getOtherrules().equals(other.getOtherrules()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getYymodel() == null) ? 0 : getYymodel().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getModelcopy() == null) ? 0 : getModelcopy().hashCode());
        result = prime * result + ((getOtherrules() == null) ? 0 : getOtherrules().hashCode());
        return result;
    }
}