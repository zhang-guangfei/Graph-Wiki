package com.sales.ops.dto.expdetail;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 样品订单出库 抽取查询实体类
 */

public class ExpdetailDto implements Serializable {

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 出库单号
     */
    private String deliveryNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单项号
     */
    private Integer itemNo;

    /**
     * 完整订单号
     */
    private String orderFno;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 发货数量
     */
    private Integer quantity;

    private Integer orderQty;

    /**
     * 货物条码
     */
    private String barcode;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    private String customerType;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 发货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date shipDate;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 发货仓库 （物流）
     */
    private String warehouseCode;

    private String warehouseCodeName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 状态: 1-写入; 2-写入失败
     */
    private Integer optCode;

    /**
     * 客户订单号
     */
    private String corderNo;

    /**
     * 客户型号
     */
    private String cmodelNo;

    /**
     * 箱号
     */
    private String caseNo;

    /**
     * 重量KG
     */
    private Double weight;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 开票抽取标识，默认为0，抽取完写1
     */
    private String invoiceFlag;

    /**
     * 开票抽取时间，开票抽取完回写
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date invoiceTime;

    /**
     * 签收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signTime;

    /**
     * 出库区分
     */
    private String stockCode;

    /**
     * 客户货期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate;

    /**
     * 合同订单号
     */
    private String orOrderNo;

    /**
     * 发货状态
     */
    private Integer signStatus;

    private String signStatusName;

    /**
     * 发货当担
     */
    private String sender;

    /**
     * 交货地点 1-直发客户; 2-直发营业; 3-自提
     */
    private String dlvSite;

    private Long id;

    private String deptNo;
    // 营业所名称
    private String deptName;

    // 签收单号
    private String signOrderNo;

    // 收货地址
    private String dlvAddress;

    // 收货人
    private String contactPsn;

    private String endUser;

    private String endUserName;

    private String hlCode;

    private String hrUnitId;

    private List<Long> idList;

    private String idString;

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCodeName() {
        return warehouseCodeName;
    }

    public void setWarehouseCodeName(String warehouseCodeName) {
        this.warehouseCodeName = warehouseCodeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getOptCode() {
        return optCode;
    }

    public void setOptCode(Integer optCode) {
        this.optCode = optCode;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo;
    }

    public String getCmodelNo() {
        return cmodelNo;
    }

    public void setCmodelNo(String cmodelNo) {
        this.cmodelNo = cmodelNo;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getInvoiceFlag() {
        return invoiceFlag;
    }

    public void setInvoiceFlag(String invoiceFlag) {
        this.invoiceFlag = invoiceFlag;
    }

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public String getOrOrderNo() {
        return orOrderNo;
    }

    public void setOrOrderNo(String orOrderNo) {
        this.orOrderNo = orOrderNo;
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignStatusName() {
        return signStatusName;
    }

    public void setSignStatusName(String signStatusName) {
        this.signStatusName = signStatusName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDlvSite() {
        return dlvSite;
    }

    public void setDlvSite(String dlvSite) {
        this.dlvSite = dlvSite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSignOrderNo() {
        return signOrderNo;
    }

    public void setSignOrderNo(String signOrderNo) {
        this.signOrderNo = signOrderNo;
    }

    public String getDlvAddress() {
        return dlvAddress;
    }

    public void setDlvAddress(String dlvAddress) {
        this.dlvAddress = dlvAddress;
    }

    public String getContactPsn() {
        return contactPsn;
    }

    public void setContactPsn(String contactPsn) {
        this.contactPsn = contactPsn;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public String getEndUserName() {
        return endUserName;
    }

    public void setEndUserName(String endUserName) {
        this.endUserName = endUserName;
    }

    public String getHlCode() {
        return hlCode;
    }

    public void setHlCode(String hlCode) {
        this.hlCode = hlCode;
    }

    public String getHrUnitId() {
        return hrUnitId;
    }

    public void setHrUnitId(String hrUnitId) {
        this.hrUnitId = hrUnitId;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
