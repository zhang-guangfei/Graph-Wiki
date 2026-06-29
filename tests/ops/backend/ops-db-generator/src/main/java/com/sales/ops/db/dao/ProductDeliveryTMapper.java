package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductDeliveryT;
import com.sales.ops.db.entity.ProductDeliveryTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductDeliveryTMapper {
    long countByExample(ProductDeliveryTExample example);

    int deleteByExample(ProductDeliveryTExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductDeliveryT record);

    int insertSelective(ProductDeliveryT record);

    List<ProductDeliveryT> selectByExample(ProductDeliveryTExample example);

    ProductDeliveryT selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductDeliveryT record, @Param("example") ProductDeliveryTExample example);

    int updateByExample(@Param("record") ProductDeliveryT record, @Param("example") ProductDeliveryTExample example);

    int updateByPrimaryKeySelective(ProductDeliveryT record);

    int updateByPrimaryKey(ProductDeliveryT record);
}