package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoInvoice;
import com.sales.ops.db.entity.OpsPoInvoiceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoInvoiceMapper {
    long countByExample(OpsPoInvoiceExample example);

    int deleteByExample(OpsPoInvoiceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPoInvoice record);

    int insertSelective(OpsPoInvoice record);

    List<OpsPoInvoice> selectByExample(OpsPoInvoiceExample example);

    OpsPoInvoice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPoInvoice record, @Param("example") OpsPoInvoiceExample example);

    int updateByExample(@Param("record") OpsPoInvoice record, @Param("example") OpsPoInvoiceExample example);

    int updateByPrimaryKeySelective(OpsPoInvoice record);

    int updateByPrimaryKey(OpsPoInvoice record);
}