package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductSubs;
import com.sales.ops.db.entity.ProductSubsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSubsMapper {
    long countByExample(ProductSubsExample example);

    int deleteByExample(ProductSubsExample example);

    int insert(ProductSubs record);

    int insertSelective(ProductSubs record);

    List<ProductSubs> selectByExample(ProductSubsExample example);

    int updateByExampleSelective(@Param("record") ProductSubs record, @Param("example") ProductSubsExample example);

    int updateByExample(@Param("record") ProductSubs record, @Param("example") ProductSubsExample example);
}