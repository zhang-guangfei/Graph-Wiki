package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InvoiceSplit;
import com.sales.ops.db.entity.InvoiceSplitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InvoiceSplitMapper {
    long countByExample(InvoiceSplitExample example);

    int deleteByExample(InvoiceSplitExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InvoiceSplit record);

    int insertSelective(InvoiceSplit record);

    List<InvoiceSplit> selectByExample(InvoiceSplitExample example);

    InvoiceSplit selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InvoiceSplit record, @Param("example") InvoiceSplitExample example);

    int updateByExample(@Param("record") InvoiceSplit record, @Param("example") InvoiceSplitExample example);

    int updateByPrimaryKeySelective(InvoiceSplit record);

    int updateByPrimaryKey(InvoiceSplit record);
}