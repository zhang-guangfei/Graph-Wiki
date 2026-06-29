package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.dto.inquiry.OpsAs400Inquiry;
import com.sales.ops.dto.inquiry.OpsAs400InquiryReply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * as400催促发送
 * @author dxf
 *
 */
@DS("jpdb")
@Mapper
public interface InquiryAs400MidMapper {


    @Insert(" <script> insert into InqueryABJApplyToJP\n" +
            "(subsidiary_code,subsidiary_no,subsidiary_item,Type,part_number,qty,smcjp_no,smcjp_item \n" +
            "  ,due_date,inquiry_message_code,inquiry_message,inquiry_message_contents,subsidiary_apply_no,subsidiary_apply_item,create_time,hotline_level)\n" +
            "    values <foreach collection='list' item='item' index='index' separator=','>" +
            "      (#{item.subsidiaryCode,jdbcType=VARCHAR},#{item.subsidiaryNo,jdbcType=VARCHAR}, #{item.subsidiaryItem}\n" +
            "    , #{item.type}, #{item.partNumber,jdbcType=VARCHAR}, #{item.qty,jdbcType=INTEGER},  #{item.smcjpNo,jdbcType=VARCHAR},\n" +
            "       #{item.smcjpItem},#{item.dueDate,jdbcType=DATE},#{item.inquiryMessageCode}, #{item.inquiryMessage,jdbcType=VARCHAR}, #{item.inquiryMessageContents,jdbcType=VARCHAR},#{item.subsidiaryAppLyNo},#{item.subsidiaryAppLyItem},#{item.createTime},#{item.hotlineLevel}) \n" +
            "     </foreach>  </script> ")
    int insertAs400Inquiry(@Param("list") List<OpsAs400Inquiry> list); // 取最近一次催促的时间，计算间隔




    // 2024-10-10 AS400取数时，取消了returnA_date的取数限制，支持多次回复
    // and returnA_date is not null
    @Select("<script> select TOP 1 id,subsidiary_code,subsidiary_no\n" +
            ",subsidiary_item,Type,part_number,qty,smcjp_no,smcjp_item,due_date,due_days\n" +
            ",inquiry_message_code,inquiry_message,inquiry_message_contents,inquiry_send_date,inquiry_send_time,return_reference_no\n" +
            ",returnA_date,returnB_days,returnB_code,return_message_code,return_message,return_message_contents\n" +
            ",CAST(return_date AS datetime) + CAST(return_return AS datetime) AS return_date,hotline_level,subsidiary_apply_no,subsidiary_apply_item  " +
            " from InqueryABJResponseFromJP  with(nolock) where subsidiary_code = #{subsidiaryCode,jdbcType=VARCHAR}  and type = #{type,jdbcType=VARCHAR} " +
            " and subsidiary_apply_no = #{subsidiaryAppLyNo,jdbcType=VARCHAR} and return_reference_no is not null and return_date >= DATEADD(month, -6, GETDATE()) order by return_date desc,return_return desc  </script>")
    List<OpsAs400InquiryReply> getInquiryReply(OpsAs400InquiryReply as400InquiryReply);

    @Select("<script> select id,subsidiary_code,subsidiary_no\n" +
            ",subsidiary_item,Type,part_number,qty,smcjp_no,smcjp_item,due_date,due_days\n" +
            ",inquiry_message_code,inquiry_message,inquiry_message_contents,inquiry_send_date,inquiry_send_time,return_reference_no\n" +
            ",returnA_date,returnB_days,returnB_code,return_message_code,return_message,return_message_contents\n" +
            ",CAST(return_date AS datetime) + CAST(return_return AS datetime) AS return_date,hotline_level,subsidiary_apply_no,subsidiary_apply_item  " +
            " from InqueryABJResponseFromJP  where type = #{type,jdbcType=VARCHAR} " +
            " and CAST(return_date AS datetime) + CAST(return_return AS datetime) &gt;= #{lasttime}  and return_reference_no is not null order by return_date desc,return_return desc  </script>")
    List<OpsAs400InquiryReply> getInquiryReplyIncrement(@Param("type") String type,@Param("lasttime") Date lasttime); // 根据同一批次号去重，取出最新更新的部分

}
