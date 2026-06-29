package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerWarehouseTodel;
import com.sales.ops.db.entity.OpsCustomerWarehouseTodelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerWarehouseTodelMapper {
    long countByExample(OpsCustomerWarehouseTodelExample example);

    int deleteByExample(OpsCustomerWarehouseTodelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsCustomerWarehouseTodel record);

    int insertSelective(OpsCustomerWarehouseTodel record);

    List<OpsCustomerWarehouseTodel> selectByExample(OpsCustomerWarehouseTodelExample example);

    OpsCustomerWarehouseTodel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsCustomerWarehouseTodel record, @Param("example") OpsCustomerWarehouseTodelExample example);

    int updateByExample(@Param("record") OpsCustomerWarehouseTodel record, @Param("example") OpsCustomerWarehouseTodelExample example);

    int updateByPrimaryKeySelective(OpsCustomerWarehouseTodel record);

    int updateByPrimaryKey(OpsCustomerWarehouseTodel record);
}