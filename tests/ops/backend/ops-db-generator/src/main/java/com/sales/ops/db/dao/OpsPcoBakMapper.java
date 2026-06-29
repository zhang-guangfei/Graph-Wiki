package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPcoBak;
import com.sales.ops.db.entity.OpsPcoBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPcoBakMapper {
    long countByExample(OpsPcoBakExample example);

    int deleteByExample(OpsPcoBakExample example);

    int deleteByPrimaryKey(String pcoId);

    int insert(OpsPcoBak record);

    int insertSelective(OpsPcoBak record);

    List<OpsPcoBak> selectByExample(OpsPcoBakExample example);

    OpsPcoBak selectByPrimaryKey(String pcoId);

    int updateByExampleSelective(@Param("record") OpsPcoBak record, @Param("example") OpsPcoBakExample example);

    int updateByExample(@Param("record") OpsPcoBak record, @Param("example") OpsPcoBakExample example);

    int updateByPrimaryKeySelective(OpsPcoBak record);

    int updateByPrimaryKey(OpsPcoBak record);
}