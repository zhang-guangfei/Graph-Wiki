package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsMaxinvoice;
import com.sales.ops.db.entity.OpsMaxinvoiceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsMaxinvoiceMapper {
    long countByExample(OpsMaxinvoiceExample example);

    int deleteByExample(OpsMaxinvoiceExample example);

    int deleteByPrimaryKey(String maxcode);

    int insert(OpsMaxinvoice record);

    int insertSelective(OpsMaxinvoice record);

    List<OpsMaxinvoice> selectByExample(OpsMaxinvoiceExample example);

    OpsMaxinvoice selectByPrimaryKey(String maxcode);

    int updateByExampleSelective(@Param("record") OpsMaxinvoice record, @Param("example") OpsMaxinvoiceExample example);

    int updateByExample(@Param("record") OpsMaxinvoice record, @Param("example") OpsMaxinvoiceExample example);

    int updateByPrimaryKeySelective(OpsMaxinvoice record);

    int updateByPrimaryKey(OpsMaxinvoice record);
}