package com.sales.ops.dto.prepareOrder;

import java.util.Date;

public class PrestockApplyDto {

    private Long id;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 申请类型 1-专备; 2-SHIKOMI; 3-委托在库补库
     */
    private String applyType;

    /**
     * 库存类型 - inventoryTypeCode
     */
    private String stockType;

    /**
     * 部门代码
     */
    private String deptNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 状态 1-编辑中; 2-待审核; 3-待处理; 4-不通过; 5-已确认(SHIKOMI); 6-已备库; 9-取消
     */
    private String status;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 项目代码
     */
    private String projectNo;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 应用设备
     */
    private String equipment;

    /**
     * 项目状态
     */
    private String projectStatus;

    /**
     * 用途
     */
    private String purpose;

    /**
     * 原因
     */
    private String reason;

    private String reasonEn;

    /**
     * 订单大小
     */
    private String orderSize;

    /**
     * 担当
     */
    private String salesPsn;

    /**
     * 用户部门
     */
    private String userDept;

    /**
     * 用户联系人
     */
    private String userPsnName;

    /**
     * 用户联系人职位
     */
    private String userPsnJob;

    /**
     * 申请人
     */
    private String applyPsn;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    /**
     * 随机key
     */
    private String passkey;

    /**
     * ppl_no项目号
     */
    private String pplNo;

    /**
     * 客户组号
     */
    private String groupCustomerNo;

    /**
     * 行业代码
     */
    private String indCode;

    /**
     * 仓库
     */
    private String warehouseCode;

    private String applyPsnNo;

    /**
     * 负责人
     */
    private String approverNo;

    private String approverName;

    /**
     * 0-全球共享; 1-中国公司共享; 2-专享可借用; 3-客户专享; 4-国内不可用;
     */
    private String shikomiClass;

    /**
     * 1-SMC提案; 2-客户提案; 3-自动周转;
     */
    private String replType;

    private String applyPsnMail;

    private String approverMail;

    private String enname;

    /**
     * 审批时间
     */
    private Date approveTime;

    /**
     * 共享客户（SHIKOMI）
     */
    private String customerNos;

    /**
     * 是否异仓库调拨
     */
    private Boolean transFlag;

    private Boolean vip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonEn() {
        return reasonEn;
    }

    public void setReasonEn(String reasonEn) {
        this.reasonEn = reasonEn;
    }

    public String getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(String orderSize) {
        this.orderSize = orderSize;
    }

    public String getSalesPsn() {
        return salesPsn;
    }

    public void setSalesPsn(String salesPsn) {
        this.salesPsn = salesPsn;
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String getUserPsnName() {
        return userPsnName;
    }

    public void setUserPsnName(String userPsnName) {
        this.userPsnName = userPsnName;
    }

    public String getUserPsnJob() {
        return userPsnJob;
    }

    public void setUserPsnJob(String userPsnJob) {
        this.userPsnJob = userPsnJob;
    }

    public String getApplyPsn() {
        return applyPsn;
    }

    public void setApplyPsn(String applyPsn) {
        this.applyPsn = applyPsn;
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

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public String getPplNo() {
        return pplNo;
    }

    public void setPplNo(String pplNo) {
        this.pplNo = pplNo;
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public String getIndCode() {
        return indCode;
    }

    public void setIndCode(String indCode) {
        this.indCode = indCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getApplyPsnNo() {
        return applyPsnNo;
    }

    public void setApplyPsnNo(String applyPsnNo) {
        this.applyPsnNo = applyPsnNo;
    }

    public String getApproverNo() {
        return approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getShikomiClass() {
        return shikomiClass;
    }

    public void setShikomiClass(String shikomiClass) {
        this.shikomiClass = shikomiClass;
    }

    public String getReplType() {
        return replType;
    }

    public void setReplType(String replType) {
        this.replType = replType;
    }

    public String getApplyPsnMail() {
        return applyPsnMail;
    }

    public void setApplyPsnMail(String applyPsnMail) {
        this.applyPsnMail = applyPsnMail;
    }

    public String getApproverMail() {
        return approverMail;
    }

    public void setApproverMail(String approverMail) {
        this.approverMail = approverMail;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getCustomerNos() {
        return customerNos;
    }

    public void setCustomerNos(String customerNos) {
        this.customerNos = customerNos;
    }

    public Boolean getTransFlag() {
        return transFlag;
    }

    public void setTransFlag(Boolean transFlag) {
        this.transFlag = transFlag;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }
}
