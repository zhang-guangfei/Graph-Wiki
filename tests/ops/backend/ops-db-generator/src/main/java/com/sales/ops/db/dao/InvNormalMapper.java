package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InvNormal;
import com.sales.ops.db.entity.InvNormalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InvNormalMapper {
    long countByExample(InvNormalExample example);

    int deleteByExample(InvNormalExample example);

    int insert(InvNormal record);

    int insertSelective(InvNormal record);

    List<InvNormal> selectByExample(InvNormalExample example);

    int updateByExampleSelective(@Param("record") InvNormal record, @Param("example") InvNormalExample example);

    int updateByExample(@Param("record") InvNormal record, @Param("example") InvNormalExample example);
}