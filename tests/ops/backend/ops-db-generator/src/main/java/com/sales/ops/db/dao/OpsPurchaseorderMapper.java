package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsPurchaseorderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchaseorderMapper {
    long countByExample(OpsPurchaseorderExample example);

    int deleteByExample(OpsPurchaseorderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPurchaseorder record);

    int insertSelective(OpsPurchaseorder record);

    List<OpsPurchaseorder> selectByExample(OpsPurchaseorderExample example);

    OpsPurchaseorder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPurchaseorder record, @Param("example") OpsPurchaseorderExample example);

    int updateByExample(@Param("record") OpsPurchaseorder record, @Param("example") OpsPurchaseorderExample example);

    int updateByPrimaryKeySelective(OpsPurchaseorder record);

    int updateByPrimaryKey(OpsPurchaseorder record);
}