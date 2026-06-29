package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductDelivery;
import com.sales.ops.db.entity.ProductDeliveryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductDeliveryMapper {
    long countByExample(ProductDeliveryExample example);

    int deleteByExample(ProductDeliveryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductDelivery record);

    int insertSelective(ProductDelivery record);

    List<ProductDelivery> selectByExample(ProductDeliveryExample example);

    ProductDelivery selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductDelivery record, @Param("example") ProductDeliveryExample example);

    int updateByExample(@Param("record") ProductDelivery record, @Param("example") ProductDeliveryExample example);

    int updateByPrimaryKeySelective(ProductDelivery record);

    int updateByPrimaryKey(ProductDelivery record);
}