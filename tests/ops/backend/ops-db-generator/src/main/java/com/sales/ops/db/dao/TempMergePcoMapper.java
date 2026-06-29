package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TempMergePco;
import com.sales.ops.db.entity.TempMergePcoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TempMergePcoMapper {
    long countByExample(TempMergePcoExample example);

    int deleteByExample(TempMergePcoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TempMergePco record);

    int insertSelective(TempMergePco record);

    List<TempMergePco> selectByExample(TempMergePcoExample example);

    TempMergePco selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TempMergePco record, @Param("example") TempMergePcoExample example);

    int updateByExample(@Param("record") TempMergePco record, @Param("example") TempMergePcoExample example);

    int updateByPrimaryKeySelective(TempMergePco record);

    int updateByPrimaryKey(TempMergePco record);
}