package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TblProductinterceptrule;
import com.sales.ops.db.entity.TblProductinterceptruleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblProductinterceptruleMapper {
    long countByExample(TblProductinterceptruleExample example);

    int deleteByExample(TblProductinterceptruleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TblProductinterceptrule record);

    int insertSelective(TblProductinterceptrule record);

    List<TblProductinterceptrule> selectByExample(TblProductinterceptruleExample example);

    TblProductinterceptrule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TblProductinterceptrule record, @Param("example") TblProductinterceptruleExample example);

    int updateByExample(@Param("record") TblProductinterceptrule record, @Param("example") TblProductinterceptruleExample example);

    int updateByPrimaryKeySelective(TblProductinterceptrule record);

    int updateByPrimaryKey(TblProductinterceptrule record);
}