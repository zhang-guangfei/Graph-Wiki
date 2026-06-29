package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.adapter.FuzzySearchCondition;
import com.smc.smccloud.model.adapter.Inventory;
import com.smc.smccloud.model.adapter.InventoryFuzzySearch;
import com.smc.smccloud.model.inventory.*;
import com.smc.smccloud.model.product.CsStockStockTakeParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
@DS("opsdb")
public interface InventoryMapper extends BaseMapper<OpsInventoryDO> {

    /**
     * 按型号+仓库+库存类型汇总在库数量
     *
     * @param modelNo
     * @return
     */
    @Select("select warehouse_code as warehouseCode , case inventory_property_id when 1 then 'TY'  else 'SP' end as inventoryTypeCode, modelno," +
            " sum(quantity) as quantity,sum(i.prepare_quantity) as prepareQty " +
            "from ops_inventory i with(nolock)  " +
            "where i.modelno=#{modelNo} and i.quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0  " +
            "group by warehouse_code, case inventory_property_id when 1 then 'TY'  else 'SP' end, modelno ")
    List<WarehouseInventoryDO> listWarehouseInventory(@Param("modelNo") String modelNo);

    /**
     * 根据属性查出数据
     *
     * @param inventoryProperty
     * @return
     */
    @Select("<script> " +
            " select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag,a.cre_time,a.creator,a.modify_time,a.modifier " +
            " From   ops_inventory  a  with(nolock)  join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where a.delflag=0 and   A.inventory_status='N' and  A.qa_status=0 and a.quantity>0 " +
            "  <if test = 'inventoryProperty.modelNos!= null and  inventoryProperty.modelNos.size() &gt; 0 '>  " +
            "    and  a.modelno in " +
            "    <foreach collection='inventoryProperty.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "      #{item} " +
            "    </foreach> " +
            "  </if> " +
            "  <if test = 'inventoryProperty.inventoryIds!= null and  inventoryProperty.inventoryIds.size() &gt; 0 '>  " +
            "    and  a.inventory_id in " +
            "    <foreach collection='inventoryProperty.inventoryIds' item='item' index='index' open='(' separator=',' close=')'> " +
            "      #{item} " +
            "    </foreach> " +
            "  </if> " +
            "  <if test = 'inventoryProperty.propertyIds!= null and  inventoryProperty.propertyIds.size() &gt; 0 '>  " +
            "    and  a.inventory_property_id in " +
            "    <foreach collection='inventoryProperty.propertyIds' item='item' index='index' open='(' separator=',' close=')'> " +
            "      #{item} " +
            "    </foreach> " +
            "  </if> " +
            "  <if test = 'inventoryProperty.isPres!= null and inventoryProperty.isPres'>  " +
            "    and  (b.inventory_type_code like'GK-%' or  b.inventory_type_code like'ZL-%') " +
            "  </if> " +
            "  <if test = 'inventoryProperty.inventoryTypeCode!= null and  inventoryProperty.inventoryTypeCode!=\"\"'>  " +
            "    and  b.inventory_type_code =#{inventoryProperty.inventoryTypeCode} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.customerNo!= null and  inventoryProperty.customerNo!=\"\"'>  " +
            "    and  b.customer_no =#{inventoryProperty.customerNo} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.ppl!= null and  inventoryProperty.ppl!=\"\"'>  " +
            "    and  b.ppl =#{inventoryProperty.ppl} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.projectCode!= null and  inventoryProperty.projectCode!=\"\"'>  " +
            "    and  b.project_code =#{inventoryProperty.projectCode} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.groupCustomerNo!= null and  inventoryProperty.groupCustomerNo!=\"\"'>  " +
            "    and  b.group_customer_no =#{inventoryProperty.groupCustomerNo} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.salesInfoNo!= null and  inventoryProperty.salesInfoNo!=\"\"'>  " +
            "    and  b.sales_info_no =#{inventoryProperty.salesInfoNo} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.dateType!= null and  inventoryProperty.dateType ==0 and inventoryProperty.beginDate !=null and inventoryProperty.endDate !=null '>  " +
            "    and  a.cre_time  between #{inventoryProperty.beginDate} and #{inventoryProperty.endDate}" +
            "  </if> " +
            "  <if test = 'inventoryProperty.dateType!= null and  inventoryProperty.dateType ==1 and inventoryProperty.beginDate !=null and inventoryProperty.endDate !=null '>  " +
            "    and  a.modify_time  between #{inventoryProperty.beginDate} and #{inventoryProperty.endDate}" +
            "  </if> " +
            "</script>")
    List<InventoryVO> listInventoryByProperty(@Param("inventoryProperty") OpsInventoryPropertyRequestDTO inventoryProperty);


    @Select("<script> " +
            " select    distinct  a.modelno " +
            " From   ops_inventory  a  with(nolock)  join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where a.delflag=0 and   A.inventory_status='N' and  A.qa_status=0 and a.quantity>0 " +
            "  <if test = 'inventoryProperty.modelNos!= null and  inventoryProperty.modelNos.size() &gt; 0 '>  " +
            "    and  a.modelno in " +
            "    <foreach collection='inventoryProperty.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "      #{item} " +
            "    </foreach> " +
            "  </if> " +
            "  <if test = 'inventoryProperty.isPres!= null and inventoryProperty.isPres'>  " +
            "    and  (b.inventory_type_code like'GK-%' or  b.inventory_type_code like'ZL-%') " +
            "  </if> " +
            "  <if test = 'inventoryProperty.inventoryTypeCode!= null and  inventoryProperty.inventoryTypeCode!=\"\"'>  " +
            "    and  b.inventory_type_code =#{inventoryProperty.inventoryTypeCode} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.customerNo!= null and  inventoryProperty.customerNo!=\"\"'>  " +
            "    and  b.customer_no =#{inventoryProperty.customerNo} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.ppl!= null and  inventoryProperty.ppl!=\"\"'>  " +
            "    and  b.ppl =#{inventoryProperty.ppl} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.projectCode!= null and  inventoryProperty.projectCode!=\"\"'>  " +
            "    and  b.project_code =#{inventoryProperty.projectCode} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.groupCustomerNo!= null and  inventoryProperty.groupCustomerNo!=\"\"'>  " +
            "    and  b.group_customer_no =#{inventoryProperty.groupCustomerNo} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.salesInfoNo!= null and  inventoryProperty.salesInfoNo!=\"\"'>  " +
            "    and  b.sales_info_no =#{inventoryProperty.salesInfoNo} " +
            "  </if> " +
            "</script>")
    List<String> listInventoryModelByProperty(@Param("inventoryProperty") OpsInventoryPropertyRequestDTO inventoryProperty);
    /**
     * 按型号统计各物流中心的在库
     *
     * @param modelNos
     * @return
     */
    @Select("<script>" +
            "select i.warehouse_code as warehouseCode, i.modelNo, sum(i.quantity-i.prepare_quantity) as tyAvaQty " +
            " from ops_warehouse a with(nolock) inner join ops_inventory i with(nolock) " +
            " ON a.warehouse_code=i.warehouse_code " +
            " where i.inventory_property_id=1 and a.warehouse_type='MASTER' " +
            " and modelNo in  " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            " and i.quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0" +
            " group by i.modelNo, i.warehouse_code" +
            "</script>")
    List<WarehouseInventoryVO> getLogisticWarehouseCanUseStock(@Param("modelNos") List<String> modelNos);


    @Select("<script>" +
            "select warehouse_code as warehouseCode, modelNo, sum(quantity-prepare_quantity) as tyAvaQty " +
            " from ops_inventory with(nolock)  " +
            " where inventory_property_id=1 and warehouse_code not like 'W%' " +
            " and warehouse_code in " +
            " <foreach collection='warehouseCodes' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            " and modelNo in  " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            " and quantity>0 and inventory_status='N' and qa_status=0 and delflag=0 " +
            " group by modelNo,warehouse_code" +
            "</script>")
    List<WarehouseInventoryVO> getWarehouseCanUseStock(@Param("warehouseCodes") List<String> warehouseCodes,
                                                       @Param("modelNos") List<String> modelNos);


    @Select("<script>" +
            " select warehouse_code as warehouseCode, modelno as modelNo, (quantity - prepare_quantity) as avaQty," +
            " quantity, inventory_id as id, inventory_property_id as propertyId " +
            " from ops_inventory with(nolock) " +
            " where i.quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 " +
            " <if test='adapter.modelNo!=null and adapter.modelNo!=\"\" '> and modelno=#{adapter.modelNo} </if>" +
            " <if test=' (adapter.customerNo!=null and adapter.customerNo!=\"\")" +
            "   or (adapter.groupCustomerNo!=null and adapter.groupCustomerNo!=\"\")" +
            "   or (adapter.ppl!=null and adapter.ppl!=\"\")" +
            "   or (adapter.projectNo!=null and adapter.projectNo!=\"\") '>" +
            "   and inventory_property_id in ( " +
            "       select inventory_property_id from ops_inventory_property where 1=1 " +
            "       <if test='adapter.propertyId!=null and adapter.propertyId>0'> and inventory_property_id=#{adapter.propertyId} </if> " +
            "       <if test='adapter.customerNo!=null and adapter.customerNo!=\"\" '> and customer_no=#{adapter.customerNo} </if>" +
            "       <if test='adapter.groupCustomerNo!=null and adapter.groupCustomerNo!=\"\" '> and group_customer_no=#{adapter.groupCustomerNo} </if>" +
            "       <if test='adapter.ppl!=null and adapter.ppl!=\"\" '> and ppl=#{adapter.ppl} </if>" +
            "       <if test='adapter.projectNo!=null and adapter.projectNo!=\"\" '> and project_code=#{adapter.projectNo} </if>" +
            " )" +
            "</if>" +
            "</script>")
    List<InventoryVO> listSpecInventory(@Param("adapter") InventoryRequestDTO dto);


//    /**
//     * 在途中数量
//     *
//     * @param modelNo
//     * @return
//     */
    /*@Select("<script>" +
            " select SUM(quantity) from ops_inventory where inventory_status='T1' and modelNo=#{modelNo} " +
            "</script>")
    Integer sumInventoryTransQty(@Param("modelNo") String modelNo);*/

    /**
     * 查询单型号的在库可用数量
     *
     * @param dto 型号在库查询条件
     * @return 在库数
     */
    @Select("<script>" +
            " select sum(quantity) as quantity, sum(prepare_quantity) as prepareQuantity " +
            " from ops_inventory i with(nolock) inner join ops_inventory_property p with(nolock) " +
            " on p.inventory_property_id=i.inventory_property_id " +
            " where p.inventory_type_code=#{dto.inventoryTypeCode} and i.warehouse_code=#{dto.warehouseCode}" +
            " and i.modelno=#{dto.modelNo} " +
            " <if test='dto.customerNo!=null and dto.customerNo!=\"\" '> " +
            "   and p.customer_no=#{dto.customerNo} " +
            " </if> " +
            " <if test='dto.ppl!=null and dto.ppl!=\"\" '> " +
            "   and p.ppl=#{dto.ppl} " +
            " </if> " +
            " <if test='dto.projectCode!=null and dto.projectCode!=\"\" '> " +
            "   and p.project_code=#{dto.projectCode} " +
            " </if> " +
            " <if test='dto.groupCustomerNo!=null and dto.groupCustomerNo!=\"\" '> " +
            "   and p.group_customer_no=#{dto.groupCustomerNo} " +
            " </if> " +
            " and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 and p.delflag=0 and quantity>0 and quantity > prepare_quantity" +
            "</script>")
    InventoryVO getModelWarehouseStock(@Param("dto") ModelWarehouseStockRequest dto);

    @Select("<script>" +
            " select sum(quantity) as quantity, sum(prepare_quantity) as prepareQuantity " +
            " from ops_inventory i with(nolock) "+
            " where " +
            " i.modelno=#{modelNo} " +
            " and i.inventory_status='N' and i.qa_status=0 and i.delflag=0  and quantity>0 and quantity > prepare_quantity" +
            "</script>")
    InventoryVO getInvByModel(@Param("modelNo") String modelNo);


    /**
     * 按库存属性id+型号,汇总在库数量
     *
     * @param modelNos   型号
     * @param propertyId inventoryPropertyId
     * @return list
     */
    @Select("<script>" +
            "select modelNo, inventory_property_id as propertyId, sum(quantity) as quantity, sum(prepare_quantity) as prepareQuantity" +
            " from ops_inventory with(nolock) " +
            " where  inventory_property_id=#{propertyId}" +
            " <if test='warehouseCode!=null and warehouseCode!=\"\" '> and warehouse_code=#{warehouseCode} </if>" +
            " and modelNo in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            " and quantity>0 and inventory_status='N' and qa_status=0 and delflag=0 " +
            " group by modelno, inventory_property_id" +
            "</script>")
    List<InventoryVO> listInventoryByPropertyId(@Param("modelNos") List<String> modelNos,
                                                @Param("propertyId") Long propertyId,
                                                @Param("warehouseCode") String warehouseCode);

    //            "<if test='!isTY'>"+
//                    " and  inventory_property_id <>1 " +
//                    "</if>"+
    @Select("<script>" +
            "select warehouse_code,   modelNo,sum(quantity-prepare_quantity) as quantity " +
            " from ops_inventory   " +
            " where modelNo in " +
            "<foreach collection='inventoryProperty.modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            "</foreach>" +
            "<if test='inventoryProperty.isPres!=null and inventoryProperty.isPres'> " +
            " and  inventory_property_id &lt;&gt; 1 " +
            "</if> " +
            "<if test='inventoryProperty.propertyIds!=null and inventoryProperty.propertyIds.size() &gt; 0'>" +
            "  and  inventory_property_id in " +
            "<foreach collection='propertyIds' item='propertyId' index='index' open='(' separator=',' close=')'>  " +
            "  #{propertyId}" +
            "</foreach>" +
            "</if>" +
            " and quantity>0 and  quantity-prepare_quantity>0 and delflag=0 " +
            " group by warehouse_code,   modelNo" +
            "</script>")
    List<OpsInventoryVO> listInventoryByPropertyIdList(@Param("inventoryProperty") OpsInventoryPropertyRequestDTO inventoryProperty);

    /**
     * 分类统计有用在库数
     *
     * @param inventoryProperty
     * @return
     */
    @Select("<script> " +
            " select a.warehouse_code, a.modelno,case when b.inventory_type_code='TY' then 'TY' " +
            " when (b.inventory_type_code like'GK-%' or  b.inventory_type_code like'ZL-%')  then 'SPEC' " +
            " else 'Other' end as classCode,sum(a.quantity-a.prepare_quantity) as quantity " +
            " From   ops_inventory  a  with(nolock)  join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where a.delflag=0  and (a.quantity-a.prepare_quantity)>0 " +
            "  <if test = 'inventoryProperty.modelNos!= null and  inventoryProperty.modelNos.size() &gt; 0 '>  " +
            "    and  a.modelno in " +
            "    <foreach collection='inventoryProperty.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "      #{item} " +
            "    </foreach> " +
            "  </if> " +
            "  <if test = 'inventoryProperty.isPres!= null and inventoryProperty.isPres'>  " +
            "    and  (b.inventory_type_code like'GK-%' or  b.inventory_type_code like'ZL-%') " +
            "  </if> " +
            "  <if test = 'inventoryProperty.inventoryTypeCode!= null and  inventoryProperty.inventoryTypeCode!=\"\"'>  " +
            "    and  b.inventory_type_code =#{inventoryProperty.inventoryTypeCode} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.customerNo!= null and  inventoryProperty.customerNo!=\"\"'>  " +
            "    and  b.customer_no =#{inventoryProperty.customerNo} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.ppl!= null and  inventoryProperty.ppl!=\"\"'>  " +
            "    and  b.ppl =#{inventoryProperty.ppl} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.projectCode!= null and  inventoryProperty.projectCode!=\"\"'>  " +
            "    and  b.project_code =#{inventoryProperty.projectCode} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.groupCustomerNo!= null and  inventoryProperty.groupCustomerNo!=\"\"'>  " +
            "    and  b.group_customer_no =#{inventoryProperty.groupCustomerNo} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.salesInfoNo!= null and  inventoryProperty.salesInfoNo!=\"\"'>  " +
            "    and  b.sales_info_no =#{inventoryProperty.salesInfoNo} " +
            "  </if> " +
            " group by a.warehouse_code,a.modelno ,case when b.inventory_type_code='TY' then 'TY' " +
            " when (b.inventory_type_code like'GK-%' or  b.inventory_type_code like'ZL-%')  then 'SPEC' else 'Other' end " +
            "</script>")
    List<OpsInventoryVO> getCanUseInventoryByProperty(@Param("inventoryProperty") OpsInventoryPropertyRequestDTO inventoryProperty);




    /**
     * 查询型号仓库的在库可用库存
     */
    @Select("<script>" +
            "select modelNo, warehouse_code as warehouseCode, inventory_type_code as inventoryTypeCode, " +
            " sum(quantity - prepare_quantity) as avaQty " +
            " from ops_inventory i with(nolock) inner join ops_inventory_property p with(nolock) " +
            " on p.inventory_property_id=i.inventory_property_id  " +
            " where quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 and p.delflag=0 " +
            " <if test='dto.warehouseCode!=null and dto.warehouseCode!=\"\" '> and warehouse_code=#{dto.warehouseCode} </if>" +
            " and modelno in  " +
            " <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            " group by modelno, warehouse_code, inventory_type_code " +
            "</script>")
    List<ModelWarehouseStockDTO> listAvaQuantityByModelAndWarehouse(@Param("dto") ModelWarehouseStockRequest dto);


    /**
     * 查询订货中的数量
     *
     * @param modelNos   型号
     * @param propertyId inventoryPropertyId
     * @return list
     */
    @Select("<script>" +
            " SELECT inventoryPropertyId as propertyId, modelNo, sum(quantity - qtyImport) as quantity  " +
            " FROM ording_po_view with(nolock)  " +
            " where inventoryPropertyId=#{propertyId} " +
            " <if test='warehouseCode!=null and warehouseCode!=\"\" '> and requestWarehouseId=#{warehouseCode} </if>" +
            "  and modelNo in  " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            " group by modelNo,inventoryPropertyId " +
            "</script>")
    List<InventoryOrderingDTO> listOrderingInventoryByPropertyId(@Param("modelNos") List<String> modelNos,
                                                                 @Param("propertyId") Long propertyId,
                                                                 @Param("warehouseCode") String warehouseCode);

    /**
     * 查询订货中的数量
     *
     * @return list
     */
    @Select("<script>" +
            "SELECT modelNo, requestWarehouseId as warehouseCode, sum(quantity - qtyImport) as quantity " +
            " FROM ording_po_view with(nolock) where  " +
            " <if test='dto.warehouseCode!=null and dto.warehouseCode!=\"\" '>" +
            "  and requestWarehouseId=#{dto.warehouseCode} " +
            " </if>" +
            " and modelNo in " +
            " <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach>" +
            " group by modelNo,requestWarehouseId " +
            "</script>")
    List<InventoryOrderingDTO> listOrderingInventoryByWarehouse(@Param("dto") ModelWarehouseStockRequest dto);


    @Select("{ call inventory_updateCNFactoryStock }")
    @Options(statementType = StatementType.CALLABLE)
    String autoUpdateStock();

    /**
     * 查询客户专备在库
     *
     * @param dto 必填: modelNos
     * @return 专备库存信息
     */
    @Select("<script>" +
            "select modelNo, warehouse_code as warehouseCode, inventory_type_code as inventoryTypeCode, customer_no as customerNo, " +
            " ppl, project_code as projectCode, group_customer_no as groupCustomerNo, (quantity - prepare_quantity) as avaQty " +
            " from ops_inventory i with(nolock) inner join ops_inventory_property p  with(nolock) " +
            " on p.inventory_property_id=i.inventory_property_id " +
            " where p.inventory_type_code <![CDATA[ <> ]]> 'TY' and modelNo in " +
            " <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach>" +
            " <if test='dto.warehouseCode!=null and dto.warehouseCode!=\"\" '> and i.warehouse_code=#{dto.warehouseCode} </if> " +
            " <if test='dto.inventoryTypeCode!=null and dto.inventoryTypeCode!=\"\" '> and p.inventory_type_code=#{dto.inventoryTypeCode} </if> " +
            " <if test='dto.customerNo!=null and dto.customerNo!=\"\" '> and p.customer_no=#{dto.customerNo} </if> " +
            " <if test='dto.ppl!=null and dto.ppl!=\"\" '> and p.ppl=#{dto.ppl} </if> " +
            " <if test='dto.projectCode!=null and dto.projectCode!=\"\" '> and p.project_code=#{dto.projectCode} </if> " +
            " <if test='dto.groupCustomerNo!=null and dto.groupCustomerNo!=\"\" '> and p.group_customer_no=#{dto.groupCustomerNo} </if> " +
            " and i.quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 and p.delflag=0 " +
            "</script>")
    List<SpecStockVO> listCustomerSpecStock(@Param("dto") ModelWarehouseStockRequest dto);

    /**
     * 查询客户的可用通用在库和专备
     *
     * @return List
     */
    @Select("<script>" +
            " select i.modelno, sum(i.quantity - i.prepare_quantity) as avaQty " +
            " from ops_inventory i with(nolock) " +
            " where i.inventory_property_id=1 " +
            " and i.warehouse_code in " +
            "   <foreach collection='dto.warehouseCodeList' item='item' index='index' open='(' separator=',' close=')'> " +
            "      #{item} " +
            "   </foreach>" +
            "   and i.modelno in" +
            "   <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "      #{item} " +
            "   </foreach> " +
            " and quantity>0 " +
            " group by i.modelno " +
            " <if test='dto.customerNo!=null and dto.customerNo!=\"\" '>" +
            " union all " +
            " select i.modelno, sum(i.quantity - i.prepare_quantity) as avaQty " +
            " from ops_inventory i with(nolock) inner join ops_inventory_property p with(nolock) " +
            " on i.inventory_property_id=p.inventory_property_id " +
            " where p.customer_no=#{dto.customerNo} " +
            " and i.modelno in " +
            " <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach>" +
            " and quantity>0 " +
            " group by i.modelno " +
            " </if> " +
            "</script>")
    List<InventoryVO> listCanUseInventory(@Param("dto") ModelWarehouseStockRequest dto);

//    /**
//     * 查询客户的可用通用在库和专备
//     *
//     * @return List
//     */
//    @Select("<script>" +
//            " select i.modelno, sum(i.quantity - i.prepare_quantity) as avaQty " +
//            " from ops_inventory i with(nolock) " +
//            " where i.inventory_property_id=1 and quantity>0 " +
//            "   <if test='dto.warehouseCode!=null and dto.warehouseCode!=\"\" '>" +
//            "       and i.warehouse_code=#{dto.warehouseCode} " +
//            "   </if> " +
//            "   and i.modelno in" +
//            "   <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
//            "       #{item} " +
//            "   </foreach>" +
//            " group by i.modelno " +
//            " <if test='dto.customerNo!=null and dto.customerNo!=\"\" '>" +
//            " union all " +
//            " select i.modelno, sum(i.quantity - i.prepare_quantity) as avaQty " +
//            " from ops_inventory i with(nolock) inner join ops_inventory_property p with(nolock) " +
//            " on i.inventory_property_id=p.inventory_property_id " +
//            " where p.customer_no=#{dto.customerNo} " +
//            " <if test='dto.warehouseCode!=null and dto.warehouseCode!=\"\" '>" +
//            "   and i.warehouse_code=#{dto.warehouseCode} " +
//            " </if> " +
//            " and i.modelno in " +
//            " <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
//            "   #{item} " +
//            " </foreach>" +
//            " and quantity>0 " +
//            " group by i.modelno " +
//            " </if> " +
//            "</script>")
//    List<InventoryVO> listCanUseInventory(@Param("dto") ModelWarehouseStockRequest dto);
//    @Select("<script>" +
//            "  select i.modelno, p.inventory_type_code as inventoryTypeCode, sum(i.quantity - i.prepare_quantity) as avaQty " +
//            "  from ops_inventory i inner join ops_inventory_property p on i.inventory_property_id=p.inventory_property_id " +
//            "  where i.modelno in " +
//            "  <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
//            "    #{item} " +
//            "  </foreach>" +
//            "  <if test='dto.warehouseCode!=null and dto.warehouseCode!=\"\" '>" +
//            "    and i.warehouse_code=#{dto.warehouseCode} " +
//            "  </if> " +
//            "  and ( p.inventory_property_id=1 " +
//            "  <if test='dto.customerNo!=null and dto.customerNo!=\"\" '>" +
//            "     or p.customer_no=#{dto.customerNo} " +
//            "  </if> " +
//            "   ) " +
//            "  and i.inventory_status='N' and i.delflag=0 and i.qa_status=0 and p.delflag=0  " +
//            "  group by i.modelno, p.inventory_type_code " +
//            "</script>")
//    List<ModelWarehouseStockDTO> listCanUseInventory(@Param("dto") ModelWarehouseStockRequest dto);


    /**
     * 查询可用库存信息-(通用在库)
     *
     * @param modelNos 型号
     * @return 库存信息
     */
    @Select("<script>" +
            " select warehouse_code as stockCode, modelno as modelNo, " +
            " sum(quantity) as qtyOnHand, sum(prepare_quantity) as qtyPrePare" +
            " from ops_inventory with(nolock) " +
            " where inventory_property_id=1 and warehouse_code in " +
            " <foreach collection='warehouseList' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            " </foreach>" +
            " and modelno in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            " </foreach>" +
            " and quantity>0 and inventory_status='N' and qa_status=0 and delflag=0" +
            " group by warehouse_code, modelno " +
            "</script>")
    List<Inventory> listTYCanUseInventory(@Param("warehouseList") List<String> warehouseList,
                                          @Param("modelNos") List<String> modelNos);

    /**
     * 查询客户备库时可用库存信息
     *
     * @param modelNos 型号
     * @return 库存信息
     */
    @Select("<script>" +
            " select i.warehouse_code as warehouseCode, i.modelno as modelNo, cp.inventory_type_code as inventoryTypeCode,  " +
            "  cp.customer_no as customerNo, cp.ppl as bomNo, cp.project_code as projectCode, cp.HRUnitId as stockCode, " +
            "  quantity as qtyOnHand, prepare_quantity as qtyPrePare " +
            "  from ops_inventory i with(nolock) inner join  " +
            "  (select p.inventory_property_id, p.inventory_type_code, p.customer_no, p.ppl, p.project_code, c.HRUnitId " +
            "  from ops_inventory_property p with(nolock) inner join ops_customer c with(nolock) " +
            "  on p.customer_no=c.customer_no  " +
            "  where p.delflag=0 and p.inventory_type_code like 'GK%' and p.customer_no not in ('95001', '95002') " + // Add by DengDengHui 2022-11-03 for bug-8547
            "   and c.HRUnitId not in ('260000', '220000', '240000', '200000', '233220', '233130') ) cp " +
            "  on i.inventory_property_id = cp.inventory_property_id  " +
            "  where i.quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 " +
            " and i.warehouse_code in " +
            " <foreach collection='warehouseList' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            " </foreach>" +
            " and i.modelno in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            " </foreach>" +
            "</script>")
    List<Inventory> listGKZYCanUseInventory(@Param("warehouseList") List<String> warehouseList, @Param("modelNos") List<String> modelNos);

    /**
     * 查询型号预占用库存信息
     *
     * @param modelNos 型号
     * @return 库存信息
     */
    @Select("<script>" +
            " select i.warehouse_code as stockCode, i.modelno as modelNo, sum(quantity) as qtyForecast" +
            " from ops_inventory i with(nolock) inner join ops_inventory_property p with(nolock) " +
            " on i.inventory_property_id = p.inventory_property_id  " +
            " where p.inventory_type_code='QB_NO' and i.warehouse_code in " +
            " <foreach collection='warehouseList' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            " </foreach>" +
            " and i.modelno in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            " </foreach>" +
            " and quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 and p.delflag=0 " +
            " group by i.warehouse_code, i.modelno " +
            "</script>")
    List<Inventory> listPreInventory(@Param("warehouseList") List<String> warehouseList,
                                     @Param("modelNos") List<String> modelNos);

    /**
     * 查询型号的库存信息
     *
     * @param modelNos 型号
     * @return 型号库存信息
     */
    @Select("<script>" +
            " select i.warehouse_code as warehouseCode, i.modelno as modelNo, i.quantity, i.prepare_quantity as prepareQuantity, " +
            " p.inventory_type_code as inventoryTypeCode, p.customer_no as customerNo, p.ppl, p.project_code as projectCode, " +
            " p.group_customer_no as groupCustomerNo, p.sales_info_no as salesInfoNo, c.HRUnitId as deptNo " +
            " from ops_inventory i with(nolock) right join ops_inventory_property p with(nolock) " +
            " on i.inventory_property_id=p.inventory_property_id " +
            " left join ops_customer c with(nolock) on p.customer_no=c.customer_no  " +
            " where i.modelno in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            " </foreach>" +
            " and ((p.customer_no is not null and p.customer_no not in ('95001', '95002')) or p.customer_no is null ) " + // Add by DengDengHui 2022-11-05 for bug-8547
            " and i.quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 and p.delflag=0 " +
            " order by i.inventory_property_id " +
            "</script>")
    List<InventoryVO> listModelInventory(@Param("modelNos") List<String> modelNos);
//    @Select("<script>" +
//            " select i.warehouse_code as warehouseCode, i.modelno as modelNo, i.inventory_property_id as propertyId, " +
//            " p.inventory_type_code as inventoryTypeCode, p.customer_no as customerNo, p.ppl, p.project_code as projectCode, " +
//            " p.group_customer_no as groupCustomerNo, quantity, prepare_quantity as prepareQuantity " +
//            " from ops_inventory i inner join ops_inventory_property p on i.inventory_property_id = p.inventory_property_id " +
//            " where i.modelno = #{modelNo} " +
//            " <if test='warehouseList != null and warehouseList.size > 0' > " +
//            "   and i.warehouse_code in " +
//            "   <foreach collection='warehouseList' item='item' index='index' open='(' separator=',' close=')'> " +
//            "       #{item} " +
//            "   </foreach>" +
//            " </if>" +
//            " and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 and p.delflag=0 " +
//            "</script>")
//    List<InventoryVO> listModelInventory(@Param("modelNo") String modelNo, @Param("warehouseList") List<String> warehouseList);

//    /**
//     * 查询型号的在途库存
//     *
//     * @param modelNo 型号
//     * @return 型号的在途库存
//     */
//    @Select(" select modelNo, warehouse_code as warehouseCode, customer_no as customerNo, group_customer_no as groupCustomerNo," +
//            " ppl, project_code as projectCode, sales_info_no as salesInfoNo, inventory_type_code as inventoryTypeCode, " +
//            " qtyW, pqtyW, qtyD, pqtyD, qtyC, pqtyC, qtyP, pqtyP" +
//            " from inventory_report with(nolock) where modelNo=#{modelNo} " +
//            " order by warehouse_code " )
//    List<InventoryOrderingDTO> listModelOnWayInventory(@Param("modelNo") String modelNo);

    /**
     * 查询型号的在途库存
     *
     * @param modelNos 型号
     * @return 型号的在途库存
     */
    @Select("<script>" +
            " select modelNo, warehouse_code as warehouseCode, customer_no as customerNo, group_customer_no as groupCustomerNo," +
            " ppl, project_code as projectCode, sales_info_no as salesInfoNo, inventory_type_code as inventoryTypeCode, " +
            " qtyW, pqtyW, qtyD, pqtyD, qtyC, pqtyC, qtyP, pqtyP" +
            " from inventory_report with(nolock) where modelNo in " +
            "  <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            "  </foreach> " +
            " and (qtyW > 0 or qtyD > 0 or qtyC > 0 or qtyP > 0)" +
            " order by warehouse_code " +
            "</script>")
    List<InventoryOrderingDTO> listModelOnWayInventory(@Param("modelNos") List<String> modelNos);

    @Select("<script>" +
            "select modelno,sum(quantity) as accountBooksNum, warehouse_code as customerNo " +
            "from ops_inventory with(nolock) " +
            " where quantity>0 and inventory_status='N' and qa_status=0 and delflag=0 " +
            " and warehouse_code=#{param.warehouseCode} and warehouse_code like CONCAT('W',#{param.customerNo}, '%') " +
//            "<if test = 'param.dataAuthoritySearchCondition != null and param.dataAuthoritySearchCondition.customerCodeListByDataAuthority != null and param.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size > 0 '>"+
//            " and warehouse_code in "+
//            "  <foreach collection='param.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item='item' index='index' open='(' separator=',' close=')'> " +
//            "    #{item} " +
//            "  </foreach>" +
//            "</if>"+
            "group by modelno,warehouse_code order by modelno" +
            "</script>")
    List<InventoryDetailDTO> listCsStockStockTake(@Param("param") CsStockStockTakeParam param);

    /**
     * 查询客户型号的可用库存
     */
    @Select("<script>" +
            " select i.modelno, p.inventory_type_code as inventoryTypeCode, p.customer_no as customerNo, p.ppl, " +
            " p.project_code as projectCode, i.quantity as quantity, i.prepare_quantity as prepareQuantity " +
            "  from ops_inventory i with(nolock) inner join ops_inventory_property p with(nolock)" +
            "  on i.inventory_property_id=p.inventory_property_id " +
            "  where i.modelno in " +
            "  <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            "  </foreach>" +
            " <if test='dto.warehouseCodes != null and dto.warehouseCodes.size > 0' > " +
            "  and warehouse_code in " +
            "  <foreach collection='dto.warehouseCodes' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            "  </foreach>" +
            "  </if>" +
            "  and (p.inventory_property_id=1 or p.customer_no=#{dto.customerNo}) " +
            "  and i.quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 and i.qa_status=0 and p.delflag=0 " +
            "</script>")
    List<InventoryVO> listCustomerBinModelInventory(@Param("dto") InventoryRequestDTO dto);

    /**
     * 根据inventoryPropertyId和型号查询在途库存
     *
     * @param dto inventoryPropertyId, modelNos
     * @return 在途库存
     */
    @Select("<script>" +
            " select  modelNo, customer_no as customerNo,inventory_type_code as inventoryTypeCode, " +
            " sum(qtyD + qtyC + qtyP) as quantity, sum(pqtyD + pqtyC + pqtyP) as prepareQuantity " +
            " from inventory_report with(nolock) where customer_no = #{dto.customerNo} and modelNo in " +
            "  <foreach collection='dto.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            "  </foreach>" +
            " <if test='dto.warehouseCodes != null and dto.warehouseCodes.size > 0' > " +
            "  and warehouse_code in " +
            "  <foreach collection='dto.warehouseCodes' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            "  </foreach>" +
            " </if>" +
            " group by modelNo, customer_no, inventory_type_code" +
            "</script>")
    List<InventoryOrderingDTO> listModelInOrderingInventory(@Param("dto") InventoryRequestDTO dto);

    @Select("select w.warehouse_type as warseType, modelno, sum(quantity-prepare_quantity) as qty " +
            " from ops_inventory i with(nolock) inner join ops_warehouse w with(nolock) on i.warehouse_code=w.warehouse_code " +
            " where modelno=#{modelNo} " +
            " and inventory_property_id=1 and quantity>0 and inventory_status='N' and qa_status=0 and i.delflag=0 " +
            " group by modelno,w.warehouse_type")
    List<InventoryTypeDTO> listInventoryForShikomiOnStock(@Param("modelNo") String modelNo);

    @Select("select w.warehouse_type as warseType, modelno, sum(quantity-prepare_quantity) as qty  " +
            " from ops_inventory_move im with(nolock) inner join ops_warehouse w with(nolock) on im.warehouse_code=w.warehouse_code " +
            " where inventory_property_id=1 and modelno=#{modelNo}  " +
            " group by modelno,w.warehouse_type")
    List<InventoryTypeDTO> listInventoryForShikomiOnHand(@Param("modelNo") String modelNo);

    @Select("select sum(quantity-prepare_quantity) as qty " +
            "from ops_inventory with(nolock) " +
            "where modelno =#{modelNo}" +
            " and quantity>0 and inventory_status='N' and qa_status=0 and delflag=0 and inventory_property_id<>1 ")
    Integer listCustomerOnStockQty(@Param("modelNo") String modelNo);

    @Select("select sum(quantity-prepare_quantity) as qty " +
            "from ops_inventory_move with(nolock) where inventory_property_id<>1  and modelno =#{modelNo}")
    Integer listCustomerOnHandQty(@Param("modelNo") String modelNo);

    /**
     * 模糊查询型号库存信息
     *
     * @param condition 模糊查询
     * @return 库存信息
     */
//    @Select("<script>" +
//            "select * from ( " +
//            "  (select i.modelNo, i.warehouse_code as stockCode, (i.quantity-i.prepare_quantity) as quantity, " +
//            "  p.inventory_type_code as stockType, p.customer_no as customerNo, c.name as customerName " +
//            "  from ops_inventory i with(nolock) inner join ops_inventory_property p with(nolock) " +
//            "  on i.inventory_property_id=p.inventory_property_id " +
//            "  left join ops_customer c with(nolock) on c.customer_no=p.customer_no " +
//            "  where i.inventory_status='N' and i.qa_status=0 and i.delflag=0 and p.delflag=0 " +
//            "  and i.modelno like #{condition.modelNo})  " +
//            "  union " +
//            "  (select modelNo, 'KJP' as stockCode, quantity, 'TY' as stockType, '' as customerNo, '日本' as customerName " +
//            "  from inventory_supplier with(nolock) where supplierId='JP' and modelNo like  #{condition.modelNo})) as temp " +
//            "  where quantity>0  " +
//            "  <choose> " +
//            "   <when test='condition.property != \"\" and condition.property != null and condition.order !=\"\" and condition.order != null'> " +
//            "     ORDER BY ${condition.property} ${condition.order} " +
//            "   </when> " +
//            "   <otherwise> " +
//            "     ORDER BY ModelNo " +
//            "   </otherwise> " +
//            "  </choose>" +
//            "</script>")
    @Select("<script>" +
            "  select temp.*, c.name as customerName from (\n" +
            " (select t1.modelNo, t1.warehouse_code as stockCode, (t1.quantity-t1.prepare_quantity) as quantity,    \n" +
            " t2.inventory_type_code as stockType, t2.customer_no as customerNo , t2.group_customer_no\n" +
            " from ops_inventory t1 with(nolock) inner join ops_inventory_property t2 with(nolock)    \n" +
            " on t1.inventory_property_id=t2.inventory_property_id \n" +
            " where t1.modelno like  #{condition.modelNo} and t1.inventory_status='N' and t1.qa_status=0 and t1.delflag=0 and t2.delflag=0 and t1.quantity>0 )     \n" +
            " union  \n" +
            " (select modelNo, supplierId as stockCode, (quantity - quantityPrepare) as quantity, 'TY' as stockType, '' as customerNo, '' as group_customer_no\n" +
            "  from inventory_supplier with(nolock) where modelNo like #{condition.modelNo} and quantity>0 )\n" +
            " ) as temp    \n" +
            " left join ops_customer c with(nolock) on temp.customerNo=c.customer_no " +
            "  <choose> " +
            "   <when test='condition.property != \"\" and condition.property != null and condition.order !=\"\" and condition.order != null'> " +
            "     ORDER BY ${condition.property} ${condition.order} " +
            "   </when> " +
            "   <otherwise> " +
            "     ORDER BY ModelNo " +
            "   </otherwise> " +
            "  </choose>" +
            "</script>")
    List<InventoryFuzzySearch> listFuzzyInventory(@Param("condition") FuzzySearchCondition condition);


    @Select("<script>" +
            "select modelno as modelNo, sum(quantity-prepare_quantity) as avaQty" +
            "  from ops_inventory i with(nolock) inner join ops_inventory_property p with(nolock) " +
            "  on i.inventory_property_id=p.inventory_property_id " +
            "  where i.modelno in " +
            "  <foreach collection='modelNoList' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            "  </foreach>" +
            "  and ( (p.inventory_property_id=1 " +
            "    <if test='warehouseList != null and warehouseList.size > 0' > " +
            "      and i.warehouse_code in " +
            "      <foreach collection='warehouseList' item='item' index='index' open='(' separator=',' close=')'> " +
            "         #{item} " +
            "      </foreach> " +
            "    </if> ) " +
            "  <if test='customerNo != null and customerNo != \"\"' > " +
            "    or p.customer_no=#{customerNo}" +
            "  </if> ) " +
            "  and i.quantity>0 and i.inventory_status='N' and i.qa_status=0 and i.delflag=0 and i.qa_status=0 and p.delflag=0 " +
            "  group by modelno" +
            "</script>")
    List<InventoryVO> listModelCanUseQuantity(@Param("modelNoList") List<String> modelNoList,
                                              @Param("warehouseList") List<String> warehouseList,
                                              @Param("customerNo") String customerNo);

    @Select("select  sum(quantity-prepare_quantity) from ops_inventory with(nolock) " +
            " where modelNo=#{modelNo} and inventory_property_id=1 and quantity>0 and inventory_status='N'")
    Integer getCanUseQty(@Param("modelNo") String modelNo);

    @Select("<script>" +
            "select sum(i.quantity-i.prepare_quantity) from ops_inventory i with(nolock)\n" +
            "inner join ops_inventory_property p with(nolock) on i.inventory_property_id = p.inventory_property_id \n" +
            " inner join ops_warehouse w on i.warehouse_code = w.warehouse_code " +
            "where i.modelNo = #{dto.modelNo} and  i.delflag = 0 and p.delFlag = 0 and i.quantity>0 and i.inventory_status='N'\n" +
            " and w.warehouse_type = 'MASTER'  " +
            " and (p.customer_no = #{dto.customerNo} or i.inventory_property_id=1" +
            "  <if test='dto.userNo != null and dto.userNo != \"\"' > " +
            "    or p.customer_no = #{dto.userNo}" +
            "  </if> " +
            ")" +
            "</script>")
    Integer getCustomerCanUseQty(@Param("dto") InventoryRequestDTO dto);


    @Select("{call ops_sharedb.dbo.Proc_InventoryForManu_View1}")
    @Options(statementType = StatementType.CALLABLE)
    void calcOPSInventoryForManuView1();

    @Select("{call ops_sharedb.dbo.Proc_InventoryForManu_View2}")
    @Options(statementType = StatementType.CALLABLE)
    void calcOPSInventoryForManuView2();


    @Select("select warehouse_code, sum(quantity-i.prepare_quantity) as tyAvaQty " +
            "from ops_inventory i with(nolock) " +
            "where " +
            "i.inventory_status='N' " +
            "and i.qa_status=0 " +
            "and i.delflag=0 " +
            "and i.inventory_property_id=1 " +
            "and modelNo = #{modelNo} " +
            "group by warehouse_code")
    List<SMSInventoryVO> getModelInventoryByWarehouse(@Param("modelNo") String modelNo);


    @Select(" select " +
            " supplierId as warehouseCode, quantity as tyAvaQty, quantityAssembly,quantityProduce  " +
            " FROM [ops_core].[dbo].[inventory_supplier] where quantity > 0  and  modelNo = #{modelNo} ")
    List<SMSInventoryVO> searchOverSeasInventoryList(@Param("modelNo") String modelNo);


    /**
     * 根据型号查通用在库数量.专备在库数量
     */
    @Select("select warehouse_code as warehouseCode , case inventory_property_id when 1 then 'TY'  else 'SP' end as inventoryTypeCode, " +
            "sum(quantity) as quantity,sum(i.prepare_quantity) as prepareQty " +
            "from ops_inventory i with(nolock) " +
            "where i.modelno= #{modelNo} " +
            "and i.quantity>0  " +
            "and i.inventory_status='N' " +
            "and i.qa_status=0 " +
            "and i.delflag=0 " +
            "group by warehouse_code, case inventory_property_id when 1 then 'TY'  else 'SP' end ")
    List<SMSInventoryVO> findTYQtyAndSpQtyByModelNo(@Param("modelNo") String modelNo);


    /**
     * 根据型号查订货中数量
     *
     * @param modelNo
     * @return
     */
    @Select("select warehouse_code, sum(quantity-prepare_quantity) as quantityOrder " +
            "from ops_inventory_move  " +
            "where ( associate_no like '88%' or associate_no like '99%' ) " +
            "and modelno = #{modelNo} " +
            "and delflag=0  " +
            "and inventory_property_id=1 " +
            "GROUP BY warehouse_code ")
    List<SMSInventoryVO> findOrderingQty(@Param("modelNo") String modelNo);


    /**
     * 统计专备在库数量
     */
    @Select("<script>" +
            "select " +
            "i.warehouse_code, " +
            "c.HLCode as deptNo, " +
            "c.customer_no, " +
            "i.modelno as modelNo, " +
            "sum(i.quantity - i.prepare_quantity) as spValidQty " +
            "from ops_inventory i with(nolock) " +
            "inner join ops_inventory_property p with(nolock)  on i.inventory_property_id = p.inventory_property_id " +
            "left join ops_customer c with(nolock) on c.customer_no = p.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " i.quantity>0 and " +
//            " i.inventory_property_id != 1 and "+
            " i.qa_status=0 and " +
            " i.delflag=0 and " +
            " i.quantity - i.prepare_quantity > 0 and " +
            " i.inventory_status='N' and " +
            // update by 9681 From LiYingChao in 2023/2/24
            " ISNULL(p.customer_no, '1212') not in ('95001','95002') and " +
            // 委托在库的所有库存都查  update LiYingChao from bug 9366 in 2023-02-15
            "<if test=' request.warehouseCode != null and request.warehouseCode != \"\" and !request.warehouseCode.startsWith(\"W\") ' >" +
            " i.inventory_property_id != 1 and " +
            "</if>" +
            // 型号
            "<if test=' request.modelNo != null and request.modelNo != \"\" ' >" +
            "  i.modelno = #{request.modelNo} and " +
            "</if>" +
            // 仓库
            "<if test=' request.warehouseCode != null and request.warehouseCode != \"\" ' >" +
            "  i.warehouse_code = #{request.warehouseCode} and " +
            "</if>" +
            // 客户
            "<if test=' request.customerNo != null and request.customerNo != \"\" ' >" +
            "  p.customer_no = #{request.customerNo} and " +
            "</if>" +
            // 部门
            "<if test = ' request.deptNos != null and request.deptNos.size() &gt; 0 ' > " +
            "  c.HLCode in " +
            "   <foreach collection = 'request.deptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            "</trim>" +
            " GROUP BY i.warehouse_code,c.HLCode,c.customer_no, i.modelno " +
            "</script>")
    List<SpecStatisticsVO> getSpecQty(@Param("request") SpecStatisticsRequest request);

    /**
     * 统计查询订货中的数量
     */
    @Select("<script>" +
            "select i.warehouse_code,c.HLCode as deptNo,c.customer_no,i.inventory_status , i.modelno as modelNo, sum(i.quantity - i.prepare_quantity) as canOrderQty " +
            "from ops_inventory_move i " +
            "inner join ops_inventory_property p on i.inventory_property_id = p.inventory_property_id " +
            "inner join ops_customer c on c.customer_no = p.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " ( i.associate_no like '88%' or i.associate_no like '99%' ) and " +
            " i.delflag=0 and " +
            " i.inventory_property_id != 1 and " +
            " i.quantity - i.prepare_quantity > 0 and " +
            // update by 9681 From LiYingChao in 2023/2/24
            "  ISNULL(p.customer_no, '1212') not in ('95001','95002') and " +
            // 型号
            "<if test=' request.modelNo != null and request.modelNo != \"\" ' >" +
            "  i.modelno = #{request.modelNo} and " +
            "</if>" +
            // 仓库
            "<if test=' request.warehouseCode != null and request.warehouseCode != \"\" ' >" +
            "  i.warehouse_code = #{request.warehouseCode} and " +
            "</if>" +
            // 客户
            "<if test=' request.customerNo != null and request.customerNo != \"\" ' >" +
            "  p.customer_no = #{request.customerNo} and " +
            "</if>" +
            // 部门
            "<if test = ' request.deptNos != null and request.deptNos.size() &gt; 0 ' > " +
            "  c.HLCode in " +
            "   <foreach collection = 'request.deptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            "</trim>" +
            " GROUP BY i.warehouse_code,c.HLCode,c.customer_no, i.modelno,i.inventory_status " +
            "</script>")
    List<SpecStatisticsVO> getCanOrderQty(@Param("request") SpecStatisticsRequest request);

    @Select("select * from ops_inventory where 1=1 ")
    @Options(timeout = 2)
    List<OpsInventoryDO> testMybatisTimeOut();

    // 在库数（在途数=到货未入库数+调拨在途数+采购在途数+生产在途数+退货在途数）
    @Select("<script>" +
            "  select warehouse_code, modelNo,sum(quantity) as quantity From ops_inventory_move where   delflag=0  and quantity>0 " +
            " and modelno in " +
            " <foreach collection='inventoryProperty.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "  #{item} " +
            " </foreach>" +
            "<if test='propertyIds!=null and propertyIds.size() &gt; 0'>" +
            "  and inventory_property_id in  " +
            " <foreach collection='inventoryProperty.propertyIds' item='propertyId' index='index' open='(' separator=',' close=')'> " +
            "  #{propertyId} " +
            " </foreach>" +
            "</if>" +
            " group by warehouse_code, modelNo" +
            " </script>")
    List<OpsInventoryDO> getInventoryMoveByModels(@Param("inventoryProperty") OpsInventoryPropertyRequestDTO inventoryProperty);

    /**
     * 在库数（在途数=到货未入库数+调拨在途数+采购在途数+生产在途数+退货在途数）
     *
     * @param inventoryProperty
     * @return
     */
    @Select("<script>" +
            "   select a.warehouse_code, a.modelNo,case when b.inventory_type_code='TY' then 'TY'  " +
            "   when (b.inventory_type_code like'GK-%' or  b.inventory_type_code like'ZL-%')  then 'SPEC'  " +
            "   else 'Other' end as classCode,sum(a.quantity-a.prepare_quantity) as quantity  " +
            "   From ops_inventory_move A with(nolock) join   ops_inventory_property b on a.inventory_property_id=b.inventory_property_id  " +
            "   where   a.delflag=0  and a.quantity>0  " +
            "  <if test = 'inventoryProperty.modelNos!= null and  inventoryProperty.modelNos.size() &gt; 0 '>  " +
            "    and  a.modelno in " +
            "    <foreach collection='inventoryProperty.modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "      #{item} " +
            "    </foreach> " +
            "  </if> " +
            "  <if test = 'inventoryProperty.isPres!= null and inventoryProperty.isPres'>  " +
            "    and  (b.inventory_type_code like'GK-%' or  b.inventory_type_code like'ZL-%') " +
            "  </if> " +
            "  <if test = 'inventoryProperty.inventoryTypeCode!= null and  inventoryProperty.inventoryTypeCode!=\"\"'>  " +
            "    and  b.inventory_type_code =#{inventoryProperty.inventoryTypeCode} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.customerNo!= null and  inventoryProperty.customerNo!=\"\"'>   " +
            "    and  b.customer_no =#{inventoryProperty.customerNo} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.ppl!= null and  inventoryProperty.ppl!=\"\"'>   " +
            "    and  b.ppl =#{inventoryProperty.ppl} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.projectCode!= null and  inventoryProperty.projectCode!=\"\"'>  " +
            "    and  b.project_code =#{inventoryProperty.projectCode} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.groupCustomerNo!= null and  inventoryProperty.groupCustomerNo!=\"\"'>  " +
            "    and  b.group_customer_no =#{inventoryProperty.groupCustomerNo} " +
            "  </if> " +
            "  <if test = 'inventoryProperty.salesInfoNo!= null and  inventoryProperty.salesInfoNo!=\"\"'>  " +
            "    and  b.sales_info_no =#{inventoryProperty.salesInfoNo} " +
            "  </if> " +
            "   group by  a.warehouse_code,a.modelno ,case when b.inventory_type_code='TY' then 'TY'  " +
            "   when (b.inventory_type_code like'GK-%' or  b.inventory_type_code like'ZL-%')  then 'SPEC'  " +
            "   else 'Other' end  " +
            "   </script>")
    List<OpsInventoryVO> getInventoryMoveByProperty(@Param("inventoryProperty") OpsInventoryPropertyRequestDTO inventoryProperty);

}

