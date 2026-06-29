package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchaseinvoiceInsert;
import com.sales.ops.db.entity.OpsPurchaseinvoiceInsertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchaseinvoiceInsertMapper {
    long countByExample(OpsPurchaseinvoiceInsertExample example);

    int deleteByExample(OpsPurchaseinvoiceInsertExample example);

    int insert(OpsPurchaseinvoiceInsert record);

    int insertSelective(OpsPurchaseinvoiceInsert record);

    List<OpsPurchaseinvoiceInsert> selectByExample(OpsPurchaseinvoiceInsertExample example);

    int updateByExampleSelective(@Param("record") OpsPurchaseinvoiceInsert record, @Param("example") OpsPurchaseinvoiceInsertExample example);

    int updateByExample(@Param("record") OpsPurchaseinvoiceInsert record, @Param("example") OpsPurchaseinvoiceInsertExample example);
}