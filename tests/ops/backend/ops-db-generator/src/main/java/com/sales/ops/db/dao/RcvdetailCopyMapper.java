package com.sales.ops.db.dao;

import com.sales.ops.db.entity.RcvdetailCopy;
import com.sales.ops.db.entity.RcvdetailCopyExample;
import com.sales.ops.db.entity.RcvdetailCopyKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvdetailCopyMapper {
    long countByExample(RcvdetailCopyExample example);

    int deleteByExample(RcvdetailCopyExample example);

    int deleteByPrimaryKey(RcvdetailCopyKey key);

    int insert(RcvdetailCopy record);

    int insertSelective(RcvdetailCopy record);

    List<RcvdetailCopy> selectByExample(RcvdetailCopyExample example);

    RcvdetailCopy selectByPrimaryKey(RcvdetailCopyKey key);

    int updateByExampleSelective(@Param("record") RcvdetailCopy record, @Param("example") RcvdetailCopyExample example);

    int updateByExample(@Param("record") RcvdetailCopy record, @Param("example") RcvdetailCopyExample example);

    int updateByPrimaryKeySelective(RcvdetailCopy record);

    int updateByPrimaryKey(RcvdetailCopy record);
}