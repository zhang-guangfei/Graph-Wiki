package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsOrderInventoryRuleConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OpsInventoryDao {

    @Select("select * from inventory_move_view where inventory_id =#{id}")
    OpsInventoryMove getInventoryMoveViewById(Long id);

    /**
     * @description 转订查询可用库存
     * @author C12961
     * @date 2022/6/27 8:56
     */
    @Select("SELECT a.*"
            + " FROM ops_inventory a"
            + " inner join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id"
            + " inner join ops_warehouse_salesbranch_config c on a.warehouse_code=c.warehouse_code"
            + " where c.sales_branch_id=#{deptNo} and a.modelno=#{modelNo} and a.quantity-a.prepare_quantity>=#{qty} and a.inventory_status='N' and a.qa_status='0'"
            + " and a.delflag='0' and b.delflag='0' and (b.customer_no is null or b.customer_no=#{customerNo})"
            + " and (b.ppl is null or b.ppl=#{ppl}) and (b.project_code is null or b.project_code=#{pj})"
            + " and (b.group_customer_no is null or b.group_customer_no in (#{groupCustomer}))"
            + "and (b.sales_info_no is null )")
    List<OpsInventory> getInventoryAvailable(@Param("deptNo") String deptNo, @Param("modelNo") String modelNo, @Param("qty") int qty,
                                             @Param("customerNo") String customerNo, @Param("ppl") String ppl, @Param("pj") String pj,
                                             @Param("groupCustomer") String groupCustomer);

    @Select("SELECT a.*"
            + " FROM ops_inventory_move a"
            + " inner join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id"
            + " inner join ops_warehouse_salesbranch_config c on a.warehouse_code=c.warehouse_code"
            + " where c.sales_branch_id=#{deptNo} and a.modelno=#{modelNo} and a.quantity-a.prepare_quantity>= #{qty} and a.qa_status='0'"
            + " and a.delflag='0' and b.delflag='0' and (b.customer_no is null or b.customer_no=#{customerNo})"
            + " and (b.ppl is null or b.ppl=#{ppl}) and (b.project_code is null or b.project_code=#{pj})"
            + " and (b.group_customer_no is null or b.group_customer_no in (#{groupCustomer}))"
            + " and (b.sales_info_no is null )")
    List<OpsInventoryMove> getInventoryMoveAvailable(@Param("deptNo") String deptNo, @Param("modelNo") String modelNo, @Param("qty") int qty,
                                                     @Param("customerNo") String customerNo, @Param("ppl") String ppl, @Param("pj") String pj,
                                                     @Param("groupCustomer") String groupCustomer);


    @Select("SELECT  inventory_id,inventory_property_id,inventory_status,modelno,quantity,prepare_quantity,unit,price" +
            ",qa_status,in_time,version,warehouse_code FROM ops_inventory " +
            "WHERE delflag=0  AND quantity>prepare_quantity")
    List<OpsInventory> getAllInvListDao();

    @Select("SELECT  inventory_id,inventory_property_id,inventory_status,modelno,quantity,prepare_quantity,unit,price" +
            ",qa_status,in_time,version,warehouse_code FROM ops_inventory " +
            "WHERE delflag=0  AND quantity>prepare_quantity AND modelno=#{modelno}")
    List<OpsInventory> getAllModelNoInvListDao(@Param("modelno") String modelno);

    @Select("SELECT associate_no,associate_no_item, inventory_id,associate_no_splitNo,inventory_property_id,inventory_status " +
            ",modelno,quantity,prepare_quantity,unit,price,qa_status,in_time,version,sign_warehouse_code as warehouse_code," +
            "preReceiveDate,supplierId,opt_status FROM ops_inventory_move " +
            "WHERE delflag=0 AND quantity>0 AND quantity>prepare_quantity AND (LEFT(orderNo,2) " +
            "IN ('99','88','BN','BI','PT','ZK','FK','WT','VT','ST') OR LEFT(orderNo,1) = 'V') AND modelno=#{modelno}")
    List<OpsInventoryMove> getAllModelNoMoveInvListDao(@Param("modelno") String modelno);


    /**
     * @author ：c02483
     * @date ：Created in 2021/9/13 9:03
     * @description：查询可用库存列表
     */
    @Select("SELECT  inventory_id,inventory_property_id,inventory_status,modelno,quantity,prepare_quantity,unit,price,qa_status,in_time,version,warehouse_code FROM ops_inventory " +
            "WHERE delflag=0 AND inventory_property_id=#{inventoryPropertyId}  AND warehouse_code=#{warehouseCode}  AND inventory_status=#{inventoryStatus} AND quantity>0  AND quantity>prepare_quantity  AND modelno=#{modelno} ")
    List<OpsInventory> getOpsInventoryListDao(@Param("inventoryPropertyId") Long inventoryPropertyId, @Param("modelno") String modelno, @Param("warehouseCode") String warehouseCode, @Param("inventoryStatus") String inventoryStatus);


    /**
     * BUGID:8570 c14717 20221107
     * 在途取库存取 签收仓为库存仓库
     * bugid:13544 c14717 20240305
     * @param inventoryPropertyId
     * @param modelno
     * @param warehouseCode
     * @param inventoryStatus
     * @return
     */
    @Select("SELECT associate_no,associate_no_item, inventory_id,associate_no_splitNo,inventory_property_id,inventory_status,modelno,quantity,prepare_quantity,unit,price,qa_status,in_time,version,sign_warehouse_code as warehouse_code,preReceiveDate,supplierId FROM ops_inventory_move " +
            "WHERE delflag=0 AND inventory_property_id=#{inventoryPropertyId}   AND sign_warehouse_code=#{warehouseCode}  AND inventory_status=#{inventoryStatus} AND quantity>0 AND quantity>prepare_quantity AND (LEFT(orderNo,2) IN ('99','88','BN','BI','PT','ZK','FK','WT','VT','ST') OR LEFT(orderNo,1) = 'V') AND modelno=#{modelno}")
    List<OpsInventoryMove> getOpsInventoryMoveListDao(@Param("inventoryPropertyId") Long inventoryPropertyId, @Param("modelno") String modelno, @Param("warehouseCode") String warehouseCode, @Param("inventoryStatus") String inventoryStatus);

    @Select("SELECT associate_no,associate_no_item, inventory_id,associate_no_splitNo,inventory_property_id,inventory_status,modelno,quantity,prepare_quantity,unit,price,qa_status,in_time,version,warehouse_code,preReceiveDate,supplierId FROM ops_inventory_move " +
            "WHERE delflag=0 AND inventory_property_id=#{inventoryPropertyId}   AND warehouse_code=#{warehouseCode}  AND inventory_status=#{inventoryStatus} AND quantity>prepare_quantity AND modelno=#{modelno}")
    List<OpsInventoryMove> getOpsInventoryMoveListDaoOld(@Param("inventoryPropertyId") Long inventoryPropertyId, @Param("modelno") String modelno, @Param("warehouseCode") String warehouseCode, @Param("inventoryStatus") String inventoryStatus);


    @Insert("insert into ops_inventory " +
            "(warehouse_code,inventory_status,quantity,qa_status,prepare_quantity,modelno,inventory_property_id,version,delflag,cre_time,creator,in_time) " +
            "values (#{warehouseCode},#{inventoryStatus},#{quantity},#{qaStatus},#{prepareQuantity},#{modelno},#{inventoryPropertyId},#{version},#{delflag},#{creTime},#{creator},#{inTime})")
    @Options(useGeneratedKeys = true, keyProperty = "inventoryId")
    Long insertInventory(OpsInventory inventory);

    @Insert("<script> insert into ops_inventory_move " +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='warehouseCode != null '> warehouse_code, </if> " +
            "      <if test='inventoryStatus != null'> inventory_status, </if> " +
            "      <if test='quantity != null'> quantity, </if> " +
            "      <if test='qaStatus != null'> qa_status, </if> " +
            "      <if test='prepareQuantity != null'> prepare_quantity, </if> " +
            "      <if test='modelno != null'> modelno, </if> " +
            "      <if test='inventoryPropertyId != null '> inventory_property_id, </if> " +
            "      <if test='version != null'> version, </if> " +
            "      <if test='orderno != null'> orderNo, </if> " +
            "      <if test='itemno != null'> itemNo, </if> " +
            "      <if test='splititemno != null'> splititemno, </if> " +
            "      <if test='supplierid != null'> supplierId, </if> " +
            "      <if test='prereceivedate != null'> preReceiveDate, </if> " +
            "      <if test='invoiceno != null'> invoiceNo, </if> " +
            "      <if test='invoiceid != null'> invoiceid, </if> " +
            "      <if test='associateNo != null'> associate_no, </if> " +
            "      <if test='associateNoItem != null'> associate_no_item, </if> " +
            "      <if test='associateNoSplitno != null'> associate_no_splitNo, </if> " +
            "      <if test='sourceType != null'> source_type, </if> " +
            "      <if test='signWarehouseCode != null'> sign_warehouse_code, </if> " +
            "      <if test='greencode != null'> greencode, </if> " +
            "      <if test='optStatus != null'> opt_Status, </if> " +
            "      <if test='optTime != null'> opt_time, </if> " +
            "      <if test='poQty != null'> po_qty, </if> " +
            "      <if test='remark != null'> remark, </if> " +
            "       delflag,cre_time,creator" +
            "    </trim> " +
            "     values " +
            "    <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "      <if test='warehouseCode != null'> #{warehouseCode},</if> " +
            "      <if test='inventoryStatus != null'> #{inventoryStatus},</if> " +
            "      <if test='quantity != null'> #{quantity},</if> " +
            "      <if test='qaStatus != null'> #{qaStatus},</if> " +
            "      <if test='prepareQuantity != null'> #{prepareQuantity},</if> " +
            "      <if test='modelno != null'> #{modelno},</if> " +
            "      <if test='inventoryPropertyId != null'> #{inventoryPropertyId},</if> " +
            "      <if test='version != null'> #{version},</if> " +
            "      <if test='orderno != null'> #{orderno},</if> " +
            "      <if test='itemno != null'> #{itemno},</if> " +
            "      <if test='splititemno != null'> #{splititemno},</if> " +
            "      <if test='supplierid != null'> #{supplierid},</if> " +
            "      <if test='prereceivedate != null'> #{prereceivedate},</if> " +
            "      <if test='invoiceno != null'> #{invoiceno},</if> " +
            "      <if test='invoiceid != null'> #{invoiceid},</if> " +
            "      <if test='associateNo != null'> #{associateNo},</if> " +
            "      <if test='associateNoItem != null'> #{associateNoItem},</if> " +
            "      <if test='associateNoSplitno != null'> #{associateNoSplitno},</if> " +
            "      <if test='sourceType != null'> #{sourceType},</if> " +
            "      <if test='signWarehouseCode != null'> #{signWarehouseCode},</if> " +
            "      <if test='greencode != null'> #{greencode},</if> " +
            "      <if test='optStatus != null'> #{optStatus},</if> " +
            "      <if test='optTime != null'> #{optTime},</if> " +
            "      <if test='poQty != null'> #{poQty},</if> " +
            "      <if test='remark != null'> #{remark},</if> " +
            "       #{delflag},#{creTime},#{creator}" +
            "    </trim> " +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "inventoryId")
    Long insertInventoryMove(OpsInventoryMove inventory);

    /*************************************************/
    //TODO 调整where条件顺序 sqlserver 优先顺序从右边开始
    @Update({"UPDATE dbo.ops_inventory SET version=version+1,prepare_quantity=prepare_quantity+#{TPreQty} ,modifier=#{Tmodifier},modify_time=GETDATE() WHERE version=#{Sversion} AND inventory_id=#{SInventoryId}   "})
    int updatePreQtyInventory(@Param("TPreQty") int TPreQty, @Param("Tmodifier") String Tmodifier, @Param("Sversion") long Sversion, @Param("SInventoryId") long SInventoryId);

    @Update({"UPDATE dbo.ops_inventory_move SET version=version+1,prepare_quantity=prepare_quantity+#{TPreQty} ,modifier=#{Tmodifier},modify_time=GETDATE() WHERE version=#{Sversion} AND inventory_id=#{SInventoryId}   "})
    int updatePreQtyInventoryMove(@Param("TPreQty") int TPreQty, @Param("Tmodifier") String Tmodifier, @Param("Sversion") long Sversion, @Param("SInventoryId") long SInventoryId);

    @Update("update ops_inventory set version=version+1,quantity=quantity+#{qty} ,modifier=#{modifier} ,modify_time=GETDATE()  where delflag=0 and inventory_id=#{id} and version=#{version}")
    int updateQtyInventory(@Param("qty") int qty, @Param("id") Long inventoryId, @Param("version") Long version, @Param("modifier") String modifier);

    @Update("update ops_inventory set version=version+1,quantity=quantity+#{qty} ,in_time=GETDATE(),modifier=#{modifier} ,modify_time=GETDATE()  where delflag=0 and inventory_id=#{id} and version=#{version}")
    int updateQtyInventoryAddInTime(@Param("qty") int qty, @Param("id") Long inventoryId, @Param("version") Long version, @Param("modifier") String modifier);

    @Update("update ops_inventory set version=version+1,quantity=quantity+#{qty} ,out_time=GETDATE(),modifier=#{modifier} ,modify_time=GETDATE()  where delflag=0 and inventory_id=#{id} and version=#{version}")
    int updateQtyInventoryAddOutTime(@Param("qty") int qty, @Param("id") Long inventoryId, @Param("version") Long version, @Param("modifier") String modifier);


    @Update("update ops_inventory_move set version=version+1,quantity=quantity+#{qty}, modifier=#{modifier} ,modify_time=GETDATE()  where delflag=0 and inventory_id=#{id} and version=#{version}")
    int updateQtyInventoryMove(@Param("qty") int qty, @Param("id") Long inventoryId, @Param("version") Long version, @Param("modifier") String modifier);

    @Update("update ops_inventory_move set version=version+1,quantity=quantity+#{qty},prepare_quantity=prepare_quantity+#{preQty} , modifier=#{modifier} ,modify_time=GETDATE()  where delflag=0 and inventory_id=#{id} and version=#{version}")
    int updateQtyInventoryMoveWithPre(@Param("qty") int qty, @Param("preQty") int preQty, @Param("id") Long inventoryId, @Param("version") Long version, @Param("modifier") String modifier);

    @Update("update ops_inventory set version=version+1,quantity=quantity+#{qty},prepare_quantity=prepare_quantity+#{preQty} , modifier=#{modifier} ,modify_time=GETDATE()  where delflag=0 and inventory_id=#{id} and version=#{version}")
    int updateQtyInventoryWithPre(@Param("qty") int qty, @Param("preQty") int preQty, @Param("id") Long inventoryId, @Param("version") Long version, @Param("modifier") String modifier);

    @Update("update ops_inventory set version=version+1,quantity=quantity+#{qty},prepare_quantity=prepare_quantity+#{preQty} ,in_time=GETDATE(), modifier=#{modifier} ,modify_time=GETDATE()  where delflag=0 and inventory_id=#{id} and version=#{version}")
    int updateQtyInventoryWithPreAddInTime(@Param("qty") int qty, @Param("preQty") int preQty, @Param("id") Long inventoryId, @Param("version") Long version, @Param("modifier") String modifier);

    @Update("update ops_inventory set version=version+1,quantity=quantity+#{qty},prepare_quantity=prepare_quantity+#{preQty} ,out_time=GETDATE(), modifier=#{modifier} ,modify_time=GETDATE()  where delflag=0 and inventory_id=#{id} and version=#{version}")
    int updateQtyInventoryWithPreAddOutTime(@Param("qty") int qty, @Param("preQty") int preQty, @Param("id") Long inventoryId, @Param("version") Long version, @Param("modifier") String modifier);

    /*************************************************/

    /**
     * 查询匹配库存的维度更新时间;
     * @author C14717
     * 根据最近一小时新增或更新来删除redis table
     */
    @Select("SELECT DISTINCT inventory_match_code FROM dbo.ops_inventory_match_config where DateDiff(mi,cre_time,getDate())<=#{time} UNION ALL SELECT DISTINCT inventory_match_code FROM dbo.ops_inventory_match_config where DateDiff(mi,modify_time,getDate())<=#{time}")
    public List<String> refreshOpsInventoryMatchConfigData(@Param("time") Long time);

    /**
     * 根据ruleCode获取出库规则 的更新时间;
     * @author C14717
     * 根据最近一小时新增或更新来删除redis table
     */
    @Select("SELECT DISTINCT rule_code FROM dbo.ops_inventory_rule_config where DateDiff(mi,cre_time,getDate())<=#{time} UNION ALL SELECT DISTINCT rule_code FROM dbo.ops_inventory_rule_config where DateDiff(mi,modify_time,getDate())<=#{time}")
    public List<String> refreshOpsInventoryRuleConfigData(@Param("time") Long time);

    /**
     * 根据ruleCode获取出库规则明细 的更新时间;
     * @author C14717
     * 根据最近一小时新增或更新来删除redis table
     */
    @Select("SELECT DISTINCT rule_code FROM dbo.ops_inventory_rule_config_item where DateDiff(mi,cre_time,getDate())<=#{time} UNION ALL SELECT DISTINCT rule_code FROM dbo.ops_inventory_rule_config_item where DateDiff(mi,modify_time,getDate())<=#{time}")
    public List<String> refreshOpsInventoryRuleConfigItemData(@Param("time") Long time);

    /**
     * 获取单据类型和单据标记对应的出库规 的更新时间;
     * @author C14717
     * 根据最近一小时新增或更新来删除redis table
     */
    @Select("SELECT DISTINCT order_type,order_tag,property_type FROM dbo.ops_order_inventory_rule_config where DateDiff(mi,cre_time,getDate())<=#{time} UNION ALL SELECT DISTINCT DISTINCT order_type,order_tag,property_type FROM dbo.ops_order_inventory_rule_config where DateDiff(mi,modify_time,getDate())<=#{time}")
    public List<OpsOrderInventoryRuleConfig> refreshOpsOrderInventoryRuleConfigData(@Param("time") Long time);

    /**
     * 按发票号更新在途状态
     */
    @Update("<script>" +
            "update ops_inventory_move set opt_status=#{optStatus} ,opt_time=GETDATE(),modify_time=GETDATE() ,version=version+1" +
            "where delflag=0 and invoiceNo=#{invoiceNo} "+
            "<if test = 'invoiceId != null'> "+
            "   and invoiceId = #{invoiceId} "+
            "</if> "+
            "</script>")
    int updateOptStatusInventoryMoveByInvoiceNo(@Param("invoiceNo") String invoiceNo,@Param("invoiceId") Long invoiceId, @Param("optStatus") Integer optStatus);


    /**
     * 签收仓库更变
     */
    @Update("update ops_inventory_move set sign_warehouse_code=#{signWarehouseCode} ,modify_time=GETDATE() where delflag=0 and invoiceNo=#{invoiceNo} ")
    int updateInventoryMoveSignWarehouseByInvoiceNo(@Param("invoiceNo") String invoiceNo, @Param("signWarehouseCode") String signWarehouseCode);


    /**
     * PO转定 更变供应商ID以及签收仓
     */
    @Update("update ops_inventory_move set sign_warehouse_code=#{signWarehouseCode},supplierId=#{supplierId} ,modify_time=GETDATE(),version=version+1 where inventory_id=#{inventoryId} and delflag=0 and version=#{version} ")
    int updateInventoryMoveConversionByInventoryId(@Param("inventoryId") Long inventoryId,@Param("supplierId") String supplierId,@Param("signWarehouseCode") String signWarehouseCode, @Param("version") Long version);

    /**
     * 发票确认数据操作状态（记录原始数量）
     */
    @Update("<script>" +
            "update ops_inventory_move set opt_status=#{optStatus} ,original_quantity=quantity,pre_original_quantity=prepare_quantity,opt_time=GETDATE() ,modify_time=GETDATE() ,version=version+1" +
            "where delflag=0 and invoiceNo=#{invoiceNo} " +
            "<if test = 'invoiceId != null'> "+
            "   and invoiceId = #{invoiceId} "+
            "</if> "+
            "</script>")
    int updateOptStatusInventoryMoveConfirm(@Param("invoiceNo") String invoiceNo,@Param("invoiceId") Long invoiceId, @Param("optStatus") Integer optStatus);



    @Select("SELECT  inventory_id,inventory_property_id,inventory_status,modelno,quantity,prepare_quantity,unit,price,qa_status,in_time,version,warehouse_code FROM ops_inventory " +
            "WHERE delflag=0 AND modelno=#{modelno} ")
    List<OpsInventory> getInvListByModelNoDao( @Param("modelno") String modelno);




}


