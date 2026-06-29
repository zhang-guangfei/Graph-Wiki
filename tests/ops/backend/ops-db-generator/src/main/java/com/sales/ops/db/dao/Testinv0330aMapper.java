package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0330a;
import com.sales.ops.db.entity.Testinv0330aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0330aMapper {
    long countByExample(Testinv0330aExample example);

    int deleteByExample(Testinv0330aExample example);

    int insert(Testinv0330a record);

    int insertSelective(Testinv0330a record);

    List<Testinv0330a> selectByExample(Testinv0330aExample example);

    int updateByExampleSelective(@Param("record") Testinv0330a record, @Param("example") Testinv0330aExample example);

    int updateByExample(@Param("record") Testinv0330a record, @Param("example") Testinv0330aExample example);
}