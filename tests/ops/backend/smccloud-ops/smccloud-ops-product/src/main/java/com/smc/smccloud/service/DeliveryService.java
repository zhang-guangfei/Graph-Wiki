package com.smc.smccloud.service;

import com.sales.ops.dto.eta.FindETADataDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.delivery.CanUseQtyAndIsBinVO;
import com.smc.smccloud.model.delivery.DeliveryInfo;

import java.util.List;

public interface DeliveryService {

	// 获取参考货期
	DeliveryInfo getDeliveryDay(DeliveryInfo infos);


    // 参考货期->获取是否BIN和可用库存
    ResultVo<List<CanUseQtyAndIsBinVO>> getDeliveryIsBInAndCanUseQty(List<FindETADataDto> dataList);
}
