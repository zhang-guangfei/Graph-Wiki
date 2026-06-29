package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.StockLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author C18117
 * @title: StockLogMapper
 * @date 2022/11/22 14:31
 */
@Mapper
@DS("reportdb")
public interface StockLogMapper extends BaseMapper<StockLogDO> {
}
