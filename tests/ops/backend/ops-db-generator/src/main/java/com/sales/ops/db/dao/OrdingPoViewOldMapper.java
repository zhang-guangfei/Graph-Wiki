package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrdingPoViewOld;
import com.sales.ops.db.entity.OrdingPoViewOldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdingPoViewOldMapper {
    long countByExample(OrdingPoViewOldExample example);

    int deleteByExample(OrdingPoViewOldExample example);

    int insert(OrdingPoViewOld record);

    int insertSelective(OrdingPoViewOld record);

    List<OrdingPoViewOld> selectByExample(OrdingPoViewOldExample example);

    int updateByExampleSelective(@Param("record") OrdingPoViewOld record, @Param("example") OrdingPoViewOldExample example);

    int updateByExample(@Param("record") OrdingPoViewOld record, @Param("example") OrdingPoViewOldExample example);
}