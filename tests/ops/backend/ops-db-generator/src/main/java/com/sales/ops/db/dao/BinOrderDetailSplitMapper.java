package com.sales.ops.db.dao;

import com.sales.ops.db.entity.BinOrderDetailSplit;
import com.sales.ops.db.entity.BinOrderDetailSplitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BinOrderDetailSplitMapper {
    long countByExample(BinOrderDetailSplitExample example);

    int deleteByExample(BinOrderDetailSplitExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BinOrderDetailSplit record);

    int insertSelective(BinOrderDetailSplit record);

    List<BinOrderDetailSplit> selectByExample(BinOrderDetailSplitExample example);

    BinOrderDetailSplit selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BinOrderDetailSplit record, @Param("example") BinOrderDetailSplitExample example);

    int updateByExample(@Param("record") BinOrderDetailSplit record, @Param("example") BinOrderDetailSplitExample example);

    int updateByPrimaryKeySelective(BinOrderDetailSplit record);

    int updateByPrimaryKey(BinOrderDetailSplit record);
}