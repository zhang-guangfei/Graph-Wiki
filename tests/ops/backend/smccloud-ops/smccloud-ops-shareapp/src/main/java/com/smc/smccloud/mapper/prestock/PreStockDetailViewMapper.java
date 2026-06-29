package com.smc.smccloud.mapper.prestock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.prestock.PreStockDetailView;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: B90034
 * Date: 2022-02-06 15:15
 * Description:
 */
@DS("shareapp")
@Mapper
public interface PreStockDetailViewMapper extends BaseMapper<PreStockDetailView> {
}
