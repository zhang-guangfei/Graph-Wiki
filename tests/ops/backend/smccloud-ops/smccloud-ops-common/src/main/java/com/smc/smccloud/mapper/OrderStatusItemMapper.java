package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OrderStatusItemDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 接单查询需要的实时查询状态 Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-11-19
 */
@Mapper
@DS("opsdb")
public interface OrderStatusItemMapper extends BaseMapper<OrderStatusItemDO> {

}
