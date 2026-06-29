package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@DS("opsdb")
@Mapper
public interface WarehouseSalesBranchConfigMapper {

    /**
     * 根据营业所代码/代理店查询出库规则列表
     *
     * @param salesBranchId 营业所代码/代理店
     * @return 出库规则列表
     */
    @Select("select warehouse_code " +
            " from ops_warehouse_salesbranch_config" +
            " where sales_branch_id=#{salesBranchId} and delflag=0 order by priority ")
    List<String> getWarehouseConfig(@Param("salesBranchId") String salesBranchId);

    /**
     * 根据营业所代码/代理店查询出库规则列表 (仅中心库)
     *
     * @param salesBranchId 营业所代码/代理店
     * @return 出库规则列表
     */
    @Select("select b.warehouse_code  " +
            "  from ops_warehouse a with(nolock) inner join ops_warehouse_salesbranch_config b with(nolock) " +
            "  on a.warehouse_code=b.warehouse_code " +
            "  where sales_branch_id=#{salesBranchId} and warehouse_type='MASTER' and b.delflag=0 order by priority  ")
    List<String> getCentralWarehouseConfig(@Param("salesBranchId") String salesBranchId);

    /**
     * 根据营业所代码/代理店查询出库规则列表 (仅分库)
     *
     * @param salesBranchId 营业所代码/代理店
     * @return 出库规则列表
     */
    @Select("select b.warehouse_code  " +
            "  from ops_warehouse a with(nolock) inner join ops_warehouse_salesbranch_config b with(nolock) " +
            "  on a.warehouse_code=b.warehouse_code " +
            "  where sales_branch_id=#{salesBranchId} and warehouse_type='SUB' and b.delflag=0 order by priority  ")
    List<String> getSubWarehouseConfig(@Param("salesBranchId") String salesBranchId);
}
