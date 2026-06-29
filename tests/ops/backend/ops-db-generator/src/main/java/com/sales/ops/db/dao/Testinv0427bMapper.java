package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0427b;
import com.sales.ops.db.entity.Testinv0427bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0427bMapper {
    long countByExample(Testinv0427bExample example);

    int deleteByExample(Testinv0427bExample example);

    int insert(Testinv0427b record);

    int insertSelective(Testinv0427b record);

    List<Testinv0427b> selectByExample(Testinv0427bExample example);

    int updateByExampleSelective(@Param("record") Testinv0427b record, @Param("example") Testinv0427bExample example);

    int updateByExample(@Param("record") Testinv0427b record, @Param("example") Testinv0427bExample example);
}