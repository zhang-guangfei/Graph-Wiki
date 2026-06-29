package com.sales.ops.dto.ips;

/**
 *
 * 发送IPS用到的仓库相关的Dto，根据仓库代码 带出 仓库地址IPS收货人 等等信息
 */

public class IpsOrderWarehouseInfoDto {
    
    private String opsWarehouseCode;// OPS仓库代码

    private String receivingAddressId;  // IPS收货地址编码

    private String receivingWarehouse; // IPS收货仓编码（由业务系统提供）

    private String plantmarkAddress; // 厂内地址,对应master表的request_address

    private String receivingAddress; // IPS收货地址

    private String receivingConnector; // IPS收货人

    private String receivingDepartmentTeleNo; // IPS收货人联系电话

    private String requestDepartmentNo; // 请购部门编码

    private String requestDepartmentName; // 请购部门名称

    private String requestPersonName; // 请购担当姓名

    private String requestPersonNo; // 请购担当ID

    private String requestPersonTele; // 请购担当联系电话

    private String requestPersonEmail; // 请购担当邮箱
    
    private String requestAddress;

    private String englishLinkman; // 联系人英文名（Mr Tian Xiao Ming）

    private String englishAddress; //英文的地址（17 Jian An St. Beijing Econmic & Technological Development Zone 100176 China）

    public String getEnglishLinkman() {
        return englishLinkman;
    }

    public void setEnglishLinkman(String englishLinkman) {
        this.englishLinkman = englishLinkman;
    }

    public String getEnglishAddress() {
        return englishAddress;
    }

    public void setEnglishAddress(String englishAddress) {
        this.englishAddress = englishAddress;
    }

    public String getOpsWarehouseCode() {
        return opsWarehouseCode;
    }

    public void setOpsWarehouseCode(String opsWarehouseCode) {
        this.opsWarehouseCode = opsWarehouseCode;
    }

    public String getReceivingAddressId() {
        return receivingAddressId;
    }

    public void setReceivingAddressId(String receivingAddressId) {
        this.receivingAddressId = receivingAddressId;
    }

    public String getReceivingWarehouse() {
        return receivingWarehouse;
    }

    public void setReceivingWarehouse(String receivingWarehouse) {
        this.receivingWarehouse = receivingWarehouse;
    }

    public String getPlantmarkAddress() {
        return plantmarkAddress;
    }

    public void setPlantmarkAddress(String plantmarkAddress) {
        this.plantmarkAddress = plantmarkAddress;
    }

    public String getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public String getReceivingConnector() {
        return receivingConnector;
    }

    public void setReceivingConnector(String receivingConnector) {
        this.receivingConnector = receivingConnector;
    }

    public String getReceivingDepartmentTeleNo() {
        return receivingDepartmentTeleNo;
    }

    public void setReceivingDepartmentTeleNo(String receivingDepartmentTeleNo) {
        this.receivingDepartmentTeleNo = receivingDepartmentTeleNo;
    }

    public String getRequestDepartmentNo() {
        return requestDepartmentNo;
    }

    public void setRequestDepartmentNo(String requestDepartmentNo) {
        this.requestDepartmentNo = requestDepartmentNo;
    }

    public String getRequestDepartmentName() {
        return requestDepartmentName;
    }

    public void setRequestDepartmentName(String requestDepartmentName) {
        this.requestDepartmentName = requestDepartmentName;
    }

    public String getRequestPersonName() {
        return requestPersonName;
    }

    public void setRequestPersonName(String requestPersonName) {
        this.requestPersonName = requestPersonName;
    }

    public String getRequestPersonNo() {
        return requestPersonNo;
    }

    public void setRequestPersonNo(String requestPersonNo) {
        this.requestPersonNo = requestPersonNo;
    }

    public String getRequestPersonTele() {
        return requestPersonTele;
    }

    public void setRequestPersonTele(String requestPersonTele) {
        this.requestPersonTele = requestPersonTele;
    }

    public String getRequestPersonEmail() {
        return requestPersonEmail;
    }

    public void setRequestPersonEmail(String requestPersonEmail) {
        this.requestPersonEmail = requestPersonEmail;
    }

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }
}
