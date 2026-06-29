package com.sales.ops.service.order;

import com.sales.ops.common.errorEnum.OrderCancelCodeEnum;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.dto.order.AutoOrderChangeToInitStatusDto;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.CancelInputForOrderDto;
import com.sales.ops.dto.order.OrderChangeToInitStatusDto;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author C14717
 * @date 2025/5/20 10:57
 */
public interface CreateTransferPlanService {

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    Boolean createTransferPlan(List<PurchaseInfoForCancel> purchaseList, String userName, String endUser)  throws OpsException;

}
