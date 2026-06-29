package com.sales.ops.db.dao;

import com.sales.ops.db.entity.BindataSh;
import com.sales.ops.db.entity.BindataShExample;
import com.sales.ops.db.entity.BindataShKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BindataShMapper {
    long countByExample(BindataShExample example);

    int deleteByExample(BindataShExample example);

    int deleteByPrimaryKey(BindataShKey key);

    int insert(BindataSh record);

    int insertSelective(BindataSh record);

    List<BindataSh> selectByExample(BindataShExample example);

    BindataSh selectByPrimaryKey(BindataShKey key);

    int updateByExampleSelective(@Param("record") BindataSh record, @Param("example") BindataShExample example);

    int updateByExample(@Param("record") BindataSh record, @Param("example") BindataShExample example);

    int updateByPrimaryKeySelective(BindataSh record);

    int updateByPrimaryKey(BindataSh record);
}