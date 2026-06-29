package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.dto.inquiry.OpsManuInquiry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author smc
 * 制造催促发送
 */
@DS("cmdb")
@Mapper
public interface InquiryManuMidMapper {


//    @Insert(" <script> insert into OPS_T_DeliveryConsulting\n" +
//            "(QueryNo,Query_Type,OrderNumber,Model,Quantity,QueryDlvyDate,Query_Psnid,QueryDateTime,Query_Question_Id,QueryDetail,CustomerNo,Query_DeptNo,\n" +
//            "  QuestionRemark,ReasonCode,StateCode)\n" +
//            "    values <foreach collection='list' item='item' index='index' separator=','>" +
//            "      (#{item.queryNo,jdbcType=VARCHAR},  #{item.queryType,jdbcType=VARCHAR},\n" +
//            "    #{item.orderNumber,jdbcType=VARCHAR},#{item.model,jdbcType=VARCHAR}, #{item.quantity,jdbcType=INTEGER}, #{item.queryDlvyDate,jdbcType=DATE},\n" +
//            "      #{item.queryPsnid,jdbcType=VARCHAR},#{item.queryDateTime,jdbcType=TIMESTAMP}, #{item.queryQuestionId,jdbcType=VARCHAR}, #{item.queryDetail,jdbcType=VARCHAR}, \n" +
//            "      #{item.customerNo,jdbcType=VARCHAR}, #{item.queryDeptNo,jdbcType=VARCHAR}, #{item.questionRemark,jdbcType=VARCHAR}, #{item.reasonCode,jdbcType=VARCHAR}, #{item.stateCode,jdbcType=VARCHAR}) \n" +
//            "     </foreach>  </script> ")
//    int insertManuInquiry(@Param("list") List<OpsManuInquiry> list); // 取最近一次催促的时间，计算间隔


    @Select(" <script> select top 1 [备注] from OPS_V_RequisitionStausToSales where [订单号]=#{pono} and [订单项号]=#{lineitem} order by 更新日期  </script> ")
    String getCheckRemark(@Param("pono") String pono, @Param("lineitem") String lineitem);

    @Select(" <script> select AnswerDateTime from OPS_T_DeliveryConsulting where OrderNumber = #{orderNo}  </script> ")
    List<Date> getCountByNo(@Param("orderNo") String orderNo);


    @Select("<script> select a.QueryNo,\n" +
            " a.Query_Type queryType, a.OrderNumber, a.Model, a.Quantity,a.StateCode,a.QueryDlvyDate queryDlvyDate,\n" +
            " a.Query_Psnid queryPsnid,a.QueryDateTime,a.QueryDetail,a.CustomerNo, a.Query_DeptNo queryDeptNo,a.QuestionRemark,\n" +
            " a.AnswerDlvyDate,a.Answer_Operator answerOperator,a.AnswerDateTime answerDate,a.Answer_remarks answerRemark,a.AnswerDept,a.Answer_Reason_ID delayReason,RequestNo from  OPS_T_DeliveryConsulting a " +
            " where  QueryNo = #{queryNo} and Query_Type = #{queryType} and OrderNumber = #{orderNumber}  and Quantity = #{quantity}  and AnswerDateTime is not null </script>")
    List<OpsManuInquiry> getInquiryReply(OpsManuInquiry opsManuInquiry);



    @Select("<script> select a.QueryNo,\n" +
            " a.Query_Type queryType, a.OrderNumber, a.Model, a.Quantity,a.StateCode,a.QueryDlvyDate queryDlvyDate,\n" +
            " a.Query_Psnid queryPsnid,a.QueryDateTime,a.QueryDetail,a.CustomerNo, a.Query_DeptNo queryDeptNo,a.QuestionRemark,\n" +
            " a.AnswerDlvyDate,a.Answer_Operator answerOperator,a.AnswerDateTime answerDate,a.Answer_remarks answerRemark,a.AnswerDept,a.Answer_Reason_ID delayReason,RequestNo from  OPS_T_DeliveryConsulting a " +
            " where  Query_Type = #{type} and AnswerDateTime is not null and AnswerDateTime &gt;= #{lasttime} </script>")
    List<OpsManuInquiry> getInquiryReplyIncrement(@Param("type") String type,@Param("lasttime") Date lasttime);


}
