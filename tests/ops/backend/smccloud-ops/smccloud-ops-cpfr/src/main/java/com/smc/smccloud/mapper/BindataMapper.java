package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.model.adapter.Product;
import com.smc.smccloud.model.bindata.BindataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

/**
 * @Author edp02 @Date 2021/10/6 14:08
 */
@Mapper
public interface BindataMapper extends BaseMapper<BindataDO> {

    @Select("{ call bindata_updateProductInfo }")
    @Options(statementType = StatementType.CALLABLE)
    void updateProductInfo();

    @Select("{ call bindata_updateProductInfoByModelNo(#{modelNo,mode=IN,jdbcType=VARCHAR}) }")
    @Options(statementType = StatementType.CALLABLE)
    void updateProductInfoByModelNo(@Param("modelNo") String modelNo);

    /**
     * 查询需要BIN补库的客户信息
     *
     * @param condition DataAuthoritySearchCondition
     * @return customerNo, customerName
     */
    @Select("<script>" +
            "  select distinct b.customerNo, c.name as customerName " +
            "  from Bindata b inner join ops_customer c on b.CustomerNo=c.customer_no " +
            "  where b.ReplQty>0 " +
            "   <if test='condition.userIdListByDataAuthority != null and condition.userIdListByDataAuthority.size > 0' > " +
            "       and c.PSNSMCID in" +
            "       <foreach collection='condition.userIdListByDataAuthority' item='item' index='index' separator=',' open='(' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   </if>" +
            "   <if test='condition.deptCodeListByDataAuthority != null and condition.deptCodeListByDataAuthority.size > 0' >" +
            "       and c.HRUnitId in" +
            "       <foreach collection='condition.deptCodeListByDataAuthority' item='item' index='index' separator=',' open='(' close=')'> " +
            "           #{item}" +
            "       </foreach>" +
            "   </if>" +
            "   <if test='condition.customerCodeListByDataAuthority != null and condition.customerCodeListByDataAuthority.size > 0' > " +
            "       and b.customerNo in" +
            "       <foreach collection='condition.customerCodeListByDataAuthority' item='item' index='index' separator=',' open='(' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   </if>" +
            "</script>")
    List<Map<String, Object>> listBinCustomerInfo(@Param("condition") DataAuthoritySearchCondition condition);

    /**
     * 查询营业所优先出库仓库
     *
     * @param deptNo 营业所代码
     * @return 仓库代码
     */
    @Select("select Top(1) b.warehouse_code " +
            "  from ops_warehouse a with(nolock) inner join ops_warehouse_salesbranch_config b with(nolock) " +
            "  on a.warehouse_code=b.warehouse_code " +
            "  where b.sales_branch_id=#{deptNo} and a.warehouse_type='MASTER' and b.delflag=0 order by priority ")
    String getDeptPriorityStock(@Param("deptNo") String deptNo);

    /**
     * 查询营业所优先出库仓库
     *
     * @param deptNo 营业所代码
     * @return 仓库代码
     */
    @Select("select b.warehouse_code " +
            "  from ops_warehouse a with(nolock) inner join ops_warehouse_salesbranch_config b with(nolock) " +
            "  on a.warehouse_code=b.warehouse_code " +
            "  where b.sales_branch_id=#{deptNo} and a.warehouse_type='MASTER' and b.delflag=0 order by priority ")
    List<String> listDeptPriorityStock(@Param("deptNo") String deptNo);

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

    @Select("select DISTINCT ModelNo from Bindata where centre_flag=1 and delFlag = 0")
    List<String> getCentreFlagModels();

//    /**
//     * 型号bin或gss品信息、bin安全库存数及有效库存数量
//     *
//     * @param modelNoList   型号列表
//     * @param warehouseCode 仓库
//     * @param customerNo    客户代码（可选）
//     */
//    @Select("<script>" +
//            "  select b.modelNo, b.StockType as modelType, b.safeQty as safeQuantity, s.quantity as canUseQuantity from " +
//            "  (select ModelNo,StockType,safeQty from Bindata where (StockType=1 or StockType=2) " +
//            "  and QtyBin>0 and BinCell>0 and warehouse_code=#{warehouseCode}) as b inner join  " +
//            "  (select i.modelno, sum(quantity-prepare_quantity) as quantity " +
//            "  from ops_inventory i inner join ops_inventory_property p on i.inventory_property_id=p.inventory_property_id\n" +
//            "  where i.inventory_status='N' and i.delflag=0 and i.qa_status=0 and p.delflag=0" +
//            "  and i.warehouse_code=#{warehouseCode} " +
//            "  and ( p.inventory_property_id=1 " +
//            "  <if test='customerNo!=null and customerNo!=\"\" '>" +
//            "      or p.customer_no=#{customerNo} " +
//            "  </if>" +
//            "  ) group by i.modelno) as s on b.ModelNo=s.modelno " +
//            "  where b.ModelNo in " +
//            "  <foreach collection='modelNoList' item='item' index='index' open='(' separator=',' close=')'> " +
//            "    #{item} " +
//            "  </foreach>" +
//            "</script>")
//    List<Product> listProductInfo(@Param("modelNoList") List<String> modelNoList,
//                                  @Param("warehouseCode") String warehouseCode,
//                                  @Param("customerNo") String customerNo);

    // 改为按型号集合+仓库代码集合查询 Edit by dengdenghui 2022-11-22 for bug-8738
    @Select("<script>" +
            " select ModelNo as modelNo, StockType as modelType, safeQty as safeQuantity, warehouse_code as stockCode " +
            " from Bindata " +
            " where warehouse_code in " +
            " <foreach collection='warehouseCodeList' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach> " +
            " and ModelNo in " +
            " <foreach collection='modelNoList' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach>" +
            " and StockType in (1, 2) and QtyBin>0 and BinCell>0 " +
            "</script>")
    List<Product> listBinAndGSS(@Param("modelNoList") List<String> modelNoList, @Param("warehouseCodeList") List<String> warehouseCodeList);
    // End

    @Select("<script>" +
            "select modelno,count(distinct modelno) as count From Bindata" +
            " where warehouse_code in " +
            " <foreach collection='warehouseCodeList' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach> " +
            " and ModelNo in " +
            " <foreach collection='modelNoList' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach>" +
            " and StockType in (1, 2) and QtyBin>0 and BinCell>0 " +
            "group by modelno "+
            "</script>")
    List<Map<String,Integer>> checkModelIsBin(@Param("modelNoList") List<String> modelNoList, @Param("warehouseCodeList") List<String> warehouseCodeList);


    @Select("select * from Bindata where StockType = 1 and warehouse_code = #{warehouseCode} and modelNo = #{modelNo} and customerNo = '' and Property_ID = '1' and delFlag = 0 ")
    BindataDO getBinDataInfo(@Param("modelNo") String modelNo, @Param("warehouseCode") String warehouseCode);

    @Select("select warehouse_code from bindata_client_warehouse where bindata_id=#{id} and del_flag=0")
    List<String> getClientsById(Long id);

}
