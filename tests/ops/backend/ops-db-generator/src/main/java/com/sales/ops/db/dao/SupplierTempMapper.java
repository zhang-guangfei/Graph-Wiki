package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SupplierTemp;
import com.sales.ops.db.entity.SupplierTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplierTempMapper {
    long countByExample(SupplierTempExample example);

    int deleteByExample(SupplierTempExample example);

    int insert(SupplierTemp record);

    int insertSelective(SupplierTemp record);

    List<SupplierTemp> selectByExample(SupplierTempExample example);

    int updateByExampleSelective(@Param("record") SupplierTemp record, @Param("example") SupplierTempExample example);

    int updateByExample(@Param("record") SupplierTemp record, @Param("example") SupplierTempExample example);
}