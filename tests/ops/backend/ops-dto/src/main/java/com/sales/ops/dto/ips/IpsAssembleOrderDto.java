package com.sales.ops.dto.ips;

import java.util.List;

/**
 * @description:
 * @author: B91717
 * @time: 2024/11/4 18:37
 */
public class IpsAssembleOrderDto {
    // 组合订单，例如阀岛
    private  String isAssembleOrder; // 组合订单标识：0 不是组合订单；1 是组合订单
    private List<IpsAssembleContentDto> assembleContent; // 组合订单指标

    public String getIsAssembleOrder() {
        return isAssembleOrder;
    }

    public void setIsAssembleOrder(String isAssembleOrder) {
        this.isAssembleOrder = isAssembleOrder;
    }

    public List<IpsAssembleContentDto> getAssembleContent() {
        return assembleContent;
    }

    public void setAssembleContent(List<IpsAssembleContentDto> assembleContent) {
        this.assembleContent = assembleContent;
    }
}
