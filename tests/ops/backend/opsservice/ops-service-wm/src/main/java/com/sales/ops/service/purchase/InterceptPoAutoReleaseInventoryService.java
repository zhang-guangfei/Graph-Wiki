package com.sales.ops.service.purchase;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.po.InterceptPoAutoReleaseInvDto;
import com.sales.ops.dto.po.PoOrderInfoDto;

import java.util.List;

/**
 * @author ：C14717
 * @version: $  17646
 * @description：采购拦截订单自动出库存，整单还原，拆分转定；
 * @date ：Created in 2025/5/26 9:24
 */
public interface InterceptPoAutoReleaseInventoryService {
    int handleDataDetail(InterceptPoAutoReleaseInvDto obj) throws OpsException;

    List<PoOrderInfoDto> getBasePoList();

    List<InterceptPoAutoReleaseInvDto> checkPoAndDoRelation(List<PoOrderInfoDto> poOrderInfoDtoList) throws OpsException;
}
