package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsDeliveryPlanConfig;
import com.sales.ops.db.entity.OpsDeliveryPlanReliabilityConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * bugid: 13685
 * @author ：C14717
 * @version: 1.0$
 * @description：交付计划
 * @date ：Created in 2024/04/01 10:03
 */
public interface OpsDeliveryPlanDao {

    /**
     * 版本号查询
     * @param orderId
     * @param orderItem
     * @return
     */
    @Select("SELECT top 1 version from ops_delivery_plan_result WHERE order_id = #{orderId} and order_item = #{orderItem} and current_valid = 1")
    Long getVersion(@Param("orderId") String orderId, @Param("orderItem") String orderItem);

    @Select("SELECT current_cycle ,current_cycle_point ,reliability  from ops_delivery_plan_reliability_config where delflag = 0")
    List<OpsDeliveryPlanReliabilityConfig> getReliablilityList();

    @Select("SELECT id FROM  ops_delivery_plan_result  WHERE order_id = #{orderId} and order_item = #{orderItem}  and current_valid = 1 ")
    List<Long> getResultsCurrentValid(@Param("orderId") String orderId, @Param("orderItem") String orderItem);

    @Select("SELECT id FROM  ops_delivery_plan_detail  WHERE order_id = #{orderId} and order_item = #{orderItem}  and current_valid = 1 ")
    List<Long> getDetailsCurrentValid(@Param("orderId") String orderId, @Param("orderItem") String orderItem);

    @Update("UPDATE ops_delivery_plan_result SET current_valid = 0 WHERE id = #{id} ")
    void updateResultsCurrentValidById(@Param("id") Long id);

    @Update("UPDATE ops_delivery_plan_detail SET current_valid = 0 WHERE id = #{id} ")
    void updateDetailsCurrentValidById(@Param("id") Long id);


    @Update("UPDATE ops_delivery_plan_result SET current_valid = 0 WHERE order_id = #{orderId} and order_item = #{orderItem}  and current_valid = 1 ")
    void updateResultsCurrentValid(@Param("orderId") String orderId, @Param("orderItem") String orderItem);

    @Update("UPDATE ops_delivery_plan_detail SET current_valid = 0 WHERE order_id = #{orderId} and order_item = #{orderItem}  and current_valid = 1 ")
    void updateDetailsCurrentValid(@Param("orderId") String orderId, @Param("orderItem") String orderItem);

    @Select("SELECT TOP 1 * FROM ops_delivery_plan_config WHERE current_cycle = #{currentCycle} ")
    OpsDeliveryPlanConfig getOpsDeliveryPlanConfig(@Param("currentCycle") String currentCycle);

    @Select("SELECT TOP 1 sign_time  from ops_ro where invoice_id = #{invoiceId} ")
    Date getOpsRoSignTime(@Param("invoiceId") Long invoiceId);

    @Select("SELECT top 1 cre_time  from ops_do_post where do_id = #{doId} ")
    Date getOpsDoPostCreateTime(@Param("doId") String doId);

    @Select("SELECT top 1 ship_date from expdetail where delivery_no = #{deliveryNo} order by ship_date desc ")
    Date getExpdetailShipDate(@Param("deliveryNo") String deliveryNo);




}
