package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0418b;
import com.sales.ops.db.entity.Testinv0418bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0418bMapper {
    long countByExample(Testinv0418bExample example);

    int deleteByExample(Testinv0418bExample example);

    int insert(Testinv0418b record);

    int insertSelective(Testinv0418b record);

    List<Testinv0418b> selectByExample(Testinv0418bExample example);

    int updateByExampleSelective(@Param("record") Testinv0418b record, @Param("example") Testinv0418bExample example);

    int updateByExample(@Param("record") Testinv0418b record, @Param("example") Testinv0418bExample example);
}