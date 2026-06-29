package com.sales.ops.db.dao;

import com.sales.ops.db.entity.DboTblCustproperty;
import com.sales.ops.db.entity.DboTblCustpropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DboTblCustpropertyMapper {
    long countByExample(DboTblCustpropertyExample example);

    int deleteByExample(DboTblCustpropertyExample example);

    int insert(DboTblCustproperty record);

    int insertSelective(DboTblCustproperty record);

    List<DboTblCustproperty> selectByExample(DboTblCustpropertyExample example);

    int updateByExampleSelective(@Param("record") DboTblCustproperty record, @Param("example") DboTblCustpropertyExample example);

    int updateByExample(@Param("record") DboTblCustproperty record, @Param("example") DboTblCustpropertyExample example);
}