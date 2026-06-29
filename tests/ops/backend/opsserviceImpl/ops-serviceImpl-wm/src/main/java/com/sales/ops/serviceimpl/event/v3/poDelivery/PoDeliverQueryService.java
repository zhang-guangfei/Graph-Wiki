package com.sales.ops.serviceimpl.event.v3.poDelivery;

import com.sales.ops.dto.poDeliver.PoDeliverQueryDto;

public interface PoDeliverQueryService {
    PoDeliverQueryDto getDeliverInfoByOrderNo(String orderno, Integer itemno, Integer splitno);
}
