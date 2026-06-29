package com.sales.ops.db.dao;

import com.sales.ops.db.entity.WGroupcustomers;
import com.sales.ops.db.entity.WGroupcustomersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WGroupcustomersMapper {
    long countByExample(WGroupcustomersExample example);

    int deleteByExample(WGroupcustomersExample example);

    int deleteByPrimaryKey(String groupid);

    int insert(WGroupcustomers record);

    int insertSelective(WGroupcustomers record);

    List<WGroupcustomers> selectByExample(WGroupcustomersExample example);

    WGroupcustomers selectByPrimaryKey(String groupid);

    int updateByExampleSelective(@Param("record") WGroupcustomers record, @Param("example") WGroupcustomersExample example);

    int updateByExample(@Param("record") WGroupcustomers record, @Param("example") WGroupcustomersExample example);

    int updateByPrimaryKeySelective(WGroupcustomers record);

    int updateByPrimaryKey(WGroupcustomers record);
}