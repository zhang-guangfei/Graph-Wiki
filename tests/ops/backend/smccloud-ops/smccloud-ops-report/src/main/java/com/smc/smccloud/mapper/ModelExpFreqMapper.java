package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.ModelExpFreqDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author C18117
 * @title: ModelExpFreqMapper
 * @date 2022/11/29 09:59
 */
@Mapper
@DS("reportdb")
public interface ModelExpFreqMapper extends BaseMapper<ModelExpFreqDO> {
}
