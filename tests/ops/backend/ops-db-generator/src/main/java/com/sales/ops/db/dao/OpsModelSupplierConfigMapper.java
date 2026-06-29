package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsModelSupplierConfig;
import com.sales.ops.db.entity.OpsModelSupplierConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsModelSupplierConfigMapper {
    long countByExample(OpsModelSupplierConfigExample example);

    int deleteByExample(OpsModelSupplierConfigExample example);

    int insert(OpsModelSupplierConfig record);

    int insertSelective(OpsModelSupplierConfig record);

    List<OpsModelSupplierConfig> selectByExample(OpsModelSupplierConfigExample example);

    int updateByExampleSelective(@Param("record") OpsModelSupplierConfig record, @Param("example") OpsModelSupplierConfigExample example);

    int updateByExample(@Param("record") OpsModelSupplierConfig record, @Param("example") OpsModelSupplierConfigExample example);
}