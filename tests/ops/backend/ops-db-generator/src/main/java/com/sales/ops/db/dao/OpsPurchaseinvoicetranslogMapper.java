package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchaseinvoicetranslog;
import com.sales.ops.db.entity.OpsPurchaseinvoicetranslogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchaseinvoicetranslogMapper {
    long countByExample(OpsPurchaseinvoicetranslogExample example);

    int deleteByExample(OpsPurchaseinvoicetranslogExample example);

    int insert(OpsPurchaseinvoicetranslog record);

    int insertSelective(OpsPurchaseinvoicetranslog record);

    List<OpsPurchaseinvoicetranslog> selectByExample(OpsPurchaseinvoicetranslogExample example);

    int updateByExampleSelective(@Param("record") OpsPurchaseinvoicetranslog record, @Param("example") OpsPurchaseinvoicetranslogExample example);

    int updateByExample(@Param("record") OpsPurchaseinvoicetranslog record, @Param("example") OpsPurchaseinvoicetranslogExample example);
}