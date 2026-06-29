package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0422b;
import com.sales.ops.db.entity.Testinv0422bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0422bMapper {
    long countByExample(Testinv0422bExample example);

    int deleteByExample(Testinv0422bExample example);

    int insert(Testinv0422b record);

    int insertSelective(Testinv0422b record);

    List<Testinv0422b> selectByExample(Testinv0422bExample example);

    int updateByExampleSelective(@Param("record") Testinv0422b record, @Param("example") Testinv0422bExample example);

    int updateByExample(@Param("record") Testinv0422b record, @Param("example") Testinv0422bExample example);
}