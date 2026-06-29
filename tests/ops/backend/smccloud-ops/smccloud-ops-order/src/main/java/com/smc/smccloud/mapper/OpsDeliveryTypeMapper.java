package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.deliveryType.OpsDeliveryTypeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-05-05
 */
@Mapper
@DS("opsdb")
public interface OpsDeliveryTypeMapper extends BaseMapper<OpsDeliveryTypeDO> {

}
