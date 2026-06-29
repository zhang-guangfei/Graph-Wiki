package com.sales.ops.service.order;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsOrderAssignResult;
import com.sales.ops.serviceimpl.order.result.NewResult;
import com.sales.ops.serviceimpl.order.result.Result;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2022/4/16 10:48
 */
public interface BaseOrderAssignResultService {


    // 记录订单分配结果
    void insertOrderAssignResult(List<OpsOrderAssignResult> list, Date allotStartTime, Date allotEndTime)throws OpsException;

    void updateRcvStockInfoByResult(List<OpsOrderAssignResult> orderAssignList) throws OpsException;

    //查询根据十位订单号
    List<OpsOrderAssignResult> findByOrder(String orderId, Integer orderItem);

    List<OpsOrderAssignResult> findDeletedByOrder(String orderId, Integer orderItem);

    //查询根据关联单号
    List<OpsOrderAssignResult> findByPo(String poNo, Integer poItem, Integer poSplitNo);

    //查询根据十位单号和关联单号
    OpsOrderAssignResult findByOrderAndPo(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo) throws OpsException;

    // 采购发单更新
    int updateByPurchaseOrder(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo, String supplier) throws OpsException;

    //订单转订更新
    int updateResultForOrderAdjust(Result result, NewResult newResult) throws OpsException;

    //更新为异常状态
    int updateToException(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo) throws OpsException;

    int updateByPurchaseOrderUpdateSupplier(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo, String supplier) throws OpsException;

    void deleteById(Long id);

    //删除订单分配结果
    int deleteResultOrder(String orderId, Integer orderItem) throws OpsException;

    void updateResultForForceFinish(String orderId, Integer orderItem) throws OpsException;
}
