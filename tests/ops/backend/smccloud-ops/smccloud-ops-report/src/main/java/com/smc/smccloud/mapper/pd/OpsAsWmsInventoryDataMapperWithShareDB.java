package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsWmsInventoryDataDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lyc
 * @Date 2025/3/28 9:58
 * @Descripton TODO
 */
@Mapper
@DS("sharedb")
public interface OpsAsWmsInventoryDataMapperWithShareDB extends BaseMapper<OpsAsWmsInventoryDataDO> {
}
