package com.sales.ops.dto.expdetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-08-11
 */

public class SampleOrderApplyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 接单订单号
     */
    private String orderNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 客户单号
     */
    private String corderNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 申请部门
     */
    private String applyDeptNo;

    /**
     * 申请类型
     */
    private String applyType;

    /**
     * 成本结算类型
     */
    private String costType;

    /**
     *  申请类别名称
     */
    private String applyTypeName;

    /**
     * 结算类型名称
     */
    private String costTypeName;

    /**
     * 申请目的
     */
    private String purpose;

    /**
     * 部门
     */
    private String deptNo;

    /**
     * 出货方式
     */
    private String dlvEntire;

    /**
     * 出货类别1
     */
    private String dlvType1;

    /**
     * 出货类别2
     */
    private String dlvType2;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收货地址类别
     */
    private Integer addressType;

    /**
     * 地址编号
     */
    private String addressNo;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 生成订单时间
     */
    private Date orderDate;

    /**
     * 回复说明
     */
    private String answerText;

    /**
     * 处理人
     */
    private String answerPsn;

    /**
     * 处理时间
     */
    private Date answerTime;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    /**
     * 申请人
     */
    private String applyPsn;

    /**
     * 收货人地址
     */
    private String receiverAddress;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 收货人
     */
    private String receiverName;

    /**
     * 收货公司
     */
    private String receiverCompany;

    /**
     * 订单的SMC交易主体
     */
    private String tradeCompanyId;

    private String receiverPostCode;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverStateCode;

    private String receiverCarrierId;

    // 索赔号
    private String claimNo;

    // 索赔金额
    private BigDecimal claimAmount;

    // 索赔公司
    private String expressCompany;

    /**
     * 收货地所在营业所代码
     */
    private String deliveryDeptNo;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo;
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

    public String getApplyDeptNo() {
        return applyDeptNo;
    }

    public void setApplyDeptNo(String applyDeptNo) {
        this.applyDeptNo = applyDeptNo;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getApplyTypeName() {
        return applyTypeName;
    }

    public void setApplyTypeName(String applyTypeName) {
        this.applyTypeName = applyTypeName;
    }

    public String getCostTypeName() {
        return costTypeName;
    }

    public void setCostTypeName(String costTypeName) {
        this.costTypeName = costTypeName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire;
    }

    public String getDlvType1() {
        return dlvType1;
    }

    public void setDlvType1(String dlvType1) {
        this.dlvType1 = dlvType1;
    }

    public String getDlvType2() {
        return dlvType2;
    }

    public void setDlvType2(String dlvType2) {
        this.dlvType2 = dlvType2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerPsn() {
        return answerPsn;
    }

    public void setAnswerPsn(String answerPsn) {
        this.answerPsn = answerPsn;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
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

    public String getApplyPsn() {
        return applyPsn;
    }

    public void setApplyPsn(String applyPsn) {
        this.applyPsn = applyPsn;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverCompany() {
        return receiverCompany;
    }

    public void setReceiverCompany(String receiverCompany) {
        this.receiverCompany = receiverCompany;
    }

    public String getTradeCompanyId() {
        return tradeCompanyId;
    }

    public void setTradeCompanyId(String tradeCompanyId) {
        this.tradeCompanyId = tradeCompanyId;
    }

    public String getReceiverPostCode() {
        return receiverPostCode;
    }

    public void setReceiverPostCode(String receiverPostCode) {
        this.receiverPostCode = receiverPostCode;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverStateCode() {
        return receiverStateCode;
    }

    public void setReceiverStateCode(String receiverStateCode) {
        this.receiverStateCode = receiverStateCode;
    }

    public String getReceiverCarrierId() {
        return receiverCarrierId;
    }

    public void setReceiverCarrierId(String receiverCarrierId) {
        this.receiverCarrierId = receiverCarrierId;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public BigDecimal getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(BigDecimal claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getDeliveryDeptNo() {
        return deliveryDeptNo;
    }

    public void setDeliveryDeptNo(String deliveryDeptNo) {
        this.deliveryDeptNo = deliveryDeptNo;
    }
}
