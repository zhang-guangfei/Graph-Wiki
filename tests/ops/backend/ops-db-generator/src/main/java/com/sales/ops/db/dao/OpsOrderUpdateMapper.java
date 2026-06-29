package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderUpdate;
import com.sales.ops.db.entity.OpsOrderUpdateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderUpdateMapper {
    long countByExample(OpsOrderUpdateExample example);

    int deleteByExample(OpsOrderUpdateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsOrderUpdate record);

    int insertSelective(OpsOrderUpdate record);

    List<OpsOrderUpdate> selectByExample(OpsOrderUpdateExample example);

    OpsOrderUpdate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsOrderUpdate record, @Param("example") OpsOrderUpdateExample example);

    int updateByExample(@Param("record") OpsOrderUpdate record, @Param("example") OpsOrderUpdateExample example);

    int updateByPrimaryKeySelective(OpsOrderUpdate record);

    int updateByPrimaryKey(OpsOrderUpdate record);
}