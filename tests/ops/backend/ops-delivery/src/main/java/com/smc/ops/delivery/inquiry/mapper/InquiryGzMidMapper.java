package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.sales.ops.dto.inquiry.OpsGzInquiryReply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 广州催促发送
 * @author dxf
 *
 */
@DS("sharedb")
@Mapper
public interface InquiryGzMidMapper {


    @Insert(" <script> insert into ops_gz_inquiry\n" +
            "([task_no],[order_no],[model_no],[quantity],[customer_no],[end_user]\n" +
            "  ,[purchase_no],[hope_export_date] ,[dlv_moddate],[hope_delivery_date]\n" +
            "  ,[inquiry_reason_type],[inquiry_reason],[inquiry_description],[inquiry_remark]\n" +
            "  ,[inquiry_type],[inquiry_time],[inquiry_dept],[inquiry_user],[inquiry_user_name]\n" +
            "  ,[create_user],[create_time]) " +
            "    values <foreach collection='list' item='item' index='index' separator=','>" +
            "      (#{item.taskNo,jdbcType=VARCHAR},  #{item.orderNo,jdbcType=VARCHAR}\n" +
            "    , #{item.modelNo,jdbcType=VARCHAR},  #{item.quantity,jdbcType=INTEGER}, #{item.customerNo,jdbcType=VARCHAR}, #{item.endUser,jdbcType=VARCHAR},\n" +
            "      #{item.purchaseNo,jdbcType=VARCHAR},#{item.hopeExportDate,jdbcType=DATE}, #{item.dlvModdate,jdbcType=DATE}, #{item.hopeDeliveryDate,jdbcType=TIMESTAMP}, \n" +
            "      #{item.inquiryReasonType,jdbcType=VARCHAR}, #{item.inquiryReason,jdbcType=VARCHAR}, #{item.inquiryDescription,jdbcType=VARCHAR}, \n" +
            "      #{item.inquiryRemark,jdbcType=VARCHAR}, #{item.inquiryType,jdbcType=CHAR}, #{item.inquiryTime,jdbcType=TIMESTAMP}, \n" +
            "      #{item.inquiryDept,jdbcType=VARCHAR}, #{item.inquiryUser,jdbcType=VARCHAR}, #{item.inquiryUserName,jdbcType=VARCHAR}, \n" +
            "      #{item.createUser,jdbcType=VARCHAR}, #{item.createTime,jdbcType=TIMESTAMP}) \n" +
            "     </foreach>  </script> ")
    int insertGzInquiry(@Param("list") List<OpsInquiryPoHandle> list); // 取最近一次催促的时间，计算间隔




    @Select("<script> select * from ops_gz_inquiry_reply where  task_no = #{taskNo,jdbcType=VARCHAR} and order_no = #{orderNo,jdbcType=VARCHAR} and model_no = #{modelNo,jdbcType=VARCHAR} and quantity = #{quantity,jdbcType=INTEGER} and status = #{status,jdbcType=VARCHAR} order by reply_time </script>")
    List<OpsGzInquiryReply> getInquiryReply(OpsGzInquiryReply opsGzInquiryReply);


    @Select("<script> select * from ops_gz_inquiry_reply " +
            "where  create_time  &gt;= #{lastTime}  and   status = #{status}  order by create_time desc </script>")
    List<OpsGzInquiryReply> getInquiryIncrementReply(@Param("status") String status,@Param("lastTime") Date lastTime);
}
