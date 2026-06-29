package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.ops.delivery.model.ips.IpsReceiveDeliveryInfoFromOpsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-12-25
 */
@DS("ips_sharedb")
@Mapper
public interface IpsReceiveDeliveryInfoFromOpsMapper extends BaseMapper<IpsReceiveDeliveryInfoFromOpsDO> {

}
