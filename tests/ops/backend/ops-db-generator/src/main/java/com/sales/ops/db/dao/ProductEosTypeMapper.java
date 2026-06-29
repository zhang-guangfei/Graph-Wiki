package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductEosType;
import com.sales.ops.db.entity.ProductEosTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductEosTypeMapper {
    long countByExample(ProductEosTypeExample example);

    int deleteByExample(ProductEosTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductEosType record);

    int insertSelective(ProductEosType record);

    List<ProductEosType> selectByExample(ProductEosTypeExample example);

    ProductEosType selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductEosType record, @Param("example") ProductEosTypeExample example);

    int updateByExample(@Param("record") ProductEosType record, @Param("example") ProductEosTypeExample example);

    int updateByPrimaryKeySelective(ProductEosType record);

    int updateByPrimaryKey(ProductEosType record);
}