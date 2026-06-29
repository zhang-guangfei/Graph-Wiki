package com.smc.ops.delivery.service.psi;

import com.sales.ops.dto.ips.IpsSendDeliveryInfoToOpsDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;


/**
 *
 * @author smc
 * @since 2025-12-02
 */

public interface IpsSendDeliveryInfoToOpsService  {

    ResultVo<List<IpsSendDeliveryInfoToOpsDto>> queryIpsSendDeliveryInfoToOpsList();

}
