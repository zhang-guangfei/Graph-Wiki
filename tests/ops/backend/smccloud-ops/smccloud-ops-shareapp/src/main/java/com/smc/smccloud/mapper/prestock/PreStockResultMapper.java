package com.smc.smccloud.mapper.prestock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.smc.smccloud.model.prestock.PreStockResultDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author B90034
 * @since 2021-11-03
 */
@DS("sharedb")
@Mapper
public interface PreStockResultMapper extends BaseMapper<PreStockResultDO> {

    // Add by Dengdenghui 2022-11-23 for bug-8754

    /**
     * 查询申请的调库单号
     *
     * @param applyNo 申请号
     * @return 调库单号
     */
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Select("select Top(1) trans_no + '-' + CAST(item_no as varchar) from ops_core.dbo.trans_order with(nolock) " +
            " where from_no=#{applyNo} order by item_no desc")
    String getTransferOrderNo(@Param("applyNo") String applyNo);

    /**
     * 根据申请号查最近的请购单
     *
     * @param applyNo 申请号
     * @return 最近的请购单
     */
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Select("select Top(1) orderNo, itemNo, requestTime from ops_core.dbo.ops_requestPurchase with(nolock) where orderNo= " +
            " (select max(orderNo) as orderNo from ops_core.dbo.ops_requestPurchase with(nolock) where corderNO=#{applyNo}) " +
            " order by itemNo desc ")
    OpsRequestpurchase getLastRequestPurchaseOrder(@Param("applyNo") String applyNo);

//    /**
//     * 查询申请的请购单号
//     *
//     * @param applyNo   申请号
//     * @param startTime 请购时间
//     * @param endTime   请购时间
//     * @return 请购单号
//     */
//    @DS("opsdb")
//    @Select("select Top(1) orderNo + '-' + CAST(itemNo as varchar) from ops_core.dbo.ops_requestPurchase with(nolock) " +
//            " where corderNO=#{applyNo} and requestTime between #{startTime} and #{endTime} order by requestTime desc ")
//    String getPurchaseOrderNo(@Param("applyNo") String applyNo, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    // End

}
