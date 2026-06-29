package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.dto.inquiry.InquiryUpTaskInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 *
 * @author smc
 * task表更新
 */
@DS("sharedb")
@Mapper
public interface InquirySalesNoticeTaskMapper {


    @Select(" <script> select * from ops_sales_notice_task where batch_no = #{batchNo} </script>")
    InquiryUpTaskInfoVO getSalesNoticeTask(@Param("batchNo") String batchNo);

    @Update(" <script> update ops_sales_notice_task set " +
            " <if test=\"callBackParameter != null  and  callBackParameter != ''\"> call_back_parameter = #{callBackParameter}, </if>\n" +
            " <if test=\"tryCount != 0 \"> try_count = #{tryCount}, </if>\n" +
            " <if test=\"handleStatus != null  and handleStatus != ''\"> handle_status = #{handleStatus}, </if>\n" +
            " <if test=\"returnStatus != null and  returnStatus != ''\">  return_status = #{returnStatus}, </if>\n" +
            " <if test=\"optUserNo != null and  optUserNo != ''\"> update_user = #{optUserNo}, </if>\n" +
            " <if test=\"returnResult != null  and returnResult != ''\"> return_result = #{returnResult}, </if>\n" +
            " <if test=\"errorMsg != null and  errorMsg != ''\"> error_msg = #{errorMsg},</if>\n" +
            " update_time = getdate() where id = #{id} and batch_no = #{batchNo} " +
            "  " +
            "</script> ")
    int updateSalesNoticeTask(InquiryUpTaskInfoVO inquiryUpTaskInfoVO);


}
