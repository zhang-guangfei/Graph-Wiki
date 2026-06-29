package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.RcvdetailExample;
import com.sales.ops.db.entity.RcvdetailKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvdetailMapper {
    long countByExample(RcvdetailExample example);

    int deleteByExample(RcvdetailExample example);

    int deleteByPrimaryKey(RcvdetailKey key);

    int insert(Rcvdetail record);

    int insertSelective(Rcvdetail record);

    List<Rcvdetail> selectByExample(RcvdetailExample example);

    Rcvdetail selectByPrimaryKey(RcvdetailKey key);

    int updateByExampleSelective(@Param("record") Rcvdetail record, @Param("example") RcvdetailExample example);

    int updateByExample(@Param("record") Rcvdetail record, @Param("example") RcvdetailExample example);

    int updateByPrimaryKeySelective(Rcvdetail record);

    int updateByPrimaryKey(Rcvdetail record);
}