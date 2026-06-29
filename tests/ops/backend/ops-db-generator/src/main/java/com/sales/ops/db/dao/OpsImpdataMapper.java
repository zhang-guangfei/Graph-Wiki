package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsImpdata;
import com.sales.ops.db.entity.OpsImpdataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsImpdataMapper {
    long countByExample(OpsImpdataExample example);

    int deleteByExample(OpsImpdataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsImpdata record);

    int insertSelective(OpsImpdata record);

    List<OpsImpdata> selectByExample(OpsImpdataExample example);

    OpsImpdata selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsImpdata record, @Param("example") OpsImpdataExample example);

    int updateByExample(@Param("record") OpsImpdata record, @Param("example") OpsImpdataExample example);

    int updateByPrimaryKeySelective(OpsImpdata record);

    int updateByPrimaryKey(OpsImpdata record);
}