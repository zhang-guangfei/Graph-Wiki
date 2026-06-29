package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductDeliveryTemp;
import com.sales.ops.db.entity.ProductDeliveryTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductDeliveryTempMapper {
    long countByExample(ProductDeliveryTempExample example);

    int deleteByExample(ProductDeliveryTempExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductDeliveryTemp record);

    int insertSelective(ProductDeliveryTemp record);

    List<ProductDeliveryTemp> selectByExample(ProductDeliveryTempExample example);

    ProductDeliveryTemp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductDeliveryTemp record, @Param("example") ProductDeliveryTempExample example);

    int updateByExample(@Param("record") ProductDeliveryTemp record, @Param("example") ProductDeliveryTempExample example);

    int updateByPrimaryKeySelective(ProductDeliveryTemp record);

    int updateByPrimaryKey(ProductDeliveryTemp record);
}