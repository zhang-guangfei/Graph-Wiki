package com.sales.ops.db.dao;

import com.sales.ops.db.entity.AbnormalCreateDb;
import com.sales.ops.db.entity.AbnormalCreateDbExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AbnormalCreateDbMapper {
    long countByExample(AbnormalCreateDbExample example);

    int deleteByExample(AbnormalCreateDbExample example);

    int deleteByPrimaryKey(String doId);

    int insert(AbnormalCreateDb record);

    int insertSelective(AbnormalCreateDb record);

    List<AbnormalCreateDb> selectByExample(AbnormalCreateDbExample example);

    AbnormalCreateDb selectByPrimaryKey(String doId);

    int updateByExampleSelective(@Param("record") AbnormalCreateDb record, @Param("example") AbnormalCreateDbExample example);

    int updateByExample(@Param("record") AbnormalCreateDb record, @Param("example") AbnormalCreateDbExample example);

    int updateByPrimaryKeySelective(AbnormalCreateDb record);

    int updateByPrimaryKey(AbnormalCreateDb record);
}