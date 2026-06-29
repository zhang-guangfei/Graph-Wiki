package com.sales.ops.dto.ips;

import java.util.List;

/**
 * @description: 附加信息，contentinfo 字段
 * @author: B91717
 * @time: 2024/11/4 18:30
 */
public class IpsReceiveContentInfoDto {
    private IpsProductInfoDto productInfo;

    private IpsPackageInfoDto packageInfo;

    private IpsDeliveryInfoDto deliveryInfo;

    private IpsCustomerInfoDto customerInfo;

    private IpsEnduserInfoDto enduserInfo;

    private IpsCancelInfoDto cancelInfo;

    private IpsAssembleOrderDto assembleOrder;

    private IpsSupplierInfoDto supplierInfo;

    private List<IpsAddEmailInfoDto> addEmailInfo;

    private IpsUserInfoDto userInfo;

    public IpsUserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(IpsUserInfoDto userInfo) {
        this.userInfo = userInfo;
    }

    public IpsProductInfoDto getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(IpsProductInfoDto productInfo) {
        this.productInfo = productInfo;
    }

    public IpsPackageInfoDto getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(IpsPackageInfoDto packageInfo) {
        this.packageInfo = packageInfo;
    }

    public IpsDeliveryInfoDto getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(IpsDeliveryInfoDto deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public IpsCustomerInfoDto getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(IpsCustomerInfoDto customerInfo) {
        this.customerInfo = customerInfo;
    }

    public IpsEnduserInfoDto getEnduserInfo() {
        return enduserInfo;
    }

    public void setEnduserInfo(IpsEnduserInfoDto enduserInfo) {
        this.enduserInfo = enduserInfo;
    }

    public IpsCancelInfoDto getCancelInfo() {
        return cancelInfo;
    }

    public void setCancelInfo(IpsCancelInfoDto cancelInfo) {
        this.cancelInfo = cancelInfo;
    }

    public IpsAssembleOrderDto getAssembleOrder() {
        return assembleOrder;
    }

    public void setAssembleOrder(IpsAssembleOrderDto assembleOrder) {
        this.assembleOrder = assembleOrder;
    }

    public IpsSupplierInfoDto getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(IpsSupplierInfoDto supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public List<IpsAddEmailInfoDto> getAddEmailInfo() {
        return addEmailInfo;
    }

    public void setAddEmailInfo(List<IpsAddEmailInfoDto> addEmailInfo) {
        this.addEmailInfo = addEmailInfo;
    }
//    private  String drawingNo; // // 图纸版本号,新采购系统

}