package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0505b;
import com.sales.ops.db.entity.Testinv0505bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0505bMapper {
    long countByExample(Testinv0505bExample example);

    int deleteByExample(Testinv0505bExample example);

    int insert(Testinv0505b record);

    int insertSelective(Testinv0505b record);

    List<Testinv0505b> selectByExample(Testinv0505bExample example);

    int updateByExampleSelective(@Param("record") Testinv0505b record, @Param("example") Testinv0505bExample example);

    int updateByExample(@Param("record") Testinv0505b record, @Param("example") Testinv0505bExample example);
}