package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.db.entity.OpsMailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsMailMapper {
    long countByExample(OpsMailExample example);

    int deleteByExample(OpsMailExample example);

    int deleteByPrimaryKey(Long mailId);

    int insert(OpsMail record);

    int insertSelective(OpsMail record);

    List<OpsMail> selectByExample(OpsMailExample example);

    OpsMail selectByPrimaryKey(Long mailId);

    int updateByExampleSelective(@Param("record") OpsMail record, @Param("example") OpsMailExample example);

    int updateByExample(@Param("record") OpsMail record, @Param("example") OpsMailExample example);

    int updateByPrimaryKeySelective(OpsMail record);

    int updateByPrimaryKey(OpsMail record);
}