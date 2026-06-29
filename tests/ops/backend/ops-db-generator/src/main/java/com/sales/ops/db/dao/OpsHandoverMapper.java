package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsHandover;
import com.sales.ops.db.entity.OpsHandoverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsHandoverMapper {
    long countByExample(OpsHandoverExample example);

    int deleteByExample(OpsHandoverExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsHandover record);

    int insertSelective(OpsHandover record);

    List<OpsHandover> selectByExample(OpsHandoverExample example);

    OpsHandover selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsHandover record, @Param("example") OpsHandoverExample example);

    int updateByExample(@Param("record") OpsHandover record, @Param("example") OpsHandoverExample example);

    int updateByPrimaryKeySelective(OpsHandover record);

    int updateByPrimaryKey(OpsHandover record);
}