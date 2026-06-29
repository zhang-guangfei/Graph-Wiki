package com.sales.ops.dto.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $ bugid:17799
 * @description：
 * @date ：Created in 2025/6/13 10:11
 */
public class OpsSpecialBomDetailDto implements Serializable {
    private static final long serialVersionUID = -2527420887628424244L;

    private Long bomId;
    private String subModelNo;
    private Integer quantity;
    private Date createTime;
    private Date updateTime;

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public String getSubModelNo() {
        return subModelNo;
    }

    public void setSubModelNo(String subModelNo) {
        this.subModelNo = subModelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
}
