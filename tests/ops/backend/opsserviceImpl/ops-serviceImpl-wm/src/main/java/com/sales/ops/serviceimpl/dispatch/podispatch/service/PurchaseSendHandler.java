package com.sales.ops.serviceimpl.dispatch.podispatch.service;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.util.CreateInfoDto;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.SendStatusEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.ba.BaseWarehouseService;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.service.wmOrder.OpsRoService;
import com.sales.ops.service.wmOrder.WmOrderTaskService;
import com.sales.ops.serviceimpl.dispatch.podispatch.domain.PurchaseSendContext;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import com.sales.ops.utils.PoNoUtil;
import com.sales.ops.utils.WmOrderFactory;
import com.sales.ops.utils.WmTaskFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2023/3/3 14:30
 */
@Slf4j
@Service
@AllArgsConstructor
public class PurchaseSendHandler {


    private final BasePoService basePoService;
    private final BaseWarehouseService baseWarehouseService;
    private final BaseDoService baseDoService;
    private final OpsDoService opsDoService;
    private final OpsRoService opsRoService;
    private final WmOrderTaskService wmOrderTaskService;
    private final OrderStateService orderStateService;
    private OpsWarehouseService opsWarehouseService;
    private PurchaseEventPublisher purchaseEventPublisher;

    /**
     * @description 采购单已发单回调
     * 生成采购调拨出入库指令
     * @author C12961
     * @date 2023/2/16 19:49
     * @update 2023/03/02
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendForPo(List<OpsPurchaseStatusToWMDto> list) throws OpsException {
        log.info("1.采购发单上下文初始化");
        PurchaseSendContext context = new PurchaseSendContext(list);
        initContext(context);
        log.info("2.处理出库入库指令");
        handle(context);
        log.info("3.发送<采购发单>消息给状态管理");
        context.getPurchases().forEach(purchase -> orderStateService.sendOrderStateForSendPurchase(purchase));
        log.info("4.发送<采购发单>事件给事件管理");
        sendEvent(context);
    }

    private void sendEvent(PurchaseSendContext context) {
        // 过滤请购单号在接单表中存在的请购单
        for (OpsPurchaseorder purchase : context.getPurchases()) {
            purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_ORDER_SEND, purchase, purchase);
        }
    }

    private void handle(PurchaseSendContext context) throws OpsException {
        for (OpsRequestpurchase request : context.getRequestListForDB()) {
            OpsPurchaseorder purchase = context.getPurchaseMap().get(request);
            String requestWarehouseCode = request.getRequestwarehouseid();
            OpsWarehouse warehouse = context.getRequestWarehouseMap().get(requestWarehouseCode);
            //生成调拨单号
            //如果存在，修改调拨单
            OpsDo checkDo = baseDoService.findCGDBCKByOrder(request.getOrderno(), request.getItemno().toString(), PoNoUtil.getSplitNo(request));
            if (!Objects.isNull(checkDo)) {
                updateCGDBCK(request, purchase, checkDo);
            } else {
                //如果不存在，生成调拨出库
                //bugid:11445 c14717 2023/07/26 仓库多地址
                opsWarehouseService.getMultAdressSetAddress(warehouse, DoTypeEnum.CGDBCK.getType());
                OpsDo opsDo = WmOrderFactory.createCGDBCKDo(request, warehouse);
                OpsDoItem opsDoItem = opsDoService.initOpsDoItem(opsDo.getDoId(), request);
                //生成调拨入库
                OpsRo opsRo = WmOrderFactory.createCGDBRKRo(request);
                OpsRoItem opsRoItem = opsRoService.initOpsRoItem(opsRo.getRoId(), request);
                //生成task
                String DBCK_ID = opsDo.getDoId();
                String DBRK_ID = opsRo.getRoId();
                List<OpsWmOrderTask> tasks = WmTaskFactory.TasksForDoAndRo(DBCK_ID, DBRK_ID, SendStatusEnum.READ, new CreateInfoDto(context.getCreator()));
                //写入调拨出入库单
                opsDoService.insertDo(opsDo, opsDoItem, null, context.getCreator());
                opsRoService.insertRo(opsRo, opsRoItem, null, context.getCreator());
                wmOrderTaskService.addBatchOpsWmOrderTask(tasks);
            }
        }
    }


    public void initContext(PurchaseSendContext context) throws OpsException {
        log.info("查询库存信息");
        Map<String, OpsWarehouse> requestWarehouseMap = context.getRequests().stream().map(OpsRequestpurchase::getRequestwarehouseid)
                .distinct().map(code -> baseWarehouseService.getWarehouseByCode(code))//！！此处如果没有查到仓库号则会触发异常
                .collect(Collectors.toMap(OpsWarehouse::getWarehouseCode, warehouse -> warehouse));
        // map<warehouseCode,warehouseType>
        Map<String, String> warehouseCodeTypeMap = requestWarehouseMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getWarehouseType()));
        log.info("筛选生成调拨单的请购单");
        List<OpsRequestpurchase> requestsForDB = context.getRequests().stream()
                //1.判断是不是补库订单-> 筛选订单类型：{20:BIN补库订单,21:先行补库订单,3:委托在库补货订单}
                .filter(request -> basePoService.isSupplyType(request.getOrdtype()))
                // 2.判断请购仓不等于采购仓
                .filter(request -> basePoService.isDifferentWarehouse(request))
                //3.请购仓库为：分库或委托在库
                .filter(request -> !baseWarehouseService.isMaster(warehouseCodeTypeMap.get(request.getRequestwarehouseid())))
                .collect(Collectors.toList());

        context.setRequestWarehouseMap(requestWarehouseMap);
        context.setWareHouseTypeMap(warehouseCodeTypeMap);
        context.setRequestListForDB(requestsForDB);
        log.info("初始化完成：{}", JSONUtil.toJsonPrettyStr(context));
    }

    private void updateCGDBCK(OpsRequestpurchase request, OpsPurchaseorder purchase, OpsDo checkDo) throws OpsException {
        if (!StringUtils.equals(checkDo.getWarehouseCode(), request.getPurchasewarehouseid())) {
            opsDoService.updateDoWaitTypeAndWareHouseCodeForId(checkDo.getId(), null, null,
                    purchase.getReceivewarehouseid(), null, checkDo.getVersion(), "sendFoPo", null, null);
        }
    }


}
