package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductPrice;
import com.sales.ops.db.entity.ProductPriceExample;
import com.sales.ops.db.entity.ProductPriceKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductPriceMapper {
    long countByExample(ProductPriceExample example);

    int deleteByExample(ProductPriceExample example);

    int deleteByPrimaryKey(ProductPriceKey key);

    int insert(ProductPrice record);

    int insertSelective(ProductPrice record);

    List<ProductPrice> selectByExample(ProductPriceExample example);

    ProductPrice selectByPrimaryKey(ProductPriceKey key);

    int updateByExampleSelective(@Param("record") ProductPrice record, @Param("example") ProductPriceExample example);

    int updateByExample(@Param("record") ProductPrice record, @Param("example") ProductPriceExample example);

    int updateByPrimaryKeySelective(ProductPrice record);

    int updateByPrimaryKey(ProductPrice record);
}