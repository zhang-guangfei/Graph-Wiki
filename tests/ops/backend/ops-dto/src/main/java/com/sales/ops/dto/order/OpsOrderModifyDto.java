package com.sales.ops.dto.order;

import com.sales.ops.db.entity.Orderdlvdata;
import com.sales.ops.dto.util.UserDto;

import java.util.Date;
import java.util.Objects;

/**
 * @author C12961
 * @date 2022/5/20 13:18
 */
public class OpsOrderModifyDto {

    //订单主单号
    private String orderId;
    //订单项号
    private Integer orderItem;
    // 是否为主单修改项
    private boolean master;
    // 主单修改项
    // 出库方式
    private String dlvEntire;
    // 分包（集约）方式
    private String dlvType;
    // 变更po单号 todo bug:11447 2023-08-09
    private String purchaseNo;
    // 变更物料号 todo bug:11447 2023-08-09
    private String cproductNo;
    // 子项修改项
    // 客户交货期和物流交货期
    private boolean updateDate;
    private Date dlvDate;
    private Date wmsDlvDate;

    // 发货地址
    private boolean updateAddress;
    private Orderdlvdata address;
    //变更承运商
    private String carrier;
    //是否特发
    private Boolean dlvSpecial;
    //是否自提
    //private boolean dlvSelf;
    //是否拆分子项发货
    private Boolean dlvSplit;
    private UserDto userDto;
    private String reason;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire;
    }

    public String getDlvType() {
        return dlvType;
    }

    public void setDlvType(String dlvType) {
        this.dlvType = dlvType;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo;
    }

    public boolean isUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(boolean updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public Date getWmsDlvDate() {
        return wmsDlvDate;
    }

    public void setWmsDlvDate(Date wmsDlvDate) {
        this.wmsDlvDate = wmsDlvDate;
    }

    public boolean isUpdateAddress() {
        return updateAddress;
    }

    public void setUpdateAddress(boolean updateAddress) {
        this.updateAddress = updateAddress;
    }

    public Orderdlvdata getAddress() {
        return address;
    }

    public void setAddress(Orderdlvdata address) {
        this.address = address;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Boolean getDlvSpecial() {
        return dlvSpecial;
    }

    public void setDlvSpecial(Boolean dlvSpecial) {
        this.dlvSpecial = dlvSpecial;
    }

    public Boolean getDlvSplit() {
        return dlvSplit;
    }

    public void setDlvSplit(Boolean dlvSplit) {
        this.dlvSplit = dlvSplit;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OpsOrderModifyDto that = (OpsOrderModifyDto) o;
        return master == that.master && updateDate == that.updateDate && updateAddress == that.updateAddress && Objects.equals(orderId, that.orderId) && Objects.equals(orderItem, that.orderItem) && Objects.equals(dlvEntire, that.dlvEntire) && Objects.equals(dlvType, that.dlvType) && Objects.equals(purchaseNo, that.purchaseNo) && Objects.equals(cproductNo, that.cproductNo) && Objects.equals(dlvDate, that.dlvDate) && Objects.equals(wmsDlvDate, that.wmsDlvDate) && Objects.equals(address, that.address) && Objects.equals(carrier, that.carrier) && Objects.equals(dlvSpecial, that.dlvSpecial) && Objects.equals(dlvSplit, that.dlvSplit) && Objects.equals(userDto, that.userDto) && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderItem, master, dlvEntire, dlvType, purchaseNo, cproductNo, updateDate, dlvDate, wmsDlvDate, updateAddress, address, carrier, dlvSpecial, dlvSplit, userDto, reason);
    }
}
