package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.order.RcvMasterDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lyc
 * @Date 2022/8/27 10:00
 * @Descripton TODO
 */
@DS("opsdb")
@Mapper
public interface RcvMasterReadOnlyMapper extends BaseMapper<RcvMasterDO> {
}
