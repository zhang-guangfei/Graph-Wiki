package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsWarehouseAddress;
import com.sales.ops.db.entity.OpsWarehouseAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsWarehouseAddressMapper {
    long countByExample(OpsWarehouseAddressExample example);

    int deleteByExample(OpsWarehouseAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsWarehouseAddress record);

    int insertSelective(OpsWarehouseAddress record);

    List<OpsWarehouseAddress> selectByExample(OpsWarehouseAddressExample example);

    OpsWarehouseAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsWarehouseAddress record, @Param("example") OpsWarehouseAddressExample example);

    int updateByExample(@Param("record") OpsWarehouseAddress record, @Param("example") OpsWarehouseAddressExample example);

    int updateByPrimaryKeySelective(OpsWarehouseAddress record);

    int updateByPrimaryKey(OpsWarehouseAddress record);
}