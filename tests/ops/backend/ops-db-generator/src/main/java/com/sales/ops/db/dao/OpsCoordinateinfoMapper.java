package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCoordinateinfo;
import com.sales.ops.db.entity.OpsCoordinateinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCoordinateinfoMapper {
    long countByExample(OpsCoordinateinfoExample example);

    int deleteByExample(OpsCoordinateinfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsCoordinateinfo record);

    int insertSelective(OpsCoordinateinfo record);

    List<OpsCoordinateinfo> selectByExampleWithBLOBs(OpsCoordinateinfoExample example);

    List<OpsCoordinateinfo> selectByExample(OpsCoordinateinfoExample example);

    OpsCoordinateinfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsCoordinateinfo record, @Param("example") OpsCoordinateinfoExample example);

    int updateByExampleWithBLOBs(@Param("record") OpsCoordinateinfo record, @Param("example") OpsCoordinateinfoExample example);

    int updateByExample(@Param("record") OpsCoordinateinfo record, @Param("example") OpsCoordinateinfoExample example);

    int updateByPrimaryKeySelective(OpsCoordinateinfo record);

    int updateByPrimaryKeyWithBLOBs(OpsCoordinateinfo record);
}