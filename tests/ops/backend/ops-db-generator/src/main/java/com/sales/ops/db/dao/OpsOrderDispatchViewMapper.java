package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderDispatchView;
import com.sales.ops.db.entity.OpsOrderDispatchViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderDispatchViewMapper {
    long countByExample(OpsOrderDispatchViewExample example);

    int deleteByExample(OpsOrderDispatchViewExample example);

    int insert(OpsOrderDispatchView record);

    int insertSelective(OpsOrderDispatchView record);

    List<OpsOrderDispatchView> selectByExample(OpsOrderDispatchViewExample example);

    int updateByExampleSelective(@Param("record") OpsOrderDispatchView record, @Param("example") OpsOrderDispatchViewExample example);

    int updateByExample(@Param("record") OpsOrderDispatchView record, @Param("example") OpsOrderDispatchViewExample example);
}