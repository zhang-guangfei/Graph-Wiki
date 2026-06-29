package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductBomTemp;
import com.sales.ops.db.entity.ProductBomTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductBomTempMapper {
    long countByExample(ProductBomTempExample example);

    int deleteByExample(ProductBomTempExample example);

    int deleteByPrimaryKey(Long bomid);

    int insert(ProductBomTemp record);

    int insertSelective(ProductBomTemp record);

    List<ProductBomTemp> selectByExample(ProductBomTempExample example);

    ProductBomTemp selectByPrimaryKey(Long bomid);

    int updateByExampleSelective(@Param("record") ProductBomTemp record, @Param("example") ProductBomTempExample example);

    int updateByExample(@Param("record") ProductBomTemp record, @Param("example") ProductBomTempExample example);

    int updateByPrimaryKeySelective(ProductBomTemp record);

    int updateByPrimaryKey(ProductBomTemp record);
}