package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TDocOrderTrackingOps implements Serializable {
    private String trackingseqno;

    private String organizationid;

    private String orderno;

    private String taskno;

    private String waybillno;

    private String expressno;

    private String showstatus;

    private Date trackingtime;

    private String milestone;

    private String showflag;

    private String branchid;

    private String carrierid;

    private String carriername;

    private String vehicleid;

    private String driverid;

    private String drivername;

    private String trackingcountry;

    private String trackingprovince;

    private String trackingcity;

    private String trackingdistrict;

    private String trackingstreet;

    private String positionid;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String udf01;

    private String udf02;

    private String udf03;

    private String udf04;

    private String udf05;

    private Integer currentversion;

    private String oprseqflag;

    private String addwho;

    private Date addtime;

    private String editwho;

    private Date edittime;

    private String activeflag;

    private String omsno;

    private String trackingstatus;

    private String ordertype;

    private String edisendflag1;

    private String edisendflag2;

    private String edisendflag3;

    private Date edisendtime1;

    private Date edisendtime2;

    private Date edisendtime3;

    private String drivertel1;

    private String wmsorderno;

    private String reference1;

    private static final long serialVersionUID = 1L;

    public String getTrackingseqno() {
        return trackingseqno;
    }

    public void setTrackingseqno(String trackingseqno) {
        this.trackingseqno = trackingseqno == null ? null : trackingseqno.trim();
    }

    public String getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(String organizationid) {
        this.organizationid = organizationid == null ? null : organizationid.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getTaskno() {
        return taskno;
    }

    public void setTaskno(String taskno) {
        this.taskno = taskno == null ? null : taskno.trim();
    }

    public String getWaybillno() {
        return waybillno;
    }

    public void setWaybillno(String waybillno) {
        this.waybillno = waybillno == null ? null : waybillno.trim();
    }

    public String getExpressno() {
        return expressno;
    }

    public void setExpressno(String expressno) {
        this.expressno = expressno == null ? null : expressno.trim();
    }

    public String getShowstatus() {
        return showstatus;
    }

    public void setShowstatus(String showstatus) {
        this.showstatus = showstatus == null ? null : showstatus.trim();
    }

    public Date getTrackingtime() {
        return trackingtime;
    }

    public void setTrackingtime(Date trackingtime) {
        this.trackingtime = trackingtime;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone == null ? null : milestone.trim();
    }

    public String getShowflag() {
        return showflag;
    }

    public void setShowflag(String showflag) {
        this.showflag = showflag == null ? null : showflag.trim();
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid == null ? null : branchid.trim();
    }

    public String getCarrierid() {
        return carrierid;
    }

    public void setCarrierid(String carrierid) {
        this.carrierid = carrierid == null ? null : carrierid.trim();
    }

    public String getCarriername() {
        return carriername;
    }

    public void setCarriername(String carriername) {
        this.carriername = carriername == null ? null : carriername.trim();
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid == null ? null : vehicleid.trim();
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid == null ? null : driverid.trim();
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername == null ? null : drivername.trim();
    }

    public String getTrackingcountry() {
        return trackingcountry;
    }

    public void setTrackingcountry(String trackingcountry) {
        this.trackingcountry = trackingcountry == null ? null : trackingcountry.trim();
    }

    public String getTrackingprovince() {
        return trackingprovince;
    }

    public void setTrackingprovince(String trackingprovince) {
        this.trackingprovince = trackingprovince == null ? null : trackingprovince.trim();
    }

    public String getTrackingcity() {
        return trackingcity;
    }

    public void setTrackingcity(String trackingcity) {
        this.trackingcity = trackingcity == null ? null : trackingcity.trim();
    }

    public String getTrackingdistrict() {
        return trackingdistrict;
    }

    public void setTrackingdistrict(String trackingdistrict) {
        this.trackingdistrict = trackingdistrict == null ? null : trackingdistrict.trim();
    }

    public String getTrackingstreet() {
        return trackingstreet;
    }

    public void setTrackingstreet(String trackingstreet) {
        this.trackingstreet = trackingstreet == null ? null : trackingstreet.trim();
    }

    public String getPositionid() {
        return positionid;
    }

    public void setPositionid(String positionid) {
        this.positionid = positionid == null ? null : positionid.trim();
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getUdf01() {
        return udf01;
    }

    public void setUdf01(String udf01) {
        this.udf01 = udf01 == null ? null : udf01.trim();
    }

    public String getUdf02() {
        return udf02;
    }

    public void setUdf02(String udf02) {
        this.udf02 = udf02 == null ? null : udf02.trim();
    }

    public String getUdf03() {
        return udf03;
    }

    public void setUdf03(String udf03) {
        this.udf03 = udf03 == null ? null : udf03.trim();
    }

    public String getUdf04() {
        return udf04;
    }

    public void setUdf04(String udf04) {
        this.udf04 = udf04 == null ? null : udf04.trim();
    }

    public String getUdf05() {
        return udf05;
    }

    public void setUdf05(String udf05) {
        this.udf05 = udf05 == null ? null : udf05.trim();
    }

    public Integer getCurrentversion() {
        return currentversion;
    }

    public void setCurrentversion(Integer currentversion) {
        this.currentversion = currentversion;
    }

    public String getOprseqflag() {
        return oprseqflag;
    }

    public void setOprseqflag(String oprseqflag) {
        this.oprseqflag = oprseqflag == null ? null : oprseqflag.trim();
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho == null ? null : addwho.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getEditwho() {
        return editwho;
    }

    public void setEditwho(String editwho) {
        this.editwho = editwho == null ? null : editwho.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getActiveflag() {
        return activeflag;
    }

    public void setActiveflag(String activeflag) {
        this.activeflag = activeflag == null ? null : activeflag.trim();
    }

    public String getOmsno() {
        return omsno;
    }

    public void setOmsno(String omsno) {
        this.omsno = omsno == null ? null : omsno.trim();
    }

    public String getTrackingstatus() {
        return trackingstatus;
    }

    public void setTrackingstatus(String trackingstatus) {
        this.trackingstatus = trackingstatus == null ? null : trackingstatus.trim();
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype == null ? null : ordertype.trim();
    }

    public String getEdisendflag1() {
        return edisendflag1;
    }

    public void setEdisendflag1(String edisendflag1) {
        this.edisendflag1 = edisendflag1 == null ? null : edisendflag1.trim();
    }

    public String getEdisendflag2() {
        return edisendflag2;
    }

    public void setEdisendflag2(String edisendflag2) {
        this.edisendflag2 = edisendflag2 == null ? null : edisendflag2.trim();
    }

    public String getEdisendflag3() {
        return edisendflag3;
    }

    public void setEdisendflag3(String edisendflag3) {
        this.edisendflag3 = edisendflag3 == null ? null : edisendflag3.trim();
    }

    public Date getEdisendtime1() {
        return edisendtime1;
    }

    public void setEdisendtime1(Date edisendtime1) {
        this.edisendtime1 = edisendtime1;
    }

    public Date getEdisendtime2() {
        return edisendtime2;
    }

    public void setEdisendtime2(Date edisendtime2) {
        this.edisendtime2 = edisendtime2;
    }

    public Date getEdisendtime3() {
        return edisendtime3;
    }

    public void setEdisendtime3(Date edisendtime3) {
        this.edisendtime3 = edisendtime3;
    }

    public String getDrivertel1() {
        return drivertel1;
    }

    public void setDrivertel1(String drivertel1) {
        this.drivertel1 = drivertel1 == null ? null : drivertel1.trim();
    }

    public String getWmsorderno() {
        return wmsorderno;
    }

    public void setWmsorderno(String wmsorderno) {
        this.wmsorderno = wmsorderno == null ? null : wmsorderno.trim();
    }

    public String getReference1() {
        return reference1;
    }

    public void setReference1(String reference1) {
        this.reference1 = reference1 == null ? null : reference1.trim();
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
        TDocOrderTrackingOps other = (TDocOrderTrackingOps) that;
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
            && (this.getReference1() == null ? other.getReference1() == null : this.getReference1().equals(other.getReference1()));
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
        return result;
    }
}