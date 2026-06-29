package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InvView;
import com.sales.ops.db.entity.InvViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InvViewMapper {
    long countByExample(InvViewExample example);

    int deleteByExample(InvViewExample example);

    int insert(InvView record);

    int insertSelective(InvView record);

    List<InvView> selectByExample(InvViewExample example);

    int updateByExampleSelective(@Param("record") InvView record, @Param("example") InvViewExample example);

    int updateByExample(@Param("record") InvView record, @Param("example") InvViewExample example);
}