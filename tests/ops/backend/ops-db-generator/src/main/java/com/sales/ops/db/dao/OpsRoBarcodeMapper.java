package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.db.entity.OpsRoBarcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRoBarcodeMapper {
    long countByExample(OpsRoBarcodeExample example);

    int deleteByExample(OpsRoBarcodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsRoBarcode record);

    int insertSelective(OpsRoBarcode record);

    List<OpsRoBarcode> selectByExample(OpsRoBarcodeExample example);

    OpsRoBarcode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsRoBarcode record, @Param("example") OpsRoBarcodeExample example);

    int updateByExample(@Param("record") OpsRoBarcode record, @Param("example") OpsRoBarcodeExample example);

    int updateByPrimaryKeySelective(OpsRoBarcode record);

    int updateByPrimaryKey(OpsRoBarcode record);
}