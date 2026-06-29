package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderModidata;
import com.sales.ops.db.entity.OpsOrderModidataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderModidataMapper {
    long countByExample(OpsOrderModidataExample example);

    int deleteByExample(OpsOrderModidataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsOrderModidata record);

    int insertSelective(OpsOrderModidata record);

    List<OpsOrderModidata> selectByExample(OpsOrderModidataExample example);

    OpsOrderModidata selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsOrderModidata record, @Param("example") OpsOrderModidataExample example);

    int updateByExample(@Param("record") OpsOrderModidata record, @Param("example") OpsOrderModidataExample example);

    int updateByPrimaryKeySelective(OpsOrderModidata record);

    int updateByPrimaryKey(OpsOrderModidata record);
}