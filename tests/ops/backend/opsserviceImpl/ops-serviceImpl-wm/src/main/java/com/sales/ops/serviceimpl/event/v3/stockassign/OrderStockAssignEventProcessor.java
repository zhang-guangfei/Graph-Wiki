package com.sales.ops.serviceimpl.event.v3.stockassign;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsOrderAssignResult;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.dto.order.ResultForOrderAdjustDto;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.event.publisher.enums.EventTypeEnum;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class OrderStockAssignEventProcessor {


    private OpsOrderAssignResultService opsOrderAssignResultService;
    private CustomerEventPublisher customerEventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public void handle(OrderEvent event) throws OpsException {
        EventTypeEnum EventType = EventSourceEnum.parse(event.getEventCode());
        EventSourceEnum eventSource = (EventSourceEnum) EventType;
        String params = event.getParams();
        switch (eventSource) {
            case CUSTOMER_ORDER_ALLOT:// 订单分配结果
                Date allotStartTime = null;
                Date allotEndTime = null;
                List<OpsOrderAssignResult> list =new ArrayList<>();
                try {
                    //map.put("allotStartTime", allotStartTime);
                    //map.put("allotEndTime", allotEndTime);
                    //map.put("assignResult", list);
                    Map<String, Object> map = JSONUtil.toBean(params, Map.class);
                    Long allotStartTimeLong = (Long)map.get("allotStartTime");
                    Long allotEndTimeLong = (Long)map.get("allotEndTime");
                    allotStartTime = DateUtil.date(allotStartTimeLong);
                    allotEndTime = DateUtil.date(allotEndTimeLong);
                    list = JSONUtil.toList(JSONUtil.parseArray(map.get("assignResult")), OpsOrderAssignResult.class);
                } catch (Exception e) {
                    //如果没解析成功，则原样解析
                    list = JSONUtil.toList(JSONUtil.parseArray(params), OpsOrderAssignResult.class);
                    log.error("ops_event_stock_assign 报文解析失败，params:{}", params,e);
                }
                opsOrderAssignResultService.insertForOrderAllot(list, allotStartTime, allotEndTime);
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_ALLOT_AFTER, event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case CUSTOMER_ORDER_PURCHASE_SEND:// 采购已发单
                OpsPurchaseorder po = JSONUtil.toBean(params, OpsPurchaseorder.class);
                opsOrderAssignResultService.updateForPoSend(event.getOrderId(), Integer.valueOf(event.getOrderItem()), po);
                break;
            case CUSTOMER_ORDER_PURCHASE_RECEIVE:// 采购接单
            case CUSTOMER_ORDER_PURCHASE_SUPPLIER:// 采购更新供应商
            case CUSTOMER_ORDER_PURCHASE_UPDATE:// 采购更新
                OpsPurchaseorder po2 = JSONUtil.toBean(params, OpsPurchaseorder.class);
                opsOrderAssignResultService.updateForPoUpdateSupplier(event.getOrderId(), Integer.valueOf(event.getOrderItem()),  po2);
                break;
            case CUSTOMER_ORDER_ADJUST:// 转订分配结果
                ResultForOrderAdjustDto result = JSONUtil.toBean(params, ResultForOrderAdjustDto.class);
                opsOrderAssignResultService.updateResultForOrderAdjustNew(event.getOrderId(), event.getOrderItem(), result);
                break;
            case CUSTOMER_ORDER_FINISH:// 完纳
                opsOrderAssignResultService.updateForOrderForceFinished(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
        }
    }


}

