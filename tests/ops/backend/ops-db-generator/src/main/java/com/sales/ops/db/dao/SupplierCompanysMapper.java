package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SupplierCompanys;
import com.sales.ops.db.entity.SupplierCompanysExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplierCompanysMapper {
    long countByExample(SupplierCompanysExample example);

    int deleteByExample(SupplierCompanysExample example);

    int insert(SupplierCompanys record);

    int insertSelective(SupplierCompanys record);

    List<SupplierCompanys> selectByExample(SupplierCompanysExample example);

    int updateByExampleSelective(@Param("record") SupplierCompanys record, @Param("example") SupplierCompanysExample example);

    int updateByExample(@Param("record") SupplierCompanys record, @Param("example") SupplierCompanysExample example);
}