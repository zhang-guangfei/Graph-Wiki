package com.smc.smccloud.mapper.stockassembly;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.stockassembly.StockAssemblyDetailView;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: B90034
 * Date: 2022-01-18 13:58
 * Description:
 */
@DS("sharedb")
@Mapper
public interface StockAssemblyDetailViewMapper extends BaseMapper<StockAssemblyDetailView> {
}
