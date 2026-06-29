package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoInvoiceMaster;
import com.sales.ops.db.entity.OpsPoInvoiceMasterExample;
import com.sales.ops.db.entity.OpsPoInvoiceMasterKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoInvoiceMasterMapper {
    long countByExample(OpsPoInvoiceMasterExample example);

    int deleteByExample(OpsPoInvoiceMasterExample example);

    int deleteByPrimaryKey(OpsPoInvoiceMasterKey key);

    int insert(OpsPoInvoiceMaster record);

    int insertSelective(OpsPoInvoiceMaster record);

    List<OpsPoInvoiceMaster> selectByExampleWithBLOBs(OpsPoInvoiceMasterExample example);

    List<OpsPoInvoiceMaster> selectByExample(OpsPoInvoiceMasterExample example);

    OpsPoInvoiceMaster selectByPrimaryKey(OpsPoInvoiceMasterKey key);

    int updateByExampleSelective(@Param("record") OpsPoInvoiceMaster record, @Param("example") OpsPoInvoiceMasterExample example);

    int updateByExampleWithBLOBs(@Param("record") OpsPoInvoiceMaster record, @Param("example") OpsPoInvoiceMasterExample example);

    int updateByExample(@Param("record") OpsPoInvoiceMaster record, @Param("example") OpsPoInvoiceMasterExample example);

    int updateByPrimaryKeySelective(OpsPoInvoiceMaster record);

    int updateByPrimaryKeyWithBLOBs(OpsPoInvoiceMaster record);

    int updateByPrimaryKey(OpsPoInvoiceMaster record);
}