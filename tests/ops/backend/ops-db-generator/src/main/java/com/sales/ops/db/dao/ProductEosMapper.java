package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductEos;
import com.sales.ops.db.entity.ProductEosExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductEosMapper {
    long countByExample(ProductEosExample example);

    int deleteByExample(ProductEosExample example);

    int deleteByPrimaryKey(String modelno);

    int insert(ProductEos record);

    int insertSelective(ProductEos record);

    List<ProductEos> selectByExample(ProductEosExample example);

    ProductEos selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") ProductEos record, @Param("example") ProductEosExample example);

    int updateByExample(@Param("record") ProductEos record, @Param("example") ProductEosExample example);

    int updateByPrimaryKeySelective(ProductEos record);

    int updateByPrimaryKey(ProductEos record);
}