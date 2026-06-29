package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Product1;
import com.sales.ops.db.entity.Product1Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Product1Mapper {
    long countByExample(Product1Example example);

    int deleteByExample(Product1Example example);

    int deleteByPrimaryKey(String modelno);

    int insert(Product1 record);

    int insertSelective(Product1 record);

    List<Product1> selectByExample(Product1Example example);

    Product1 selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") Product1 record, @Param("example") Product1Example example);

    int updateByExample(@Param("record") Product1 record, @Param("example") Product1Example example);

    int updateByPrimaryKeySelective(Product1 record);

    int updateByPrimaryKey(Product1 record);
}