package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductPriceT;
import com.sales.ops.db.entity.ProductPriceTExample;
import com.sales.ops.db.entity.ProductPriceTKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductPriceTMapper {
    long countByExample(ProductPriceTExample example);

    int deleteByExample(ProductPriceTExample example);

    int deleteByPrimaryKey(ProductPriceTKey key);

    int insert(ProductPriceT record);

    int insertSelective(ProductPriceT record);

    List<ProductPriceT> selectByExample(ProductPriceTExample example);

    ProductPriceT selectByPrimaryKey(ProductPriceTKey key);

    int updateByExampleSelective(@Param("record") ProductPriceT record, @Param("example") ProductPriceTExample example);

    int updateByExample(@Param("record") ProductPriceT record, @Param("example") ProductPriceTExample example);

    int updateByPrimaryKeySelective(ProductPriceT record);

    int updateByPrimaryKey(ProductPriceT record);
}