package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductDeli;
import com.sales.ops.db.entity.ProductDeliExample;
import com.sales.ops.db.entity.ProductDeliKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductDeliMapper {
    long countByExample(ProductDeliExample example);

    int deleteByExample(ProductDeliExample example);

    int deleteByPrimaryKey(ProductDeliKey key);

    int insert(ProductDeli record);

    int insertSelective(ProductDeli record);

    List<ProductDeli> selectByExample(ProductDeliExample example);

    ProductDeli selectByPrimaryKey(ProductDeliKey key);

    int updateByExampleSelective(@Param("record") ProductDeli record, @Param("example") ProductDeliExample example);

    int updateByExample(@Param("record") ProductDeli record, @Param("example") ProductDeliExample example);

    int updateByPrimaryKeySelective(ProductDeli record);

    int updateByPrimaryKey(ProductDeli record);
}