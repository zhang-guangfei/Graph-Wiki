package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseinvoiceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchaseinvoiceMapper {
    long countByExample(OpsPurchaseinvoiceExample example);

    int deleteByExample(OpsPurchaseinvoiceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPurchaseinvoice record);

    int insertSelective(OpsPurchaseinvoice record);

    List<OpsPurchaseinvoice> selectByExample(OpsPurchaseinvoiceExample example);

    OpsPurchaseinvoice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPurchaseinvoice record, @Param("example") OpsPurchaseinvoiceExample example);

    int updateByExample(@Param("record") OpsPurchaseinvoice record, @Param("example") OpsPurchaseinvoiceExample example);

    int updateByPrimaryKeySelective(OpsPurchaseinvoice record);

    int updateByPrimaryKey(OpsPurchaseinvoice record);
}