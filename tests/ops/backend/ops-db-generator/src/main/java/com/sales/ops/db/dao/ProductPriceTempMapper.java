package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductPriceTemp;
import com.sales.ops.db.entity.ProductPriceTempExample;
import com.sales.ops.db.entity.ProductPriceTempKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductPriceTempMapper {
    long countByExample(ProductPriceTempExample example);

    int deleteByExample(ProductPriceTempExample example);

    int deleteByPrimaryKey(ProductPriceTempKey key);

    int insert(ProductPriceTemp record);

    int insertSelective(ProductPriceTemp record);

    List<ProductPriceTemp> selectByExample(ProductPriceTempExample example);

    ProductPriceTemp selectByPrimaryKey(ProductPriceTempKey key);

    int updateByExampleSelective(@Param("record") ProductPriceTemp record, @Param("example") ProductPriceTempExample example);

    int updateByExample(@Param("record") ProductPriceTemp record, @Param("example") ProductPriceTempExample example);

    int updateByPrimaryKeySelective(ProductPriceTemp record);

    int updateByPrimaryKey(ProductPriceTemp record);
}