package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsInquiryAdapterConfig;
import com.sales.ops.db.entity.OpsInquiryPoHandle;
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
public interface InquiryPoHandleMapper {


    @Select(" <script> select * from ops_inquiry_po_handle where inquiry_status = #{status} and supplier_id is not null   </script> ")
    List<OpsInquiryPoHandle> selectUnHandleList(@Param("status") int status);


    @Select(" <script>  select  a.supplier_class,a.callback_increment_class_name,a.callback_increment_method_name ,Min(callback_increment_lastid) callback_increment_lastid,Min(callback_increment_lasttime) callback_increment_lasttime,MAX(warning_mail_to) warning_mail_to,MAX(warning_mail_cc) warning_mail_cc \n" +
            " from ops_inquiry_adapter_config a  group by a.supplier_class,a.callback_increment_class_name,a.callback_increment_method_name   </script> ")
    List<OpsInquiryAdapterConfig> selectIncrementList();

    @Update(" <script> update a set callback_increment_lasttime = #{callbackIncrementLasttime},callback_increment_lastid = #{callbackIncrementLastid},update_time = GETDATE(),update_user = #{updateUser} \n" +
            " from ops_inquiry_adapter_config a inner join supplier b on a.supplier_id = b.id \n " +
            " where b.supplier_class = #{supplierClass} </script> ")
    //  AND A.callback_increment_lasttime &lt; #{callbackIncrementLasttime}
    int updateIncrementMaxTime(OpsInquiryAdapterConfig opsInquiryAdapterConfig);

    @Select(" <script> select * from ops_inquiry_adapter_config where supplier_id = #{supplierId} and is_deleted ='0'   </script> ")
    List<OpsInquiryAdapterConfig> selectAdapterBySupplier(@Param("supplierId") String supplierId);

    @Select(" <script> select * from ops_inquiry_po_handle with(nolock) where task_no = #{taskNo,jdbcType=VARCHAR} and order_no = #{orderNo,jdbcType=VARCHAR} and model_no = #{modelNo,jdbcType=VARCHAR} and quantity = #{quantity,jdbcType=INTEGER}   </script> ")
    List<OpsInquiryPoHandle> selectPoHandleList(OpsInquiryPoHandle opsInquiryPoHandle);

    @Select(" <script> select * from ops_inquiry_po_handle with(nolock) where task_no = #{taskNo}  </script> ")
    List<OpsInquiryPoHandle> selectPoHandleListByTaskNo(@Param("taskNo") String taskNo);

    @Update(" <script> update ops_inquiry_po_handle set inquiry_status = #{status},inquiry_handle_result =#{resultMsg},update_user ='system',update_time = getdate() " +
            " where  id in " +
            "<foreach collection=\"list\" item=\"item\"  open=\"(\" separator=\",\" close=\")\">\n" +
            " #{item} " +
            "</foreach>  </script> ")
    int updateHandleResult(@Param("status") int status,@Param("resultMsg") String resultMsg,@Param("list") List<Long> list);


    @Update(" <script> update ops_inquiry_po_handle set task_no = #{taskno} " +
            " where  id in " +
            "<foreach collection=\"list\" item=\"item\"  open=\"(\" separator=\",\" close=\")\">\n" +
            " #{item} " +
            "</foreach>  </script> ")
    int updateHandleTaskno(@Param("taskno") String taskno,@Param("list") List<Long> list);
    @Insert("<script> insert into dbo.ops_inquiry_po_handle ( task_no, inquiry_apply_no, \n" +
            "       order_no, model_no, \n" +
            "      quantity, customer_no, end_user, \n" +
            "      purchase_no, order_status, supplier_id, \n" +
            "      inquiry_status, hope_export_date, dlv_moddate, \n" +
            "      hope_delivery_date, inquiry_reason_type, \n" +
            "      inquiry_reason, inquiry_description, inquiry_remark, \n" +
            "      inquiry_type, inquiry_time, inquiry_dept, \n" +
            "      inquiry_user, inquiry_user_name, reply_dept, \n" +
            "      supplier_order_no, reply_no, reply_delivery_date, \n" +
            "      reply_user, reply_time, reply_delay_reason, \n" +
            "      reply_remark, inquiry_level, create_user, \n" +
            "      create_time, update_user, update_time, poNo,lineItem,rorder_no,rorder_item,splitItemNo,smcCode,purchaseType,inquiry_reason_param,item_no\n" +
            "      )" +
            " values <foreach collection='list' item='item' index='index' separator=','>" +
            "( #{item.taskNo,jdbcType=VARCHAR}, #{item.inquiryApplyNo,jdbcType=VARCHAR}, \n" +
            "      #{item.orderNo,jdbcType=VARCHAR}, #{item.modelNo,jdbcType=VARCHAR}, \n" +
            "      #{item.quantity,jdbcType=INTEGER}, #{item.customerNo,jdbcType=VARCHAR}, #{item.endUser,jdbcType=VARCHAR}, \n" +
            "      #{item.purchaseNo,jdbcType=VARCHAR}, #{item.orderStatus,jdbcType=INTEGER}, #{item.supplierId,jdbcType=VARCHAR}, \n" +
            "      #{item.inquiryStatus,jdbcType=INTEGER}, #{item.hopeExportDate,jdbcType=DATE}, #{item.dlvModdate,jdbcType=DATE}, \n" +
            "      #{item.hopeDeliveryDate,jdbcType=TIMESTAMP}, #{item.inquiryReasonType,jdbcType=VARCHAR}, \n" +
            "      #{item.inquiryReason,jdbcType=VARCHAR}, #{item.inquiryDescription,jdbcType=VARCHAR}, #{item.inquiryRemark,jdbcType=VARCHAR}, \n" +
            "      #{item.inquiryType,jdbcType=CHAR}, #{item.inquiryTime,jdbcType=TIMESTAMP}, #{item.inquiryDept,jdbcType=VARCHAR}, \n" +
            "      #{item.inquiryUser,jdbcType=VARCHAR}, #{item.inquiryUserName,jdbcType=VARCHAR}, #{item.replyDept,jdbcType=VARCHAR}, \n" +
            "      #{item.supplierOrderNo,jdbcType=VARCHAR}, #{item.replyNo,jdbcType=VARCHAR}, #{item.replyDeliveryDate,jdbcType=TIMESTAMP}, \n" +
            "      #{item.replyUser,jdbcType=CHAR}, #{item.replyTime,jdbcType=TIMESTAMP}, #{item.replyDelayReason,jdbcType=VARCHAR}, \n" +
            "      #{item.replyRemark,jdbcType=VARCHAR}, #{item.inquiryLevel,jdbcType=VARCHAR}, #{item.createUser,jdbcType=VARCHAR}, \n" +
            "      #{item.createTime,jdbcType=TIMESTAMP}, #{item.updateUser,jdbcType=VARCHAR}, #{item.updateTime,jdbcType=TIMESTAMP},\n" +
            "      #{item.pono}, #{item.lineitem}, #{item.rorderNo},#{item.rorderItem}, #{item.splititemno},#{item.smccode}, #{item.purchasetype}, #{item.inquiryReasonParam},#{item.itemNo})" +
            "  </foreach>  </script>")
    public int inquiryPoHandleInsert(@Param("list") List<OpsInquiryPoHandle> list);

    /**
     *  回复数据的更新
     * @param opsInquiryPoHandle
     * @return  and model_no = #{modelNo,jdbcType=VARCHAR}
     */
    @Update("<script> update ops_inquiry_po_handle set inquiry_status = #{inquiryStatus,jdbcType=INTEGER},\n" +
            "reply_dept = #{replyDept,jdbcType=VARCHAR},reply_supplier_dept =#{replySupplierDept,jdbcType=VARCHAR},reply_no = #{replyNo,jdbcType=VARCHAR},\n" +
            "supplier_order_no = #{supplierOrderNo,jdbcType=VARCHAR},reply_delivery_date = #{replyDeliveryDate,jdbcType=TIMESTAMP},reply_delivery_date_src = #{replyDeliveryDateSrc,jdbcType=VARCHAR},\n" +
            "reply_user = #{replyUser,jdbcType=CHAR},reply_delay_reason = #{replyDelayReason,jdbcType=VARCHAR},reply_time = #{replyTime,jdbcType=TIMESTAMP},\n" +
            "reply_remark = #{replyRemark,jdbcType=VARCHAR},inquiry_level = #{inquiryLevel,jdbcType=VARCHAR},update_user = #{updateUser,jdbcType=VARCHAR},update_time = #{updateTime,jdbcType=TIMESTAMP},reply_result_desc = #{replyResultDesc,jdbcType=VARCHAR} \n" +
            " where id = #{id,jdbcType=BIGINT} and inquiry_apply_no = #{inquiryApplyNo,jdbcType=VARCHAR} and task_no = #{taskNo,jdbcType=VARCHAR} and order_no = #{orderNo,jdbcType=VARCHAR} and quantity = #{quantity,jdbcType=INTEGER}  " +
            " </script>")
    int updateReplyMsg(OpsInquiryPoHandle opsInquiryPoHandle);

    @Select(" <script> select Max(task_no) from ops_inquiry_po_handle where inquiry_type = #{inquiryType} and SUBSTRING(task_no, 3,8) = #{localDate} </script>")
    public String getMaxTaskNo(@Param("localDate") String localDate, @Param("inquiryType") String inquiryType); // 获取最大taskno

    @Select(" <script> select * from ops_inquiry_po_handle with(nolock) where inquiry_apply_no = #{applyno} and inquiry_status = #{status} </script> ")
    List<OpsInquiryPoHandle> getPoHandleByApplyno(@Param("applyno") String applyno,@Param("status") int status);

}
