package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.DataTypeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/8/20 14:17
 */
@DS("opscmm")
@Mapper
public interface DataTypeMapper  extends BaseMapper<DataTypeDO> {
}
