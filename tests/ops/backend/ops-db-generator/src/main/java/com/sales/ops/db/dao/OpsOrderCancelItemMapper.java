package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderCancelItem;
import com.sales.ops.db.entity.OpsOrderCancelItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderCancelItemMapper {
    long countByExample(OpsOrderCancelItemExample example);

    int deleteByExample(OpsOrderCancelItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsOrderCancelItem record);

    int insertSelective(OpsOrderCancelItem record);

    List<OpsOrderCancelItem> selectByExample(OpsOrderCancelItemExample example);

    OpsOrderCancelItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsOrderCancelItem record, @Param("example") OpsOrderCancelItemExample example);

    int updateByExample(@Param("record") OpsOrderCancelItem record, @Param("example") OpsOrderCancelItemExample example);

    int updateByPrimaryKeySelective(OpsOrderCancelItem record);

    int updateByPrimaryKey(OpsOrderCancelItem record);
}