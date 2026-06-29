package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsResult;
import com.sales.ops.db.entity.OpsResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsResultMapper {
    long countByExample(OpsResultExample example);

    int deleteByExample(OpsResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsResult record);

    int insertSelective(OpsResult record);

    List<OpsResult> selectByExample(OpsResultExample example);

    OpsResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsResult record, @Param("example") OpsResultExample example);

    int updateByExample(@Param("record") OpsResult record, @Param("example") OpsResultExample example);

    int updateByPrimaryKeySelective(OpsResult record);

    int updateByPrimaryKey(OpsResult record);
}