package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductEoss;
import com.sales.ops.db.entity.ProductEossExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductEossMapper {
    long countByExample(ProductEossExample example);

    int deleteByExample(ProductEossExample example);

    int deleteByPrimaryKey(String modelno);

    int insert(ProductEoss record);

    int insertSelective(ProductEoss record);

    List<ProductEoss> selectByExample(ProductEossExample example);

    ProductEoss selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") ProductEoss record, @Param("example") ProductEossExample example);

    int updateByExample(@Param("record") ProductEoss record, @Param("example") ProductEossExample example);

    int updateByPrimaryKeySelective(ProductEoss record);

    int updateByPrimaryKey(ProductEoss record);
}