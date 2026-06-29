package com.sales.ops.dto.zd;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.enums.InventoryTableTypeEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * @author C12961
 * @date 2022/4/6 14:05
 */

public class ZDInventoryInfoDTO implements Serializable {


    private static final long serialVersionUID = 1080161092143347403L;
    private String inventoryTableType;
    private Long inventoryId;
    private String inventoryStatus;
    private String modelno;

    private String warehouseCode;
    private String warehouseName;
    private String warehouseType;

    //批属性ID
    private Long inventoryPropertyId;
    //库存分类：顾客在库，战略在库、通用在库
    private String inventoryTypeCode;
    private String customerNo;
    private String groupCustomerNo;
    private String ppl;
    private String projectCode;
    private String salesInfoNo;

    private Integer quantity;
    private Integer prepareQuantity;
    private Integer allocatedQuantity;
    private Integer availableQuantity;

    private String associateNo;
    private int associateItemNo;
    private int associateSplitNo;

    private String unit;
    private String qaStatus;
    private String po;
    private Long price;
    private Date inTime;

    private String invoiceNo;
    private Date preReceiveDate;

    private String dk;

    private Boolean invRisk = false;


    public ZDInventoryInfoDTO() {
    }

    public ZDInventoryInfoDTO(OpsInventory inv) {
        this.inventoryTableType = InventoryTableTypeEnum.NORMAL.getType();
        this.inventoryId = inv.getInventoryId();
        this.inventoryStatus = inv.getInventoryStatus();
        this.modelno = inv.getModelno();
        this.warehouseCode = inv.getWarehouseCode();
        this.inventoryPropertyId = inv.getInventoryPropertyId();
        this.quantity = inv.getQuantity();
        this.prepareQuantity = inv.getPrepareQuantity();
        this.availableQuantity = quantity - prepareQuantity;
    }

    public ZDInventoryInfoDTO(OpsInventoryMove inv) {
        this.inventoryTableType = InventoryTableTypeEnum.TRANS.getType();
        this.inventoryId = inv.getInventoryId();
        this.inventoryStatus = inv.getInventoryStatus();
        this.modelno = inv.getModelno();
        this.warehouseCode = inv.getWarehouseCode();
        this.inventoryPropertyId = inv.getInventoryPropertyId();
        this.quantity = inv.getQuantity();
        this.prepareQuantity = inv.getPrepareQuantity();
        this.availableQuantity = quantity - prepareQuantity;
        this.invoiceNo = inv.getInvoiceno();
        this.preReceiveDate = inv.getPrereceivedate();

    }


    public void setProperty(OpsInventoryProperty property) {
        this.inventoryPropertyId = property.getInventoryPropertyId();
        this.inventoryTypeCode = property.getInventoryTypeCode();
        this.customerNo = property.getCustomerNo();
        this.groupCustomerNo = property.getGroupCustomerNo();
        this.ppl = property.getPpl();
        this.projectCode = property.getProjectCode();
        this.salesInfoNo = property.getSalesInfoNo();
    }

    public void setWarehouseInfo(OpsWarehouse warehouse) {
        this.warehouseCode = warehouse.getWarehouseCode();
        this.warehouseName = warehouse.getWarehouseName();
        this.warehouseType = warehouse.getWarehouseType();
    }

    public void setAssociateNo(String associateNo,int associateItemNo,int associateSplitNo){
        this.associateNo=associateNo;
        this.associateItemNo=associateItemNo;
        this.associateSplitNo=associateSplitNo;
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo;
    }

    public int getAssociateItemNo() {
        return associateItemNo;
    }

    public void setAssociateItemNo(int associateItemNo) {
        this.associateItemNo = associateItemNo;
    }

    public int getAssociateSplitNo() {
        return associateSplitNo;
    }

    public void setAssociateSplitNo(int associateSplitNo) {
        this.associateSplitNo = associateSplitNo;
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrepareQuantity() {
        return prepareQuantity;
    }

    public void setPrepareQuantity(Integer prepareQuantity) {
        this.prepareQuantity = prepareQuantity;
    }

    public Integer getAllocatedQuantity() {
        return allocatedQuantity;
    }

    public void setAllocatedQuantity(Integer allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(String qaStatus) {
        this.qaStatus = qaStatus;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getPreReceiveDate() {
        return preReceiveDate;
    }

    public void setPreReceiveDate(Date preReceiveDate) {
        this.preReceiveDate = preReceiveDate;
    }

    public String getDk() {
        return dk;
    }

    public void setDk(String dk) {
        this.dk = dk;
    }

    public Boolean getInvRisk() {
        return invRisk;
    }

    public void setInvRisk(Boolean invRisk) {
        this.invRisk = invRisk;
    }
}
