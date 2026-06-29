package com.sales.ops.service.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.order.OrderInventoryQueryDto;
import com.sales.ops.dto.order.ReorderOutputDTO;
import com.sales.ops.dto.order.TrasferimentoDTO;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.PurchaseCancelSourceEnum;

import java.util.HashMap;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：转定服务 / 订单还原
 * @date ：Created in 2022/4/12 8:45
 */
public interface OpsChangeOrderService {

    //订单还原 取消物理指令
    boolean orderChangeToInitStatusDelDo(String orderId, String orderItem, String userName) throws OpsException;

    //订单还原 取消po
    CommonResult<Boolean> orderChangeToInitStatusDelPo(PurchaseInfoForCancel info) throws OpsException;

    boolean askWmServerDelPo(HashMap map, String orderNo, Integer itemNo, Integer splitItemNo,
                             List<OpsRequestpurchase> requestpurchaseList) throws OpsException;

    void delPoSoDelDoNotItemInv(OpsRequestpurchase requestPurchase, List<OrderInventoryQueryDto> resutlList) throws OpsException;

    List<OrderInventoryQueryDto> delPoSoDelDoNew(String orderNo, Integer itemNo, Integer splitItemNo,
                                                 List<OpsRequestpurchase> requestpurchaseList, PurchaseCancelSourceEnum cancelSourceEnum) throws OpsException;
}
