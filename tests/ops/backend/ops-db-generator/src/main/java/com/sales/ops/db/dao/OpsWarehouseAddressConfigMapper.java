package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsWarehouseAddressConfig;
import com.sales.ops.db.entity.OpsWarehouseAddressConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsWarehouseAddressConfigMapper {
    long countByExample(OpsWarehouseAddressConfigExample example);

    int deleteByExample(OpsWarehouseAddressConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsWarehouseAddressConfig record);

    int insertSelective(OpsWarehouseAddressConfig record);

    List<OpsWarehouseAddressConfig> selectByExample(OpsWarehouseAddressConfigExample example);

    OpsWarehouseAddressConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsWarehouseAddressConfig record, @Param("example") OpsWarehouseAddressConfigExample example);

    int updateByExample(@Param("record") OpsWarehouseAddressConfig record, @Param("example") OpsWarehouseAddressConfigExample example);

    int updateByPrimaryKeySelective(OpsWarehouseAddressConfig record);

    int updateByPrimaryKey(OpsWarehouseAddressConfig record);
}