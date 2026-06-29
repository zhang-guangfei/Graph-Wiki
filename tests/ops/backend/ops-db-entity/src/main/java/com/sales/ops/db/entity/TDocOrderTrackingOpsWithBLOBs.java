package com.sales.ops.db.entity;

import java.io.Serializable;

public class TDocOrderTrackingOpsWithBLOBs extends TDocOrderTrackingOps implements Serializable {
    private String trackingdescr;

    private String notetext;

    private static final long serialVersionUID = 1L;

    public String getTrackingdescr() {
        return trackingdescr;
    }

    public void setTrackingdescr(String trackingdescr) {
        this.trackingdescr = trackingdescr == null ? null : trackingdescr.trim();
    }

    public String getNotetext() {
        return notetext;
    }

    public void setNotetext(String notetext) {
        this.notetext = notetext == null ? null : notetext.trim();
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
        TDocOrderTrackingOpsWithBLOBs other = (TDocOrderTrackingOpsWithBLOBs) that;
        return (this.getTrackingseqno() == null ? other.getTrackingseqno() == null : this.getTrackingseqno().equals(other.getTrackingseqno()))
            && (this.getOrganizationid() == null ? other.getOrganizationid() == null : this.getOrganizationid().equals(other.getOrganizationid()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getTaskno() == null ? other.getTaskno() == null : this.getTaskno().equals(other.getTaskno()))
            && (this.getWaybillno() == null ? other.getWaybillno() == null : this.getWaybillno().equals(other.getWaybillno()))
            && (this.getExpressno() == null ? other.getExpressno() == null : this.getExpressno().equals(other.getExpressno()))
            && (this.getShowstatus() == null ? other.getShowstatus() == null : this.getShowstatus().equals(other.getShowstatus()))
            && (this.getTrackingtime() == null ? other.getTrackingtime() == null : this.getTrackingtime().equals(other.getTrackingtime()))
            && (this.getMilestone() == null ? other.getMilestone() == null : this.getMilestone().equals(other.getMilestone()))
            && (this.getShowflag() == null ? other.getShowflag() == null : this.getShowflag().equals(other.getShowflag()))
            && (this.getBranchid() == null ? other.getBranchid() == null : this.getBranchid().equals(other.getBranchid()))
            && (this.getCarrierid() == null ? other.getCarrierid() == null : this.getCarrierid().equals(other.getCarrierid()))
            && (this.getCarriername() == null ? other.getCarriername() == null : this.getCarriername().equals(other.getCarriername()))
            && (this.getVehicleid() == null ? other.getVehicleid() == null : this.getVehicleid().equals(other.getVehicleid()))
            && (this.getDriverid() == null ? other.getDriverid() == null : this.getDriverid().equals(other.getDriverid()))
            && (this.getDrivername() == null ? other.getDrivername() == null : this.getDrivername().equals(other.getDrivername()))
            && (this.getTrackingcountry() == null ? other.getTrackingcountry() == null : this.getTrackingcountry().equals(other.getTrackingcountry()))
            && (this.getTrackingprovince() == null ? other.getTrackingprovince() == null : this.getTrackingprovince().equals(other.getTrackingprovince()))
            && (this.getTrackingcity() == null ? other.getTrackingcity() == null : this.getTrackingcity().equals(other.getTrackingcity()))
            && (this.getTrackingdistrict() == null ? other.getTrackingdistrict() == null : this.getTrackingdistrict().equals(other.getTrackingdistrict()))
            && (this.getTrackingstreet() == null ? other.getTrackingstreet() == null : this.getTrackingstreet().equals(other.getTrackingstreet()))
            && (this.getPositionid() == null ? other.getPositionid() == null : this.getPositionid().equals(other.getPositionid()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getUdf01() == null ? other.getUdf01() == null : this.getUdf01().equals(other.getUdf01()))
            && (this.getUdf02() == null ? other.getUdf02() == null : this.getUdf02().equals(other.getUdf02()))
            && (this.getUdf03() == null ? other.getUdf03() == null : this.getUdf03().equals(other.getUdf03()))
            && (this.getUdf04() == null ? other.getUdf04() == null : this.getUdf04().equals(other.getUdf04()))
            && (this.getUdf05() == null ? other.getUdf05() == null : this.getUdf05().equals(other.getUdf05()))
            && (this.getCurrentversion() == null ? other.getCurrentversion() == null : this.getCurrentversion().equals(other.getCurrentversion()))
            && (this.getOprseqflag() == null ? other.getOprseqflag() == null : this.getOprseqflag().equals(other.getOprseqflag()))
            && (this.getAddwho() == null ? other.getAddwho() == null : this.getAddwho().equals(other.getAddwho()))
            && (this.getAddtime() == null ? other.getAddtime() == null : this.getAddtime().equals(other.getAddtime()))
            && (this.getEditwho() == null ? other.getEditwho() == null : this.getEditwho().equals(other.getEditwho()))
            && (this.getEdittime() == null ? other.getEdittime() == null : this.getEdittime().equals(other.getEdittime()))
            && (this.getActiveflag() == null ? other.getActiveflag() == null : this.getActiveflag().equals(other.getActiveflag()))
            && (this.getOmsno() == null ? other.getOmsno() == null : this.getOmsno().equals(other.getOmsno()))
            && (this.getTrackingstatus() == null ? other.getTrackingstatus() == null : this.getTrackingstatus().equals(other.getTrackingstatus()))
            && (this.getOrdertype() == null ? other.getOrdertype() == null : this.getOrdertype().equals(other.getOrdertype()))
            && (this.getEdisendflag1() == null ? other.getEdisendflag1() == null : this.getEdisendflag1().equals(other.getEdisendflag1()))
            && (this.getEdisendflag2() == null ? other.getEdisendflag2() == null : this.getEdisendflag2().equals(other.getEdisendflag2()))
            && (this.getEdisendflag3() == null ? other.getEdisendflag3() == null : this.getEdisendflag3().equals(other.getEdisendflag3()))
            && (this.getEdisendtime1() == null ? other.getEdisendtime1() == null : this.getEdisendtime1().equals(other.getEdisendtime1()))
            && (this.getEdisendtime2() == null ? other.getEdisendtime2() == null : this.getEdisendtime2().equals(other.getEdisendtime2()))
            && (this.getEdisendtime3() == null ? other.getEdisendtime3() == null : this.getEdisendtime3().equals(other.getEdisendtime3()))
            && (this.getDrivertel1() == null ? other.getDrivertel1() == null : this.getDrivertel1().equals(other.getDrivertel1()))
            && (this.getWmsorderno() == null ? other.getWmsorderno() == null : this.getWmsorderno().equals(other.getWmsorderno()))
            && (this.getReference1() == null ? other.getReference1() == null : this.getReference1().equals(other.getReference1()))
            && (this.getTrackingdescr() == null ? other.getTrackingdescr() == null : this.getTrackingdescr().equals(other.getTrackingdescr()))
            && (this.getNotetext() == null ? other.getNotetext() == null : this.getNotetext().equals(other.getNotetext()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTrackingseqno() == null) ? 0 : getTrackingseqno().hashCode());
        result = prime * result + ((getOrganizationid() == null) ? 0 : getOrganizationid().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getTaskno() == null) ? 0 : getTaskno().hashCode());
        result = prime * result + ((getWaybillno() == null) ? 0 : getWaybillno().hashCode());
        result = prime * result + ((getExpressno() == null) ? 0 : getExpressno().hashCode());
        result = prime * result + ((getShowstatus() == null) ? 0 : getShowstatus().hashCode());
        result = prime * result + ((getTrackingtime() == null) ? 0 : getTrackingtime().hashCode());
        result = prime * result + ((getMilestone() == null) ? 0 : getMilestone().hashCode());
        result = prime * result + ((getShowflag() == null) ? 0 : getShowflag().hashCode());
        result = prime * result + ((getBranchid() == null) ? 0 : getBranchid().hashCode());
        result = prime * result + ((getCarrierid() == null) ? 0 : getCarrierid().hashCode());
        result = prime * result + ((getCarriername() == null) ? 0 : getCarriername().hashCode());
        result = prime * result + ((getVehicleid() == null) ? 0 : getVehicleid().hashCode());
        result = prime * result + ((getDriverid() == null) ? 0 : getDriverid().hashCode());
        result = prime * result + ((getDrivername() == null) ? 0 : getDrivername().hashCode());
        result = prime * result + ((getTrackingcountry() == null) ? 0 : getTrackingcountry().hashCode());
        result = prime * result + ((getTrackingprovince() == null) ? 0 : getTrackingprovince().hashCode());
        result = prime * result + ((getTrackingcity() == null) ? 0 : getTrackingcity().hashCode());
        result = prime * result + ((getTrackingdistrict() == null) ? 0 : getTrackingdistrict().hashCode());
        result = prime * result + ((getTrackingstreet() == null) ? 0 : getTrackingstreet().hashCode());
        result = prime * result + ((getPositionid() == null) ? 0 : getPositionid().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getUdf01() == null) ? 0 : getUdf01().hashCode());
        result = prime * result + ((getUdf02() == null) ? 0 : getUdf02().hashCode());
        result = prime * result + ((getUdf03() == null) ? 0 : getUdf03().hashCode());
        result = prime * result + ((getUdf04() == null) ? 0 : getUdf04().hashCode());
        result = prime * result + ((getUdf05() == null) ? 0 : getUdf05().hashCode());
        result = prime * result + ((getCurrentversion() == null) ? 0 : getCurrentversion().hashCode());
        result = prime * result + ((getOprseqflag() == null) ? 0 : getOprseqflag().hashCode());
        result = prime * result + ((getAddwho() == null) ? 0 : getAddwho().hashCode());
        result = prime * result + ((getAddtime() == null) ? 0 : getAddtime().hashCode());
        result = prime * result + ((getEditwho() == null) ? 0 : getEditwho().hashCode());
        result = prime * result + ((getEdittime() == null) ? 0 : getEdittime().hashCode());
        result = prime * result + ((getActiveflag() == null) ? 0 : getActiveflag().hashCode());
        result = prime * result + ((getOmsno() == null) ? 0 : getOmsno().hashCode());
        result = prime * result + ((getTrackingstatus() == null) ? 0 : getTrackingstatus().hashCode());
        result = prime * result + ((getOrdertype() == null) ? 0 : getOrdertype().hashCode());
        result = prime * result + ((getEdisendflag1() == null) ? 0 : getEdisendflag1().hashCode());
        result = prime * result + ((getEdisendflag2() == null) ? 0 : getEdisendflag2().hashCode());
        result = prime * result + ((getEdisendflag3() == null) ? 0 : getEdisendflag3().hashCode());
        result = prime * result + ((getEdisendtime1() == null) ? 0 : getEdisendtime1().hashCode());
        result = prime * result + ((getEdisendtime2() == null) ? 0 : getEdisendtime2().hashCode());
        result = prime * result + ((getEdisendtime3() == null) ? 0 : getEdisendtime3().hashCode());
        result = prime * result + ((getDrivertel1() == null) ? 0 : getDrivertel1().hashCode());
        result = prime * result + ((getWmsorderno() == null) ? 0 : getWmsorderno().hashCode());
        result = prime * result + ((getReference1() == null) ? 0 : getReference1().hashCode());
        result = prime * result + ((getTrackingdescr() == null) ? 0 : getTrackingdescr().hashCode());
        result = prime * result + ((getNotetext() == null) ? 0 : getNotetext().hashCode());
        return result;
    }
}