package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductRestrictT;
import com.sales.ops.db.entity.ProductRestrictTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductRestrictTMapper {
    long countByExample(ProductRestrictTExample example);

    int deleteByExample(ProductRestrictTExample example);

    int deleteByPrimaryKey(String modelno);

    int insert(ProductRestrictT record);

    int insertSelective(ProductRestrictT record);

    List<ProductRestrictT> selectByExample(ProductRestrictTExample example);

    ProductRestrictT selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") ProductRestrictT record, @Param("example") ProductRestrictTExample example);

    int updateByExample(@Param("record") ProductRestrictT record, @Param("example") ProductRestrictTExample example);

    int updateByPrimaryKeySelective(ProductRestrictT record);

    int updateByPrimaryKey(ProductRestrictT record);
}