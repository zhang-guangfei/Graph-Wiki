package com.smc.smccloud.mapper.sampleorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.sampleorder.SampleBalApplyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-10-25
 */
@DS("sharedb")
@Mapper
public interface SampleBalApplyMapper extends BaseMapper<SampleBalApplyDO> {

}
