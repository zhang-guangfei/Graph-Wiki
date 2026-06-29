package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductDeliveryProd;
import com.sales.ops.db.entity.ProductDeliveryProdExample;
import com.sales.ops.db.entity.ProductDeliveryProdKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductDeliveryProdMapper {
    long countByExample(ProductDeliveryProdExample example);

    int deleteByExample(ProductDeliveryProdExample example);

    int deleteByPrimaryKey(ProductDeliveryProdKey key);

    int insert(ProductDeliveryProd record);

    int insertSelective(ProductDeliveryProd record);

    List<ProductDeliveryProd> selectByExample(ProductDeliveryProdExample example);

    ProductDeliveryProd selectByPrimaryKey(ProductDeliveryProdKey key);

    int updateByExampleSelective(@Param("record") ProductDeliveryProd record, @Param("example") ProductDeliveryProdExample example);

    int updateByExample(@Param("record") ProductDeliveryProd record, @Param("example") ProductDeliveryProdExample example);

    int updateByPrimaryKeySelective(ProductDeliveryProd record);

    int updateByPrimaryKey(ProductDeliveryProd record);
}