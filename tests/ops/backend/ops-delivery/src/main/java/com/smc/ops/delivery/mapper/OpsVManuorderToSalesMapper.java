package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.ops.delivery.model.ips.OpsVManuorderToSales;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("cmdb")
public interface OpsVManuorderToSalesMapper extends BaseMapper<OpsVManuorderToSales> {
}