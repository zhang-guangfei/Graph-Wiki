package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SalesinvoiceMidInfo;
import com.sales.ops.db.entity.SalesinvoiceMidInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalesinvoiceMidInfoMapper {
    long countByExample(SalesinvoiceMidInfoExample example);

    int deleteByExample(SalesinvoiceMidInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SalesinvoiceMidInfo record);

    int insertSelective(SalesinvoiceMidInfo record);

    List<SalesinvoiceMidInfo> selectByExample(SalesinvoiceMidInfoExample example);

    SalesinvoiceMidInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SalesinvoiceMidInfo record, @Param("example") SalesinvoiceMidInfoExample example);

    int updateByExample(@Param("record") SalesinvoiceMidInfo record, @Param("example") SalesinvoiceMidInfoExample example);

    int updateByPrimaryKeySelective(SalesinvoiceMidInfo record);

    int updateByPrimaryKey(SalesinvoiceMidInfo record);
}