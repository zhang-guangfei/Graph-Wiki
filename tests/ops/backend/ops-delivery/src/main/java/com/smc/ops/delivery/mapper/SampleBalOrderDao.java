package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.dto.expdetail.SampleOrderApplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ops 样品出库数据抽取到 sample_balOrder
 */
@DS("sharedb")
@Mapper
public interface SampleBalOrderDao {


    /**
     * 查询申请子表数据
     * @param orderno
     * @return
     */
    @Select("<script> select top 1 a.* from sampleOrder_apply a inner join sampleOrder_detail b on a.id = b.apply_id where b.order_no = #{orderno}   </script>")
    public List<SampleOrderApplyDto> getSampleApply(@Param("orderno") String orderno);



}
