package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TblReplstockinterceptrule;
import com.sales.ops.db.entity.TblReplstockinterceptruleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblReplstockinterceptruleMapper {
    long countByExample(TblReplstockinterceptruleExample example);

    int deleteByExample(TblReplstockinterceptruleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TblReplstockinterceptrule record);

    int insertSelective(TblReplstockinterceptrule record);

    List<TblReplstockinterceptrule> selectByExample(TblReplstockinterceptruleExample example);

    TblReplstockinterceptrule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TblReplstockinterceptrule record, @Param("example") TblReplstockinterceptruleExample example);

    int updateByExample(@Param("record") TblReplstockinterceptrule record, @Param("example") TblReplstockinterceptruleExample example);

    int updateByPrimaryKeySelective(TblReplstockinterceptrule record);

    int updateByPrimaryKey(TblReplstockinterceptrule record);
}