package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsInquiryApply;
import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.dto.inquiry.InquiryApplyAddParam;
import com.sales.ops.dto.inquiry.InquiryApplyRequest;
import com.sales.ops.dto.inquiry.InquiryOrderDetailReturnDto;
import com.sales.ops.dto.inquiry.InquiryOrderMasterDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-10-24
 */
@DS("opsdb")
@Mapper
public interface InquiryApplyMapper {


    @Select(" <script> select  * from ops_inquiry_apply where order_no = #{orderno} order by inquiry_time desc </script> ")
    List<OpsInquiryApply> selectRecentApply(@Param("orderno") String orderno); // 取最近一次催促的时间，计算间隔

    @Select(" <script> select * from ops_inquiry_apply  where 1=1 " +
            " <if test=\"inquiryApplyNo != null  and inquiryApplyNo != ''\">and  inquiry_apply_no =#{inquiryApplyNo} </if> " +
            " <if test=\"orderNo != null  and orderNo != ''\">and  order_no =#{orderNo} </if>" +
            " <if test=\"modelNo != null  and modelNo != ''\">and  model_no =#{modelNo} </if>" +
            " <if test=\"inquiryStatus != null  and inquiryStatus != ''\">and  inquiry_status =#{inquiryStatus} </if>" +
            " <if test=\"inquiryUser != null  and inquiryUser != ''\">and  inquiry_user =#{inquiryUser} </if>" +
            " <if test=\"purchaseNo != null  and purchaseNo != ''\">and  purchase_no =#{purchaseNo} </if>" +
            " <if test=\"customerNo != null  and customerNo != ''\">and  customer_no =#{customerNo} </if>" +
            " <if test=\"endUser != null  and endUser != ''\">and  end_user =#{endUser} </if>" +
            " <if test=\"replyDept != null  and replyDept != ''\">and  reply_dept =#{replyDept} </if>" +
            " <if test=\"replyDeptList != null and replyDeptList.size() > 0\">\n" +
            "      and reply_dept in\n" +
            "      <foreach collection=\"replyDeptList\" item=\"item\" index=\"index\" separator=\",\" open=\"(\" close=\")\">\n" +
            "        #{item}\n" +
            "      </foreach>\n" +
            " </if>" +
            " <if test=\"replyNo != null  and replyNo != ''\">and  reply_no =#{replyNo} </if>" +
            " <if test=\"inquiryType != null  and inquiryType != ''\">and  inquiry_type =#{inquiryType} </if>" +
            " <if test=\"inquiryDept != null  and inquiryDept != ''\">and  inquiry_dept =#{inquiryDept} </if>" +
            " <if test=\"startDate != null\">and inquiry_time between #{startDate} and #{endDate} </if>" +
            " <if test=\"orderType != null  and orderType != ''\">and  order_type =#{orderType} </if>" +
            " <if test=\"inquiryReasonType != null  and inquiryReasonType != ''\">and  inquiry_reason_type =#{inquiryReasonType} </if>" +
            " <if test=\"inquiryDeptList != null and inquiryDeptList.size() > 0\">\n" +
            "      and inquiry_dept in\n" +
            "      <foreach collection=\"inquiryDeptList\" item=\"item\" index=\"index\" separator=\",\" open=\"(\" close=\")\">\n" +
            "        #{item}\n" +
            "      </foreach>\n" +
            "    </if>" +
            "</script> ")
    List<OpsInquiryApply> selectInquiryApply(InquiryApplyRequest inquiryApplyRequest);

    @Select(" <script> select * from ops_inquiry_detail where inquiry_apply_no = #{applyno} </script> ")
    List<InquiryOrderDetailReturnDto> selectInquiryDetail(@Param("applyno") String applyno);

    @Select(" <script> select b.rorder_splitno,b.model_no,b.quantity,c.status order_status,b.dlv_moddate,c.expected_delivery_time,\n" +
            "c.estimated_delivery_day,b.hope_export_date,b.hope_delivery_date,b.supplier_id,b.supplier_order_no,b.reply_no,b.reply_delivery_date,\n" +
            "b.reply_user,b.reply_time,b.reply_delay_reason,b.reply_remark,b.inquiry_level ,b.reply_delivery_date_src\n" +
            "from ops_inquiry_apply a inner join ops_inquiry_detail  b on a.inquiry_apply_no = b.inquiry_apply_no\n" +
            "inner join (\n" +
            "select   b.rorder_fno,a.modelno,a.quantity qty,a.stock_type status, b.expected_delivery_time,b.estimated_delivery_day,\n"+
            " b.rorder_fno as rorder_splitno \n" +
            "from ops_order_assign_result a inner join rcv_view b\n" +
            "on a.order_no = b.rorder_no and a.order_item = b.rorder_item  \n" +
            ") c on a.order_no = c.rorder_fno and b.model_no = c.modelno and b.quantity = c.qty\n" +
            "where a.inquiry_apply_no = #{applyno} order by b.item_no </script> ")
    List<InquiryOrderDetailReturnDto> selectInquiryDetailOrder(@Param("applyno") String applyno);

    @Select(" <script> select * from rcvdetail where rorder_no = #{orderno} and rorder_item = #{itemno}  </script> ")
    List<Rcvdetail> getRcvDetail(@Param("orderno") String orderno, @Param("itemno") Integer itemno);


    @Insert("<script> insert into dbo.ops_inquiry_apply ( inquiry_apply_no, batch_no, \n" +
            "      data_sources, order_no, model_no, \n" +
            "      quantity, customer_no, end_user, \n" +
            "      purchase_no, order_status, inquiry_status, \n" +
            "      hope_export_date, dlv_moddate, hope_delivery_date, \n" +
            "      inquiry_reason_type, inquiry_reason, inquiry_description, \n" +
            "      inquiry_remark, inquiry_type, inquiry_time, \n" +
            "      inquiry_dept, inquiry_user, inquiry_user_name, \n" +
            "      reply_dept, supplier_order_no, reply_no, \n" +
            "      reply_delivery_date, reply_user, reply_time, \n" +
            "      reply_delay_reason, reply_remark, inquiry_level, \n" +
            "      create_user, create_time, update_user, \n" +
            "      update_time,order_type, poNo,lineItem,rorder_no,rorder_item,splitItemNo,smcCode,purchaseType,inquiry_reason_param,sources_apply_no)\n" +
            "    values <foreach collection='list' item='item' index='index' separator=','>" +
            "      ( #{item.inquiryApplyNo,jdbcType=VARCHAR}, #{item.batchNo,jdbcType=VARCHAR}, \n" +
            "      #{item.dataSources,jdbcType=VARCHAR}, #{item.orderNo,jdbcType=VARCHAR}, #{item.modelNo,jdbcType=VARCHAR}, \n" +
            "      #{item.quantity,jdbcType=INTEGER}, #{item.customerNo,jdbcType=VARCHAR}, #{item.endUser,jdbcType=VARCHAR}, \n" +
            "      #{item.purchaseNo,jdbcType=VARCHAR}, #{item.orderStatus,jdbcType=INTEGER}, #{item.inquiryStatus,jdbcType=INTEGER}, \n" +
            "      #{item.hopeExportDate,jdbcType=DATE}, #{item.dlvModdate,jdbcType=DATE}, #{item.hopeDeliveryDate,jdbcType=TIMESTAMP}, \n" +
            "      #{item.inquiryReasonType,jdbcType=VARCHAR}, #{item.inquiryReason,jdbcType=VARCHAR}, #{item.inquiryDescription,jdbcType=VARCHAR}, \n" +
            "      #{item.inquiryRemark,jdbcType=VARCHAR}, #{item.inquiryType,jdbcType=CHAR}, #{item.inquiryTime,jdbcType=TIMESTAMP}, \n" +
            "      #{item.inquiryDept,jdbcType=VARCHAR}, #{item.inquiryUser,jdbcType=VARCHAR}, #{item.inquiryUserName,jdbcType=VARCHAR}, \n" +
            "      #{item.replyDept,jdbcType=VARCHAR}, #{item.supplierOrderNo,jdbcType=VARCHAR}, #{item.replyNo,jdbcType=VARCHAR}, \n" +
            "      #{item.replyDeliveryDate,jdbcType=TIMESTAMP}, #{item.replyUser,jdbcType=VARCHAR}, #{item.replyTime,jdbcType=TIMESTAMP}, \n" +
            "      #{item.replyDelayReason,jdbcType=VARCHAR}, #{item.replyRemark,jdbcType=VARCHAR}, #{item.inquiryLevel,jdbcType=VARCHAR}, \n" +
            "      #{item.createUser,jdbcType=VARCHAR}, #{item.createTime,jdbcType=TIMESTAMP}, #{item.updateUser,jdbcType=VARCHAR}, \n" +
            "      #{item.updateTime,jdbcType=TIMESTAMP},#{item.orderType,jdbcType=VARCHAR},#{item.pono}, #{item.lineitem}, #{item.rorderNo},#{item.rorderItem}, #{item.splititemno},#{item.smccode}, #{item.purchasetype}, #{item.inquiryReasonParam},#{item.sourcesApplyNo})  </foreach>  </script> ")
    public int inquiryApplyInsert(@Param("list") List<OpsInquiryApply> list);


    /**
     * 发送供应商后，更新待处理状态
     * @param status
     * @param list
     * @return
     */
    @Update(" <script> update ops_inquiry_apply set inquiry_status = #{status},update_user ='system',update_time = getdate() " +
            " where inquiry_status ='0' and  inquiry_apply_no in " +
            "<foreach collection=\"list\" item=\"item\"  open=\"(\" separator=\",\" close=\")\">\n" +
            " #{item} " +
            "</foreach>  </script> ")
    int updateResult(@Param("status") int status,@Param("list") List<String> list);


    @Update("<script> update ops_inquiry_apply set inquiry_status = #{inquiryStatus,jdbcType=INTEGER},\n" +
            "reply_dept = #{replyDept,jdbcType=VARCHAR},reply_supplier_dept =#{replySupplierDept,jdbcType=VARCHAR},reply_no = #{replyNo,jdbcType=VARCHAR},\n" +
            "supplier_order_no = #{supplierOrderNo,jdbcType=VARCHAR},reply_delivery_date = #{replyDeliveryDate,jdbcType=TIMESTAMP},reply_delivery_date_src = #{replyDeliveryDateSrc,jdbcType=VARCHAR},\n" +
            "reply_user = #{replyUser,jdbcType=CHAR},reply_delay_reason = #{replyDelayReason,jdbcType=VARCHAR},reply_time = #{replyTime,jdbcType=TIMESTAMP},\n" +
            "reply_remark = #{replyRemark,jdbcType=VARCHAR},inquiry_level = #{inquiryLevel,jdbcType=VARCHAR},update_user = #{updateUser,jdbcType=VARCHAR},update_time = #{updateTime,jdbcType=TIMESTAMP},reply_result_desc = #{replyResultDesc,jdbcType=VARCHAR} \n" +
            " where id = #{id,jdbcType=BIGINT} and inquiry_apply_no = #{inquiryApplyNo,jdbcType=VARCHAR} and order_no = #{orderNo,jdbcType=VARCHAR} and model_no = #{modelNo,jdbcType=VARCHAR} and quantity = #{quantity,jdbcType=INTEGER}  " +
            " </script>")
    int updateReplyMsg(OpsInquiryPoHandle opsInquiryPoHandle);

    @Update("<script> update ops_inquiry_apply set inquiry_status = #{inquiryStatus,jdbcType=INTEGER},\n" +
            "reply_dept = #{replyDept,jdbcType=VARCHAR},reply_supplier_dept =#{replySupplierDept,jdbcType=VARCHAR},reply_no = #{replyNo,jdbcType=VARCHAR},\n" +
            "supplier_order_no = #{supplierOrderNo,jdbcType=VARCHAR},reply_delivery_date = #{replyDeliveryDate,jdbcType=TIMESTAMP},reply_delivery_date_src = #{replyDeliveryDateSrc,jdbcType=VARCHAR},\n" +
            "reply_user = #{replyUser,jdbcType=CHAR},reply_delay_reason = #{replyDelayReason,jdbcType=VARCHAR},reply_time = #{replyTime,jdbcType=TIMESTAMP},\n" +
            "reply_remark = #{replyRemark,jdbcType=VARCHAR},inquiry_level = #{inquiryLevel,jdbcType=VARCHAR},update_user = #{updateUser,jdbcType=VARCHAR},update_time = #{updateTime,jdbcType=TIMESTAMP},reply_result_desc = #{replyResultDesc,jdbcType=VARCHAR} \n" +
            " where id = #{id,jdbcType=BIGINT} and inquiry_apply_no = #{inquiryApplyNo,jdbcType=VARCHAR}   " +
            " </script>")
    int updateReplyMsgByApply(OpsInquiryApply opsInquiryApply);
    @Select(" <script> select Max(inquiry_apply_no) from ops_inquiry_apply where inquiry_type = #{inquiryType} and SUBSTRING(inquiry_apply_no, 3,8) = #{localDate} </script>")
    public String getMaxApplyNo(@Param("localDate") String localDate, @Param("inquiryType") String inquiryType);

    @Select(" <script> select inquiry_apply_no from ops_inquiry_apply where inquiry_apply_no = #{applyNo} </script>")
    public String getApplyNoUnique(@Param("applyNo") String applyNo);

    // 根据apply_no查询，apple表相关信息  and order_no = #{orderNo,jdbcType=VARCHAR} and model_no = #{modelNo,jdbcType=VARCHAR} and quantity = #{quantity,jdbcType=INTEGER}
    @Select(" <script> select * from ops_inquiry_apply with(nolock) where inquiry_apply_no = #{inquiryApplyNo,jdbcType=VARCHAR} </script>")
    OpsInquiryApply getInquiryApplyByHandle(OpsInquiryPoHandle opsInquiryPoHandle);


    @Select(" <script> select * from ops_inquiry_apply where sources_apply_no = #{inquiryApplyNo} </script>")
    public List<InquiryOrderMasterDto> getInquiryApplyByParameter(InquiryApplyAddParam param);

    @Select(" <script> select * from ops_inquiry_apply where inquiry_apply_no = #{applyNo} </script>")
    public List<InquiryOrderMasterDto> getInquiryApplyByApplyno(@Param("applyNo") String applyNo);

}
