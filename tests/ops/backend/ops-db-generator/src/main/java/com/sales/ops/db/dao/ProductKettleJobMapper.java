package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductKettleJob;
import com.sales.ops.db.entity.ProductKettleJobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductKettleJobMapper {
    long countByExample(ProductKettleJobExample example);

    int deleteByExample(ProductKettleJobExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductKettleJob record);

    int insertSelective(ProductKettleJob record);

    List<ProductKettleJob> selectByExample(ProductKettleJobExample example);

    ProductKettleJob selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductKettleJob record, @Param("example") ProductKettleJobExample example);

    int updateByExample(@Param("record") ProductKettleJob record, @Param("example") ProductKettleJobExample example);

    int updateByPrimaryKeySelective(ProductKettleJob record);

    int updateByPrimaryKey(ProductKettleJob record);
}