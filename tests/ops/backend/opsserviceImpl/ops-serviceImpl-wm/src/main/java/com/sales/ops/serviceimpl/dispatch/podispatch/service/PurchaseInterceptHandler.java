package com.sales.ops.serviceimpl.dispatch.podispatch.service;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import com.sales.ops.utils.PoNoUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PurchaseInterceptHandler {

    private OrderStateService orderStateService;
    private BasePoService basePoService;
    private PurchaseEventPublisher purchaseEventPublisher;

    /**
     * @description：请购拦截回执
     * @author C12961
     * @date 2022/2/25 13:09
     */
    public void interceptForRequestPo(List<OpsRequestpurchase> list) throws OpsException {
        for (OpsRequestpurchase dto : list) {
            String poFullNo = PoNoUtil.getFullRePoNo(dto);
            log.info("请购拦截：{}", poFullNo);
            if (RequestPurchaseStatusEnum.SHIKOMILANJIE.equals(dto.getStatecode())
                    || RequestPurchaseStatusEnum.LANJIE.equals(dto.getStatecode())) {
                log.info("发送交货期消息：{}", JSONUtil.toJsonPrettyStr(dto));
                //1.发事件
                purchaseEventPublisher.requestPurchaseOrderEvent(EventSourceEnum.REQUEST_INTERCEPT, dto);
                //2.发MQ
                orderStateService.sendOrderStateForInterceptPurchase(dto);
            }
        }
    }

    /**
     * @description：请购拦截回执
     * @author C12961
     * @date 2022/2/25 13:09
     */
    public void releaseForRequestPo(List<OpsRequestpurchase> list) throws OpsException {
        for (OpsRequestpurchase dto : list) {
            String poFullNo = PoNoUtil.getFullRePoNo(dto);
            log.info("请购拦截：{}", poFullNo);
            if (RequestPurchaseStatusEnum.SHIKOMILANJIE.equals(dto.getStatecode())
                    || RequestPurchaseStatusEnum.LANJIE.equals(dto.getStatecode())) {
                log.info("发送交货期消息：{}", JSONUtil.toJsonPrettyStr(dto));
                purchaseEventPublisher.requestPurchaseOrderEvent(EventSourceEnum.REQUEST_RELEASE, dto);
                orderStateService.sendOrderStateForInterceptPurchase(dto);
            }
        }
    }


}
