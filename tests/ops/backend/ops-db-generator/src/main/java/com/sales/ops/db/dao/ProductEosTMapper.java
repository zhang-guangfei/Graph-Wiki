package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductEosT;
import com.sales.ops.db.entity.ProductEosTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductEosTMapper {
    long countByExample(ProductEosTExample example);

    int deleteByExample(ProductEosTExample example);

    int deleteByPrimaryKey(String modelno);

    int insert(ProductEosT record);

    int insertSelective(ProductEosT record);

    List<ProductEosT> selectByExample(ProductEosTExample example);

    ProductEosT selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") ProductEosT record, @Param("example") ProductEosTExample example);

    int updateByExample(@Param("record") ProductEosT record, @Param("example") ProductEosTExample example);

    int updateByPrimaryKeySelective(ProductEosT record);

    int updateByPrimaryKey(ProductEosT record);
}