package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.dto.inquiry.OpsManuInquiry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author smc
 * 制造催促发送
 * 写入制造中间表，读写分别用两个数据库链接
 */
@DS("cmdmldb")
@Mapper
public interface InquiryManuMidInsertMapper {


    @Insert(" <script> insert into OPS_T_DeliveryConsulting\n" +
            "(QueryNo,Query_Type,OrderNumber,Model,Quantity,QueryDlvyDate,Query_Psnid,QueryDateTime,Query_Question_Id,QueryDetail,CustomerNo,Query_DeptNo,\n" +
            "  QuestionRemark,ReasonCode,StateCode)\n" +
            "    values <foreach collection='list' item='item' index='index' separator=','>" +
            "      (#{item.queryNo,jdbcType=VARCHAR},  #{item.queryType,jdbcType=VARCHAR},\n" +
            "    #{item.orderNumber,jdbcType=VARCHAR},#{item.model,jdbcType=VARCHAR}, #{item.quantity,jdbcType=INTEGER}, #{item.queryDlvyDate,jdbcType=DATE},\n" +
            "      #{item.queryPsnid,jdbcType=VARCHAR},#{item.queryDateTime,jdbcType=TIMESTAMP}, #{item.queryQuestionId}, #{item.queryDetail,jdbcType=VARCHAR}, \n" +
            "      #{item.customerNo,jdbcType=VARCHAR}, #{item.queryDeptNo,jdbcType=VARCHAR}, #{item.questionRemark,jdbcType=VARCHAR}, #{item.reasonCode,jdbcType=VARCHAR}, #{item.stateCode,jdbcType=VARCHAR}) \n" +
            "     </foreach>  </script> ")
    int insertManuInquiry(@Param("list") List<OpsManuInquiry> list); // 写入制造中间表中

}
