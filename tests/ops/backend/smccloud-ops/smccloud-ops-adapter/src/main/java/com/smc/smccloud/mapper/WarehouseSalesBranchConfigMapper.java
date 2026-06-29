package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Author: B90034
 * Date: 2022-03-19 16:56
 * Description:
 */
@DS("opsdb")
@Mapper
public interface WarehouseSalesBranchConfigMapper {

    /**
     * 查询营业所最优先出库仓库
     *
     * @param deptNo 营业所代码
     * @return 仓库代码
     */
    @Select(" select Top(1) warehouse_code from ops_warehouse_salesbranch_config " +
            " where sales_branch_id=#{deptNo} and delflag=0 order by priority")
    String getDeptPriorityWarehouse(@Param("deptNo") String deptNo);

    /**
     * 查询营业所最优先出库的中心仓库
     *
     * @param deptNo 营业所代码
     * @return 仓库代码
     */
    @Select("select Top(1) b.warehouse_code " +
            "  from ops_warehouse a with(nolock) inner join ops_warehouse_salesbranch_config b with(nolock) " +
            "  on a.warehouse_code=b.warehouse_code " +
            "  where b.sales_branch_id=#{deptNo} and a.warehouse_type='MASTER' and b.delflag=0 order by priority ")
    String getDeptPriorityCentralWarehouse(@Param("deptNo") String deptNo);
}
