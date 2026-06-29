package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OpsPurchaseOrderDOCommon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author lyc
 * @Date 2023/10/20 11:37
 * @Descripton TODO
 */
@DS("opsdb")
@Mapper
public interface OpsPurchaseOrderMapperCommon extends BaseMapper<OpsPurchaseOrderDOCommon> {
    @Select(" select * from ops_purchaseOrder where orderNo = #{orderNo} and itemNo = #{itemNo} and isnull(splitItemNo,0) = #{splitItemNo}")
    OpsPurchaseOrderDOCommon getInfoByOrderNoWithitemNoWithSplitItemNo(@Param("orderNo") String orderNo,
                                                                 @Param("itemNo") int itemNo,
                                                                 @Param("splitItemNo") int splitItemNo);
}
