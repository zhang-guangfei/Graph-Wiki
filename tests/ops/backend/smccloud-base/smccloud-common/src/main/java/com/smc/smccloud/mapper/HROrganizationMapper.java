package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.HROrganizationDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: B90034
 * Date: 2022-04-29 16:13
 * Description:
 */
@DS("opscmm")
@Mapper
public interface HROrganizationMapper extends BaseMapper<HROrganizationDO> {
}
