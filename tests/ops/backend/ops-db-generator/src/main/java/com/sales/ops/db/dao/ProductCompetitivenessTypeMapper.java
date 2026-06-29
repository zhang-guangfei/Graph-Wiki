package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ProductCompetitivenessType;
import com.sales.ops.db.entity.ProductCompetitivenessTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductCompetitivenessTypeMapper {
    long countByExample(ProductCompetitivenessTypeExample example);

    int deleteByExample(ProductCompetitivenessTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductCompetitivenessType record);

    int insertSelective(ProductCompetitivenessType record);

    List<ProductCompetitivenessType> selectByExample(ProductCompetitivenessTypeExample example);

    ProductCompetitivenessType selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductCompetitivenessType record, @Param("example") ProductCompetitivenessTypeExample example);

    int updateByExample(@Param("record") ProductCompetitivenessType record, @Param("example") ProductCompetitivenessTypeExample example);

    int updateByPrimaryKeySelective(ProductCompetitivenessType record);

    int updateByPrimaryKey(ProductCompetitivenessType record);
}