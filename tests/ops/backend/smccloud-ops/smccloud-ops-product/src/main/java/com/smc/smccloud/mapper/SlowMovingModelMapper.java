package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingModelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

@Mapper
public interface SlowMovingModelMapper extends BaseMapper<SlowMovingModelDO> {

    @Select("{ call dbo.updateSlowModelOnHnad }")
    @Options(statementType = StatementType.CALLABLE)
    void calcSlowModel();
}
