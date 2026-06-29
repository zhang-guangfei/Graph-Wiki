package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoInvoiceDetail;
import com.sales.ops.db.entity.OpsPoInvoiceDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoInvoiceDetailMapper {
    long countByExample(OpsPoInvoiceDetailExample example);

    int deleteByExample(OpsPoInvoiceDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPoInvoiceDetail record);

    int insertSelective(OpsPoInvoiceDetail record);

    List<OpsPoInvoiceDetail> selectByExample(OpsPoInvoiceDetailExample example);

    OpsPoInvoiceDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPoInvoiceDetail record, @Param("example") OpsPoInvoiceDetailExample example);

    int updateByExample(@Param("record") OpsPoInvoiceDetail record, @Param("example") OpsPoInvoiceDetailExample example);

    int updateByPrimaryKeySelective(OpsPoInvoiceDetail record);

    int updateByPrimaryKey(OpsPoInvoiceDetail record);
}