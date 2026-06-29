package com.sales.ops.db.dao;

import com.sales.ops.db.entity.AgentstockGz;
import com.sales.ops.db.entity.AgentstockGzExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentstockGzMapper {
    long countByExample(AgentstockGzExample example);

    int deleteByExample(AgentstockGzExample example);

    int insert(AgentstockGz record);

    int insertSelective(AgentstockGz record);

    List<AgentstockGz> selectByExample(AgentstockGzExample example);

    int updateByExampleSelective(@Param("record") AgentstockGz record, @Param("example") AgentstockGzExample example);

    int updateByExample(@Param("record") AgentstockGz record, @Param("example") AgentstockGzExample example);
}