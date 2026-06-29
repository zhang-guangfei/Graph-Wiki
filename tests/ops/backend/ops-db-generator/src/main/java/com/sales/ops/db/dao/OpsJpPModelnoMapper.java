package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsJpPModelno;
import com.sales.ops.db.entity.OpsJpPModelnoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsJpPModelnoMapper {
    long countByExample(OpsJpPModelnoExample example);

    int deleteByExample(OpsJpPModelnoExample example);

    int insert(OpsJpPModelno record);

    int insertSelective(OpsJpPModelno record);

    List<OpsJpPModelno> selectByExample(OpsJpPModelnoExample example);

    int updateByExampleSelective(@Param("record") OpsJpPModelno record, @Param("example") OpsJpPModelnoExample example);

    int updateByExample(@Param("record") OpsJpPModelno record, @Param("example") OpsJpPModelnoExample example);
}