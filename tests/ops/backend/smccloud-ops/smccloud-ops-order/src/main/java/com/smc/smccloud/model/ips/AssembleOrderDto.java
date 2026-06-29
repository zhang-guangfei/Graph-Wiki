package com.smc.smccloud.model.ips;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: B91717
 * @time: 2024/11/4 18:37
 */
@Data
public class AssembleOrderDto {

    // 组合订单，例如阀岛
    private  String isAssembleOrder; // 组合订单标识：0 不是组合订单；1 是组合订单

    private List<AssembleContentDto> assembleContent; // 组合订单指标

}
