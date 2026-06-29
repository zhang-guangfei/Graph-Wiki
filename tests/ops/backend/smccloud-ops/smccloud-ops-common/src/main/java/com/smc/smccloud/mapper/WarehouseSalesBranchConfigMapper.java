package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OpsWarehouseSalesBranchConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wuweidong
 * @create 2023/6/8 10:17
 * @description
 */
@DS("opsdb")
@Mapper
public interface WarehouseSalesBranchConfigMapper extends BaseMapper<OpsWarehouseSalesBranchConfigDO> {
}
