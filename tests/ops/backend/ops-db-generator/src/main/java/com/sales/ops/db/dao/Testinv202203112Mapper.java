package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv202203112;
import com.sales.ops.db.entity.Testinv202203112Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv202203112Mapper {
    long countByExample(Testinv202203112Example example);

    int deleteByExample(Testinv202203112Example example);

    int insert(Testinv202203112 record);

    int insertSelective(Testinv202203112 record);

    List<Testinv202203112> selectByExample(Testinv202203112Example example);

    int updateByExampleSelective(@Param("record") Testinv202203112 record, @Param("example") Testinv202203112Example example);

    int updateByExample(@Param("record") Testinv202203112 record, @Param("example") Testinv202203112Example example);
}