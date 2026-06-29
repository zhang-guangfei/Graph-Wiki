package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRoConfirmLog;
import com.sales.ops.db.entity.OpsRoConfirmLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRoConfirmLogMapper {
    long countByExample(OpsRoConfirmLogExample example);

    int deleteByExample(OpsRoConfirmLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsRoConfirmLog record);

    int insertSelective(OpsRoConfirmLog record);

    List<OpsRoConfirmLog> selectByExample(OpsRoConfirmLogExample example);

    OpsRoConfirmLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsRoConfirmLog record, @Param("example") OpsRoConfirmLogExample example);

    int updateByExample(@Param("record") OpsRoConfirmLog record, @Param("example") OpsRoConfirmLogExample example);

    int updateByPrimaryKeySelective(OpsRoConfirmLog record);

    int updateByPrimaryKey(OpsRoConfirmLog record);
}