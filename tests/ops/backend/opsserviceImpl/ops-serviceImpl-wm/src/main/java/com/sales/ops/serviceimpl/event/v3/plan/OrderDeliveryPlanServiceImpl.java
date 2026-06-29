package com.sales.ops.serviceimpl.event.v3.plan;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsDeliveryPlanResultMapper;
import com.sales.ops.db.entity.OpsDeliveryPlanResult;
import com.sales.ops.db.entity.OpsDeliveryPlanResultExample;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.OpsCustomerOrderService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.serviceimpl.delivery.plan.DeliveryPlanService;
import com.sales.ops.serviceimpl.event.v3.order.entity.JYCK;
import com.sales.ops.serviceimpl.event.v3.order.entity.Order;
import com.sales.ops.serviceimpl.event.v3.order.service.OrderRelationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class OrderDeliveryPlanServiceImpl implements OrderDeliveryPlanService {

    @Autowired
    private DeliveryPlanService deliveryPlanService;
    @Autowired
    private OpsDeliveryPlanResultMapper opsDeliveryPlanResultMapper;
    @Resource
    private BaseCustomerOrderService baseCustomerOrderService;
    @Resource
    private OrderRelationService orderRelationService;
    @Resource
    private OrderDeliveryPlanReliabilityConfig orderDeliveryPlanReliabilityConfig;
    @Autowired
    private OpsCustomerOrderService opsCustomerOrderService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private BaseDoService baseDoService;

    @Override
    // 按照交易出库单号查询交付计划结果表
    public OpsDeliveryPlanResult OpsDeliveryPlanResultByDoId(String doId) throws OpsException {
        OpsDeliveryPlanResultExample e = new OpsDeliveryPlanResultExample();
        e.createCriteria().andDelflagEqualTo(0).andCurrentValidEqualTo(1)
                .andDoIdEqualTo(doId);
        List<OpsDeliveryPlanResult> results = opsDeliveryPlanResultMapper.selectByExample(e);
        if (CollectionUtils.isEmpty(results)) {
            return null;
        }
        if (results.size() > 1) {
            throw Exceptions.OpsException("查询出" + results.size() + "条交付计划结果");
        }
        return results.get(0);
    }

    @Override
    public List<OpsDeliveryPlanResult> findPlanResultListByDoId(String doId) {
        OpsDeliveryPlanResultExample e = new OpsDeliveryPlanResultExample();
        e.createCriteria().andDelflagEqualTo(0).andCurrentValidEqualTo(1)
                .andDoIdEqualTo(doId);
        List<OpsDeliveryPlanResult> results = opsDeliveryPlanResultMapper.selectByExample(e);
        return results;
    }

    @Transactional
    @Override
    public void handle(String orderId, Integer orderItem, String pointStatus) throws OpsException {
        // 计算交付计划
        Boolean updateFlag = deliveryPlanService.calculateDeliveryPlanDate(pointStatus, orderId, orderItem);
        //无新内容，不用计算后续 bugid：17143 c14717
        if(!updateFlag){
            return;
        }
        // 查询该订单的发货方式
        Order order = orderRelationService.getOrder(orderId, orderItem);
        // 计算交付计划表预计送达日期（按照发货方式）
        updatePlanDeliveryDayForResults(order);
        // 计算rcvdetail预计送达日期（按照可信度）
        getPlanDeliveryDayForRcvOrder(order);
        // 更新rcv，如果为空，则不变更
        baseCustomerOrderService.updateEstimatedDeliveryDay(orderId, orderItem, order.getEstimatedDeliveryDay());
        //计算do级别的货齐时间
        List<OpsDo> jyckDoList = baseDoService.findAllJYCKByOrder(orderId, orderItem.toString());
        for (OpsDo opsDo : jyckDoList) {
            opsDoService.calculateDoReadyDate(opsDo.getDoId());
        }
        //计算并更新rcv的货齐时间，首先计算do货齐时间
        Date readyTime = opsCustomerOrderService.analyzeRcvReadyTime(orderId, orderItem.toString());
        Date entryTime = opsCustomerOrderService.analyzeRcvEntryTime(orderId, orderItem.toString());
        baseCustomerOrderService.updateReadyTime(orderId, orderItem, readyTime, entryTime);
    }


    /**
     * 先按照发货方式选择不同颗粒度的单号，查询planResult表数据，
     * 按照不同颗粒度的单号，更新result表的PlanDeliveryDay
     */
    private void updatePlanDeliveryDayForResults(Order order) {
        List<List<OpsDeliveryPlanResult>> list = getDeliveryPlanResultListByDlvEntire(order);
        for (List<OpsDeliveryPlanResult> results : list) {
            // 取最晚的日期
            Date planDeliveryDay = calculatePlanDeliveryDay(results);
            updateDeliveryPlanResultsById(results, planDeliveryDay);
        }
    }

    // 按发货方式获取plan，检测是否都可信，并按发货方式提取最晚的plan日期
    private void getPlanDeliveryDayForRcvOrder(Order order) {
        List<List<OpsDeliveryPlanResult>> list = getDeliveryPlanResultListByDlvEntire(order);
        ArrayList<OpsDeliveryPlanResult> rcvDetailResults = new ArrayList<>();
        for (List<OpsDeliveryPlanResult> results : list) {
            for (OpsDeliveryPlanResult result : results) {
                if (!orderDeliveryPlanReliabilityConfig.reliable(result.getReliability())) {
                    return;
                }
            }
            rcvDetailResults.addAll(results);
        }
        Date planDeliveryDay = getPlanDeliveryDay(rcvDetailResults);
        order.setEstimatedDeliveryDay(planDeliveryDay);
    }


    // 第一层List按照发货方式分组,如果为随发分批，则第一层list有多个元素，如果是其他，则第一层List只有一个元素
    private List<List<OpsDeliveryPlanResult>> getDeliveryPlanResultListByDlvEntire(Order order) {
        List<List<OpsDeliveryPlanResult>> list = new ArrayList<>();
        switch (order.getDlvEntire()) {
            case ORDER_GETHER_SIGNLE_HOUSE: // 货齐单仓
            case ORDER_GETHER_MULTI_HOUSE: // 货齐多仓
                // 查询交付计划结果表和明细
                List<OpsDeliveryPlanResult> rcvMasterResults = selectDeliveryPlanResultsByOrderNo(order.getOrderId());
                list.add(rcvMasterResults);
                break;
            case ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS: // 随发单仓
            case ITEM_GETHER_SIGNLE_HOUSE: // 随发
                List<OpsDeliveryPlanResult> rcvDetailResults = selectDeliveryPlanResultsByOrderNo(order.getOrderId(), order.getOrderItem().toString());
                list.add(rcvDetailResults);
                break;
            case NOTIFY_UNLIMIT: // 通知发货 按随发分批计算
            case ITEM_UNLIMIT: // 随发分批
                for (JYCK jyck : order.getJycks()) {
                    // 按照doId单号查询
                    List<OpsDeliveryPlanResult> jyckResults = selectDeliveryPlanResultsByDoId(jyck.getJyDoId());
                    list.add(jyckResults);
                }
                break;
        }
        return list;
    }


    // 按照七位单号查询交付计划结果
    private List<OpsDeliveryPlanResult> selectDeliveryPlanResultsByOrderNo(String orderId) {
        OpsDeliveryPlanResultExample e = new OpsDeliveryPlanResultExample();
        e.createCriteria().andDelflagEqualTo(0).andCurrentValidEqualTo(1)
                .andOrderIdEqualTo(orderId);
        List<OpsDeliveryPlanResult> results = opsDeliveryPlanResultMapper.selectByExample(e);
        return results;
    }

    // 按照十位单号查询交付计划结果
    @Override
    public List<OpsDeliveryPlanResult> selectDeliveryPlanResultsByOrderNo(String orderId, String orderItem) {
        OpsDeliveryPlanResultExample e = new OpsDeliveryPlanResultExample();
        e.createCriteria().andDelflagEqualTo(0).andCurrentValidEqualTo(1)
                .andOrderIdEqualTo(orderId)
                .andOrderItemEqualTo(orderItem);
        List<OpsDeliveryPlanResult> results = opsDeliveryPlanResultMapper.selectByExample(e);
        return results;
    }

    // 按照交易出库单号查询交付计划结果
    private List<OpsDeliveryPlanResult> selectDeliveryPlanResultsByDoId(String doId) {
        OpsDeliveryPlanResultExample e = new OpsDeliveryPlanResultExample();
        e.createCriteria().andDelflagEqualTo(0).andCurrentValidEqualTo(1)
                .andDoIdEqualTo(doId);
        return opsDeliveryPlanResultMapper.selectByExample(e);
    }


    // 取最晚的日期
    private Date calculatePlanDeliveryDay(List<OpsDeliveryPlanResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return null;
        }
        return results.stream()
                .map(OpsDeliveryPlanResult::getExpectDeliveryDay)
                .filter(Objects::nonNull)
                .max(Date::compareTo).get();
    }


    private Date getPlanDeliveryDay(List<OpsDeliveryPlanResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return null;
        }
        return results.stream()
                .map(OpsDeliveryPlanResult::getPlanDeliveryDay)
                .filter(Objects::nonNull)
                .max(Date::compareTo).get();
    }


    private int updateDeliveryPlanResultsById(List<OpsDeliveryPlanResult> results, Date planDeliveryDay) {
        if (planDeliveryDay == null) {
            return 0;
        }
        //id排序，单条更新
        results.sort(Comparator.comparing(OpsDeliveryPlanResult::getId));
        for (OpsDeliveryPlanResult result : results) {
            OpsDeliveryPlanResult update = new OpsDeliveryPlanResult();
            update.setId(result.getId());
            update.setPlanDeliveryDay(planDeliveryDay);
            opsDeliveryPlanResultMapper.updateByPrimaryKeySelective(update);
        }
        return results.size();
    }


}
