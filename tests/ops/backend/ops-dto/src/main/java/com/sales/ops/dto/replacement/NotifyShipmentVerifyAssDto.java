package com.sales.ops.dto.replacement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description： 通知发货拆分验证
 * @date ：Created in 2024/11/29 16:32
 */
public class NotifyShipmentVerifyAssDto implements Serializable {
    private static final long serialVersionUID = 1414518728785418316L;

    // 是否存在计划
    private Boolean existPlan = false;

    // 计划主表
    private List<NotifyShipmentPlanDto> planList;
    // 计划子表
    private List<NotifyShipmentPlanItemDto> planItemList;

    // 是否存在计划子表
    private Boolean existPlanItem = false;

    //已拆分指令和数量<doid,qty>
    private HashMap<String,Integer> planItemMap = new HashMap<>();

    public Boolean getExistPlan() {
        return existPlan;
    }

    public void setExistPlan(Boolean existPlan) {
        this.existPlan = existPlan;
    }

    public List<NotifyShipmentPlanDto> getPlanList() {
        return planList;
    }

    public void setPlanList(List<NotifyShipmentPlanDto> planList) {
        this.planList = planList;
    }

    public List<NotifyShipmentPlanItemDto> getPlanItemList() {
        return planItemList;
    }

    public void setPlanItemList(List<NotifyShipmentPlanItemDto> planItemList) {
        this.planItemList = planItemList;
    }

    public Boolean getExistPlanItem() {
        return existPlanItem;
    }

    public void setExistPlanItem(Boolean existPlanItem) {
        this.existPlanItem = existPlanItem;
    }

    public HashMap<String, Integer> getPlanItemMap() {
        return planItemMap;
    }

    public void setPlanItemMap(HashMap<String, Integer> planItemMap) {
        this.planItemMap = planItemMap;
    }
}
