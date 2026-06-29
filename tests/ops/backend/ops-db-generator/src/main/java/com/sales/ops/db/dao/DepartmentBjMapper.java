package com.sales.ops.db.dao;

import com.sales.ops.db.entity.DepartmentBj;
import com.sales.ops.db.entity.DepartmentBjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentBjMapper {
    long countByExample(DepartmentBjExample example);

    int deleteByExample(DepartmentBjExample example);

    int insert(DepartmentBj record);

    int insertSelective(DepartmentBj record);

    List<DepartmentBj> selectByExample(DepartmentBjExample example);

    int updateByExampleSelective(@Param("record") DepartmentBj record, @Param("example") DepartmentBjExample example);

    int updateByExample(@Param("record") DepartmentBj record, @Param("example") DepartmentBjExample example);
}