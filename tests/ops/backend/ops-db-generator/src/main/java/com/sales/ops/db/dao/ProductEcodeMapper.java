package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductEcode;
import com.sales.ops.db.entity.ProductEcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductEcodeMapper {
    long countByExample(ProductEcodeExample example);

    int deleteByExample(ProductEcodeExample example);

    int insert(ProductEcode record);

    int insertSelective(ProductEcode record);

    List<ProductEcode> selectByExample(ProductEcodeExample example);

    int updateByExampleSelective(@Param("record") ProductEcode record, @Param("example") ProductEcodeExample example);

    int updateByExample(@Param("record") ProductEcode record, @Param("example") ProductEcodeExample example);
}