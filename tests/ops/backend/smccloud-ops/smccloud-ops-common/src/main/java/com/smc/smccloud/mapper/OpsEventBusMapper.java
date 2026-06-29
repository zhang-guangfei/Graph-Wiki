package com.smc.smccloud.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OpsEventBusDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author edp04
 * @title: OpsEventBusMapper
 * @date 2022/07/20 08:39
 */
@Mapper
@DS("opsdb")
public interface OpsEventBusMapper extends BaseMapper<OpsEventBusDO> {
}
