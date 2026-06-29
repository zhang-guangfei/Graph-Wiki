package com.sales.ops.db.dao;

import com.sales.ops.db.entity.RcvorderAllocate;
import com.sales.ops.db.entity.RcvorderAllocateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvorderAllocateMapper {
    long countByExample(RcvorderAllocateExample example);

    int deleteByExample(RcvorderAllocateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RcvorderAllocate record);

    int insertSelective(RcvorderAllocate record);

    List<RcvorderAllocate> selectByExample(RcvorderAllocateExample example);

    RcvorderAllocate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RcvorderAllocate record, @Param("example") RcvorderAllocateExample example);

    int updateByExample(@Param("record") RcvorderAllocate record, @Param("example") RcvorderAllocateExample example);

    int updateByPrimaryKeySelective(RcvorderAllocate record);

    int updateByPrimaryKey(RcvorderAllocate record);
}