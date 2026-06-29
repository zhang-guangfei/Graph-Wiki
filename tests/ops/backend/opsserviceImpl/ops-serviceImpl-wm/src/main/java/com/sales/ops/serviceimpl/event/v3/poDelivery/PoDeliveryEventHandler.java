package com.sales.ops.serviceimpl.event.v3.poDelivery;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.PurchaseStatusRule;
import com.sales.ops.db.extdao.PoDeliverQueryDao;
import com.sales.ops.dto.poDeliver.PoDeliverQueryDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoDeliveryEventHandler {


    @Autowired
    private PoDeliverQueryService poDeliverQueryService;
    @Autowired
    private PoDeliverQueryDao poDeliverQueryDao;


    public PoDeliverQueryDto getDeliverInfoByOrderNo(String orderNo, Integer itemno, Integer splitno) throws OpsException {
        if (splitno != null && splitno == 0) {
            splitno = null;
        }
        //查询交付信息
        PoDeliverQueryDto deliverInfoByOrderNo = poDeliverQueryService.getDeliverInfoByOrderNo(orderNo, itemno, splitno);
        //查询匹配规则
        List<PurchaseStatusRule> purchaseStatusRules = poDeliverQueryDao.selectPurchaseStatusRule();
        //创建规则引擎
        PurchaseStatusEngine purchaseStatusEngine = new PurchaseStatusEngine(purchaseStatusRules);
        //执行规则引擎，生成详细状态和状态描述
        purchaseStatusEngine.execute(deliverInfoByOrderNo);
        if (deliverInfoByOrderNo.getDetailStatusCode() != null || StringUtils.isNotBlank(deliverInfoByOrderNo.getStatusDescription())) {
            // 更新状态信息
            poDeliverQueryDao.updatePurchaseInvoieDetailStatusAndStatusDesc(orderNo, itemno, splitno,
                    deliverInfoByOrderNo.getDetailStatusCode(), deliverInfoByOrderNo.getStatusDescription());
        }
        return deliverInfoByOrderNo;
    }

}
