package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SpecOrder;
import com.sales.ops.db.entity.SpecOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SpecOrderMapper {
    long countByExample(SpecOrderExample example);

    int deleteByExample(SpecOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SpecOrder record);

    int insertSelective(SpecOrder record);

    List<SpecOrder> selectByExample(SpecOrderExample example);

    SpecOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SpecOrder record, @Param("example") SpecOrderExample example);

    int updateByExample(@Param("record") SpecOrder record, @Param("example") SpecOrderExample example);

    int updateByPrimaryKeySelective(SpecOrder record);

    int updateByPrimaryKey(SpecOrder record);
}