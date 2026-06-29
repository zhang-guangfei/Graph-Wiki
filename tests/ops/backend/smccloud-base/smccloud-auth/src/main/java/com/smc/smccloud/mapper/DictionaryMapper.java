package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.dict.Dictionary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("opscmm")
public interface DictionaryMapper extends BaseMapper<Dictionary> {
}
