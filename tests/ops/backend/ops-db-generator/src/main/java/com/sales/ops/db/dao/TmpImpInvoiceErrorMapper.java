package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TmpImpInvoiceError;
import com.sales.ops.db.entity.TmpImpInvoiceErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TmpImpInvoiceErrorMapper {
    long countByExample(TmpImpInvoiceErrorExample example);

    int deleteByExample(TmpImpInvoiceErrorExample example);

    int insert(TmpImpInvoiceError record);

    int insertSelective(TmpImpInvoiceError record);

    List<TmpImpInvoiceError> selectByExample(TmpImpInvoiceErrorExample example);

    int updateByExampleSelective(@Param("record") TmpImpInvoiceError record, @Param("example") TmpImpInvoiceErrorExample example);

    int updateByExample(@Param("record") TmpImpInvoiceError record, @Param("example") TmpImpInvoiceErrorExample example);
}