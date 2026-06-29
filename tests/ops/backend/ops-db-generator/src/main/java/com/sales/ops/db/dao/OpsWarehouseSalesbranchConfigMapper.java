package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsWarehouseSalesbranchConfigMapper {
    long countByExample(OpsWarehouseSalesbranchConfigExample example);

    int deleteByExample(OpsWarehouseSalesbranchConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsWarehouseSalesbranchConfig record);

    int insertSelective(OpsWarehouseSalesbranchConfig record);

    List<OpsWarehouseSalesbranchConfig> selectByExample(OpsWarehouseSalesbranchConfigExample example);

    OpsWarehouseSalesbranchConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsWarehouseSalesbranchConfig record, @Param("example") OpsWarehouseSalesbranchConfigExample example);

    int updateByExample(@Param("record") OpsWarehouseSalesbranchConfig record, @Param("example") OpsWarehouseSalesbranchConfigExample example);

    int updateByPrimaryKeySelective(OpsWarehouseSalesbranchConfig record);

    int updateByPrimaryKey(OpsWarehouseSalesbranchConfig record);
}