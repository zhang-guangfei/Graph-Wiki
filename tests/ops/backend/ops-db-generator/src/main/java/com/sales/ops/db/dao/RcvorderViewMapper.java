package com.sales.ops.db.dao;

import com.sales.ops.db.entity.RcvorderView;
import com.sales.ops.db.entity.RcvorderViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvorderViewMapper {
    long countByExample(RcvorderViewExample example);

    int deleteByExample(RcvorderViewExample example);

    int insert(RcvorderView record);

    int insertSelective(RcvorderView record);

    List<RcvorderView> selectByExample(RcvorderViewExample example);

    int updateByExampleSelective(@Param("record") RcvorderView record, @Param("example") RcvorderViewExample example);

    int updateByExample(@Param("record") RcvorderView record, @Param("example") RcvorderViewExample example);
}