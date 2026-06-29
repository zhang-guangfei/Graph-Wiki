package com.sales.ops.db.dao;

import com.sales.ops.db.entity.CreditOpsBlockOrder;
import com.sales.ops.db.entity.CreditOpsBlockOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreditOpsBlockOrderMapper {
    long countByExample(CreditOpsBlockOrderExample example);

    int deleteByExample(CreditOpsBlockOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CreditOpsBlockOrder record);

    int insertSelective(CreditOpsBlockOrder record);

    List<CreditOpsBlockOrder> selectByExample(CreditOpsBlockOrderExample example);

    CreditOpsBlockOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CreditOpsBlockOrder record, @Param("example") CreditOpsBlockOrderExample example);

    int updateByExample(@Param("record") CreditOpsBlockOrder record, @Param("example") CreditOpsBlockOrderExample example);

    int updateByPrimaryKeySelective(CreditOpsBlockOrder record);

    int updateByPrimaryKey(CreditOpsBlockOrder record);
}