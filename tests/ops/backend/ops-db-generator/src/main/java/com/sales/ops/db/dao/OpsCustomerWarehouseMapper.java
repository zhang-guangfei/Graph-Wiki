package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerWarehouse;
import com.sales.ops.db.entity.OpsCustomerWarehouseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerWarehouseMapper {
    long countByExample(OpsCustomerWarehouseExample example);

    int deleteByExample(OpsCustomerWarehouseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsCustomerWarehouse record);

    int insertSelective(OpsCustomerWarehouse record);

    List<OpsCustomerWarehouse> selectByExample(OpsCustomerWarehouseExample example);

    OpsCustomerWarehouse selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsCustomerWarehouse record, @Param("example") OpsCustomerWarehouseExample example);

    int updateByExample(@Param("record") OpsCustomerWarehouse record, @Param("example") OpsCustomerWarehouseExample example);

    int updateByPrimaryKeySelective(OpsCustomerWarehouse record);

    int updateByPrimaryKey(OpsCustomerWarehouse record);
}