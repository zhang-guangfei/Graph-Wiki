package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsOrderAssignResult;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.dto.order.OpsSalesNoticeTaskDto;
import com.sales.ops.dto.prepareOrder.PrestockApplyDto;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CommonMqpper {

    @Select("select * from ops_sharedb.dbo.prestock_apply where id = #{id} ")
    PrestockApplyDto queryPrestockApply(@Param("id") Long id);

    @Select("select * from ops_sharedb.dbo.order_modify where order_no = #{orderNo} and modify_type = 'C' ")
    List<OrderModifyVO> queryOrderModifyList(@Param("orderNo") String orderNo);


    @Insert("" +
            "INSERT INTO [ops_sharedb].[dbo].[order_modify] (\n" +
            "            batch_no,\n" +
            "            modify_type,\n" +
            "            status,\n" +
            "            order_no,\n" +
            "            dept_no,\n" +
            "            customer_no,\n" +
            "            change_type,\n" +
            "            answer_text,\n" +
            "            create_user,\n" +
            "            create_time,\n" +
            "            supplier_orderNo\n" +
            "        ) VALUES (\n" +
            "            COALESCE(#{batchNo}, ''),\n" +
            "            #{modifyType},\n" +
            "            #{status},\n" +
            "            #{orderNo},\n" +
            "            #{deptNo},\n" +
            "            #{customerNo},\n" +
            "            #{changeType},\n" +
            "            #{answerText},\n" +
            "            #{createUser},\n" +
            "            #{createTime},\n" +
            "            #{supplierOrderNo}\n" +
            "        )" +
            "")
    void insertOrderModify(OrderModifyVO orderModifyVO);


    @Insert("" +
            "INSERT INTO [ops_sharedb].[dbo].[ops_sales_notice_task] (\n" +
            "    batch_no,\n" +
            "    business_code,\n" +
            "    order_fno,\n" +
            "    call_back_parameter,\n" +
            "    handle_start_time,\n" +
            "    handle_status,\n" +
            "    create_time,\n" +
            "    return_status,\n" +
            "    try_count,\n" +
            "    error_hand_count,\n" +
            "    source\n" +
            ") VALUES (\n" +
           " #{batchNo}," +
            " #{businessCode}," +
            " #{orderFno}, " +
            " #{callBackParameter}," +
            " #{handleStartTime}," +
            " #{handleStatus}," +
            " #{createTime}," +
            " #{returnStatus}," +
            " #{tryCount}," +
            " #{errorHandCount}, " +
            " #{source} " +
            ");" +
            "")
    void insertSalesNoticeTask(OpsSalesNoticeTaskDto opsSalesNoticeTaskDto);

    @Update(" update [ops_sharedb].[dbo].[order_modify] set answer_time = getDate(), status = '6' , answer_text = #{cancelDesc}, answer_pns = #{pns} " +
            " where id = #{id} " +
            "")
    void updateOrderModify(@Param("cancelDesc") String cancelDesc, @Param("pns") String pns, @Param("id") Long id);


    @Update("update [ops_sharedb].[dbo].[ops_sales_notice_task] set call_back_parameter = #{callBackParam},return_status = #{returnStatus},update_time = getDate() where batch_no = #{batchNo}  ")
    void updateSalesNoticeTaskCallBackParam(@Param("callBackParam")String callBackParam,
                                            @Param("batchNo")String batchNo,
                                            @Param("returnStatus")int returnStatus);


    @Select("SELECT m.dept_no,m.customer_no,r.order_type FROM ops_sharedb.dbo.order_modify m " +
            " left join ops_core.dbo.rcvdetail r on m.order_no = r.rorder_fno   " +
            " WHERE m.order_no =#{orderNo} and m.modify_type='C' and m.status=6 ")
    List<OrderModifyVO> getOrderCancelData(@Param("orderNo") String orderNo);


    @Select("SELECT Top(1) apply_dept_no as dept_no, customerNo FROM ops_requestPurchase_cancel_log WHERE orderNo=#{orderNo} AND itemNo =#{itemNo} order by insertTime desc")
    OrderModifyVO getPurchaseOrderByNo(@Param("orderNo") String orderNo,@Param("itemNo") String itemNo);


    @Select("select * from ops_requestPurchase where orderNo = #{orderNo} and itemNo = #{itemNo}")
    List<OpsPurchaseorder> queryPurchaseOrder(@Param("orderNo") String orderNo,@Param("itemNo") String itemNo);


    /**
     * 采购详情界面追加显示先行在库补库单预约情况
     */
    @Select("select order_no,model_no as modelno,order_qty as quantity, create_Time as updateTime from [ops_sharedb].[dbo].[prestock_result] where prepare_order = #{orderNo} and opt_status = 2 ")
    List<OpsOrderAssignResult> queryPreStockResult(@Param("orderNo") String orderNo);


    @Select("select ppl_no,project_no,group_customer_no from ops_core.dbo.rcvdetail where rorder_no = #{rorderNo} and rorder_item = #{itemNo}")
    Rcvdetail queryRcvDetail(@Param("rorderNo") String rorderNo, @Param("itemNo") Integer itemNo);

    @Select("select ppl,projectCode,groupCustomerNo from ops_requestpurchase where orderNo = #{orderNo} and itemNo = #{itemNo}")
    OpsRequestpurchase queryOpsRequestpurchase(@Param("orderNo") String orderNo, @Param("itemNo") Integer itemNo);
}
