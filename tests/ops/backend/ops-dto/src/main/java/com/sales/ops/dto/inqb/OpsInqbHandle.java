package com.sales.ops.dto.inqb;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 货期问询处理表（对应中国）
 *
 * @author smc
 * @since 2024-07-09
 */
@Data
public class OpsInqbHandle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 任务号（gps返回），不问询：null
     */
    private String taskNo;

    /**
     * 供应商代码
     */
    private String supplierCode;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 最终用户
     */
    private String endUser;

    /**
     * 状态：未处理，问询中，已回复
     */
    private Integer status;

    /**
     * 期望货期日期
     */
    private Date expectedDelivery;

    /**
     * 期望货期
     */
    private Integer expectedDeliveryDate;

    /**
     * 申请时间
     */
    private Date inqbDate;

    /**
     * 是否强制问询、一般问询、可预占问询
     */
    private Integer inqbType;

    /**
     * 问询分类码
     */
    private String inqbClassify;

    /**
     * 申请部门
     */
    private String inqbDept;

    /**
     * 申请人员工编号
     */
    private String inqbUser;

    /**
     * 申请人员工姓名
     */
    private String inqbUserName;

    /**
     * 申请备注信息
     */
    private String description;

    /**
     * 回复单位(对应催促的供应商)
     */
    private String replyDept;

    /**
     * 对应供应商的实际回复部门代码
     */
    private String replySupplierDept;

    /**
     * 回复号
     */
    private String replyNo;

    /**
     * 期望货期日期
     */
    private Date replyDeliveryDate;

    /**
     * 回复货期天数
     */
    private Integer replyDeliveryDays;

    /**
     * 制造接收HL
     */
    private String replyAcceptHl;

    /**
     * 回复人，按detail中回复最晚的
     */
    private String replyUser;

    /**
     * 回复时间，按detail中回复最晚的
     */
    private Date replyTime;

    /**
     * 回复原因id（参照制造视图OPS_V_DeliveryConsultingReason），按detail中回复最晚的
     */
    private String replyReasonCode;

    /**
     * 回复原因中文描述
     */
    private String replyReason;

    /**
     * 回复备注，按detail中回复最晚的
校验不通过时，写入失败原因
     */
    private String replyRemark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String handleResult;

    // 发送中国制造需要用到，订单区域区分
    private String deptProvince;

    public String getReplyReason() {
        return replyReason;
    }

    public void setReplyReason(String replyReason) {
        this.replyReason = replyReason;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getDeptProvince() {
        return deptProvince;
    }

    public void setDeptProvince(String deptProvince) {
        this.deptProvince = deptProvince;
    }

    public Date getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(Date expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public Date getReplyDeliveryDate() {
        return replyDeliveryDate;
    }

    public void setReplyDeliveryDate(Date replyDeliveryDate) {
        this.replyDeliveryDate = replyDeliveryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
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

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Integer expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Date getInqbDate() {
        return inqbDate;
    }

    public void setInqbDate(Date inqbDate) {
        this.inqbDate = inqbDate;
    }

    public Integer getInqbType() {
        return inqbType;
    }

    public void setInqbType(Integer inqbType) {
        this.inqbType = inqbType;
    }

    public String getInqbClassify() {
        return inqbClassify;
    }

    public void setInqbClassify(String inqbClassify) {
        this.inqbClassify = inqbClassify;
    }

    public String getInqbDept() {
        return inqbDept;
    }

    public void setInqbDept(String inqbDept) {
        this.inqbDept = inqbDept;
    }

    public String getInqbUser() {
        return inqbUser;
    }

    public void setInqbUser(String inqbUser) {
        this.inqbUser = inqbUser;
    }

    public String getInqbUserName() {
        return inqbUserName;
    }

    public void setInqbUserName(String inqbUserName) {
        this.inqbUserName = inqbUserName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReplyDept() {
        return replyDept;
    }

    public void setReplyDept(String replyDept) {
        this.replyDept = replyDept;
    }

    public String getReplySupplierDept() {
        return replySupplierDept;
    }

    public void setReplySupplierDept(String replySupplierDept) {
        this.replySupplierDept = replySupplierDept;
    }

    public String getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(String replyNo) {
        this.replyNo = replyNo;
    }

    public Integer getReplyDeliveryDays() {
        return replyDeliveryDays;
    }

    public void setReplyDeliveryDays(Integer replyDeliveryDays) {
        this.replyDeliveryDays = replyDeliveryDays;
    }

    public String getReplyAcceptHl() {
        return replyAcceptHl;
    }

    public void setReplyAcceptHl(String replyAcceptHl) {
        this.replyAcceptHl = replyAcceptHl;
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyReasonCode() {
        return replyReasonCode;
    }

    public void setReplyReasonCode(String replyReasonCode) {
        this.replyReasonCode = replyReasonCode;
    }

    public String getReplyRemark() {
        return replyRemark;
    }

    public void setReplyRemark(String replyRemark) {
        this.replyRemark = replyRemark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
