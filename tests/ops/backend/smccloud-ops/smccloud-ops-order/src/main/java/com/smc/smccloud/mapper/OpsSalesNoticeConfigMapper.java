package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.notice.OpsSalesNoticeConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-07-12
 */
@Mapper
@DS("sharedb")
public interface OpsSalesNoticeConfigMapper extends BaseMapper<OpsSalesNoticeConfigDO> {

}
