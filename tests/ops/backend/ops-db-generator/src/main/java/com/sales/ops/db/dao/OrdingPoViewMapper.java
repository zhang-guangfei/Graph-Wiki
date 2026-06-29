package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrdingPoView;
import com.sales.ops.db.entity.OrdingPoViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdingPoViewMapper {
    long countByExample(OrdingPoViewExample example);

    int deleteByExample(OrdingPoViewExample example);

    int insert(OrdingPoView record);

    int insertSelective(OrdingPoView record);

    List<OrdingPoView> selectByExample(OrdingPoViewExample example);

    int updateByExampleSelective(@Param("record") OrdingPoView record, @Param("example") OrdingPoViewExample example);

    int updateByExample(@Param("record") OrdingPoView record, @Param("example") OrdingPoViewExample example);
}