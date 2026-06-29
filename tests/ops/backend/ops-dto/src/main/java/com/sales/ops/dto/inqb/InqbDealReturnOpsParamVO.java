package com.sales.ops.dto.inqb;

/**
 * @Description: 回调门户传参实体
 */
public class InqbDealReturnOpsParamVO {

    private Integer applyType;

    private InqbDealReturnOpsParam dealReturnOpsParam;

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public InqbDealReturnOpsParam getDealReturnOpsParam() {
        return dealReturnOpsParam;
    }

    public void setDealReturnOpsParam(InqbDealReturnOpsParam dealReturnOpsParam) {
        this.dealReturnOpsParam = dealReturnOpsParam;
    }
}
