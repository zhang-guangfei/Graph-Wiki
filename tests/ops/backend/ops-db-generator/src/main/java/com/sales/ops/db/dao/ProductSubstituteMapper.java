package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductSubstitute;
import com.sales.ops.db.entity.ProductSubstituteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSubstituteMapper {
    long countByExample(ProductSubstituteExample example);

    int deleteByExample(ProductSubstituteExample example);

    int insert(ProductSubstitute record);

    int insertSelective(ProductSubstitute record);

    List<ProductSubstitute> selectByExample(ProductSubstituteExample example);

    int updateByExampleSelective(@Param("record") ProductSubstitute record, @Param("example") ProductSubstituteExample example);

    int updateByExample(@Param("record") ProductSubstitute record, @Param("example") ProductSubstituteExample example);
}