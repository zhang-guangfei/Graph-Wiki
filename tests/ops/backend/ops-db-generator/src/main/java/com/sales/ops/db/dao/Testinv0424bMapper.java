package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0424b;
import com.sales.ops.db.entity.Testinv0424bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0424bMapper {
    long countByExample(Testinv0424bExample example);

    int deleteByExample(Testinv0424bExample example);

    int insert(Testinv0424b record);

    int insertSelective(Testinv0424b record);

    List<Testinv0424b> selectByExample(Testinv0424bExample example);

    int updateByExampleSelective(@Param("record") Testinv0424b record, @Param("example") Testinv0424bExample example);

    int updateByExample(@Param("record") Testinv0424b record, @Param("example") Testinv0424bExample example);
}