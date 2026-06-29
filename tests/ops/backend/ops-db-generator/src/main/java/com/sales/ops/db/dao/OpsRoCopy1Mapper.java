package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRoCopy1;
import com.sales.ops.db.entity.OpsRoCopy1Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRoCopy1Mapper {
    long countByExample(OpsRoCopy1Example example);

    int deleteByExample(OpsRoCopy1Example example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsRoCopy1 record);

    int insertSelective(OpsRoCopy1 record);

    List<OpsRoCopy1> selectByExample(OpsRoCopy1Example example);

    OpsRoCopy1 selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsRoCopy1 record, @Param("example") OpsRoCopy1Example example);

    int updateByExample(@Param("record") OpsRoCopy1 record, @Param("example") OpsRoCopy1Example example);

    int updateByPrimaryKeySelective(OpsRoCopy1 record);

    int updateByPrimaryKey(OpsRoCopy1 record);
}