package com.sales.ops.service.purchase;

import java.util.List;
import java.util.Map;

import com.sales.ops.dto.purchase.ModifyPurchaseDto;

public interface PurchaseModifyService {

    // 针对日本采购单修改申请返回的数据，因此运输方式不包含陆运
    List<ModifyPurchaseDto> getPurchase(List<String> orderNos, String businessCode);

}
