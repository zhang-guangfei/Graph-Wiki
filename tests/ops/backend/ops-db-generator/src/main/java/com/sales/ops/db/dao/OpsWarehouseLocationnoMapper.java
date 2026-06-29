package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsWarehouseLocationno;
import com.sales.ops.db.entity.OpsWarehouseLocationnoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsWarehouseLocationnoMapper {
    long countByExample(OpsWarehouseLocationnoExample example);

    int deleteByExample(OpsWarehouseLocationnoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsWarehouseLocationno record);

    int insertSelective(OpsWarehouseLocationno record);

    List<OpsWarehouseLocationno> selectByExample(OpsWarehouseLocationnoExample example);

    OpsWarehouseLocationno selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsWarehouseLocationno record, @Param("example") OpsWarehouseLocationnoExample example);

    int updateByExample(@Param("record") OpsWarehouseLocationno record, @Param("example") OpsWarehouseLocationnoExample example);

    int updateByPrimaryKeySelective(OpsWarehouseLocationno record);

    int updateByPrimaryKey(OpsWarehouseLocationno record);
}