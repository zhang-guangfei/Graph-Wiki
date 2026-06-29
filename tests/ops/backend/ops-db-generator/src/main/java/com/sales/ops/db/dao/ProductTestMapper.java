package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductTest;
import com.sales.ops.db.entity.ProductTestExample;
import com.sales.ops.db.entity.ProductTestKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductTestMapper {
    long countByExample(ProductTestExample example);

    int deleteByExample(ProductTestExample example);

    int deleteByPrimaryKey(ProductTestKey key);

    int insert(ProductTest record);

    int insertSelective(ProductTest record);

    List<ProductTest> selectByExample(ProductTestExample example);

    ProductTest selectByPrimaryKey(ProductTestKey key);

    int updateByExampleSelective(@Param("record") ProductTest record, @Param("example") ProductTestExample example);

    int updateByExample(@Param("record") ProductTest record, @Param("example") ProductTestExample example);

    int updateByPrimaryKeySelective(ProductTest record);

    int updateByPrimaryKey(ProductTest record);
}