package com.sales.ops.serviceimpl.order;


import com.sales.ops.db.entity.*;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.service.inventory.AdjustInventoryService;
import com.sales.ops.service.order.CreateTransferPlanService;
import com.sales.ops.service.po.BasePoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * @author C14717
 * @date 2025/5/20 10:56
 */
@Slf4j
@Service
public class CreateTransferPlanServiceImpl implements CreateTransferPlanService {

    @Autowired
    private AdjustInventoryService adjustInventoryService;
    @Autowired
    private BasePoService basePoService;

    /**
     * bugid:17697 c14717 2025-05-20
     * @param purchaseList
     * @param userName
     * @param endUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    public Boolean createTransferPlan(List<PurchaseInfoForCancel> purchaseList, String userName, String endUser) {
        Boolean result = null;
        for (PurchaseInfoForCancel info : purchaseList) {
            //bugid:14473 c14717 20240624
            // 12625 如果勾选了调库计划，则创建
            if (!info.isTransfer()) {
                continue;
            }
            OpsRequestpurchase requestPurchase = basePoService.getRequestPurchase(info.getRequestno(), info.getRequestItemno(), info.getRequestSplitno());
            if(Objects.nonNull(requestPurchase)){
                OpsPurchaseorder purchaseorder = new OpsPurchaseorder();
                purchaseorder.setOrderno(info.getPurchaseno());
                purchaseorder.setItemno(info.getPurchaseItemno());
                purchaseorder.setSplititemno(info.getPurchaseSplitno());
                purchaseorder.setMergeflag(info.isMerge());
                try {
                    // bugid:14473 c14717 20240624
                    if(StringUtils.isNotBlank(info.getEndUser())){
                        endUser = info.getEndUser();
                    }
                    //如果采购已完成，执行调库 需要判断是否有调拨单
                    if(StringUtils.equals(info.getStatus(), PurchaseOrderStatusEnum.YIWANCHENG)){
                         adjustInventoryService.handlePOFinishTransferPlan(requestPurchase, purchaseorder, endUser, userName);
                         result = true;
                    }else {
                        adjustInventoryService.createTransferPlanForDelOrderPage(requestPurchase, purchaseorder, endUser, userName,null);
                        result = true;
                    }
                } catch (Exception e) {
                    log.error("创建调库计划异常",e);
                    return false;
                }
            }else {
                log.info("创建调库计划-无采购信息");
            }
        }
        return result;
    }
}
