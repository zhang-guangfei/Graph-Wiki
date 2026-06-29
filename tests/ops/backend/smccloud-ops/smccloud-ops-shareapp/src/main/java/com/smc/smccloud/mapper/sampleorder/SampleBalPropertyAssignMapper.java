package com.smc.smccloud.mapper.sampleorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.sampleorder.SampleBalPropertyAssignDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2025-01-06
 */
@Mapper
@DS("opsdb")
public interface SampleBalPropertyAssignMapper extends BaseMapper<SampleBalPropertyAssignDO> {

}
