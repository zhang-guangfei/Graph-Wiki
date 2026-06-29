package com.sales.ops.serviceimpl.dispatch.podispatch.service;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.purchase.PoToWmDto;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.serviceimpl.dispatch.podispatch.domain.PurchaseParamsValidator;
import com.sales.ops.serviceimpl.dispatch.podispatch.domain.PurchaseResetContext;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.utils.PoNoUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author C12961
 * @date 2023/5/16 8:34
 */
@Slf4j
@Service
@AllArgsConstructor
public class PurchaseResetHandler {

    private final BaseInventoryService baseInventoryService;
    private final OrderStateService orderStateService;

    public void resetForPo(PoToWmDto poDto) throws OpsException {
        log.info("采购转订：{}", PoNoUtil.getFullPoNo(poDto.getPurchase()));
        PurchaseResetContext context = new PurchaseResetContext(poDto.getPurchase(), poDto.getRequests());
        initContext(context);
        // 发送请购单创建消息 给交货期队列
        context.getRequests().forEach(request -> orderStateService.sendOrderStateForCreateRequestPurchase(request));
    }




    public void initContext(PurchaseResetContext context) throws OpsException {
        // 根据采购单号查询在途库存
        List<OpsInventoryMove> moveList = baseInventoryService.findInventoryMoveByPo(context.getOrderNo(), context.getItemNo(), context.getSplitNo());
        // 数据校验
        PurchaseParamsValidator.reset(context.getPurchase(), moveList);
        context.setMoves(moveList);
    }
}
