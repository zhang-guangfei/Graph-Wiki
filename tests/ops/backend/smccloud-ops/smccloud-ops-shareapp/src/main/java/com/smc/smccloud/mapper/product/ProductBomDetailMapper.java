package com.smc.smccloud.mapper.product;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.prestock.ProductBomDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-27 13:41
 * Description:
 */
@DS("opsdb")
@Mapper
public interface ProductBomDetailMapper extends BaseMapper<ProductBomDetailDO> {

    /**
     * 根据型号查询可拆分型号详情项
     *
     * @param modelNo 型号
     * @return List
     */
    @Select("select bomId, ModelNo, Quantity, id from product_bom_detail where bomId in ( " +
            "  select Top (1) bomId from product_bom where IsValid=1 and ModelNo=#{modelNo} order by Priority_level asc )")
    List<ProductBomDetailDO> listSplittableDetailByModelNo(@Param("modelNo") String modelNo);

    /**
     * 根据型号查询先行在库可拆分型号详情项
     *
     * @param modelNo 型号
     * @return List
     */
    @Select("select bomId, ModelNo, Quantity, id from product_bom_detail where bomId in ( " +
            "  select Top (1) bomId from product_bom " +
            "  where IsValid=1 and Priority_Complete<>1 and ModelNo=#{modelNo} order by Priority_level asc )")
    List<ProductBomDetailDO> listPreStockSplitDetailByModelNo(@Param("modelNo") String modelNo);
}
