package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.model.adapter.ProductBomInventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-05-09 11:37
 * Description:
 */
@DS("opsdb")
@Mapper
public interface ProductBomMapper {

//    @Select("select b.bomId, b.Priority_level, d.ModelNo, d.Quantity, d.UpdateTime " +
//            "  from product_bom b inner join product_bom_detail d on b.bomId=d.bomId " +
//            "  where b.ModelNo=#{modelNo} and b.IsValid=1 ORDER BY Priority_level ASC")
//    List<ProductBomInfo> listProductBomInfo(@Param("modelNo") String modelNo);

    @Select("select a.bomId, a.Priority_level, b.ModelNo, b.Quantity " +
            "from product_bom a with(nolock) " +
            "inner join product_bom_detail b with(nolock) on a.bomId=b.bomId " +
            "where a.ModelNo=#{modelNo} and a.IsValid=1 " +
            "ORDER BY a.Priority_level ASC")
    List<ProductBomInventory> listProductBomInfo(@Param("modelNo") String modelNo);
}
