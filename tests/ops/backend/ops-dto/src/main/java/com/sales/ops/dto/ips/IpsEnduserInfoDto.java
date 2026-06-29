package com.sales.ops.dto.ips;

/**
 * @description:
 * "enduserVipCode": "",
 * "enduserVipPriority": ""
 * "enduserOrderNo": "",//最终用户订单号
 * "enduserItemNo":"",//最终用户订单项号
 * "enduserModelNo":"",//最终用户型号，如代理店型号
 * "warehouseCode": "",
 * "enduserGateNo": "",//最终用户的入库号，如美国入库号
 * "enduserZoneMark": "", //地域号
 * "enduserSortNo": "", //分类编号
 * "enduserShelfNo "",//最终用户货架号，如代理店货架号
 * "enduserRemarks": ""
 * "customerNs": "",// 南北方客户
 * @author: B91717
 * @time: 2024/12/17 16:38
 */
public class IpsEnduserInfoDto {

    private String enduserVipCode; // VIP代码
    private String enduserVipPriority; // VIP优先级
    private String enduserOrderNo; // 最终用户订单号
    private String enduserItemNo; // 最终用户订单项号
    private String enduserModelNo; // 最终用户型号，如代理店型号

    private String enduserKogo; // 客户工号(日本工号)

    private String warehouseCode; // 仓库代码
    private String enduserGateNo; // 最终用户的入库号，如美国入库号
    private String enduserZoneMark; // 地域号
    private String enduserSortNo; // 分类编号
    private String enduserShelfNo; // 最终用户货架号，如代理店货架号
    private String enduserRemarks; // 备注
    private String enduserNs; // 南北方客户

    private String purchaseEnduserNo; //采购最终用户代码，写营业客单的最终用户
    private String enduserSalesDepartmentNo; //最终用户所属营业所

    public String getEnduserVipCode() {
        return enduserVipCode;
    }

    public void setEnduserVipCode(String enduserVipCode) {
        this.enduserVipCode = enduserVipCode;
    }

    public String getEnduserVipPriority() {
        return enduserVipPriority;
    }

    public void setEnduserVipPriority(String enduserVipPriority) {
        this.enduserVipPriority = enduserVipPriority;
    }

    public String getEnduserOrderNo() {
        return enduserOrderNo;
    }

    public void setEnduserOrderNo(String enduserOrderNo) {
        this.enduserOrderNo = enduserOrderNo;
    }

    public String getEnduserItemNo() {
        return enduserItemNo;
    }

    public void setEnduserItemNo(String enduserItemNo) {
        this.enduserItemNo = enduserItemNo;
    }

    public String getEnduserModelNo() {
        return enduserModelNo;
    }

    public void setEnduserModelNo(String enduserModelNo) {
        this.enduserModelNo = enduserModelNo;
    }

    public String getEnduserKogo() {
        return enduserKogo;
    }

    public void setEnduserKogo(String enduserKogo) {
        this.enduserKogo = enduserKogo;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getEnduserGateNo() {
        return enduserGateNo;
    }

    public void setEnduserGateNo(String enduserGateNo) {
        this.enduserGateNo = enduserGateNo;
    }

    public String getEnduserZoneMark() {
        return enduserZoneMark;
    }

    public void setEnduserZoneMark(String enduserZoneMark) {
        this.enduserZoneMark = enduserZoneMark;
    }

    public String getEnduserSortNo() {
        return enduserSortNo;
    }

    public void setEnduserSortNo(String enduserSortNo) {
        this.enduserSortNo = enduserSortNo;
    }

    public String getEnduserShelfNo() {
        return enduserShelfNo;
    }

    public void setEnduserShelfNo(String enduserShelfNo) {
        this.enduserShelfNo = enduserShelfNo;
    }

    public String getEnduserRemarks() {
        return enduserRemarks;
    }

    public void setEnduserRemarks(String enduserRemarks) {
        this.enduserRemarks = enduserRemarks;
    }

    public String getEnduserNs() {
        return enduserNs;
    }

    public void setEnduserNs(String enduserNs) {
        this.enduserNs = enduserNs;
    }

    public String getPurchaseEnduserNo() {
        return purchaseEnduserNo;
    }

    public void setPurchaseEnduserNo(String purchaseEnduserNo) {
        this.purchaseEnduserNo = purchaseEnduserNo;
    }

    public String getEnduserSalesDepartmentNo() {
        return enduserSalesDepartmentNo;
    }

    public void setEnduserSalesDepartmentNo(String enduserSalesDepartmentNo) {
        this.enduserSalesDepartmentNo = enduserSalesDepartmentNo;
    }
}
