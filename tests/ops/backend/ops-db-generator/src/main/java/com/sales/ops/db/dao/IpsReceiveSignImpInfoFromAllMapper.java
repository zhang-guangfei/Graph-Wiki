package com.sales.ops.db.dao;

import com.sales.ops.db.entity.IpsReceiveSignImpInfoFromAll;
import com.sales.ops.db.entity.IpsReceiveSignImpInfoFromAllExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IpsReceiveSignImpInfoFromAllMapper {
    long countByExample(IpsReceiveSignImpInfoFromAllExample example);

    int deleteByExample(IpsReceiveSignImpInfoFromAllExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IpsReceiveSignImpInfoFromAll record);

    int insertSelective(IpsReceiveSignImpInfoFromAll record);

    List<IpsReceiveSignImpInfoFromAll> selectByExampleWithBLOBs(IpsReceiveSignImpInfoFromAllExample example);

    List<IpsReceiveSignImpInfoFromAll> selectByExample(IpsReceiveSignImpInfoFromAllExample example);

    IpsReceiveSignImpInfoFromAll selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IpsReceiveSignImpInfoFromAll record, @Param("example") IpsReceiveSignImpInfoFromAllExample example);

    int updateByExampleWithBLOBs(@Param("record") IpsReceiveSignImpInfoFromAll record, @Param("example") IpsReceiveSignImpInfoFromAllExample example);

    int updateByExample(@Param("record") IpsReceiveSignImpInfoFromAll record, @Param("example") IpsReceiveSignImpInfoFromAllExample example);

    int updateByPrimaryKeySelective(IpsReceiveSignImpInfoFromAll record);

    int updateByPrimaryKeyWithBLOBs(IpsReceiveSignImpInfoFromAll record);

    int updateByPrimaryKey(IpsReceiveSignImpInfoFromAll record);
}