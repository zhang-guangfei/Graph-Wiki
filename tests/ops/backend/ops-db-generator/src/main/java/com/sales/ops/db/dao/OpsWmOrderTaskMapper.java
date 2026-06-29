package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.db.entity.OpsWmOrderTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsWmOrderTaskMapper {
    long countByExample(OpsWmOrderTaskExample example);

    int deleteByExample(OpsWmOrderTaskExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsWmOrderTask record);

    int insertSelective(OpsWmOrderTask record);

    List<OpsWmOrderTask> selectByExample(OpsWmOrderTaskExample example);

    OpsWmOrderTask selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsWmOrderTask record, @Param("example") OpsWmOrderTaskExample example);

    int updateByExample(@Param("record") OpsWmOrderTask record, @Param("example") OpsWmOrderTaskExample example);

    int updateByPrimaryKeySelective(OpsWmOrderTask record);

    int updateByPrimaryKey(OpsWmOrderTask record);
}