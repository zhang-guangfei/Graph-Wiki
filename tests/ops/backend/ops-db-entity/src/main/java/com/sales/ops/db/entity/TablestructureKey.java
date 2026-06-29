package com.sales.ops.db.entity;

import java.io.Serializable;

public class TablestructureKey implements Serializable {
    private String dbname;

    private String tblname;

    private String colname;

    private static final long serialVersionUID = 1L;

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname == null ? null : dbname.trim();
    }

    public String getTblname() {
        return tblname;
    }

    public void setTblname(String tblname) {
        this.tblname = tblname == null ? null : tblname.trim();
    }

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname == null ? null : colname.trim();
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
        TablestructureKey other = (TablestructureKey) that;
        return (this.getDbname() == null ? other.getDbname() == null : this.getDbname().equals(other.getDbname()))
            && (this.getTblname() == null ? other.getTblname() == null : this.getTblname().equals(other.getTblname()))
            && (this.getColname() == null ? other.getColname() == null : this.getColname().equals(other.getColname()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDbname() == null) ? 0 : getDbname().hashCode());
        result = prime * result + ((getTblname() == null) ? 0 : getTblname().hashCode());
        result = prime * result + ((getColname() == null) ? 0 : getColname().hashCode());
        return result;
    }
}