package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderCancel;
import com.sales.ops.db.entity.OpsOrderCancelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderCancelMapper {
    long countByExample(OpsOrderCancelExample example);

    int deleteByExample(OpsOrderCancelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsOrderCancel record);

    int insertSelective(OpsOrderCancel record);

    List<OpsOrderCancel> selectByExample(OpsOrderCancelExample example);

    OpsOrderCancel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsOrderCancel record, @Param("example") OpsOrderCancelExample example);

    int updateByExample(@Param("record") OpsOrderCancel record, @Param("example") OpsOrderCancelExample example);

    int updateByPrimaryKeySelective(OpsOrderCancel record);

    int updateByPrimaryKey(OpsOrderCancel record);
}