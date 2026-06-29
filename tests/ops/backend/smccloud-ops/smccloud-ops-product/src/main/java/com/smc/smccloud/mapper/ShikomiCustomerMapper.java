package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.shikomi.ShikomiCustomerDO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
@DS("opsdb")
public interface ShikomiCustomerMapper extends BaseMapper<ShikomiCustomerDO> {


}
