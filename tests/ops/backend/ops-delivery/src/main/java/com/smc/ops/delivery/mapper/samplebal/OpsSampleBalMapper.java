package com.smc.ops.delivery.mapper.samplebal;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.ops.delivery.model.samplebal.OpsSamplebalDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 */
@Mapper
@DS("opsdb")
public interface OpsSampleBalMapper extends BaseMapper<OpsSamplebalDO> {


}
