package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsDoBak implements Serializable {
    private Long id;

    private String doId;

    private String orderId;

    private String orderItem;

    private Integer num;

    private Integer assNum;

    private String doSource;

    private String doType;

    private String waitType;

    private String warehouseCode;

    private String gatherWarehouseCode;

    private String customerNo;

    private Integer doStatus;

    private String carried;

    private String expressCode;

    private String province;

    private String city;

    private String district;

    private String street;

    private String address;

    private String linkman;

    private String mobile;

    private String phone;

    private String remark;

    private Integer version;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Integer spceialFlag;

    private Date hopeDate;

    private String pakageType;

    private Integer extNum;

    private String userNo;

    private String postcode;

    private Integer orderCount;

    private String dlvEntire;

    private Integer isWms;

    private Date wlDate;

    private String corderNo;

    private Integer extRoId;

    private Integer spceialNum;

    private String doState;

    private String doStateDetail;

    private String dlvSite;

    private String deptNo;

    private Integer expDlvType;

    private String expLinkNo;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAssNum() {
        return assNum;
    }

    public void setAssNum(Integer assNum) {
        this.assNum = assNum;
    }

    public String getDoSource() {
        return doSource;
    }

    public void setDoSource(String doSource) {
        this.doSource = doSource == null ? null : doSource.trim();
    }

    public String getDoType() {
        return doType;
    }

    public void setDoType(String doType) {
        this.doType = doType == null ? null : doType.trim();
    }

    public String getWaitType() {
        return waitType;
    }

    public void setWaitType(String waitType) {
        this.waitType = waitType == null ? null : waitType.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode == null ? null : gatherWarehouseCode.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public Integer getDoStatus() {
        return doStatus;
    }

    public void setDoStatus(Integer doStatus) {
        this.doStatus = doStatus;
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried == null ? null : carried.trim();
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode == null ? null : expressCode.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Integer getSpceialFlag() {
        return spceialFlag;
    }

    public void setSpceialFlag(Integer spceialFlag) {
        this.spceialFlag = spceialFlag;
    }

    public Date getHopeDate() {
        return hopeDate;
    }

    public void setHopeDate(Date hopeDate) {
        this.hopeDate = hopeDate;
    }

    public String getPakageType() {
        return pakageType;
    }

    public void setPakageType(String pakageType) {
        this.pakageType = pakageType == null ? null : pakageType.trim();
    }

    public Integer getExtNum() {
        return extNum;
    }

    public void setExtNum(Integer extNum) {
        this.extNum = extNum;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire == null ? null : dlvEntire.trim();
    }

    public Integer getIsWms() {
        return isWms;
    }

    public void setIsWms(Integer isWms) {
        this.isWms = isWms;
    }

    public Date getWlDate() {
        return wlDate;
    }

    public void setWlDate(Date wlDate) {
        this.wlDate = wlDate;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo == null ? null : corderNo.trim();
    }

    public Integer getExtRoId() {
        return extRoId;
    }

    public void setExtRoId(Integer extRoId) {
        this.extRoId = extRoId;
    }

    public Integer getSpceialNum() {
        return spceialNum;
    }

    public void setSpceialNum(Integer spceialNum) {
        this.spceialNum = spceialNum;
    }

    public String getDoState() {
        return doState;
    }

    public void setDoState(String doState) {
        this.doState = doState == null ? null : doState.trim();
    }

    public String getDoStateDetail() {
        return doStateDetail;
    }

    public void setDoStateDetail(String doStateDetail) {
        this.doStateDetail = doStateDetail == null ? null : doStateDetail.trim();
    }

    public String getDlvSite() {
        return dlvSite;
    }

    public void setDlvSite(String dlvSite) {
        this.dlvSite = dlvSite == null ? null : dlvSite.trim();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public Integer getExpDlvType() {
        return expDlvType;
    }

    public void setExpDlvType(Integer expDlvType) {
        this.expDlvType = expDlvType;
    }

    public String getExpLinkNo() {
        return expLinkNo;
    }

    public void setExpLinkNo(String expLinkNo) {
        this.expLinkNo = expLinkNo == null ? null : expLinkNo.trim();
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
        OpsDoBak other = (OpsDoBak) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getAssNum() == null ? other.getAssNum() == null : this.getAssNum().equals(other.getAssNum()))
            && (this.getDoSource() == null ? other.getDoSource() == null : this.getDoSource().equals(other.getDoSource()))
            && (this.getDoType() == null ? other.getDoType() == null : this.getDoType().equals(other.getDoType()))
            && (this.getWaitType() == null ? other.getWaitType() == null : this.getWaitType().equals(other.getWaitType()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getGatherWarehouseCode() == null ? other.getGatherWarehouseCode() == null : this.getGatherWarehouseCode().equals(other.getGatherWarehouseCode()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getDoStatus() == null ? other.getDoStatus() == null : this.getDoStatus().equals(other.getDoStatus()))
            && (this.getCarried() == null ? other.getCarried() == null : this.getCarried().equals(other.getCarried()))
            && (this.getExpressCode() == null ? other.getExpressCode() == null : this.getExpressCode().equals(other.getExpressCode()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getDistrict() == null ? other.getDistrict() == null : this.getDistrict().equals(other.getDistrict()))
            && (this.getStreet() == null ? other.getStreet() == null : this.getStreet().equals(other.getStreet()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getLinkman() == null ? other.getLinkman() == null : this.getLinkman().equals(other.getLinkman()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getSpceialFlag() == null ? other.getSpceialFlag() == null : this.getSpceialFlag().equals(other.getSpceialFlag()))
            && (this.getHopeDate() == null ? other.getHopeDate() == null : this.getHopeDate().equals(other.getHopeDate()))
            && (this.getPakageType() == null ? other.getPakageType() == null : this.getPakageType().equals(other.getPakageType()))
            && (this.getExtNum() == null ? other.getExtNum() == null : this.getExtNum().equals(other.getExtNum()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getPostcode() == null ? other.getPostcode() == null : this.getPostcode().equals(other.getPostcode()))
            && (this.getOrderCount() == null ? other.getOrderCount() == null : this.getOrderCount().equals(other.getOrderCount()))
            && (this.getDlvEntire() == null ? other.getDlvEntire() == null : this.getDlvEntire().equals(other.getDlvEntire()))
            && (this.getIsWms() == null ? other.getIsWms() == null : this.getIsWms().equals(other.getIsWms()))
            && (this.getWlDate() == null ? other.getWlDate() == null : this.getWlDate().equals(other.getWlDate()))
            && (this.getCorderNo() == null ? other.getCorderNo() == null : this.getCorderNo().equals(other.getCorderNo()))
            && (this.getExtRoId() == null ? other.getExtRoId() == null : this.getExtRoId().equals(other.getExtRoId()))
            && (this.getSpceialNum() == null ? other.getSpceialNum() == null : this.getSpceialNum().equals(other.getSpceialNum()))
            && (this.getDoState() == null ? other.getDoState() == null : this.getDoState().equals(other.getDoState()))
            && (this.getDoStateDetail() == null ? other.getDoStateDetail() == null : this.getDoStateDetail().equals(other.getDoStateDetail()))
            && (this.getDlvSite() == null ? other.getDlvSite() == null : this.getDlvSite().equals(other.getDlvSite()))
            && (this.getDeptNo() == null ? other.getDeptNo() == null : this.getDeptNo().equals(other.getDeptNo()))
            && (this.getExpDlvType() == null ? other.getExpDlvType() == null : this.getExpDlvType().equals(other.getExpDlvType()))
            && (this.getExpLinkNo() == null ? other.getExpLinkNo() == null : this.getExpLinkNo().equals(other.getExpLinkNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getAssNum() == null) ? 0 : getAssNum().hashCode());
        result = prime * result + ((getDoSource() == null) ? 0 : getDoSource().hashCode());
        result = prime * result + ((getDoType() == null) ? 0 : getDoType().hashCode());
        result = prime * result + ((getWaitType() == null) ? 0 : getWaitType().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getGatherWarehouseCode() == null) ? 0 : getGatherWarehouseCode().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getDoStatus() == null) ? 0 : getDoStatus().hashCode());
        result = prime * result + ((getCarried() == null) ? 0 : getCarried().hashCode());
        result = prime * result + ((getExpressCode() == null) ? 0 : getExpressCode().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getDistrict() == null) ? 0 : getDistrict().hashCode());
        result = prime * result + ((getStreet() == null) ? 0 : getStreet().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getLinkman() == null) ? 0 : getLinkman().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getSpceialFlag() == null) ? 0 : getSpceialFlag().hashCode());
        result = prime * result + ((getHopeDate() == null) ? 0 : getHopeDate().hashCode());
        result = prime * result + ((getPakageType() == null) ? 0 : getPakageType().hashCode());
        result = prime * result + ((getExtNum() == null) ? 0 : getExtNum().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getPostcode() == null) ? 0 : getPostcode().hashCode());
        result = prime * result + ((getOrderCount() == null) ? 0 : getOrderCount().hashCode());
        result = prime * result + ((getDlvEntire() == null) ? 0 : getDlvEntire().hashCode());
        result = prime * result + ((getIsWms() == null) ? 0 : getIsWms().hashCode());
        result = prime * result + ((getWlDate() == null) ? 0 : getWlDate().hashCode());
        result = prime * result + ((getCorderNo() == null) ? 0 : getCorderNo().hashCode());
        result = prime * result + ((getExtRoId() == null) ? 0 : getExtRoId().hashCode());
        result = prime * result + ((getSpceialNum() == null) ? 0 : getSpceialNum().hashCode());
        result = prime * result + ((getDoState() == null) ? 0 : getDoState().hashCode());
        result = prime * result + ((getDoStateDetail() == null) ? 0 : getDoStateDetail().hashCode());
        result = prime * result + ((getDlvSite() == null) ? 0 : getDlvSite().hashCode());
        result = prime * result + ((getDeptNo() == null) ? 0 : getDeptNo().hashCode());
        result = prime * result + ((getExpDlvType() == null) ? 0 : getExpDlvType().hashCode());
        result = prime * result + ((getExpLinkNo() == null) ? 0 : getExpLinkNo().hashCode());
        return result;
    }
}