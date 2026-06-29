package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRoQtyAdjust;
import com.sales.ops.db.entity.OpsRoQtyAdjustExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRoQtyAdjustMapper {
    long countByExample(OpsRoQtyAdjustExample example);

    int deleteByExample(OpsRoQtyAdjustExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsRoQtyAdjust record);

    int insertSelective(OpsRoQtyAdjust record);

    List<OpsRoQtyAdjust> selectByExample(OpsRoQtyAdjustExample example);

    OpsRoQtyAdjust selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsRoQtyAdjust record, @Param("example") OpsRoQtyAdjustExample example);

    int updateByExample(@Param("record") OpsRoQtyAdjust record, @Param("example") OpsRoQtyAdjustExample example);

    int updateByPrimaryKeySelective(OpsRoQtyAdjust record);

    int updateByPrimaryKey(OpsRoQtyAdjust record);
}