package com.smc.smccloud.service;

import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import com.sales.ops.dto.util.CommonResult;

import java.util.List;

/**
 * @author B91717
 * @date 2022/5/9
 * @apiNote
 */
public interface PurchaseIpsOrderService {

    /**
     * 广州制造订单写入
     *
     * @return
     */
    CommonResult<String> insertIpsOrder(List<IpsReceiveOrderAllOriginalInfoDto> ipsReceiveOrderAllOriginalInfoDtos) throws Exception ;


}
