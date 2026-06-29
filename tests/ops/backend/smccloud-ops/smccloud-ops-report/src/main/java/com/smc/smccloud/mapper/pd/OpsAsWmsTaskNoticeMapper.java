package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsWmsTaskNoticeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-06-28
 */
@Mapper
@DS("sharedb")
public interface OpsAsWmsTaskNoticeMapper extends BaseMapper<OpsAsWmsTaskNoticeDO> {

}
