package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductBomDetailT;
import com.sales.ops.db.entity.ProductBomDetailTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductBomDetailTMapper {
    long countByExample(ProductBomDetailTExample example);

    int deleteByExample(ProductBomDetailTExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductBomDetailT record);

    int insertSelective(ProductBomDetailT record);

    List<ProductBomDetailT> selectByExample(ProductBomDetailTExample example);

    ProductBomDetailT selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductBomDetailT record, @Param("example") ProductBomDetailTExample example);

    int updateByExample(@Param("record") ProductBomDetailT record, @Param("example") ProductBomDetailTExample example);

    int updateByPrimaryKeySelective(ProductBomDetailT record);

    int updateByPrimaryKey(ProductBomDetailT record);
}