package com.smc.smccloud.mapper.prestock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsImpdata;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.sales.ops.db.entity.StockTransferPlan;
import com.smc.smccloud.model.RcvDetailDO;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.model.inventory.InventoryVO;
import com.smc.smccloud.model.inventory.OpsInventoryLogVO;
import com.smc.smccloud.model.inventory.OpsInventoryPropertyRequestDTO;
import com.smc.smccloud.model.inventory.OpsInventoryVO;
import com.smc.smccloud.model.prestock.PreOrderAccountDetailDO;
import com.smc.smccloud.model.product.ProductPriceVO;
import com.smc.smccloud.model.trans.TransOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/6/17 13:35
 * @Descripton TODO
 */
@Mapper
@DS("opsdb")
public interface PreOrderAccountCommonMapper {

    /**
     * 查询有效在库库存
     * @param inventoryProperty
     * @return
     */
    @Select("<script> " +
            " select a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag,a.cre_time,a.creator,ISNULL(a.modify_time,a.cre_time) as modify_time, a.modifier " +
            " From   ops_inventory  a  with(nolock)  join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where a.delflag=0 and   a.inventory_status='N' and  a.qa_status=0 and a.quantity - a.prepare_quantity>0 " +
            " and ( b.customer_no is null or b.customer_no NOT IN ('95001','95002','SM050') ) and ( b.group_customer_no is null or b.group_customer_no not in ('WF001') ) " +
//            " and a.inventory_id in (\n" +
//            " '1065245' ) " +
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
            "    and  (b.inventory_type_code like'GK-%' or  b.inventory_type_code like'ZL-%') and a.warehouse_code not like 'W%' " +
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
            "  <if test = 'inventoryProperty.dateType!= null and  inventoryProperty.dateType ==2 and inventoryProperty.queryDateStr !=null'>  " +
            "    and  (a.modify_time >= #{inventoryProperty.queryDateStr} or a.cre_time >= #{inventoryProperty.queryDateStr} )" +
            "  </if> " +
            "  order by ISNULL(a.modify_time, a.cre_time) desc "+
            "</script>")
    List<InventoryVO> listInventoryByProperty(@Param("inventoryProperty") OpsInventoryPropertyRequestDTO inventoryProperty);


    // 根据库存id查询入库日志
    @Select(" select * from ops_inventory_log where type = 1 and delflag = 0 and inventory_id = #{inventoryId} order by inventory_log_id desc ")
    List<OpsInventoryLogVO> getOpsInventoryLogByInventoryId(@Param("inventoryId") Long inventoryId);

    // 根据logid查询入库日志
    @Select(" select * from ops_inventory_log where inventory_log_id = #{logId} ")
    OpsInventoryLogVO getOpsInventoryLogByLogId(@Param("logId") String logId);

    // 查询订货频率以及订货月均数
    @Select("<script> " +
            "select modelno, AvgQtyOf12 ,MonthsOf12 from ops_report.dbo.model_exp_freq where  modelno = #{modelNo}"+
            " and StockCode = #{warehouseCode} and ModelType = '2' " +
            "</script>")
    List<ModelExpFreqVO> getModelExpFreqForAvgQty12(@Param("modelNo") String modelNo,
                                                    @Param("warehouseCode") String warehouseCode);


    /**
     * 根据起订数查询E价
     */
    @Select("select top(1)* from ops_core.dbo.product_price where modelNo = #{modelNo} and MinQuantity <= #{qty} and is_deleted = 0 order by MinQuantity Desc")
    ProductPriceVO getEpriceByModelNoAndMinQuantity(@Param("modelNo") String modelNo, @Param("qty") int qty);


    /**
     * 判断是否ALL bin
     */
    @Select("<script> " +
            "select modelNo from ops_core.dbo.bindata where delFlag = 0 and " +
            " ModelNo in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach> " +
            "  and StockType in (1,4) and warehouse_code = 'ALL' " +
            "</script>")
    List<String> isBin(@Param("modelNos") List<String> modelNos);

    @Select("select sum(push_qty) as qty from ops_sharedb.dbo.preorder_account_detail where status not in ('1','5','7') and inventory_id = #{inventoryId}")
    Integer getAccountSumPushQty(@Param("inventoryId") Long inventoryId);


    @Select("select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno, " +
            "  a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag,a.cre_time,a.creator,ISNULL(a.modify_time,a.cre_time) as modify_time, a.modifier  " +
            " from ops_core.dbo.ops_inventory a with(nolock) join ops_core.dbo.ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where inventory_id = #{inventoryId} ")
    InventoryVO getInventoryByInventoryId(@Param("inventoryId") Long inventoryId);


    @Select("select * from ops_core.dbo.trans_order where trans_no = #{orderNo} and item_no = #{itemNo}")
    List<TransOrderVO> getTransOrderByTransOrderNoAndItemNo(@Param("orderNo") String orderNo,@Param("itemNo") String itemNo);

    @Select("select * from ops_core.dbo.stock_transfer_plan where plan_no = #{fromNo}")
    List<StockTransferPlan> getStockTransferPlanByFromNo(@Param("fromNo") String fromNo);

    @Select("select * from ops_core.dbo.ops_inventory where inventory_id = #{inventoryId}")
    OpsInventoryVO getOpsInventoryByInventoryId(@Param("inventoryId") Long inventoryId);


    @Update(" update ops_sharedb.dbo.ops_sales_notice_task set call_back_parameter = #{callBackParam}, return_status = '0' where apply_no = #{applyNo}")
    int updateTaskByApplyNo(@Param("applyNo")String applyNo, @Param("callBackParam")String callBackParam);


    @Update("update ops_sharedb.dbo.preorder_account_detail set delflag = 1 where status = 7 or (status =1 and isnull(preflag,0) <> 1 ) ")
    void upPreorderAccountDetailDelFlag();

    @Update("update ops_sharedb.dbo.preorder_account_detail set ava_qty = 0 where delflag != 1 and order_no not like 'YJS%' ")
    void upPreorderAccountDetailAvgQty();

    @Update("update ops_sharedb.dbo.preorder_account_detail set ava_qty = 0 where delflag != 1 and order_no like 'YJS%' " +
            " and inventory_id=#{inventoryId} and model_no = #{modelNo} ")
    void upPreorderAccountDetailAvgQtyYJS(@Param("inventoryId")Long inventoryId,@Param("modelNo")String modelNo);

    @Select("select * from ops_core.dbo.ops_impdata where orderNo=#{orderNo} and itemNo=#{itemNo}")
    List<OpsImpdata> getOpsImpdataByOrderNo(@Param("orderNo") String orderNo,
                                      @Param("itemNo") int itemNo);


    @Select("select * from ops_core.dbo.rcvdetail where rorder_no=#{orderNo} and rorder_item=#{itemNo}")
    RcvDetailDO getRcvDetailByOrderNoAndItemNo(@Param("orderNo") String orderNo,@Param("itemNo") int itemNo);


}
