package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsOrderAssignResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-10-24
 */
@DS("opsdb")
@Mapper
public interface OpsAssignReasultMapper {

    @Select(" <script>   SELECT * from ops_order_assign_result where order_no =  #{order_no} and order_item =  #{order_item}  and delflag = '0' order by seqNo  </script> ")
    List<OpsOrderAssignResult> getAssignResult(@Param("order_no") String order_no, @Param("order_item") Integer order_item);

}
