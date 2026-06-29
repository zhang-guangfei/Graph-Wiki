package com.sales.ops.serviceimpl.event.v3.purchase;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.purchase.PoReplyInfoDto;
import com.sales.ops.dto.purchase.PurchaseCancelEventParam;

public interface OpsPurchaseOrderEventService {


    void cancelPurchaseOrder(String poNo, Integer poItem, Integer poSplitNo, PurchaseCancelEventParam param) throws OpsException;

    void purchaseReplyDate(String poNo, Integer poItem, Integer poSplitNo, PoReplyInfoDto replyInfo) throws OpsException;

    void purchaseDelayDate(String poNo, Integer poItem, Integer poSplitNo, PoReplyInfoDto delayInfo) throws OpsException;
}
