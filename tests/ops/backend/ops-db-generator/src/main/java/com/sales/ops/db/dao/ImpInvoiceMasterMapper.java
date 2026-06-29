package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpInvoiceMaster;
import com.sales.ops.db.entity.ImpInvoiceMasterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpInvoiceMasterMapper {
    long countByExample(ImpInvoiceMasterExample example);

    int deleteByExample(ImpInvoiceMasterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ImpInvoiceMaster record);

    int insertSelective(ImpInvoiceMaster record);

    List<ImpInvoiceMaster> selectByExample(ImpInvoiceMasterExample example);

    ImpInvoiceMaster selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ImpInvoiceMaster record, @Param("example") ImpInvoiceMasterExample example);

    int updateByExample(@Param("record") ImpInvoiceMaster record, @Param("example") ImpInvoiceMasterExample example);

    int updateByPrimaryKeySelective(ImpInvoiceMaster record);

    int updateByPrimaryKey(ImpInvoiceMaster record);
}