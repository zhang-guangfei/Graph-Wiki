package com.sales.ops.serviceimpl.event.v3.stockadjust;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.enums.*;
import com.sales.ops.service.inventory.AdjustInventoryService;
import com.sales.ops.service.order.TransOrderService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.BaseRoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 主动调拨订单管理
 *
 * @author C12961
 * @date 2022/3/12 14:11
 */
@Slf4j
@Service
public class OpsTransferOrderEventHandler implements OpsTransferOrderEventService {

    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private TransOrderService transOrderService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private BasePoService basePoService;
    @Autowired
    private AdjustInventoryService adjustInventoryService;
    @Override
    public int updateForWait(String transNo, int transItem) throws OpsException {
        TransOrder transOrder = transOrderService.findTransOrder(transNo, transItem);
        return transOrderService.updateStatus(transOrder.getId(), OrderTransStatusEnum.DB_WAIT);
    }

    @Override
    public int updateForOuting(String transNo, int transItem) throws OpsException {
        TransOrder transOrder = transOrderService.findTransOrder(transNo, transItem);
        if (OrderTransStatusEnum.DB_OUTED.getStatus().equals(transOrder.getStatus())) {
            return 0;
        }
        return transOrderService.updateStatus(transOrder.getId(), OrderTransStatusEnum.DB_OUTING);
    }

    @Override
    public int updateForOuted(String transNo, int transItem) throws OpsException {
        TransOrder transOrder = transOrderService.findTransOrder(transNo, transItem);
        return transOrderService.updateStatus(transOrder.getId(), OrderTransStatusEnum.DB_OUTED);
    }

    @Override
    public int updateForSignIn(String transNo, int transItem) throws OpsException {
        TransOrder transOrder = transOrderService.findTransOrder(transNo, transItem);
        return transOrderService.updateStatus(transOrder.getId(), OrderTransStatusEnum.DB_SIGNIN);
    }

    @Override
    public int updateForConfirm(String transNo, int transItem) throws OpsException {
        TransOrder transOrder = transOrderService.findTransOrder(transNo, transItem);
        return transOrderService.updateStatus(transOrder.getId(), OrderTransStatusEnum.DB_CONFIRM);
    }

    @Override
    public int updateForFinish(String transNo, int transItem) throws OpsException {
        TransOrder transOrder = transOrderService.findTransOrder(transNo, transItem);
        Integer recQty = getRecQty(transNo, transItem);
        transOrderService.updateInQuantity(transOrder.getId(), recQty);
        if (recQty >= transOrder.getQuantity()) {
            return transOrderService.updateStatus(transOrder.getId(), OrderTransStatusEnum.DB_FINISHED);
        }
        return 0;
    }

    private Integer getRecQty(String transNo, int transItem) {
        List<OpsRo> opsRos = baseRoService.findRoByOrderNo(transNo, transItem);
        Integer qty = opsRos.stream().map(ro -> {
            try {
                Integer recQty = baseRoService.findRoItemByRoId(ro.getRoId()).getRecQty();
                return Optional.ofNullable(recQty).orElse(0);
            } catch (OpsException e) {
                log.info("调库更新失败，" + String.join("-", transNo, String.valueOf(transItem)) + "没有查询到ro_item:" + ro.getRoId());
                e.printStackTrace();
            }
            return 0;
        }).reduce(Integer::sum).get();
        return qty;
    }


    @Override
    public void exeStockTransferPlan(String poNo, Integer poItem, Integer poSplitNo, Map<String, Object> param) throws OpsException {
        OpsRo ro = JSONUtil.toBean((JSONObject) param.get("ro"), OpsRo.class);
        OpsRoItem opsRoItem = JSONUtil.toBean((JSONObject) param.get("roItem"), OpsRoItem.class);
        Integer roFinQty = opsRoItem.getRecQty();
        String warehouseCode = ro.getWarehouseCode();
        if (RoTypeEnum.CGRKBK.getType().equals(ro.getRoType())) {
            // ro收货完成时，才往下执行
            if (opsRoItem.getQty() - opsRoItem.getRecQty() != 0) {
                return;
            }
            // 采购单是否完成
            // 是否为ADD类型订单，如果是，则直接跳过
            boolean finishPo = false;
            boolean addPo = org.apache.commons.lang.StringUtils.startsWith(ro.getOrderId(), "ADD");
            if (!addPo) {
                OpsPurchaseorder purchase = basePoService.getPurchase(ro.getOrderId(), Integer.valueOf(ro.getOrderItem()), ro.getNum());
                if (purchase == null) {
                    return;
                    // bugid: 12853
                    // throw Exceptions.OpsException("调库计划未查询到采购单" + ro.getOrderId() + "-" + ro.getOrderItem());
                }
                finishPo = PurchaseOrderStatusEnum.YIWANCHENG.equals(purchase.getStatecode());
            }
            adjustInventoryService.exeStockTransferPlan(ro.getOrderId(), Integer.parseInt(ro.getOrderItem()), ro.getNum(),
                    roFinQty, finishPo, warehouseCode);
        }
        //bug14473 2024-07-01 调拨入库时，查询调拨出库单号，执行调库计划
        else if (RoTypeEnum.DBRK.getType().equals(ro.getRoType())) {
            List<OpsDo> doList = baseDoService.findDoListByOrder(ro.getOrderId(), ro.getOrderItem(), ro.getNum(), ro.getAssNum(), DoTypeEnum.DBCK);
            if (CollectionUtils.isNotEmpty(doList) && doList.size() == 1) {
                OpsDo dbck = doList.get(0);
                // 调拨是否全完成
                List<OpsRo> roList = baseRoService.findRoByOrderItemNum(ro.getOrderId(), Integer.valueOf(ro.getOrderItem()), ro.getNum(), ro.getAssNum(),ro.getExtNum(), RoTypeEnum.DBRK.getType());
                boolean notFinish = roList.stream().anyMatch(item -> !RoStatusEnum.FINISH.getStatus().equals(item.getRoStatus()));
                adjustInventoryService.exeStockTransferPlanByDoId(dbck.getDoId(), roFinQty, !notFinish, warehouseCode);
            }
        }
    }





}
