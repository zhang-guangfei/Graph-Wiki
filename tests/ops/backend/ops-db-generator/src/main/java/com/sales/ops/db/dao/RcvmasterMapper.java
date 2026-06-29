package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Rcvmaster;
import com.sales.ops.db.entity.RcvmasterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvmasterMapper {
    long countByExample(RcvmasterExample example);

    int deleteByExample(RcvmasterExample example);

    int deleteByPrimaryKey(String rorderNo);

    int insert(Rcvmaster record);

    int insertSelective(Rcvmaster record);

    List<Rcvmaster> selectByExample(RcvmasterExample example);

    Rcvmaster selectByPrimaryKey(String rorderNo);

    int updateByExampleSelective(@Param("record") Rcvmaster record, @Param("example") RcvmasterExample example);

    int updateByExample(@Param("record") Rcvmaster record, @Param("example") RcvmasterExample example);

    int updateByPrimaryKeySelective(Rcvmaster record);

    int updateByPrimaryKey(Rcvmaster record);
}