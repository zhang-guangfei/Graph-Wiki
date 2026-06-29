package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ExpdetailBarcode;
import com.sales.ops.db.entity.ExpdetailBarcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExpdetailBarcodeMapper {
    long countByExample(ExpdetailBarcodeExample example);

    int deleteByExample(ExpdetailBarcodeExample example);

    int insert(ExpdetailBarcode record);

    int insertSelective(ExpdetailBarcode record);

    List<ExpdetailBarcode> selectByExample(ExpdetailBarcodeExample example);

    int updateByExampleSelective(@Param("record") ExpdetailBarcode record, @Param("example") ExpdetailBarcodeExample example);

    int updateByExample(@Param("record") ExpdetailBarcode record, @Param("example") ExpdetailBarcodeExample example);
}