package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TempMergeInv;
import com.sales.ops.db.entity.TempMergeInvExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TempMergeInvMapper {
    long countByExample(TempMergeInvExample example);

    int deleteByExample(TempMergeInvExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TempMergeInv record);

    int insertSelective(TempMergeInv record);

    List<TempMergeInv> selectByExample(TempMergeInvExample example);

    TempMergeInv selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TempMergeInv record, @Param("example") TempMergeInvExample example);

    int updateByExample(@Param("record") TempMergeInv record, @Param("example") TempMergeInvExample example);

    int updateByPrimaryKeySelective(TempMergeInv record);

    int updateByPrimaryKey(TempMergeInv record);
}