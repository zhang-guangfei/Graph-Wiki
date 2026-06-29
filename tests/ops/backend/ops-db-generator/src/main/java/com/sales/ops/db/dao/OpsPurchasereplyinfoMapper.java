package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchasereplyinfo;
import com.sales.ops.db.entity.OpsPurchasereplyinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchasereplyinfoMapper {
    long countByExample(OpsPurchasereplyinfoExample example);

    int deleteByExample(OpsPurchasereplyinfoExample example);

    int insert(OpsPurchasereplyinfo record);

    int insertSelective(OpsPurchasereplyinfo record);

    List<OpsPurchasereplyinfo> selectByExample(OpsPurchasereplyinfoExample example);

    int updateByExampleSelective(@Param("record") OpsPurchasereplyinfo record, @Param("example") OpsPurchasereplyinfoExample example);

    int updateByExample(@Param("record") OpsPurchasereplyinfo record, @Param("example") OpsPurchasereplyinfoExample example);
}