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
public class SampleOrderDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * samplyOrder_apply.id
     */
    private Long applyId;

    /**
     * 项号
     */
    private Integer itemNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 客户型号
     */
    private String cmodelNo;

    /**
     * 希望货期
     */
    private Date dlvDate;

    /**
     * 备注
     */
    private String remark;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    /**
     * 结转状态
     */
    private Integer costStatus;

    private Date costTime;

    private String invoiceNo;

    private String invoiceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCmodelNo() {
        return cmodelNo;
    }

    public void setCmodelNo(String cmodelNo) {
        this.cmodelNo = cmodelNo;
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getCostStatus() {
        return costStatus;
    }

    public void setCostStatus(Integer costStatus) {
        this.costStatus = costStatus;
    }

    public Date getCostTime() {
        return costTime;
    }

    public void setCostTime(Date costTime) {
        this.costTime = costTime;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
}
