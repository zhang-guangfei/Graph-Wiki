package com.sales.ops.db.extdao;

import com.sales.ops.dto.purchase.UnusualViewDto;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface OpsUnusualDao {

    @Select(
            "<script>" +
                    "select  " +
                    "u.id, " +
                    "i.deptNo, " +
                    "u.order_fno as orderFno, " +
                    "u.order_no as orderNo, " +
                    "u.item_no as itemNo, " +
                    "u.split_item_no as splitItemNo, " +
                    "u.modelno, " +
                    "u.quantity, " +
                    "u.reply_date, " +
                    "u.reply_content, " +
                    "u.inquiry_date, " +
                    "i.customerNo, " +
                    "i.userNo, " +
                    "i.end_user, " +
                    "i.purchaseDate, " +
                    "i.replyOrderNo, " +
                    "i.src_delivery_time as dlvModdate, " +
                    "c.unusual_classify as unusualClassify, " +
                    "c.unusual_type as unusualType, " +
                    "u.unusual_desc_eng as unusualDescEng, " +
                    "c.unusual_desc_chn as unusualDescChn, " +
                    "c.job_dept_name as jobDeptName, " +
                    "u.record_date as recordDate, " +
                    "u.record_count as recordCount, " +
                    "u.handle_status as handleStatus, " +
                    "u.reply_time as replyJapDate, " +
                    "u.handle_user as handleUser, " +
                    "u.handle_user_name as handleUserName, " +
                    "u.complete_time as completeDate " +
                    "from ops_po_delivery_unusual u " +
                    "left join ops_po_delivery_unusual_conf c on u.unusual_identification_code=c.unusual_identification_code and c.del_flag=0" +
                    "left join ops_purchaseInvoice i on u.order_no=i.orderNo and u.item_no=i.itemNo and ISNULL(u.split_item_no, 0)=  ISNULL(i.splitItemNo, 0)" +
                    "<where>" +
                    "    u.del_flag=0 and u.supplier_code='JP0' " +
                    "    <if test=\"orderFnoList != null and orderFnoList.size() > 0\"> " +
                    "      and u.order_fno in " +
                    "        <foreach collection=\"orderFnoList\" item=\"item\" separator=\",\" open=\"(\" close=\")\"> " +
                    "            #{item} " +
                    "        </foreach>" +
                    "    </if> " +
                    "    <if test=\"orderFno != null and orderFno != ''\"> " +
                    "      and  u.order_fno like #{orderFno} " +
                    "    </if> " +
                    "    <if test=\"deptNo != null and deptNo .size() > 0\"> " +
                    "       and i.deptNo in " +
                    "        <foreach collection=\"deptNo\" item=\"item\" separator=\",\" open=\"(\" close=\")\"> " +
                    "            #{item} " +
                    "        </foreach>" +
                    "    </if> " +
                    "    <if test=\"modelno != null and modelno != ''\"> " +
                    "      and  u.modelno = #{modelno} " +
                    "    </if> " +
                    "    <if test=\"unusualType != null and unusualType != ''\"> " +
                    "      and  u.unusual_type = #{unusualType} " +
                    "    </if> " +
                    "    <if test=\"customerNo != null and customerNo != ''\"> " +
                    "      and  i.customerNo = #{customerNo} " +
                    "    </if> " +
                    "    <if test=\"userNo != null and userNo != ''\"> " +
                    "      and  i.userNo = #{userNo} " +
                    "    </if> " +
                    "    <if test=\" startInquiryDate != null and endInquiryDate != null\"> " +
                    "      and u.inquiry_date between #{startInquiryDate} and #{endInquiryDate} " +
                    "    </if> " +
                    "    <if test=\"jobDeptName != null and jobDeptName != ''\"> " +
                    "      and  c.job_dept_name = #{jobDeptName} " +
                    "    </if> " +
                    "    <if test=\" handleStatus != null\"> " +
                    "      and  u.handle_status = #{handleStatus} " +
                    "    </if> " +
                    "    <if test=\" recordCount != null\"> " +
                    "      and  u.record_count = #{recordCount} " +
                    "    </if> " +
                    "    <if test=\"unusualDescChn != null and unusualDescChn != ''\"> " +
                    "      and  c.unusual_desc_chn like #{unusualDescChn} " +
                    "    </if> " +
                    "</where>" +
                    "</script>")
    List<UnusualViewDto> selectUnusualViewDto(String orderFno, List<String> orderFnoList,
                                              List<String> deptNo, String modelno,
                                              String customerNo, String userNo,
                                              Date startInquiryDate, Date endInquiryDate,
                                              String jobDeptName, Integer handleStatus,
                                              Integer recordCount,String unusualDescChn,
                                              String unusualType
    );


}
