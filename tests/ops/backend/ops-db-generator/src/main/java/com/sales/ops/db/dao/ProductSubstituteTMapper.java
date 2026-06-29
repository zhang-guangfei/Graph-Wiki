package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductSubstituteT;
import com.sales.ops.db.entity.ProductSubstituteTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSubstituteTMapper {
    long countByExample(ProductSubstituteTExample example);

    int deleteByExample(ProductSubstituteTExample example);

    int insert(ProductSubstituteT record);

    int insertSelective(ProductSubstituteT record);

    List<ProductSubstituteT> selectByExample(ProductSubstituteTExample example);

    int updateByExampleSelective(@Param("record") ProductSubstituteT record, @Param("example") ProductSubstituteTExample example);

    int updateByExample(@Param("record") ProductSubstituteT record, @Param("example") ProductSubstituteTExample example);
}