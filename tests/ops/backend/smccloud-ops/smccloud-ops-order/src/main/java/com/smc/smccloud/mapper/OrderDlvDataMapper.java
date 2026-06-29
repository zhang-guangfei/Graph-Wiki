package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OrderSales.OrderDlvDataDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: B90034
 * Date: 2022-01-12 08:59
 * Description:
 */

@Mapper
@DS("opsdb")
public interface OrderDlvDataMapper extends BaseMapper<OrderDlvDataDO> {
}
