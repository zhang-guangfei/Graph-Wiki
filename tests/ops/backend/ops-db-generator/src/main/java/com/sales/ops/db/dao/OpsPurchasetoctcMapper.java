package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchasetoctc;
import com.sales.ops.db.entity.OpsPurchasetoctcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchasetoctcMapper {
    long countByExample(OpsPurchasetoctcExample example);

    int deleteByExample(OpsPurchasetoctcExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPurchasetoctc record);

    int insertSelective(OpsPurchasetoctc record);

    List<OpsPurchasetoctc> selectByExample(OpsPurchasetoctcExample example);

    OpsPurchasetoctc selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPurchasetoctc record, @Param("example") OpsPurchasetoctcExample example);

    int updateByExample(@Param("record") OpsPurchasetoctc record, @Param("example") OpsPurchasetoctcExample example);

    int updateByPrimaryKeySelective(OpsPurchasetoctc record);

    int updateByPrimaryKey(OpsPurchasetoctc record);
}