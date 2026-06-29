package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpInvoiceError;
import com.sales.ops.db.entity.ImpInvoiceErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpInvoiceErrorMapper {
    long countByExample(ImpInvoiceErrorExample example);

    int deleteByExample(ImpInvoiceErrorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImpInvoiceError record);

    int insertSelective(ImpInvoiceError record);

    List<ImpInvoiceError> selectByExample(ImpInvoiceErrorExample example);

    ImpInvoiceError selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImpInvoiceError record, @Param("example") ImpInvoiceErrorExample example);

    int updateByExample(@Param("record") ImpInvoiceError record, @Param("example") ImpInvoiceErrorExample example);

    int updateByPrimaryKeySelective(ImpInvoiceError record);

    int updateByPrimaryKey(ImpInvoiceError record);
}