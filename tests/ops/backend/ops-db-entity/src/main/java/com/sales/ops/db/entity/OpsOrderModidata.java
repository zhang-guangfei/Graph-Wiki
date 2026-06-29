package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsOrderModidata implements Serializable {
    private Long id;

    private String orderId;

    private Integer orderItem;

    private String rorderFno;

    private String typeCode;

    private String oModelno;

    private Integer oQuantity;

    private BigDecimal oPrice;

    private Date oDlvdate;

    private String nModelno;

    private Integer nQuantity;

    private BigDecimal nPrice;

    private Date nDlvdate;

    private String remark;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String dutyName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public String getRorderFno() {
        return rorderFno;
    }

    public void setRorderFno(String rorderFno) {
        this.rorderFno = rorderFno == null ? null : rorderFno.trim();
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getoModelno() {
        return oModelno;
    }

    public void setoModelno(String oModelno) {
        this.oModelno = oModelno == null ? null : oModelno.trim();
    }

    public Integer getoQuantity() {
        return oQuantity;
    }

    public void setoQuantity(Integer oQuantity) {
        this.oQuantity = oQuantity;
    }

    public BigDecimal getoPrice() {
        return oPrice;
    }

    public void setoPrice(BigDecimal oPrice) {
        this.oPrice = oPrice;
    }

    public Date getoDlvdate() {
        return oDlvdate;
    }

    public void setoDlvdate(Date oDlvdate) {
        this.oDlvdate = oDlvdate;
    }

    public String getnModelno() {
        return nModelno;
    }

    public void setnModelno(String nModelno) {
        this.nModelno = nModelno == null ? null : nModelno.trim();
    }

    public Integer getnQuantity() {
        return nQuantity;
    }

    public void setnQuantity(Integer nQuantity) {
        this.nQuantity = nQuantity;
    }

    public BigDecimal getnPrice() {
        return nPrice;
    }

    public void setnPrice(BigDecimal nPrice) {
        this.nPrice = nPrice;
    }

    public Date getnDlvdate() {
        return nDlvdate;
    }

    public void setnDlvdate(Date nDlvdate) {
        this.nDlvdate = nDlvdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName == null ? null : dutyName.trim();
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
        OpsOrderModidata other = (OpsOrderModidata) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getRorderFno() == null ? other.getRorderFno() == null : this.getRorderFno().equals(other.getRorderFno()))
            && (this.getTypeCode() == null ? other.getTypeCode() == null : this.getTypeCode().equals(other.getTypeCode()))
            && (this.getoModelno() == null ? other.getoModelno() == null : this.getoModelno().equals(other.getoModelno()))
            && (this.getoQuantity() == null ? other.getoQuantity() == null : this.getoQuantity().equals(other.getoQuantity()))
            && (this.getoPrice() == null ? other.getoPrice() == null : this.getoPrice().equals(other.getoPrice()))
            && (this.getoDlvdate() == null ? other.getoDlvdate() == null : this.getoDlvdate().equals(other.getoDlvdate()))
            && (this.getnModelno() == null ? other.getnModelno() == null : this.getnModelno().equals(other.getnModelno()))
            && (this.getnQuantity() == null ? other.getnQuantity() == null : this.getnQuantity().equals(other.getnQuantity()))
            && (this.getnPrice() == null ? other.getnPrice() == null : this.getnPrice().equals(other.getnPrice()))
            && (this.getnDlvdate() == null ? other.getnDlvdate() == null : this.getnDlvdate().equals(other.getnDlvdate()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getDutyName() == null ? other.getDutyName() == null : this.getDutyName().equals(other.getDutyName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getRorderFno() == null) ? 0 : getRorderFno().hashCode());
        result = prime * result + ((getTypeCode() == null) ? 0 : getTypeCode().hashCode());
        result = prime * result + ((getoModelno() == null) ? 0 : getoModelno().hashCode());
        result = prime * result + ((getoQuantity() == null) ? 0 : getoQuantity().hashCode());
        result = prime * result + ((getoPrice() == null) ? 0 : getoPrice().hashCode());
        result = prime * result + ((getoDlvdate() == null) ? 0 : getoDlvdate().hashCode());
        result = prime * result + ((getnModelno() == null) ? 0 : getnModelno().hashCode());
        result = prime * result + ((getnQuantity() == null) ? 0 : getnQuantity().hashCode());
        result = prime * result + ((getnPrice() == null) ? 0 : getnPrice().hashCode());
        result = prime * result + ((getnDlvdate() == null) ? 0 : getnDlvdate().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getDutyName() == null) ? 0 : getDutyName().hashCode());
        return result;
    }
}