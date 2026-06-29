package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OrderStatusDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 接单查询需要的实时查询状态 Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2025-05-06
 */
@DS("opsdb")
@Mapper
public interface OrderStatusMapper extends BaseMapper<OrderStatusDO> {

}
