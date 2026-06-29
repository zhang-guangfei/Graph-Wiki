package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderUpdateNew;
import com.sales.ops.db.entity.OpsOrderUpdateNewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderUpdateNewMapper {
    long countByExample(OpsOrderUpdateNewExample example);

    int deleteByExample(OpsOrderUpdateNewExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsOrderUpdateNew record);

    int insertSelective(OpsOrderUpdateNew record);

    List<OpsOrderUpdateNew> selectByExample(OpsOrderUpdateNewExample example);

    OpsOrderUpdateNew selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsOrderUpdateNew record, @Param("example") OpsOrderUpdateNewExample example);

    int updateByExample(@Param("record") OpsOrderUpdateNew record, @Param("example") OpsOrderUpdateNewExample example);

    int updateByPrimaryKeySelective(OpsOrderUpdateNew record);

    int updateByPrimaryKey(OpsOrderUpdateNew record);
}