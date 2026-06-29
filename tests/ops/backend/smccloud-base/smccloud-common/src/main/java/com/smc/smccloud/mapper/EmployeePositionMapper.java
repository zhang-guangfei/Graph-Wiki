package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.EmployeePositionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wuweidong
 * @create 2023/12/12 08:51
 * @description
 */
@Mapper
@DS("opscmm")
public interface EmployeePositionMapper extends BaseMapper<EmployeePositionDO> {
}
