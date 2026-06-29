package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductBomDetailTemp;
import com.sales.ops.db.entity.ProductBomDetailTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductBomDetailTempMapper {
    long countByExample(ProductBomDetailTempExample example);

    int deleteByExample(ProductBomDetailTempExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductBomDetailTemp record);

    int insertSelective(ProductBomDetailTemp record);

    List<ProductBomDetailTemp> selectByExample(ProductBomDetailTempExample example);

    ProductBomDetailTemp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductBomDetailTemp record, @Param("example") ProductBomDetailTempExample example);

    int updateByExample(@Param("record") ProductBomDetailTemp record, @Param("example") ProductBomDetailTempExample example);

    int updateByPrimaryKeySelective(ProductBomDetailTemp record);

    int updateByPrimaryKey(ProductBomDetailTemp record);
}