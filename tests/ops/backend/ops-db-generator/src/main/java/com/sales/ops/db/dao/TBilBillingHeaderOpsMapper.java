package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TBilBillingHeaderOps;
import com.sales.ops.db.entity.TBilBillingHeaderOpsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBilBillingHeaderOpsMapper {
    long countByExample(TBilBillingHeaderOpsExample example);

    int deleteByExample(TBilBillingHeaderOpsExample example);

    int insert(TBilBillingHeaderOps record);

    int insertSelective(TBilBillingHeaderOps record);

    List<TBilBillingHeaderOps> selectByExampleWithBLOBs(TBilBillingHeaderOpsExample example);

    List<TBilBillingHeaderOps> selectByExample(TBilBillingHeaderOpsExample example);

    int updateByExampleSelective(@Param("record") TBilBillingHeaderOps record, @Param("example") TBilBillingHeaderOpsExample example);

    int updateByExampleWithBLOBs(@Param("record") TBilBillingHeaderOps record, @Param("example") TBilBillingHeaderOpsExample example);

    int updateByExample(@Param("record") TBilBillingHeaderOps record, @Param("example") TBilBillingHeaderOpsExample example);
}