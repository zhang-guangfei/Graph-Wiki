package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductRestrict;
import com.sales.ops.db.entity.ProductRestrictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductRestrictMapper {
    long countByExample(ProductRestrictExample example);

    int deleteByExample(ProductRestrictExample example);

    int deleteByPrimaryKey(String modelno);

    int insert(ProductRestrict record);

    int insertSelective(ProductRestrict record);

    List<ProductRestrict> selectByExample(ProductRestrictExample example);

    ProductRestrict selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") ProductRestrict record, @Param("example") ProductRestrictExample example);

    int updateByExample(@Param("record") ProductRestrict record, @Param("example") ProductRestrictExample example);

    int updateByPrimaryKeySelective(ProductRestrict record);

    int updateByPrimaryKey(ProductRestrict record);
}