package com.sales.ops.dto.prepareOrder;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OpsPrepareOrderVO {

    @ExcelIgnore
    private Long id;

    @ExcelProperty("准备订单号")
    private String orderFno;

    @ExcelProperty("备库地点")
    private String manuFactSupplierCodeName;

    @ExcelProperty("型号")
    private String modelNo;

    @ExcelProperty("状态")
    private String statusName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty("采购日期")
    private Date sendTime;

    @ExcelProperty("申请数量")
    private Integer applyQty;

    @ExcelProperty("残数")
    private Integer remainQty;

    @ExcelProperty("已预约数量")
    private Integer preQty;

    @ExcelProperty("已使用数量")
    private Integer useQty;

    @ExcelProperty("制造接单号")
    private String manuOrderNo;

    @ExcelProperty("制造接单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date supplierOperateTime;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("客户交货期")
    private Date dlvDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("指定制造出荷日")
    private Date hopeExportDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("返信日期")
    private Date manuDlvDate;

    @ExcelProperty("出库区分")
    private String supplierExpinvCode;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("核销日期")
    private Date verificationDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("终止时间")
    private Date terminateDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("决算时间")
    private Date finalAccountDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("清算时间")
    private Date liquidationDate;

    @ExcelProperty("库存类型")
    private String inventoryTypeCode;

    @ExcelProperty("客户代码")
    private String customerNo;

    @ExcelProperty("可用客户")
    private String availableCustomers;

    @ExcelProperty("ppl号")
    private String ppl;

    @ExcelProperty("项目号")
    private String projectCode;

    @ExcelProperty("客户群代码")
    private String groupCustomerNo;

    @ExcelProperty("ROHS")
    private String rohs;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("申请人")
    private String applyPsn;

    @ExcelProperty("申请号")
    private String applyNo;

    @ExcelProperty("申请部门")
    private String applyDeptNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty("申请日期")
    private Date applyTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty("更新时间")
    private Date updateTime;

    @ExcelIgnore
    private String orderNo;

    @ExcelIgnore
    private Integer itemNo;

    @ExcelIgnore
    private String supplierCode;

    @ExcelIgnore
    private String transType;

    @ExcelIgnore
    private String batchNo;

    @ExcelIgnore
    private Long applyId;

    @ExcelIgnore
    private String warehouseCode;

    @ExcelIgnore
    private Long inventoryPropertyId;

    @ExcelIgnore
    private String manuFactSupplierCode;

    @ExcelIgnore
    private Integer status;

    @ExcelIgnore
    private Boolean delFlag;

    @ExcelIgnore
    private Boolean canUseFlag;

    @ExcelIgnore
    private Integer version;

    @ExcelIgnore
    private Date createTime;

    @ExcelIgnore
    private String createUser;

    @ExcelIgnore
    private String updateUser;

    @ExcelIgnore
    private Long applyDetailId;

    @ExcelIgnore
    private String supplierExpinvRemark;

    @ExcelIgnore
    private String inventoryCustomerNo;

    public Date getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(Date liquidationDate) {
        this.liquidationDate = liquidationDate;
    }

    public String getInventoryCustomerNo() {
        return inventoryCustomerNo;
    }

    public void setInventoryCustomerNo(String inventoryCustomerNo) {
        this.inventoryCustomerNo = inventoryCustomerNo;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public Date getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(Date terminateDate) {
        this.terminateDate = terminateDate;
    }

    public Date getFinalAccountDate() {
        return finalAccountDate;
    }

    public void setFinalAccountDate(Date finalAccountDate) {
        this.finalAccountDate = finalAccountDate;
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

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
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

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getHopeExportDate() {
        return hopeExportDate;
    }

    public void setHopeExportDate(Date hopeExportDate) {
        this.hopeExportDate = hopeExportDate;
    }

    public Integer getPreQty() {
        return preQty;
    }

    public void setPreQty(Integer preQty) {
        this.preQty = preQty;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getAvailableCustomers() {
        return availableCustomers;
    }

    public void setAvailableCustomers(String availableCustomers) {
        this.availableCustomers = availableCustomers;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Integer getApplyQty() {
        return applyQty;
    }

    public void setApplyQty(Integer applyQty) {
        this.applyQty = applyQty;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getRohs() {
        return rohs;
    }

    public void setRohs(String rohs) {
        this.rohs = rohs;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplyPsn() {
        return applyPsn;
    }

    public void setApplyPsn(String applyPsn) {
        this.applyPsn = applyPsn;
    }

    public String getApplyDeptNo() {
        return applyDeptNo;
    }

    public void setApplyDeptNo(String applyDeptNo) {
        this.applyDeptNo = applyDeptNo;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }

    public Integer getUseQty() {
        return useQty;
    }

    public void setUseQty(Integer useQty) {
        this.useQty = useQty;
    }

    public String getManuOrderNo() {
        return manuOrderNo;
    }

    public void setManuOrderNo(String manuOrderNo) {
        this.manuOrderNo = manuOrderNo;
    }

    public String getManuFactSupplierCode() {
        return manuFactSupplierCode;
    }

    public void setManuFactSupplierCode(String manuFactSupplierCode) {
        this.manuFactSupplierCode = manuFactSupplierCode;
    }

    public String getManuFactSupplierCodeName() {
        return manuFactSupplierCodeName;
    }

    public void setManuFactSupplierCodeName(String manuFactSupplierCodeName) {
        this.manuFactSupplierCodeName = manuFactSupplierCodeName;
    }

    public Date getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(Date verificationDate) {
        this.verificationDate = verificationDate;
    }

    public Date getManuDlvDate() {
        return manuDlvDate;
    }

    public void setManuDlvDate(Date manuDlvDate) {
        this.manuDlvDate = manuDlvDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Boolean getCanUseFlag() {
        return canUseFlag;
    }

    public void setCanUseFlag(Boolean canUseFlag) {
        this.canUseFlag = canUseFlag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getApplyDetailId() {
        return applyDetailId;
    }

    public void setApplyDetailId(Long applyDetailId) {
        this.applyDetailId = applyDetailId;
    }

    public Date getSupplierOperateTime() {
        return supplierOperateTime;
    }

    public void setSupplierOperateTime(Date supplierOperateTime) {
        this.supplierOperateTime = supplierOperateTime;
    }

    public String getSupplierExpinvCode() {
        return supplierExpinvCode;
    }

    public void setSupplierExpinvCode(String supplierExpinvCode) {
        this.supplierExpinvCode = supplierExpinvCode;
    }

    public String getSupplierExpinvRemark() {
        return supplierExpinvRemark;
    }

    public void setSupplierExpinvRemark(String supplierExpinvRemark) {
        this.supplierExpinvRemark = supplierExpinvRemark;
    }

    public Integer getRemainQty() {
        return remainQty;
    }

    public void setRemainQty(Integer remainQty) {
        this.remainQty = remainQty;
    }
}
