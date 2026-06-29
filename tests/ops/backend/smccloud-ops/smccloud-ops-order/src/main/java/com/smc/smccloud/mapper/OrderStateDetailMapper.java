package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.orderstate.OrderStateDetailDO;
import org.apache.ibatis.annotations.Mapper;

@DS("opsdb")
@Mapper
public interface OrderStateDetailMapper extends BaseMapper<OrderStateDetailDO> {
}
