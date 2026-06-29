package com.smc.smccloud.log.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.log.model.OpsSysLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 日志表 Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-05-29
 */
@Mapper
@DS("opslog")
public interface OpsSysLogMapper extends BaseMapper<OpsSysLogDO> {

}
