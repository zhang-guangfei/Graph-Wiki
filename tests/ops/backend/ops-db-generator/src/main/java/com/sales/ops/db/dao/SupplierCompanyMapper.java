package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SupplierCompany;
import com.sales.ops.db.entity.SupplierCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplierCompanyMapper {
    long countByExample(SupplierCompanyExample example);

    int deleteByExample(SupplierCompanyExample example);

    int deleteByPrimaryKey(String id);

    int insert(SupplierCompany record);

    int insertSelective(SupplierCompany record);

    List<SupplierCompany> selectByExample(SupplierCompanyExample example);

    SupplierCompany selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SupplierCompany record, @Param("example") SupplierCompanyExample example);

    int updateByExample(@Param("record") SupplierCompany record, @Param("example") SupplierCompanyExample example);

    int updateByPrimaryKeySelective(SupplierCompany record);

    int updateByPrimaryKey(SupplierCompany record);
}