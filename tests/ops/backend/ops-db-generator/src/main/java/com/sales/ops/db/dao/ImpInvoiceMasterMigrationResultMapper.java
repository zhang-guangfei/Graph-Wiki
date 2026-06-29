package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpInvoiceMasterMigrationResult;
import com.sales.ops.db.entity.ImpInvoiceMasterMigrationResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpInvoiceMasterMigrationResultMapper {
    long countByExample(ImpInvoiceMasterMigrationResultExample example);

    int deleteByExample(ImpInvoiceMasterMigrationResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ImpInvoiceMasterMigrationResult record);

    int insertSelective(ImpInvoiceMasterMigrationResult record);

    List<ImpInvoiceMasterMigrationResult> selectByExample(ImpInvoiceMasterMigrationResultExample example);

    ImpInvoiceMasterMigrationResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ImpInvoiceMasterMigrationResult record, @Param("example") ImpInvoiceMasterMigrationResultExample example);

    int updateByExample(@Param("record") ImpInvoiceMasterMigrationResult record, @Param("example") ImpInvoiceMasterMigrationResultExample example);

    int updateByPrimaryKeySelective(ImpInvoiceMasterMigrationResult record);

    int updateByPrimaryKey(ImpInvoiceMasterMigrationResult record);
}