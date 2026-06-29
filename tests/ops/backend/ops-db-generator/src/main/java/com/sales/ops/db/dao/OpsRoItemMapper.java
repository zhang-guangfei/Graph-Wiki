package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRoItem;
import com.sales.ops.db.entity.OpsRoItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRoItemMapper {
    long countByExample(OpsRoItemExample example);

    int deleteByExample(OpsRoItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsRoItem record);

    int insertSelective(OpsRoItem record);

    List<OpsRoItem> selectByExample(OpsRoItemExample example);

    OpsRoItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsRoItem record, @Param("example") OpsRoItemExample example);

    int updateByExample(@Param("record") OpsRoItem record, @Param("example") OpsRoItemExample example);

    int updateByPrimaryKeySelective(OpsRoItem record);

    int updateByPrimaryKey(OpsRoItem record);
}