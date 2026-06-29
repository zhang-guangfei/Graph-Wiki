package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductSourceType;
import com.sales.ops.db.entity.ProductSourceTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSourceTypeMapper {
    long countByExample(ProductSourceTypeExample example);

    int deleteByExample(ProductSourceTypeExample example);

    int insert(ProductSourceType record);

    int insertSelective(ProductSourceType record);

    List<ProductSourceType> selectByExample(ProductSourceTypeExample example);

    int updateByExampleSelective(@Param("record") ProductSourceType record, @Param("example") ProductSourceTypeExample example);

    int updateByExample(@Param("record") ProductSourceType record, @Param("example") ProductSourceTypeExample example);
}