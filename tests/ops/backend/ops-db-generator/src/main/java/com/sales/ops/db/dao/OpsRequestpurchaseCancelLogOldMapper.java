package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRequestpurchaseCancelLogOld;
import com.sales.ops.db.entity.OpsRequestpurchaseCancelLogOldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRequestpurchaseCancelLogOldMapper {
    long countByExample(OpsRequestpurchaseCancelLogOldExample example);

    int deleteByExample(OpsRequestpurchaseCancelLogOldExample example);

    int insert(OpsRequestpurchaseCancelLogOld record);

    int insertSelective(OpsRequestpurchaseCancelLogOld record);

    List<OpsRequestpurchaseCancelLogOld> selectByExample(OpsRequestpurchaseCancelLogOldExample example);

    int updateByExampleSelective(@Param("record") OpsRequestpurchaseCancelLogOld record, @Param("example") OpsRequestpurchaseCancelLogOldExample example);

    int updateByExample(@Param("record") OpsRequestpurchaseCancelLogOld record, @Param("example") OpsRequestpurchaseCancelLogOldExample example);
}