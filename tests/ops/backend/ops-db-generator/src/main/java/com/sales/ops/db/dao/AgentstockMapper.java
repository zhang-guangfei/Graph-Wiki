package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Agentstock;
import com.sales.ops.db.entity.AgentstockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentstockMapper {
    long countByExample(AgentstockExample example);

    int deleteByExample(AgentstockExample example);

    int insert(Agentstock record);

    int insertSelective(Agentstock record);

    List<Agentstock> selectByExample(AgentstockExample example);

    int updateByExampleSelective(@Param("record") Agentstock record, @Param("example") AgentstockExample example);

    int updateByExample(@Param("record") Agentstock record, @Param("example") AgentstockExample example);
}