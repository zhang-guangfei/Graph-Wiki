package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductBomExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductBomMapper {
    long countByExample(ProductBomExample example);

    int deleteByExample(ProductBomExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductBom record);

    int insertSelective(ProductBom record);

    List<ProductBom> selectByExample(ProductBomExample example);

    ProductBom selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductBom record, @Param("example") ProductBomExample example);

    int updateByExample(@Param("record") ProductBom record, @Param("example") ProductBomExample example);

    int updateByPrimaryKeySelective(ProductBom record);

    int updateByPrimaryKey(ProductBom record);
}