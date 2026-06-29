package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductPhysicsT;
import com.sales.ops.db.entity.ProductPhysicsTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductPhysicsTMapper {
    long countByExample(ProductPhysicsTExample example);

    int deleteByExample(ProductPhysicsTExample example);

    int deleteByPrimaryKey(String modelno);

    int insert(ProductPhysicsT record);

    int insertSelective(ProductPhysicsT record);

    List<ProductPhysicsT> selectByExample(ProductPhysicsTExample example);

    ProductPhysicsT selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") ProductPhysicsT record, @Param("example") ProductPhysicsTExample example);

    int updateByExample(@Param("record") ProductPhysicsT record, @Param("example") ProductPhysicsTExample example);

    int updateByPrimaryKeySelective(ProductPhysicsT record);

    int updateByPrimaryKey(ProductPhysicsT record);
}