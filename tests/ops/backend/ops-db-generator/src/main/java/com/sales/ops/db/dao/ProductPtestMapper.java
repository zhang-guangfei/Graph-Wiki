package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductPtest;
import com.sales.ops.db.entity.ProductPtestExample;
import com.sales.ops.db.entity.ProductPtestKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductPtestMapper {
    long countByExample(ProductPtestExample example);

    int deleteByExample(ProductPtestExample example);

    int deleteByPrimaryKey(ProductPtestKey key);

    int insert(ProductPtest record);

    int insertSelective(ProductPtest record);

    List<ProductPtest> selectByExample(ProductPtestExample example);

    ProductPtest selectByPrimaryKey(ProductPtestKey key);

    int updateByExampleSelective(@Param("record") ProductPtest record, @Param("example") ProductPtestExample example);

    int updateByExample(@Param("record") ProductPtest record, @Param("example") ProductPtestExample example);

    int updateByPrimaryKeySelective(ProductPtest record);

    int updateByPrimaryKey(ProductPtest record);
}