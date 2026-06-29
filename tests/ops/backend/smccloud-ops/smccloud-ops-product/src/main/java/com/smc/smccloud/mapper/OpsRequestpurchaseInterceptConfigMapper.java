package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.interceptConfig.OpsRequestpurchaseInterceptConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-09-13
 */
@DS("opsdb")
@Mapper
public interface OpsRequestpurchaseInterceptConfigMapper extends BaseMapper<OpsRequestpurchaseInterceptConfigDO> {

}
