package com.smc.smccloud.mapper.prestock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.smc.smccloud.model.inventory.InventoryVO;
import com.smc.smccloud.model.inventory.OpsInventoryPropertyRequestDTO;
import com.smc.smccloud.model.prestock.PreOrderAccountDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author wuweidong
 * @create 2023/12/7 09:55
 * @description
 */
@DS("sharedb")
@Mapper
public interface PreOrderAccountMapper extends BaseMapper<PreOrderAccountDO> {

    /**
     * 25个参数，2100/25=84
     *
     * @param list
     * @return
     */
    @Select("<script> " +
            " insert  into preorder_account (inventory_id,status,model_no,warehouse_code,inventory_property_id,inventory_type_code,customer_no,ppl,project_code,group_customer_no" +
            ",sales_info_no,ro_qty,ava_qty,push_qty,frequency,avg,ava_month,is_bin,eprice,eamount,charger,dept_no,create_time,creator,modify_time,modifier) VALUES" +
            " <if test='list !=null and list.size() &gt; 0'>" +
            "  <foreach collection='list' item='item' index='index'  separator=',' > " +
            "   (#{item.inventoryId},#{item.status}, #{item.modelNo}, #{item.warehouseCode},#{item.inventoryPropertyId},#{item.inventoryTypeCode}, #{item.customerNo}, #{item.ppl}," +
            "   #{item.projectCode},#{item.groupCustomerNo}, #{item.salesInfoNo}, #{item.roQty},#{item.avaQty},#{item.pushQty}, #{item.frequency}, #{item.avg}," +
            "   #{item.avaMonth},#{item.isBin},#{item.ePrice},#{item.eAmount},#{item.charger},#{item.deptNo},#{item.createTime}, #{item.creator},#{item.createTime}, #{item.creator}) " +
            "  </foreach>" +
            " </if>" +
            "</script>")
    Integer insertByBatch(@Param("list") List<PreOrderAccountDO> list);

    @Select("select max(dlv_date)  as dlvDate from prestock_detail with(nolock) where status=6 and  inventory_property_id=#{propertyId} and model_no=#{modelNo}   ")
    Date getPreStockDlvDate(@Param("propertyId") Long propertyId, @Param("modelNo") String modelNo);

    /**
     * 根据属性查出数据
     *
     * @param inventoryProperty
     * @return
     */
    @DS("opsdb")
    @Select("<script> " +
            " select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag,a.cre_time,a.creator,a.modify_time,a.modifier " +
            " From   ops_inventory  a  with(nolock)  join ops_inventory_property b  with(nolock)  on a.inventory_property_id=b.inventory_property_id " +
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



    @DS("opsdb")
    @Select("select Top(1) * from ops_core.dbo.ops_purchaseOrder with(nolock) where orderNo=#{orderNo} and itemNo=#{itemNo} order by finishDate desc  ")
    OpsPurchaseorder getRequestPurchaseOrder(@Param("orderNo") String orderNo, @Param("itemNo") Integer itemNo);

    @Select("update preorder_account set delay_qty=b.delayQty,approve_qty=b.approveQty,clear_qty=b.clearQty From preorder_account A join ( " +
            "select inventory_id,sum(isnull(clear_qty,0)) as clearQty,sum(isnull(delay_qty,0)) as delayQty,sum(isnull(approve_count_qty,0)) as approveQty  From preorder_account_detail " +
            "where inventory_id=#{inventoryId} " +
            "group by inventory_id " +
            ")B on a.inventory_id=b.inventory_id ")
    void updateClearDelayQty(@Param("inventoryId") Long inventoryId);

}
