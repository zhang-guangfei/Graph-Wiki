package com.sales.ops.db.extdao;

import com.sales.ops.dto.replacement.*;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/29 8:21
 */
public interface NotifyShipmentPlanDao {



    @Select("SELECT sum(plan_qty) from notify_shipment_plan where delflag=0 and order_id = #{orderId} and order_item = #{orderItem} and modelno = #{modelno}")
    Integer sumNotifyShipmentPlanQty(String orderId ,String orderItem,String modelno);

    @Select("SELECT count(plan_no) from notify_shipment_plan where delflag=0 and plan_no = #{planNo}")
    Integer countNotifyShipmentPlan(String planNo);

    @Select("<script>with itemSum as ( " +
            "SELECT plan_no,sum(pi.qty) as match_qty, sum(d.out_qty) as out_qty from " +
            "notify_shipment_plan_item pi left join ops_do_item d on pi.do_id = d.do_id and d.delflag=0 where pi.delflag=0 group by pi.plan_no) " +
            "SELECT p.id ,p.plan_no ,p.order_fno ,r.quantity as order_qty,r.model_no as order_model_no,p.modelno ,p.plan_qty \n" +
            ",i.match_qty,i.out_qty,p.hope_date ,p.wl_date ,p.status ,p.create_time ,p.creator ,p.remark " +
            "from notify_shipment_plan p \n" +
            "left join rcvdetail r on p.order_fno = r.rorder_fno  \n" +
            "left join itemSum i on p.plan_no = i.plan_no where p.delflag=0 "+
            " <if test=\"planNo != null and planNo != '' \"> and p.plan_no = #{planNo}  </if> \n"+
            " <if test=\"status != null  \"> and p.status = #{status}  </if> \n"+
            " <if test=\"orderFno != null and orderFno != ''   \"> and p.order_fno like concat(#{orderFno},'%')    </if> \n"+
            " <if test=\"modelNo != null and modelNo != ''  \"> and p.modelNo = #{modelNo}  </if> \n"+
            " <if test=\"orderModelNo != null and orderModelNo != ''  \"> and r.model_no = #{orderModelNo}  </if> \n"+
            "</script>")
    List<NotifyShipmentPlanPageDto> findNSForPage(NotifyShipmentPlanPageDto item);


    @Select("<script>with itemSum as ( " +
            "SELECT plan_no,sum(pi.qty) as match_qty, sum(d.out_qty) as out_qty from " +
            "notify_shipment_plan_item pi left join ops_do_item d on pi.do_id = d.do_id and d.delflag=0 where pi.delflag=0 group by pi.plan_no) " +
            "SELECT p.id ,p.plan_no ,p.order_fno ,r.quantity as order_qty,r.model_no as order_model_no,p.modelno ,p.plan_qty \n" +
            ",i.match_qty,i.out_qty,p.hope_date ,p.wl_date ,p.status ,p.create_time ,p.creator ,p.remark  " +
            "from notify_shipment_plan p \n" +
            "left join rcvdetail r on p.order_fno = r.rorder_fno  \n" +
            "left join itemSum i on p.plan_no = i.plan_no where p.delflag=0 "+
            " <if test=\"planNo != null and planNo != '' \"> and p.plan_no = #{planNo}  </if> \n"+
            " <if test=\"status != null  \"> and p.status = #{status}  </if> \n"+
            " <if test=\"orderFno != null and orderFno != ''   \"> and p.order_fno like concat(#{orderFno},'%')    </if> \n"+
            " <if test=\"modelNo != null and modelNo != ''  \"> and p.modelNo = #{modelNo}  </if> \n"+
            " <if test=\"orderModelNo != null and orderModelNo != ''  \"> and r.model_no = #{orderModelNo}  </if> \n"+
            "</script>")
    List<NotifyShipmentPlanPageDto> findNS(NotifyShipmentFindParam item);

    @Select("SELECT p.id ,\n" +
            "p.plan_no ,\n" +
            "p.plan_qty,\n" +
            "i.match_qty,\n" +
            "i.out_qty,\n" +
            "p.hope_date \n" +
            "from notify_shipment_plan p   \n" +
            "left join (  \n" +
            "SELECT plan_no,\n" +
            "sum(pi.qty) as match_qty, \n" +
            "sum(d.out_qty) as out_qty \n" +
            "from   notify_shipment_plan_item pi \n" +
            "left join ops_do_item d on pi.do_id = d.do_id and d.delflag=0 \n" +
            "where  pi.delflag=0  \n" +
            "group by pi.plan_no\n" +
            ")i on p.plan_no = i.plan_no \n" +
            "inner join notify_shipment_plan_item pi on pi.plan_no=p.plan_no \n" +
            "where pi.delflag=0 and p.delflag=0 and pi.do_id= #{wmOrderId} ")
    List<NotifyShipmentPlanPageDto> findNotifyShipmentPlanByWmOrderId(String wmOrderId);


    @Select("<script>with itemSum as ( " +
            "SELECT plan_no,sum(pi.qty) as match_qty, sum(d.out_qty) as out_qty from " +
            "notify_shipment_plan_item pi left join ops_do_item d on pi.do_id = d.do_id and d.delflag=0 where pi.delflag=0 group by pi.plan_no) " +
            "SELECT top 2000 p.id ,p.plan_no ,p.order_fno ,r.quantity as order_qty,r.model_no as order_model_no,p.modelno ,p.plan_qty \n" +
            ",i.match_qty,i.out_qty,p.hope_date ,p.wl_date ,p.status ,p.create_time ,p.creator  " +
            "from notify_shipment_plan p \n" +
            "left join rcvdetail r on p.order_fno = r.rorder_fno  \n" +
            "left join itemSum i on p.plan_no = i.plan_no where p.delflag=0 "+
            " <if test=\"planNo != null and planNo != '' \"> and p.plan_no = #{planNo}  </if> \n"+
            " <if test=\"status != null \"> and p.status = #{status}  </if> \n"+
            " <if test=\"orderFno != null and orderFno != ''   \"> and p.order_fno like concat(#{orderFno},'%')    </if> \n"+
            " <if test=\"modelNo != null and modelNo != ''  \"> and p.modelNo = #{modelNo}  </if> \n"+
            " <if test=\"orderModelNo != null and orderModelNo != ''  \"> and r.model_no = #{orderModelNo}  </if> \n"+
            "</script>")
    List<NotifyShipmentPlanPageDto> findNotifyShipmentPlanListExport(NotifyShipmentPlanExportPageDto item);

    @Select("SELECT pi.plan_no ,pi.qty ,pi.do_id ,d.gather_warehouse_code as warehouse_code ,d.do_status ,d.is_wms\n" +
            "from notify_shipment_plan_item pi \n" +
            "left join ops_do d on pi.do_id = d.do_id where pi.plan_no = #{planNo} and d.delflag=0 and pi.delflag=0 ")
    List<NotifyShipmentPlanItemPageDto> findNotifyShipentPlanItemPageList(String planNo);

    @Select("SELECT * from notify_shipment_plan where delflag=0 and order_id = #{orderId} and order_item = #{orderItem}")
    List<NotifyShipmentPlanDto> findNotifyShipmentPlanList(String orderId , String orderItem);

    @Select("SELECT count(plan_no) from notify_shipment_plan where delflag=0 and order_id = #{orderId} and order_item = #{orderItem}")
    int countNotifyShipmentPlanList(String orderId , String orderItem);

    @Select("SELECT * from notify_shipment_plan_item where delflag=0 and plan_no = #{planNo}")
    List<NotifyShipmentPlanItemDto> findNotifyShipentPlanItemList(String planNo);

    @Update("UPDATE notify_shipment_plan set  status = #{status} , update_time = GETDATE() where  plan_no = #{planNo} and delflag =0")
    int updateNotifyShipmentStatus(String planNo, Integer status);

    @Update("UPDATE notify_shipment_plan set  status = 2 ,remark = #{remark} , update_time = GETDATE() where order_id = #{orderId} and order_item = #{orderItem} and delflag =0")
    int updateNotifyShipmentFinish(String orderId, String orderItem,String remark);
    @Update("UPDATE notify_shipment_plan_item set delflag=1 ,updator =#{updator}, update_time = GETDATE()  where plan_no = #{planNo} and delflag =0")
    int updateNotifyShipmentItemDel(String planNo,String updator);

    @Update("UPDATE notify_shipment_plan set  status = 0 ,updator =#{updator} ,remark='' , update_time = GETDATE() where  plan_no = #{planNo} and delflag =0")
    int updateNotifyShipmentRealse(String planNo, String updator);

}
