package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsWarehouseSupplierConfig;
import com.sales.ops.db.entity.OpsWarehouseSupplierConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsWarehouseSupplierConfigMapper {
    long countByExample(OpsWarehouseSupplierConfigExample example);

    int deleteByExample(OpsWarehouseSupplierConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsWarehouseSupplierConfig record);

    int insertSelective(OpsWarehouseSupplierConfig record);

    List<OpsWarehouseSupplierConfig> selectByExample(OpsWarehouseSupplierConfigExample example);

    OpsWarehouseSupplierConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsWarehouseSupplierConfig record, @Param("example") OpsWarehouseSupplierConfigExample example);

    int updateByExample(@Param("record") OpsWarehouseSupplierConfig record, @Param("example") OpsWarehouseSupplierConfigExample example);

    int updateByPrimaryKeySelective(OpsWarehouseSupplierConfig record);

    int updateByPrimaryKey(OpsWarehouseSupplierConfig record);
}