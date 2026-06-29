package com.sales.ops.dto.replacement;

import com.sales.ops.dto.util.DateUtil;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/29 9:10
 */
public class NotifyShipmentPlanItemDto implements Serializable {
    private static final long serialVersionUID = -6690018379652451780L;
    private Long Id;
    private String planNo;
    private String doId;
    private String pcoId;
    private Integer qty;
    private Integer status;
    private Integer version;
    private Integer delflag;
    private Date createTime;
    private String creator;
    private Date updateTime;
    private String updator;

    public NotifyShipmentPlanItemDto build(String doId, Integer qty){
        this.doId = doId;
        this.qty = qty;
        this.delflag = 0;
        this.version = 0;
        this.createTime = DateUtil.getNow();
        this.creator = "AUTO";
        return this;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId;
    }

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

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
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
}
