package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoTranstypeConfig;
import com.sales.ops.db.entity.OpsPoTranstypeConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoTranstypeConfigMapper {
    long countByExample(OpsPoTranstypeConfigExample example);

    int deleteByExample(OpsPoTranstypeConfigExample example);

    int deleteByPrimaryKey(String modelno);

    int insert(OpsPoTranstypeConfig record);

    int insertSelective(OpsPoTranstypeConfig record);

    List<OpsPoTranstypeConfig> selectByExample(OpsPoTranstypeConfigExample example);

    OpsPoTranstypeConfig selectByPrimaryKey(String modelno);

    int updateByExampleSelective(@Param("record") OpsPoTranstypeConfig record, @Param("example") OpsPoTranstypeConfigExample example);

    int updateByExample(@Param("record") OpsPoTranstypeConfig record, @Param("example") OpsPoTranstypeConfigExample example);

    int updateByPrimaryKeySelective(OpsPoTranstypeConfig record);

    int updateByPrimaryKey(OpsPoTranstypeConfig record);
}