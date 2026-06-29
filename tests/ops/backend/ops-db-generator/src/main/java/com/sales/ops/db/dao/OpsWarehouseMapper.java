package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.OpsWarehouseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsWarehouseMapper {
    long countByExample(OpsWarehouseExample example);

    int deleteByExample(OpsWarehouseExample example);

    int deleteByPrimaryKey(String warehouseCode);

    int insert(OpsWarehouse record);

    int insertSelective(OpsWarehouse record);

    List<OpsWarehouse> selectByExample(OpsWarehouseExample example);

    OpsWarehouse selectByPrimaryKey(String warehouseCode);

    int updateByExampleSelective(@Param("record") OpsWarehouse record, @Param("example") OpsWarehouseExample example);

    int updateByExample(@Param("record") OpsWarehouse record, @Param("example") OpsWarehouseExample example);

    int updateByPrimaryKeySelective(OpsWarehouse record);

    int updateByPrimaryKey(OpsWarehouse record);
}