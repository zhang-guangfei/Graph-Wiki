package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductT;
import com.sales.ops.db.entity.ProductTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductTMapper {
    long countByExample(ProductTExample example);

    int deleteByExample(ProductTExample example);

    int deleteByPrimaryKey(String modelno);

    int insert(ProductT record);

    int insertSelective(ProductT record);

    List<ProductT> selectByExample(ProductTExample example);

    ProductT selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") ProductT record, @Param("example") ProductTExample example);

    int updateByExample(@Param("record") ProductT record, @Param("example") ProductTExample example);

    int updateByPrimaryKeySelective(ProductT record);

    int updateByPrimaryKey(ProductT record);
}