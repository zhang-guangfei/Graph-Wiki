package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0331i;
import com.sales.ops.db.entity.Testinv0331iExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0331iMapper {
    long countByExample(Testinv0331iExample example);

    int deleteByExample(Testinv0331iExample example);

    int insert(Testinv0331i record);

    int insertSelective(Testinv0331i record);

    List<Testinv0331i> selectByExample(Testinv0331iExample example);

    int updateByExampleSelective(@Param("record") Testinv0331i record, @Param("example") Testinv0331iExample example);

    int updateByExample(@Param("record") Testinv0331i record, @Param("example") Testinv0331iExample example);
}