package com.sales.ops.dto.replacement;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/29 9:09
 */
public class NotifyShipmentPlanDto implements Serializable {
    private static final long serialVersionUID = -6903619247297344695L;

    private Long Id;
    private String planNo;
    private String orderFno;
    private String orderId;
    private String orderItem;
    private Integer planQty; //通知发货数量

    private Integer matchQty = 0;//匹配数量
    private Boolean matchFinish = false;//匹配完成
    private Integer logisticsQty;//下发数量
    private Date hopeDate;//客户货期
    private Date wlDate;//物流指定货期
    private String modelNo;
    private Integer status = 0;//状态 0 待分配 1：分配中 2：分配完成
    private Integer version;
    private Integer delflag;
    private Date createTime;
    private String creator;
    private Date updateTime;
    private String updator;
    private String remark;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getLogisticsQty() {
        return logisticsQty;
    }

    public void setLogisticsQty(Integer logisticsQty) {
        this.logisticsQty = logisticsQty;
    }

    public Date getHopeDate() {
        return hopeDate;
    }

    public void setHopeDate(Date hopeDate) {
        this.hopeDate = hopeDate;
    }

    public Date getWlDate() {
        return wlDate;
    }

    public void setWlDate(Date wlDate) {
        this.wlDate = wlDate;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = StringUtils.strip(modelNo);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Integer getMatchQty() {
        return matchQty;
    }

    public void setMatchQty(Integer matchQty) {
        this.matchQty = matchQty;
        this.matchFinish = matchQty.equals(this.planQty);
    }

    public Boolean getMatchFinish() {
        return matchFinish;
    }

    public void setMatchFinish(Boolean matchFinish) {
        this.matchFinish = matchFinish;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = StringUtils.strip(orderFno);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
