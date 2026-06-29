package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCoordinate;
import com.sales.ops.db.entity.OpsCoordinateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCoordinateMapper {
    long countByExample(OpsCoordinateExample example);

    int deleteByExample(OpsCoordinateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsCoordinate record);

    int insertSelective(OpsCoordinate record);

    List<OpsCoordinate> selectByExample(OpsCoordinateExample example);

    OpsCoordinate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsCoordinate record, @Param("example") OpsCoordinateExample example);

    int updateByExample(@Param("record") OpsCoordinate record, @Param("example") OpsCoordinateExample example);

    int updateByPrimaryKeySelective(OpsCoordinate record);

    int updateByPrimaryKey(OpsCoordinate record);
}