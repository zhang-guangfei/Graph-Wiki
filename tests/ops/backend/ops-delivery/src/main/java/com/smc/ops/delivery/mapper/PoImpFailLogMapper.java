package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.ops.delivery.model.poImps.OpsImpinvoiceFailLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:imp同步的异常表，用于处理记录异常数据
 */
@Mapper
@DS("opsdb")
public interface PoImpFailLogMapper extends BaseMapper<OpsImpinvoiceFailLogDO> {

}
