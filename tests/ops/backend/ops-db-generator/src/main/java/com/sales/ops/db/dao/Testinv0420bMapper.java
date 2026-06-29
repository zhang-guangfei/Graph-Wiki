package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0420b;
import com.sales.ops.db.entity.Testinv0420bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0420bMapper {
    long countByExample(Testinv0420bExample example);

    int deleteByExample(Testinv0420bExample example);

    int insert(Testinv0420b record);

    int insertSelective(Testinv0420b record);

    List<Testinv0420b> selectByExample(Testinv0420bExample example);

    int updateByExampleSelective(@Param("record") Testinv0420b record, @Param("example") Testinv0420bExample example);

    int updateByExample(@Param("record") Testinv0420b record, @Param("example") Testinv0420bExample example);
}