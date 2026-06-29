package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.Manufacture.OpsTModelAvailableToSales;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author edp04
 * @title: OpsTModelAvailableToSalesMapper
 * @date 2022/04/28 11:52
 */
@Mapper
@DS("cmdb")
public interface OpsTModelAvailableToSalesMapper extends BaseMapper<OpsTModelAvailableToSales> {
}
