package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.db.entity.OpsPoTranstypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoTranstypeMapper {
    long countByExample(OpsPoTranstypeExample example);

    int deleteByExample(OpsPoTranstypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(OpsPoTranstype record);

    int insertSelective(OpsPoTranstype record);

    List<OpsPoTranstype> selectByExample(OpsPoTranstypeExample example);

    OpsPoTranstype selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OpsPoTranstype record, @Param("example") OpsPoTranstypeExample example);

    int updateByExample(@Param("record") OpsPoTranstype record, @Param("example") OpsPoTranstypeExample example);

    int updateByPrimaryKeySelective(OpsPoTranstype record);

    int updateByPrimaryKey(OpsPoTranstype record);
}