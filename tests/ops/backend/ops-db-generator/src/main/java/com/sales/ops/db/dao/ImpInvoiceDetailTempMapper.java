package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpInvoiceDetailTemp;
import com.sales.ops.db.entity.ImpInvoiceDetailTempExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImpInvoiceDetailTempMapper {
    long countByExample(ImpInvoiceDetailTempExample example);

    int deleteByExample(ImpInvoiceDetailTempExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImpInvoiceDetailTemp record);

    int insertSelective(ImpInvoiceDetailTemp record);

    List<ImpInvoiceDetailTemp> selectByExample(ImpInvoiceDetailTempExample example);

    ImpInvoiceDetailTemp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImpInvoiceDetailTemp record, @Param("example") ImpInvoiceDetailTempExample example);

    int updateByExample(@Param("record") ImpInvoiceDetailTemp record, @Param("example") ImpInvoiceDetailTempExample example);

    int updateByPrimaryKeySelective(ImpInvoiceDetailTemp record);

    int updateByPrimaryKey(ImpInvoiceDetailTemp record);
}