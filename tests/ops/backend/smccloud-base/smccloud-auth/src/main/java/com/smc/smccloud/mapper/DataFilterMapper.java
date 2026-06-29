package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.authority.DataFilter;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("opscmm")
public interface DataFilterMapper extends BaseMapper<DataFilter> {

}
