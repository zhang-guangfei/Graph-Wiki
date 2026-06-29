package com.smc.smccloud.mapper.salesInvoice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.salesinvoice.SalesImpAgentDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author edp04
 * @title: SalesImpAgentMapper
 * @date 2022/07/16 11:24
 */
@Mapper
@DS("costdb")
public interface SalesImpAgentMapper extends BaseMapper<SalesImpAgentDO> {
}
