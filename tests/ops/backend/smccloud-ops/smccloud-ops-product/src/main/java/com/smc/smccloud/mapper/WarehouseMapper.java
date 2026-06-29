package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.model.adapter.StockCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-03-16 15:40
 * Description:
 */
@DS("opsdb")
@Mapper
public interface WarehouseMapper {

    /**
     * 根据营业所代码查询可补库分库信息
     *
     * @param deptNo 营业所代码
     * @return 可补库分库信息
     */
    @Select(" select a.warehouse_code as stockCode, a.warehouse_name as stockName, a.warehouse_type as stockType " +
            "  from ops_warehouse a with(nolock) inner join ops_warehouse_salesbranch_config b with(nolock) " +
            "  on a.warehouse_code=b.warehouse_code " +
            "  where sales_branch_id=#{deptNo} and warehouse_type='SUB' and b.delflag=0 order by priority ")
    List<StockCode> findSubTreasury(@Param("deptNo") String deptNo);

    /**
     * 根据仓库代码列表查出仓库信息
     *
     * @param warehouseList 仓库列表
     * @return 仓库信息
     */
    @Select("<script>" +
            "select warehouse_code as stockCode, warehouse_name as stockName, warehouse_type as stockType " +
            " from ops_warehouse with(nolock) where warehouse_code in " +
            " <foreach collection='warehouseList' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item}  " +
            " </foreach> " +
            "</script>")
    List<StockCode> findGoodsLocation(@Param("warehouseList") List<String> warehouseList);

    /**
     * 根据客户代码查询可出库的代理商委托在库仓库
     *
     * @param customerNo 客户代码
     * @return 代理商委托在库仓库
     */
    @Select("select warehouse_code from ops_core.dbo.ops_warehouse where warehouse_type='WT' " +
            " and delflag=0 and disable_flag=0 and customer_no in " +
            " ( select AgentNo from ops_core.dbo.ops_customer where customer_no=#{customerNo} )" )
    List<String> getWTWarehouseByCustomerNo(@Param("customerNo") String customerNo);
}
