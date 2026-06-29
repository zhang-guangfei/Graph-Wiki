package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TransferInfo;
import com.sales.ops.db.entity.TransferInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransferInfoMapper {
    long countByExample(TransferInfoExample example);

    int deleteByExample(TransferInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TransferInfo record);

    int insertSelective(TransferInfo record);

    List<TransferInfo> selectByExample(TransferInfoExample example);

    TransferInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TransferInfo record, @Param("example") TransferInfoExample example);

    int updateByExample(@Param("record") TransferInfo record, @Param("example") TransferInfoExample example);

    int updateByPrimaryKeySelective(TransferInfo record);

    int updateByPrimaryKey(TransferInfo record);
}