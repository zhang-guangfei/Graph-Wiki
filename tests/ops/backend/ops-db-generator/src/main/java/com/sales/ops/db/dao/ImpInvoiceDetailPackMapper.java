package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpInvoiceDetailPack;
import com.sales.ops.db.entity.ImpInvoiceDetailPackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpInvoiceDetailPackMapper {
    long countByExample(ImpInvoiceDetailPackExample example);

    int deleteByExample(ImpInvoiceDetailPackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImpInvoiceDetailPack record);

    int insertSelective(ImpInvoiceDetailPack record);

    List<ImpInvoiceDetailPack> selectByExample(ImpInvoiceDetailPackExample example);

    ImpInvoiceDetailPack selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImpInvoiceDetailPack record, @Param("example") ImpInvoiceDetailPackExample example);

    int updateByExample(@Param("record") ImpInvoiceDetailPack record, @Param("example") ImpInvoiceDetailPackExample example);

    int updateByPrimaryKeySelective(ImpInvoiceDetailPack record);

    int updateByPrimaryKey(ImpInvoiceDetailPack record);
}