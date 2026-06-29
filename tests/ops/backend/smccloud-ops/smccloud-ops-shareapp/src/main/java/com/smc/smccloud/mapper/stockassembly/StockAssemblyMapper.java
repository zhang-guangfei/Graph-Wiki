package com.smc.smccloud.mapper.stockassembly;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.stockassembly.StockAssemblyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: B90034
 * Date: 2021-09-27 12:16
 * Description:
 */
@DS("sharedb")
@Mapper
public interface StockAssemblyMapper extends BaseMapper<StockAssemblyDO> {
}
