package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Expdetail;
import com.sales.ops.db.entity.ExpdetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExpdetailMapper {
    long countByExample(ExpdetailExample example);

    int deleteByExample(ExpdetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Expdetail record);

    int insertSelective(Expdetail record);

    List<Expdetail> selectByExample(ExpdetailExample example);

    Expdetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Expdetail record, @Param("example") ExpdetailExample example);

    int updateByExample(@Param("record") Expdetail record, @Param("example") ExpdetailExample example);

    int updateByPrimaryKeySelective(Expdetail record);

    int updateByPrimaryKey(Expdetail record);
}