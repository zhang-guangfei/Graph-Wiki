package com.sales.ops.serviceimpl.event.v3.plan;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDeliveryPlanResult;

import java.util.List;

public interface OrderDeliveryPlanService {
    // 按照交易出库单号查询交付计划结果
    OpsDeliveryPlanResult OpsDeliveryPlanResultByDoId(String doId) throws OpsException;

    // 按照交易出库单号查询交付计划结果表,返回list不抛异常
    List<OpsDeliveryPlanResult> findPlanResultListByDoId(String doId);

    void handle(String orderId, Integer orderItem, String pointStatus) throws OpsException;

    // 按照十位单号查询交付计划结果
    List<OpsDeliveryPlanResult> selectDeliveryPlanResultsByOrderNo(String orderId, String orderItem);
}
