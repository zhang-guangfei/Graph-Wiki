package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.dto.inventory.InventoryRiskDTO;
import com.sales.ops.dto.inventory.RiskDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: $ 19127
 * @description：
 * @date ：Created in 2025/11/4 9:56
 */
public interface InventoryRiskDao {

    // 更新成失效数据
    @Update("update ops_inventory_risk set delflag =1,update_time =GETDATE(),update_user ='everyday' where delflag =0")
    Long updateInvRiskExpired();

    @Select("SELECT id,batch_no,model_no,inventory_id,quantity,prepare_quantity,source_table,booking_details,delflag" +
            ",create_user FROM ops_inventory_risk")
    List<InventoryRiskDTO> findAllRisk();

    @Delete("delete from ops_inventory_risk")
    void delAllRisk();

    // 查找来源一数据
    @Select("SELECT DISTINCT inventory_id, CONVERT(VARCHAR(8), GETDATE() , 112) AS batch_no ,modelno as model_no "
            +",quantity ,prepare_quantity ,ava_qty ,'preorder_account' as source_table FROM ops_inventory i "
            +"WHERE EXISTS (  SELECT 1 FROM ops_sharedb.dbo.preorder_account pa "
            +" INNER JOIN ops_sharedb.dbo.preorder_account_detail pad ON pa.inventory_id = pad.inventory_id "
            +" WHERE "
            + " NOT EXISTS ( " +
            " SELECT 1 " +
            " FROM (SELECT 14651 AS id UNION ALL SELECT 10101) AS temp_table " + // 忽略95001 95002
            " WHERE temp_table.id = i.inventory_property_id " +
            ") and "
            + " pad.delflag = 0 AND pad.status IN ('1','2','3','8') AND pa.inventory_id = i.inventory_id "
            +" ) and (i.quantity - i.prepare_quantity) > 0 and i.delflag =0 and LEN(i.warehouse_code) = 3")
    List<InventoryRiskDTO> findPreorderAccountData();


    @Select("SELECT " +
            " i.inventory_id, i.inventory_property_id, #{batchNo} AS batch_no," +
            " i.modelno AS model_no" +
            ",IIF(sub.total_ava_qty < (i.quantity - i.prepare_quantity), sub.total_ava_qty, (i.quantity - i.prepare_quantity)) AS quantity" +
            ",0 AS prepare_quantity,'preorder_account' AS source_table FROM ops_inventory i "+
            "INNER JOIN (" +
            "    SELECT " +
            "        pa.inventory_id, " +
            "        SUM(pad.ava_qty) as total_ava_qty " +
            "    FROM ops_sharedb.dbo.preorder_account pa " +
            "    JOIN ops_sharedb.dbo.preorder_account_detail pad ON pa.inventory_id = pad.inventory_id " +
            "    WHERE pad.delflag = 0 AND pad.status IN ('1','2','3','8') " +
            "    GROUP BY pa.inventory_id " +
            ") sub ON sub.inventory_id = i.inventory_id " +
            "WHERE i.inventory_property_id NOT IN (14651, 10101) " +
            "  and (i.quantity - i.prepare_quantity) > 0  AND i.delflag = 0 AND LEN(i.warehouse_code) = 3 and sub.total_ava_qty > 0")
    List<InventoryRiskDTO> findPreorderAccountDataNew(String batchNo);

    // 查找来源二数据
    @Select("SELECT " + // 这里不需要 DISTINCT 了
            "i.inventory_id, " +
            "#{batchNo} AS batch_no, " +
            "i.modelno AS model_no, " +
            "(i.quantity - i.prepare_quantity) AS quantity, " +
            "0 AS prepare_quantity, " +
            "'model_exp_freq' AS source_table " +
            "FROM ops_inventory i " +
            "INNER JOIN ops_inventory_property ip " +
            "   ON i.inventory_property_id = ip.inventory_property_id " +
            "   AND ip.delflag = 0 " +
            "   AND ip.inventory_type_code IN ('GK-TY', 'GK-PJ', 'GK-PPL') " +
            "WHERE i.inventory_property_id NOT IN (14651, 10101) " +
            "AND i.delflag = 0 " +
            "AND (i.quantity - i.prepare_quantity) > 0 " +
            "AND LEN(i.warehouse_code) = 3 " +
            " AND EXISTS (" +
            " SELECT 1 " +
            " FROM ops_report.dbo.model_exp_freq m " +
            " WHERE m.modelno = i.modelno   " +
            "   AND m.StockCode = 'ALL' " +
            "   AND m.modelType IN (1, 2)  " +
            " GROUP BY m.modelno " +
            " HAVING MAX(m.AvgQtyOf8) = 0  " +
            " )")
    List<InventoryRiskDTO> findModelExpFreqDataNew(@Param("batchNo") String batchNo);


    // 查找来源二数据
    @Select("SELECT DISTINCT  inventory_id , CONVERT(VARCHAR(8), GETDATE() , 112) AS batch_no,i.modelno as model_no "
            +",quantity -prepare_quantity as quantity , 0 as prepare_quantity ,'model_exp_freq' as source_table "
            +"FROM ops_inventory i "
            +"INNER JOIN ops_inventory_property ip "
            +" ON i.inventory_property_id = ip.inventory_property_id "
            +" AND ip.delflag = 0  AND ip.inventory_type_code IN ('GK-TY', 'GK-PJ', 'GK-PPL') "
            +"INNER JOIN ops_report.dbo.model_exp_freq m "
            + " ON i.modelno = m.modelno  AND m.StockCode = 'ALL'  AND m.AvgQtyOf8 = 0 "
            +"WHERE i.inventory_property_id NOT IN (14651, 10101) "
            + " and i.delflag = 0  AND (i.quantity - i.prepare_quantity) > 0  AND LEN(i.warehouse_code) = 3 ")
    List<InventoryRiskDTO> findModelExpFreqData();

    // 写入数据
    @Insert("<script> INSERT INTO ops_inventory_risk ("
            +"batch_no,model_no,inventory_id,quantity,prepare_quantity,source_table,delflag,create_user,version) " +
            "values <foreach collection='list' item='item' index='index' separator=','>  "
            +" (#{item.batchNo},#{item.modelNo},#{item.inventoryId},#{item.quantity},#{item.prepareQuantity},"
            +"#{item.sourceTable},#{item.delflag},#{item.createUser},#{item.version}"
            +" )  </foreach> </script>")
    Boolean insertInvRiskBatch(@Param("list") List<InventoryRiskDTO> list);

    @Insert("<script> INSERT INTO ops_inventory_risk_log ("
            +"id,batch_no,model_no,inventory_id,quantity,prepare_quantity,source_table,booking_details,delflag,create_user) " +
            "values <foreach collection='list' item='item' index='index' separator=','>  "
            +" (#{item.id},#{item.batchNo},#{item.modelNo},#{item.inventoryId},#{item.quantity},#{item.prepareQuantity},"
            +"#{item.sourceTable},#{item.bookingDetails},#{item.delflag},#{item.createUser}"
            +" )  </foreach> </script>")
    Boolean insertInvRiskLogBatch(@Param("list") List<InventoryRiskDTO> list);

    @Select(" SELECT DISTINCT i.inventory_property_id ,i.inventory_id from ops_inventory_risk  r " +
            "left join ops_inventory i on r.inventory_id = i.inventory_id  " +
            "where r.delflag =0 and r.model_no = #{modelNo} ")
    List<OpsInventory> findInvRisk(String modelNo);

    @Select("<script>" +
            "select inventory_id, quantity, prepare_quantity " +
            "from ops_inventory_risk " +
            "where delflag = 0 " +
            "and inventory_id in " +
            "<foreach item='id' collection='invIds' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<RiskDto> getInvRisk(@Param("invIds") List<Long> invIds);

    @Select("<script>" +
            " select TOP 1 inventory_id, quantity, prepare_quantity,version,booking_details " +
            "from ops_inventory_risk " +
            "where delflag = 0 " +
            "and inventory_id = #{invId} </script>")
    InventoryRiskDTO getInvRiskByInvId(@Param("invId") Long invId);

    @Update({"UPDATE dbo.ops_inventory_risk SET version=version+1,prepare_quantity=prepare_quantity+#{TPreQty} " +
            ",update_user=#{Tmodifier},update_time=GETDATE() ,booking_details= #{bookingDetails} WHERE version=#{Sversion} AND inventory_id=#{SInventoryId}"})
    int updatePreQtyRiskInv(@Param("TPreQty") int TPreQty, @Param("Tmodifier") String Tmodifier, @Param("Sversion") long Sversion
            , @Param("SInventoryId") long SInventoryId, @Param("bookingDetails") String bookingDetails);



    @Select("<script>" +
            "select inventory_property_id, inventory_type_code, customer_no, ppl, project_code, " +
            "group_customer_no, sales_info_no, exp_date, version " +
            "from ops_inventory_property " +
            "where delflag = 0 " +
            "and inventory_property_id in " +
            "<foreach item='id' collection='invProIds' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<OpsInventoryProperty> getInvProperty(@Param("invProIds") List<Long> invProIds);



    @Select("SELECT count(inventory_id)  from ops_inventory_risk where delflag =0 and inventory_id = #{invId}")
    int findInvRiskById(Long invId);

    @Select("SELECT * from ops_inventory_risk where delflag =0 and model_no = #{modelNo}")
    List<InventoryRiskDTO> selectInvRiskByModelNO(String modelNo);

    @Select("SELECT sum(i.quantity) - sum (i.prepare_quantity) from ops_inventory_risk  r\n" +
            "                       left join ops_inventory i on r.inventory_id = i.inventory_id \n" +
            "                      where r.delflag =0 and r.model_no =#{modelNo} group by r.model_no ")
    Integer findInvRiskByModelNOGetInvQty(String modelNo);


    @Select("SELECT DISTINCT i.inventory_property_id ,i.inventory_id,i.warehouse_code from ops_inventory_risk  r " +
            "left join ops_inventory i on r.inventory_id = i.inventory_id  " +
            "where r.delflag =0 and r.model_no = #{modelNo}")
    List<OpsInventory> findInvRiskToShow(String modelNo);

    @Select("SELECT sum (quantity - prepare_quantity) from ops_inventory where delflag =0  and inventory_property_id =1 " +
            "and len(warehouse_code) = 3 and quantity - prepare_quantity  > 0 and modelno = #{modelNo} group by modelno")
    Integer sumTYInv(String modelNo);

    @Select("SELECT sum (quantity - prepare_quantity) from ops_inventory_risk where delflag =0 " +
            "and quantity - prepare_quantity  > 0 and model_no = #{modelNo} group by model_no")
    Integer sumRiskInv(String modelNo);
}
