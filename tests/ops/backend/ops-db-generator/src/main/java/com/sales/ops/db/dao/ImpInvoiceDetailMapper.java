package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpInvoiceDetail;
import com.sales.ops.db.entity.ImpInvoiceDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpInvoiceDetailMapper {
    long countByExample(ImpInvoiceDetailExample example);

    int deleteByExample(ImpInvoiceDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImpInvoiceDetail record);

    int insertSelective(ImpInvoiceDetail record);

    List<ImpInvoiceDetail> selectByExample(ImpInvoiceDetailExample example);

    ImpInvoiceDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImpInvoiceDetail record, @Param("example") ImpInvoiceDetailExample example);

    int updateByExample(@Param("record") ImpInvoiceDetail record, @Param("example") ImpInvoiceDetailExample example);

    int updateByPrimaryKeySelective(ImpInvoiceDetail record);

    int updateByPrimaryKey(ImpInvoiceDetail record);
}