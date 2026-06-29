package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SupplierFcost;
import com.sales.ops.db.entity.SupplierFcostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplierFcostMapper {
    long countByExample(SupplierFcostExample example);

    int deleteByExample(SupplierFcostExample example);

    int deleteByPrimaryKey(String code);

    int insert(SupplierFcost record);

    int insertSelective(SupplierFcost record);

    List<SupplierFcost> selectByExample(SupplierFcostExample example);

    SupplierFcost selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") SupplierFcost record, @Param("example") SupplierFcostExample example);

    int updateByExample(@Param("record") SupplierFcost record, @Param("example") SupplierFcostExample example);

    int updateByPrimaryKeySelective(SupplierFcost record);

    int updateByPrimaryKey(SupplierFcost record);
}