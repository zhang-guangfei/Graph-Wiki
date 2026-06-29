package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0505a;
import com.sales.ops.db.entity.Testinv0505aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0505aMapper {
    long countByExample(Testinv0505aExample example);

    int deleteByExample(Testinv0505aExample example);

    int insert(Testinv0505a record);

    int insertSelective(Testinv0505a record);

    List<Testinv0505a> selectByExample(Testinv0505aExample example);

    int updateByExampleSelective(@Param("record") Testinv0505a record, @Param("example") Testinv0505aExample example);

    int updateByExample(@Param("record") Testinv0505a record, @Param("example") Testinv0505aExample example);
}