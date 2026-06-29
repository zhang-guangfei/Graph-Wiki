package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.SalesDataModiDataDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author C18117
 * @title: SalesDataModiDataMapper
 * @date 2022/10/28 09:28
 */
@Mapper
@DS("smcdb30")
public interface SalesDataModiDataMapper extends BaseMapper<SalesDataModiDataDO> {
}
