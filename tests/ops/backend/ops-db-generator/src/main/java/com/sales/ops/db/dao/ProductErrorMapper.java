package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductError;
import com.sales.ops.db.entity.ProductErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductErrorMapper {
    long countByExample(ProductErrorExample example);

    int deleteByExample(ProductErrorExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductError record);

    int insertSelective(ProductError record);

    List<ProductError> selectByExample(ProductErrorExample example);

    ProductError selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductError record, @Param("example") ProductErrorExample example);

    int updateByExample(@Param("record") ProductError record, @Param("example") ProductErrorExample example);

    int updateByPrimaryKeySelective(ProductError record);

    int updateByPrimaryKey(ProductError record);
}