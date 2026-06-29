package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InvPropView;
import com.sales.ops.db.entity.InvPropViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InvPropViewMapper {
    long countByExample(InvPropViewExample example);

    int deleteByExample(InvPropViewExample example);

    int insert(InvPropView record);

    int insertSelective(InvPropView record);

    List<InvPropView> selectByExample(InvPropViewExample example);

    int updateByExampleSelective(@Param("record") InvPropView record, @Param("example") InvPropViewExample example);

    int updateByExample(@Param("record") InvPropView record, @Param("example") InvPropViewExample example);
}