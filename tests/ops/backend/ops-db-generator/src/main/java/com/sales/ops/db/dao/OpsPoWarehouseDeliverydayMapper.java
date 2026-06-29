package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoWarehouseDeliveryday;
import com.sales.ops.db.entity.OpsPoWarehouseDeliverydayExample;
import com.sales.ops.db.entity.OpsPoWarehouseDeliverydayKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoWarehouseDeliverydayMapper {
    long countByExample(OpsPoWarehouseDeliverydayExample example);

    int deleteByExample(OpsPoWarehouseDeliverydayExample example);

    int deleteByPrimaryKey(OpsPoWarehouseDeliverydayKey key);

    int insert(OpsPoWarehouseDeliveryday record);

    int insertSelective(OpsPoWarehouseDeliveryday record);

    List<OpsPoWarehouseDeliveryday> selectByExample(OpsPoWarehouseDeliverydayExample example);

    OpsPoWarehouseDeliveryday selectByPrimaryKey(OpsPoWarehouseDeliverydayKey key);

    int updateByExampleSelective(@Param("record") OpsPoWarehouseDeliveryday record, @Param("example") OpsPoWarehouseDeliverydayExample example);

    int updateByExample(@Param("record") OpsPoWarehouseDeliveryday record, @Param("example") OpsPoWarehouseDeliverydayExample example);

    int updateByPrimaryKeySelective(OpsPoWarehouseDeliveryday record);

    int updateByPrimaryKey(OpsPoWarehouseDeliveryday record);
}