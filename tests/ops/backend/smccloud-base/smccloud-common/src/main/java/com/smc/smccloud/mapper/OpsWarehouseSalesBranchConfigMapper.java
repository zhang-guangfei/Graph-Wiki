package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.CalTransDayEntity;
import com.smc.smccloud.model.OpsWarehouseSalesbranchConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-27 16:09
 * Description:
 */
@DS("opsdb")
@Mapper
public interface OpsWarehouseSalesBranchConfigMapper extends BaseMapper<OpsWarehouseSalesbranchConfigDO> {

    @Select(" select a.sales_branch_id,a.warehouse_code,a.priority,a.delivery_day,w.warehouse_type\n" +
            " from ops_warehouse_salesbranch_config a INNER JOIN ops_warehouse w on a.warehouse_code = w.warehouse_code\n" +
            " where a.sales_branch_id = #{deptNo} and a.delflag=0 and w.warehouse_type = 'MASTER' order by a.priority asc")
    List<CalTransDayEntity> getTranDaysByDeptNo(@Param("deptNo") String deptNo);

    /**
     * 优先仓库数据
     * @return
     */
    @Select(" select *From (select *,ROW_NUMBER() over(partition by sales_branch_id  order by priority) as idx "+
              " From   ops_warehouse_salesbranch_config where delflag=0) A where idx=1 " )
    List<OpsWarehouseSalesbranchConfigDO> getWarehouseSalesBranchConfigForPriority();

    /**
     * 优先物流中心数据
     * @return
     */
    @Select(" select *From (select a.*,ROW_NUMBER() over(partition by sales_branch_id  order by priority) as idx From   " +
            "ops_warehouse_salesbranch_config A join ops_warehouse b on a.warehouse_code=b.warehouse_code " +
            "where a.delflag=0 and b.delflag=0 and b.warehouse_type='Master') A where idx=1" )
    List<OpsWarehouseSalesbranchConfigDO> getWarehouseSalesBranchConfigForPriorityByMaster();
}
