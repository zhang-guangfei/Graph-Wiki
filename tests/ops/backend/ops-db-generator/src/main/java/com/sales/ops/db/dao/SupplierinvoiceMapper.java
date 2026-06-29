package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Supplierinvoice;
import com.sales.ops.db.entity.SupplierinvoiceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupplierinvoiceMapper {
    long countByExample(SupplierinvoiceExample example);

    int deleteByExample(SupplierinvoiceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Supplierinvoice record);

    int insertSelective(Supplierinvoice record);

    List<Supplierinvoice> selectByExample(SupplierinvoiceExample example);

    Supplierinvoice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Supplierinvoice record, @Param("example") SupplierinvoiceExample example);

    int updateByExample(@Param("record") Supplierinvoice record, @Param("example") SupplierinvoiceExample example);

    int updateByPrimaryKeySelective(Supplierinvoice record);

    int updateByPrimaryKey(Supplierinvoice record);
}