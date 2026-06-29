package com.sales.ops.serviceimpl.event.v3.stockassign;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsOrderAssignResult;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.dto.order.ResultForOrderAdjustDto;
import com.sales.ops.enums.InventoryTableTypeEnum;

import java.util.Date;
import java.util.List;

public interface OpsOrderAssignResultService {
    //订单分配事件，插入订单分配结果
    void insertForOrderAllot(List<OpsOrderAssignResult> list, Date allotStartTime, Date allotEndTime) throws OpsException;

    //采购发单事件，关联单号更新
    void updateForPoSend(String orderId, Integer orderItem, OpsPurchaseorder po) throws OpsException;

    void updateForPoUpdateSupplier(String orderId, Integer orderItem, OpsPurchaseorder po) throws OpsException;

    void updateResultForOrderAdjustNew(String orderId, String orderItem, ResultForOrderAdjustDto resultDTO) throws OpsException;

    //客单转订事件
    void updateResultForOrderAdjust(String orderId, String orderItem, String modelno, int qty,
                                    Long oldMoveId, Long newInventoryId, InventoryTableTypeEnum tableType) throws OpsException;

    //采购删单事件，客单转异常
    void updateForPoCancelToException(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo) throws OpsException;

    //采购删单事件，客单重新处理
    void updateForPoCancelToReAllot(String orderId, Integer orderItem) throws OpsException;

    void updateForOrderForceFinished(String orderId, Integer orderItem) throws OpsException;
}
