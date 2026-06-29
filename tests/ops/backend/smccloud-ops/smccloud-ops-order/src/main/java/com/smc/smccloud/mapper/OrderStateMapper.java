package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.yulichang.base.MPJBaseMapper;
import com.smc.smccloud.model.adapter.order.PurchaseOrderBean;
import com.smc.smccloud.model.adapter.order.PurchaseOrderCondition;
import com.smc.smccloud.model.orderstate.CheckOrderStateVO;
import com.smc.smccloud.model.orderstate.OrderStateDO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@DS("opsdb")
@Mapper
public interface OrderStateMapper extends MPJBaseMapper<OrderStateDO> {



    @Update("update order_state \n" +
            "set cust_dlv_date=d.dlv_date,cust_ship_date=d.wms_dlv_date ,order_type=d.order_type,corder_no=d.corder_no,\n" +
            "cmodel_no=d.cproduct_no\n" +
            "from order_state o inner join rcvdetail d on d.rorder_no=o.rorder_no and d.rorder_item=o.item_no\n" +
            "where o.cust_dlv_date is null or cust_dlv_date<>d.dlv_date\n" +
            "or o.order_type is null\n" +
            "or o.corder_no is null;" +
            "update order_state set customer_no=m.customer_no,user_no=m.end_user,Receive_Date=m.ROrdDate\n" +
            "from order_state o inner join rcvmaster m on m.rorder_no=o.rorder_no\n" +
            "where o.customer_no is null or o.user_no is null or Receive_Date is null")
    void syncOrderStateInfo();

    @Select("SELECT Top(1) apply_dept_no as dept_no, customerNo FROM ops_requestPurchase_cancel_log WHERE orderNo=#{orderNo} AND itemNo =#{itemNo} order by insertTime desc")
    OrderStateDO getPurchaseOrderByNo(@Param("orderNo") String orderNo,@Param("itemNo") String itemNo);

    @Select("SELECT m.dept_no,m.customer_no,r.order_type FROM ops_sharedb.dbo.order_modify m " +
            " left join ops_core.dbo.rcvdetail r on m.order_no = r.rorder_fno   " +
            " WHERE m.order_no =#{orderNo} and m.modify_type='C' and m.status=6 ")
    List<OrderStateDO> getOrderCancelData(@Param("orderNo") String orderNo);
}
