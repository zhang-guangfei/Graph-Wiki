package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductBomDetailVt;
import com.sales.ops.db.entity.ProductBomDetailVtExample;
import com.sales.ops.db.entity.ProductBomDetailVtKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductBomDetailVtMapper {
    long countByExample(ProductBomDetailVtExample example);

    int deleteByExample(ProductBomDetailVtExample example);

    int deleteByPrimaryKey(ProductBomDetailVtKey key);

    int insert(ProductBomDetailVt record);

    int insertSelective(ProductBomDetailVt record);

    List<ProductBomDetailVt> selectByExample(ProductBomDetailVtExample example);

    ProductBomDetailVt selectByPrimaryKey(ProductBomDetailVtKey key);

    int updateByExampleSelective(@Param("record") ProductBomDetailVt record, @Param("example") ProductBomDetailVtExample example);

    int updateByExample(@Param("record") ProductBomDetailVt record, @Param("example") ProductBomDetailVtExample example);

    int updateByPrimaryKeySelective(ProductBomDetailVt record);

    int updateByPrimaryKey(ProductBomDetailVt record);
}