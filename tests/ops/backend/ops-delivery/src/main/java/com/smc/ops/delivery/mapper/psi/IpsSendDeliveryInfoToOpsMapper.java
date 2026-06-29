package com.smc.ops.delivery.mapper.psi;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.ops.delivery.model.ips.IpsSendDeliveryInfoToOpsDO;


/**
 *
 * @author smc
 * @since 2025-12-02
 */
@DS("ips_sharedb")
public interface IpsSendDeliveryInfoToOpsMapper extends BaseMapper<IpsSendDeliveryInfoToOpsDO> {

}
