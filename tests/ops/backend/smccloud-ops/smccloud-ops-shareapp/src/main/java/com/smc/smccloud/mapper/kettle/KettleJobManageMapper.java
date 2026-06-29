package com.smc.smccloud.mapper.kettle;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.kettle.KettleJobManageDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lyc
 * @Date 2023/4/21 15:27
 * @Descripton TODO
 */
@DS("kettledb")
@Mapper
public interface KettleJobManageMapper extends BaseMapper<KettleJobManageDO> {



}
