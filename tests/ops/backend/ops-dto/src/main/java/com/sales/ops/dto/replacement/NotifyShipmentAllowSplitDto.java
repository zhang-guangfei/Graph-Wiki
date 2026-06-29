package com.sales.ops.dto.replacement;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $ 指令可匹配数量（在库数量）
 * @description：
 * @date ：Created in 2024/12/3 13:25
 */
public class NotifyShipmentAllowSplitDto implements Serializable {
    private static final long serialVersionUID = -5812133327797899658L;


    private String doId;

    private String modelNo;

    //doItem表数量
    private Integer doItemQty;

    //do可参加匹配数量
    private Integer allowSplitQty;

    //匹配到计划的数量
    private Integer planMatchQty;

    public NotifyShipmentAllowSplitDto(){};

    //获取可拆指令实体
    public NotifyShipmentAllowSplitDto(String doId, Integer allowSplitQty,  String modelNo){
        this.doId = doId;
        this.allowSplitQty = allowSplitQty;
        this.modelNo = modelNo;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public Integer getAllowSplitQty() {
        return allowSplitQty;
    }

    public void setAllowSplitQty(Integer allowSplitQty) {
        this.allowSplitQty = allowSplitQty;
    }

    public Integer getDoItemQty() {
        return doItemQty;
    }

    public void setDoItemQty(Integer doItemQty) {
        this.doItemQty = doItemQty;
    }


    public Integer getPlanMatchQty() {
        return planMatchQty;
    }

    public void setPlanMatchQty(Integer planMatchQty) {
        this.planMatchQty = planMatchQty;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }
}
