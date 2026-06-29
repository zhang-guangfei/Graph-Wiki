package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SalesinvoiceArrivalConfig;
import com.sales.ops.db.entity.SalesinvoiceArrivalConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalesinvoiceArrivalConfigMapper {
    long countByExample(SalesinvoiceArrivalConfigExample example);

    int deleteByExample(SalesinvoiceArrivalConfigExample example);

    int insert(SalesinvoiceArrivalConfig record);

    int insertSelective(SalesinvoiceArrivalConfig record);

    List<SalesinvoiceArrivalConfig> selectByExample(SalesinvoiceArrivalConfigExample example);

    int updateByExampleSelective(@Param("record") SalesinvoiceArrivalConfig record, @Param("example") SalesinvoiceArrivalConfigExample example);

    int updateByExample(@Param("record") SalesinvoiceArrivalConfig record, @Param("example") SalesinvoiceArrivalConfigExample example);
}