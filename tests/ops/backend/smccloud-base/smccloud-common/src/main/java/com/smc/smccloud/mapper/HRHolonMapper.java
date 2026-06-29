package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.HRHolonDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: B90034
 * Date: 2022-04-13 09:43
 * Description:
 */
@DS("opsdb")
@Mapper
public interface HRHolonMapper extends BaseMapper<HRHolonDO> {
}
