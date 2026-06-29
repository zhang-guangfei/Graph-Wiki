package com.sales.ops.db.dao;

import com.sales.ops.db.entity.RcvView;
import com.sales.ops.db.entity.RcvViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvViewMapper {
    long countByExample(RcvViewExample example);

    int deleteByExample(RcvViewExample example);

    int insert(RcvView record);

    int insertSelective(RcvView record);

    List<RcvView> selectByExample(RcvViewExample example);

    int updateByExampleSelective(@Param("record") RcvView record, @Param("example") RcvViewExample example);

    int updateByExample(@Param("record") RcvView record, @Param("example") RcvViewExample example);
}