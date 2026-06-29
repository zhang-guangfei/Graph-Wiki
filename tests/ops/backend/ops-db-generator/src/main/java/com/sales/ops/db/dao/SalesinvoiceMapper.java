package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Salesinvoice;
import com.sales.ops.db.entity.SalesinvoiceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalesinvoiceMapper {
    long countByExample(SalesinvoiceExample example);

    int deleteByExample(SalesinvoiceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Salesinvoice record);

    int insertSelective(Salesinvoice record);

    List<Salesinvoice> selectByExample(SalesinvoiceExample example);

    Salesinvoice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Salesinvoice record, @Param("example") SalesinvoiceExample example);

    int updateByExample(@Param("record") Salesinvoice record, @Param("example") SalesinvoiceExample example);

    int updateByPrimaryKeySelective(Salesinvoice record);

    int updateByPrimaryKey(Salesinvoice record);
}