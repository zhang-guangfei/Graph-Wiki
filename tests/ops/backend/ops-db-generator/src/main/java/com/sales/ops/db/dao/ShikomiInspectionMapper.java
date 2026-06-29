package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiInspection;
import com.sales.ops.db.entity.ShikomiInspectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiInspectionMapper {
    long countByExample(ShikomiInspectionExample example);

    int deleteByExample(ShikomiInspectionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ShikomiInspection record);

    int insertSelective(ShikomiInspection record);

    List<ShikomiInspection> selectByExample(ShikomiInspectionExample example);

    ShikomiInspection selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ShikomiInspection record, @Param("example") ShikomiInspectionExample example);

    int updateByExample(@Param("record") ShikomiInspection record, @Param("example") ShikomiInspectionExample example);

    int updateByPrimaryKeySelective(ShikomiInspection record);

    int updateByPrimaryKey(ShikomiInspection record);
}