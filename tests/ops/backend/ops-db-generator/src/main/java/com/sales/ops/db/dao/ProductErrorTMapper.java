package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductErrorT;
import com.sales.ops.db.entity.ProductErrorTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductErrorTMapper {
    long countByExample(ProductErrorTExample example);

    int deleteByExample(ProductErrorTExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductErrorT record);

    int insertSelective(ProductErrorT record);

    List<ProductErrorT> selectByExample(ProductErrorTExample example);

    ProductErrorT selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductErrorT record, @Param("example") ProductErrorTExample example);

    int updateByExample(@Param("record") ProductErrorT record, @Param("example") ProductErrorTExample example);

    int updateByPrimaryKeySelective(ProductErrorT record);

    int updateByPrimaryKey(ProductErrorT record);
}