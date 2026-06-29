package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCountgapWms;
import com.sales.ops.db.entity.OpsCountgapWmsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCountgapWmsMapper {
    long countByExample(OpsCountgapWmsExample example);

    int deleteByExample(OpsCountgapWmsExample example);

    int insert(OpsCountgapWms record);

    int insertSelective(OpsCountgapWms record);

    List<OpsCountgapWms> selectByExample(OpsCountgapWmsExample example);

    int updateByExampleSelective(@Param("record") OpsCountgapWms record, @Param("example") OpsCountgapWmsExample example);

    int updateByExample(@Param("record") OpsCountgapWms record, @Param("example") OpsCountgapWmsExample example);
}