package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductPhysics;
import com.sales.ops.db.entity.ProductPhysicsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductPhysicsMapper {
    long countByExample(ProductPhysicsExample example);

    int deleteByExample(ProductPhysicsExample example);

    int deleteByPrimaryKey(String modelno);

    int insert(ProductPhysics record);

    int insertSelective(ProductPhysics record);

    List<ProductPhysics> selectByExample(ProductPhysicsExample example);

    ProductPhysics selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") ProductPhysics record, @Param("example") ProductPhysicsExample example);

    int updateByExample(@Param("record") ProductPhysics record, @Param("example") ProductPhysicsExample example);

    int updateByPrimaryKeySelective(ProductPhysics record);

    int updateByPrimaryKey(ProductPhysics record);
}