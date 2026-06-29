package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsInquiryDetail;
import com.sales.ops.dto.inquiry.InquiryOrderDetailReturnDto;
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
public interface InquiryDetailMapper {



    @Select(" <script> select * from ops_inquiry_detail where inquiry_apply_no = #{applyno} </script> ")
    List<OpsInquiryDetail> selectInquiryDetail(@Param("applyno") String applyno);


    @Select(" <script> select * from ops_inquiry_detail with(nolock) where task_no = #{taskNo,jdbcType=VARCHAR} and order_no = #{orderNo,jdbcType=VARCHAR} and inquiry_apply_no = #{inquiryApplyNo,jdbcType=VARCHAR}  </script> ")
    List<OpsInquiryDetail> selectInquiryDetailByTask(OpsInquiryDetail opsInquiryDetail);
    @Update("<script> update ops_inquiry_detail set \n" +
            "reply_dept = #{replyDept,jdbcType=VARCHAR},reply_supplier_dept =#{replySupplierDept,jdbcType=VARCHAR},reply_no = #{replyNo,jdbcType=VARCHAR},\n" +
            "supplier_order_no = #{supplierOrderNo,jdbcType=VARCHAR},reply_delivery_date = #{replyDeliveryDate,jdbcType=TIMESTAMP},reply_delivery_date_src = #{replyDeliveryDateSrc,jdbcType=VARCHAR},\n" +
            "reply_user = #{replyUser,jdbcType=CHAR},reply_delay_reason = #{replyDelayReason,jdbcType=VARCHAR},reply_time = #{replyTime,jdbcType=TIMESTAMP},\n" +
            "reply_remark = #{replyRemark,jdbcType=VARCHAR},inquiry_level = #{inquiryLevel,jdbcType=VARCHAR},update_user = #{updateUser,jdbcType=VARCHAR},update_time = #{updateTime,jdbcType=TIMESTAMP},reply_result_desc = #{replyResultDesc,jdbcType=VARCHAR} \n" +
            " where id = #{id,jdbcType=BIGINT} and inquiry_apply_no = #{inquiryApplyNo,jdbcType=VARCHAR} and task_no = #{taskNo,jdbcType=VARCHAR} and order_no = #{orderNo,jdbcType=VARCHAR} " +
            " </script>")
    int updateTaknoTodetail(OpsInquiryDetail opsInquiryDetail); // 更新回复信息到子表中
    /**
     *
     * @param list
     * @return
     */
    @Insert("<script> insert into dbo.ops_inquiry_detail ( inquiry_apply_no, item_no,task_no, fpType, order_no, \n" +
            "      version, reply_dept, supplier_order_no, \n" +
            "      reply_no, reply_delivery_date, reply_user, \n" +
            "      reply_time, reply_delay_reason, reply_remark, \n" +
            "      inquiry_level, create_user, create_time, \n" +
            "      update_user, update_time)" +
            " values <foreach collection='list' item='item' index='index' separator=','>" +
            "(#{item.inquiryApplyNo,jdbcType=VARCHAR}, #{item.itemNo,jdbcType=INTEGER}, \n" +
            "      #{item.taskNo,jdbcType=VARCHAR}, #{item.fptype,jdbcType=VARCHAR}, #{item.orderNo,jdbcType=VARCHAR}, \n" +
            "      #{item.version,jdbcType=INTEGER}, #{item.replyDept,jdbcType=VARCHAR}, #{item.supplierOrderNo,jdbcType=VARCHAR}, \n" +
            "      #{item.replyNo,jdbcType=VARCHAR}, #{item.replyDeliveryDate,jdbcType=TIMESTAMP}, #{item.replyUser,jdbcType=VARCHAR}, \n" +
            "      #{item.replyTime,jdbcType=TIMESTAMP}, #{item.replyDelayReason,jdbcType=VARCHAR}, #{item.replyRemark,jdbcType=VARCHAR}, \n" +
            "      #{item.inquiryLevel,jdbcType=TIMESTAMP}, #{item.createUser,jdbcType=VARCHAR}, #{item.createTime,jdbcType=TIMESTAMP}, \n" +
            "      #{item.updateUser,jdbcType=VARCHAR}, #{item.updateTime,jdbcType=TIMESTAMP})" +
            "  </foreach>  </script>")
    public int inquiryDetailInsertBatch(@Param("list") List<OpsInquiryDetail> list);

    /**
     * 根据回复信息，写入催促申请子表
     * @return
     */
    @Insert("<script> insert into dbo.ops_inquiry_detail ( inquiry_apply_no, item_no,task_no, fpType, order_no," +
            "   model_no,quantity,order_status,supplier_id,hope_export_date,dlv_moddate,hope_delivery_date, " +
            "      version, reply_dept, supplier_order_no, " +
            "      reply_no, reply_delivery_date, reply_user, " +
            "      reply_time, reply_delay_reason, reply_remark, " +
            "      inquiry_level, create_user, create_time, poNo,lineItem,rorder_no,rorder_item,splitItemNo,smcCode,purchaseType,rorder_splitno) " +
            " values (#{inquiryApplyNo,jdbcType=VARCHAR}, #{itemNo,jdbcType=INTEGER}, " +
            " #{taskNo,jdbcType=VARCHAR}, #{fptype,jdbcType=VARCHAR}, #{orderNo,jdbcType=VARCHAR}, " +
            "  #{modelNo,jdbcType=VARCHAR}, #{quantity,jdbcType=INTEGER}, #{orderStatus,jdbcType=INTEGER},#{supplierId,jdbcType=VARCHAR},  #{hopeExportDate,jdbcType=DATE}, #{dlvModdate,jdbcType=DATE}, #{hopeDeliveryDate,jdbcType=TIMESTAMP}, " +
            " #{version,jdbcType=INTEGER}, #{replyDept,jdbcType=VARCHAR}, #{supplierOrderNo,jdbcType=VARCHAR}, " +
            " #{replyNo,jdbcType=VARCHAR}, #{replyDeliveryDate,jdbcType=TIMESTAMP}, #{replyUser,jdbcType=VARCHAR}, " +
            " #{replyTime,jdbcType=TIMESTAMP}, #{replyDelayReason,jdbcType=VARCHAR}, #{replyRemark,jdbcType=VARCHAR}, " +
            " #{inquiryLevel,jdbcType=TIMESTAMP}, #{createUser,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP},#{pono}, #{lineitem}, #{rorderNo},#{rorderItem}, #{splititemno},#{smccode}, #{purchasetype},#{rorderSplitno}) " +
            " </script>")
    public int inquiryDetailInsert(OpsInquiryDetail opsInquiryDetail);

    @Select(" <script> select * from ops_inquiry_detail where inquiry_apply_no = #{applyno} </script> ")
    List<InquiryOrderDetailReturnDto> selectInquiryDetailReturn(@Param("applyno") String applyno);



}
