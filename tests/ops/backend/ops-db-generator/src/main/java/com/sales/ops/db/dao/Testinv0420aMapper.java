package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0420a;
import com.sales.ops.db.entity.Testinv0420aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0420aMapper {
    long countByExample(Testinv0420aExample example);

    int deleteByExample(Testinv0420aExample example);

    int insert(Testinv0420a record);

    int insertSelective(Testinv0420a record);

    List<Testinv0420a> selectByExample(Testinv0420aExample example);

    int updateByExampleSelective(@Param("record") Testinv0420a record, @Param("example") Testinv0420aExample example);

    int updateByExample(@Param("record") Testinv0420a record, @Param("example") Testinv0420aExample example);
}