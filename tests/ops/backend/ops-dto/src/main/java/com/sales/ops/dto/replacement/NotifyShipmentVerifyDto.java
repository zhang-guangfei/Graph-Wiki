package com.sales.ops.dto.replacement;

import com.sales.ops.db.entity.OpsDoItem;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/28 15:46
 */
public class NotifyShipmentVerifyDto implements Serializable {
    private static final long serialVersionUID = 6528513508700980521L;

    private Integer orderQty;

    private Integer planQty;//已计划数量

    private String modelNo;// 型号

    private Boolean assChildFlag = false;//子项发货

    private List<NotifyShipmentVerifyItemDto> items;

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public List<NotifyShipmentVerifyItemDto> getItems() {
        return items;
    }

    public void setItems(List<NotifyShipmentVerifyItemDto> items) {
        this.items = items;
    }

    public Boolean getAssChildFlag() {
        return assChildFlag;
    }

    public void setAssChildFlag(Boolean assChildFlag) {
        this.assChildFlag = assChildFlag;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

}
