package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductBomT;
import com.sales.ops.db.entity.ProductBomTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductBomTMapper {
    long countByExample(ProductBomTExample example);

    int deleteByExample(ProductBomTExample example);

    int deleteByPrimaryKey(Long bomid);

    int insert(ProductBomT record);

    int insertSelective(ProductBomT record);

    List<ProductBomT> selectByExample(ProductBomTExample example);

    ProductBomT selectByPrimaryKey(Long bomid);

    int updateByExampleSelective(@Param("record") ProductBomT record, @Param("example") ProductBomTExample example);

    int updateByExample(@Param("record") ProductBomT record, @Param("example") ProductBomTExample example);

    int updateByPrimaryKeySelective(ProductBomT record);

    int updateByPrimaryKey(ProductBomT record);
}