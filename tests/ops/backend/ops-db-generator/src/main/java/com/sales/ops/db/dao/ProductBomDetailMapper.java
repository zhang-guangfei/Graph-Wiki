package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductBomDetail;
import com.sales.ops.db.entity.ProductBomDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductBomDetailMapper {
    long countByExample(ProductBomDetailExample example);

    int deleteByExample(ProductBomDetailExample example);

    int insert(ProductBomDetail record);

    int insertSelective(ProductBomDetail record);

    List<ProductBomDetail> selectByExample(ProductBomDetailExample example);

    int updateByExampleSelective(@Param("record") ProductBomDetail record, @Param("example") ProductBomDetailExample example);

    int updateByExample(@Param("record") ProductBomDetail record, @Param("example") ProductBomDetailExample example);
}