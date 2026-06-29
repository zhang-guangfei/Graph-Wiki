package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerWarehouse2del;
import com.sales.ops.db.entity.OpsCustomerWarehouse2delExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerWarehouse2delMapper {
    long countByExample(OpsCustomerWarehouse2delExample example);

    int deleteByExample(OpsCustomerWarehouse2delExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsCustomerWarehouse2del record);

    int insertSelective(OpsCustomerWarehouse2del record);

    List<OpsCustomerWarehouse2del> selectByExample(OpsCustomerWarehouse2delExample example);

    OpsCustomerWarehouse2del selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsCustomerWarehouse2del record, @Param("example") OpsCustomerWarehouse2delExample example);

    int updateByExample(@Param("record") OpsCustomerWarehouse2del record, @Param("example") OpsCustomerWarehouse2delExample example);

    int updateByPrimaryKeySelective(OpsCustomerWarehouse2del record);

    int updateByPrimaryKey(OpsCustomerWarehouse2del record);
}