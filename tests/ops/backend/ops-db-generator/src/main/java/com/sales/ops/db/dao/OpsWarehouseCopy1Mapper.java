package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsWarehouseCopy1;
import com.sales.ops.db.entity.OpsWarehouseCopy1Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsWarehouseCopy1Mapper {
    long countByExample(OpsWarehouseCopy1Example example);

    int deleteByExample(OpsWarehouseCopy1Example example);

    int deleteByPrimaryKey(String warehouseCode);

    int insert(OpsWarehouseCopy1 record);

    int insertSelective(OpsWarehouseCopy1 record);

    List<OpsWarehouseCopy1> selectByExample(OpsWarehouseCopy1Example example);

    OpsWarehouseCopy1 selectByPrimaryKey(String warehouseCode);

    int updateByExampleSelective(@Param("record") OpsWarehouseCopy1 record, @Param("example") OpsWarehouseCopy1Example example);

    int updateByExample(@Param("record") OpsWarehouseCopy1 record, @Param("example") OpsWarehouseCopy1Example example);

    int updateByPrimaryKeySelective(OpsWarehouseCopy1 record);

    int updateByPrimaryKey(OpsWarehouseCopy1 record);
}