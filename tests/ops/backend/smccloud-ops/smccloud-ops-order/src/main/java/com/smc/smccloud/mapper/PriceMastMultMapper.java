package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.PriceMastMultDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author edp04
 * @title: PriceMastMultMapper
 * @date 2022/10/24 10:17
 */
@Mapper
@DS("smcdb30")
public interface PriceMastMultMapper extends BaseMapper<PriceMastMultDO> {
}
