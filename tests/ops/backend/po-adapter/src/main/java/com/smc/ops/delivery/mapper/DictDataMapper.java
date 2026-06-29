package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.ops.delivery.model.DataTypeDO;
import org.apache.ibatis.annotations.Mapper;


@DS("opscmm")
@Mapper
public interface DictDataMapper extends BaseMapper<DataTypeDO> {

}
