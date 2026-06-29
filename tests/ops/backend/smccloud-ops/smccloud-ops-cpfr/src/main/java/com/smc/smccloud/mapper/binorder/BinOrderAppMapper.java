package com.smc.smccloud.mapper.binorder;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.binorder.BinOrderAppDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-10
 */
@Mapper
public interface BinOrderAppMapper extends BaseMapper<BinOrderAppDO> {

    @Update(" update bin_order_app set model_count=t.C, model_qty=t.Q,eAmount=t.P " +
            " from bin_order_app b inner join ( " +
            " select count(*) as C,sum(confirm_qty) as q,sum(eprice*confirm_qty) as p " +
            " from bin_order_detail " +
            " where app_id=#{appId}) T on 1=1 " +
            " where b.app_id=#{appId}")
    Integer updateSumApplyQty(Long appId);

    @Select(" select  top 1 supplyId from product_delivery where modelNo=#{modelNo} and isPrimary=1 and is_deleted=0")
    String getSupplierid(String modelNo);
}
