package com.sales.ops.db.entity;

import java.io.Serializable;

public class OpsModelNotcal3sbom implements Serializable {
    private String matchstring;

    private String seriesbig;

    private String seriessmall;

    private String descInfo;

    private static final long serialVersionUID = 1L;

    public String getMatchstring() {
        return matchstring;
    }

    public void setMatchstring(String matchstring) {
        this.matchstring = matchstring == null ? null : matchstring.trim();
    }

    public String getSeriesbig() {
        return seriesbig;
    }

    public void setSeriesbig(String seriesbig) {
        this.seriesbig = seriesbig == null ? null : seriesbig.trim();
    }

    public String getSeriessmall() {
        return seriessmall;
    }

    public void setSeriessmall(String seriessmall) {
        this.seriessmall = seriessmall == null ? null : seriessmall.trim();
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo == null ? null : descInfo.trim();
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
        OpsModelNotcal3sbom other = (OpsModelNotcal3sbom) that;
        return (this.getMatchstring() == null ? other.getMatchstring() == null : this.getMatchstring().equals(other.getMatchstring()))
            && (this.getSeriesbig() == null ? other.getSeriesbig() == null : this.getSeriesbig().equals(other.getSeriesbig()))
            && (this.getSeriessmall() == null ? other.getSeriessmall() == null : this.getSeriessmall().equals(other.getSeriessmall()))
            && (this.getDescInfo() == null ? other.getDescInfo() == null : this.getDescInfo().equals(other.getDescInfo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMatchstring() == null) ? 0 : getMatchstring().hashCode());
        result = prime * result + ((getSeriesbig() == null) ? 0 : getSeriesbig().hashCode());
        result = prime * result + ((getSeriessmall() == null) ? 0 : getSeriessmall().hashCode());
        result = prime * result + ((getDescInfo() == null) ? 0 : getDescInfo().hashCode());
        return result;
    }
}