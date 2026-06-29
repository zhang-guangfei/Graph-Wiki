package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0331b;
import com.sales.ops.db.entity.Testinv0331bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0331bMapper {
    long countByExample(Testinv0331bExample example);

    int deleteByExample(Testinv0331bExample example);

    int insert(Testinv0331b record);

    int insertSelective(Testinv0331b record);

    List<Testinv0331b> selectByExample(Testinv0331bExample example);

    int updateByExampleSelective(@Param("record") Testinv0331b record, @Param("example") Testinv0331bExample example);

    int updateByExample(@Param("record") Testinv0331b record, @Param("example") Testinv0331bExample example);
}