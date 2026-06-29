package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.SpecOrder.SpecOrderDO;
import com.smc.smccloud.model.SpecOrder.SpecOrderExpDetailData;
import com.smc.smccloud.model.specorder.SpecOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@DS("opsdb")
@Mapper
public interface SpecOrderMapper extends BaseMapper<SpecOrderDO> {

    @Select("SELECT b.id,a.customer_no,b.invoice_no,a.price,a.dlv_date,a.model_no,a.quantity,b.volume,\n" +
            "b.case_no,b.weight,a.org_currency,b.ship_date,a.remark,b.box_type,\n" +
            "a.full_order_no FROM spec_order a inner join expdetail b ON a.order_no = b.order_no AND a.order_item = b.item_no")
    List<SpecOrderExpDetailData> findSpecOrderExpDetail();

}
