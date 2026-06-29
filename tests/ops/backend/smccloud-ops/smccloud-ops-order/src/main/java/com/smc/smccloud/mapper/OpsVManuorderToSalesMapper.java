package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.VSalesManuorder.OpsVManuorderToSales;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("cmdb")
public interface OpsVManuorderToSalesMapper extends BaseMapper<OpsVManuorderToSales> {
}
