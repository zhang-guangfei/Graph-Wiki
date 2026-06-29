package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpInvoiceEventLog;
import com.sales.ops.db.entity.ImpInvoiceEventLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpInvoiceEventLogMapper {
    long countByExample(ImpInvoiceEventLogExample example);

    int deleteByExample(ImpInvoiceEventLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImpInvoiceEventLog record);

    int insertSelective(ImpInvoiceEventLog record);

    List<ImpInvoiceEventLog> selectByExample(ImpInvoiceEventLogExample example);

    ImpInvoiceEventLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImpInvoiceEventLog record, @Param("example") ImpInvoiceEventLogExample example);

    int updateByExample(@Param("record") ImpInvoiceEventLog record, @Param("example") ImpInvoiceEventLogExample example);

    int updateByPrimaryKeySelective(ImpInvoiceEventLog record);

    int updateByPrimaryKey(ImpInvoiceEventLog record);
}