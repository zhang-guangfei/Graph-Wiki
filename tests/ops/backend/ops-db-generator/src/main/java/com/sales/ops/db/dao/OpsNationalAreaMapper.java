package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsNationalArea;
import com.sales.ops.db.entity.OpsNationalAreaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsNationalAreaMapper {
    long countByExample(OpsNationalAreaExample example);

    int deleteByExample(OpsNationalAreaExample example);

    int insert(OpsNationalArea record);

    int insertSelective(OpsNationalArea record);

    List<OpsNationalArea> selectByExample(OpsNationalAreaExample example);

    int updateByExampleSelective(@Param("record") OpsNationalArea record, @Param("example") OpsNationalAreaExample example);

    int updateByExample(@Param("record") OpsNationalArea record, @Param("example") OpsNationalAreaExample example);
}