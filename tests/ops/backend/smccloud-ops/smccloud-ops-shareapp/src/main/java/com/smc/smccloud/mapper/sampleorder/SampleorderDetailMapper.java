package com.smc.smccloud.mapper.sampleorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.sampleorder.SampleorderDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-10-27
 */
@Mapper
@DS("sharedb")
public interface SampleorderDetailMapper extends BaseMapper<SampleorderDetailDO> {

}
