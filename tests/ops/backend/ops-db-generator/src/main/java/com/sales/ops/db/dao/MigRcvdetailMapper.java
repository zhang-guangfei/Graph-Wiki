package com.sales.ops.db.dao;

import com.sales.ops.db.entity.MigRcvdetail;
import com.sales.ops.db.entity.MigRcvdetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MigRcvdetailMapper {
    long countByExample(MigRcvdetailExample example);

    int deleteByExample(MigRcvdetailExample example);

    int insert(MigRcvdetail record);

    int insertSelective(MigRcvdetail record);

    List<MigRcvdetail> selectByExample(MigRcvdetailExample example);

    int updateByExampleSelective(@Param("record") MigRcvdetail record, @Param("example") MigRcvdetailExample example);

    int updateByExample(@Param("record") MigRcvdetail record, @Param("example") MigRcvdetailExample example);
}