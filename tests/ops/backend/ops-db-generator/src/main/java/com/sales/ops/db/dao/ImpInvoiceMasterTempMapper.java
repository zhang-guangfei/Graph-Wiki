package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpInvoiceMasterTemp;
import com.sales.ops.db.entity.ImpInvoiceMasterTempExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImpInvoiceMasterTempMapper {
    long countByExample(ImpInvoiceMasterTempExample example);

    int deleteByExample(ImpInvoiceMasterTempExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImpInvoiceMasterTemp record);

    int insertSelective(ImpInvoiceMasterTemp record);

    List<ImpInvoiceMasterTemp> selectByExample(ImpInvoiceMasterTempExample example);

    ImpInvoiceMasterTemp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImpInvoiceMasterTemp record, @Param("example") ImpInvoiceMasterTempExample example);

    int updateByExample(@Param("record") ImpInvoiceMasterTemp record, @Param("example") ImpInvoiceMasterTempExample example);

    int updateByPrimaryKeySelective(ImpInvoiceMasterTemp record);

    int updateByPrimaryKey(ImpInvoiceMasterTemp record);
}