package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpInvoiceDetailPackTemp;
import com.sales.ops.db.entity.ImpInvoiceDetailPackTempExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImpInvoiceDetailPackTempMapper {
    long countByExample(ImpInvoiceDetailPackTempExample example);

    int deleteByExample(ImpInvoiceDetailPackTempExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImpInvoiceDetailPackTemp record);

    int insertSelective(ImpInvoiceDetailPackTemp record);

    List<ImpInvoiceDetailPackTemp> selectByExample(ImpInvoiceDetailPackTempExample example);

    ImpInvoiceDetailPackTemp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImpInvoiceDetailPackTemp record, @Param("example") ImpInvoiceDetailPackTempExample example);

    int updateByExample(@Param("record") ImpInvoiceDetailPackTemp record, @Param("example") ImpInvoiceDetailPackTempExample example);

    int updateByPrimaryKeySelective(ImpInvoiceDetailPackTemp record);

    int updateByPrimaryKey(ImpInvoiceDetailPackTemp record);
}