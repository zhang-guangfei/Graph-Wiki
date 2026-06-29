package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductPric;
import com.sales.ops.db.entity.ProductPricExample;
import com.sales.ops.db.entity.ProductPricKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductPricMapper {
    long countByExample(ProductPricExample example);

    int deleteByExample(ProductPricExample example);

    int deleteByPrimaryKey(ProductPricKey key);

    int insert(ProductPric record);

    int insertSelective(ProductPric record);

    List<ProductPric> selectByExample(ProductPricExample example);

    ProductPric selectByPrimaryKey(ProductPricKey key);

    int updateByExampleSelective(@Param("record") ProductPric record, @Param("example") ProductPricExample example);

    int updateByExample(@Param("record") ProductPric record, @Param("example") ProductPricExample example);

    int updateByPrimaryKeySelective(ProductPric record);

    int updateByPrimaryKey(ProductPric record);
}