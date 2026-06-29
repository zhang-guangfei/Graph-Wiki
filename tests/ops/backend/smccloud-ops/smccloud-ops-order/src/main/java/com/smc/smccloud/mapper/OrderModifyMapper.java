package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.OrderModify.OrderModifyDO;
import com.smc.smccloud.model.ordermodify.OrderModifyRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Mapper
@DS("sharedb")
public interface OrderModifyMapper extends BaseMapper<OrderModifyDO> {


    @Select("<script>" +
            "SELECT \n" +
            "order_modify.[id] \n" +
            "FROM [ops_sharedb].[dbo].[order_modify] order_modify    \n" +
            "left join ops_core.dbo.rcvdetail rcvdetail on rcvdetail.rorder_fno =  " +
            " case \n" +
            " when LEN(order_modify.order_no) - LEN(REPLACE(order_modify.order_no, '-', '')) = 1 then order_modify.order_no \n" +
            " else left(order_modify.order_no, CHARINDEX('-', order_modify.order_no, CHARINDEX('-',order_modify.order_no) + 1)-1)\n" +
            " end \n" +
            "left join ops_core.dbo.rcvmaster rcvmaster on rcvmaster.rorder_no = rcvdetail.rorder_no    \n" +
            "left join ops_core.dbo.ops_customer customer on customer.customer_no = rcvmaster.end_user\n" +
            "left join ops_core.dbo.order_status order_status on order_modify.order_no =  \n" +
            "concat(order_status.order_id,'-', order_status.order_item,\n" +
            "CASE \n" +
            "WHEN order_status.split_type!='2' and order_status.split_no = 0 THEN null \n" +
            "WHEN order_status.split_type!='2' and order_status.split_no != 0 THEN concat('-', order_status.split_no)\n" +
            "WHEN order_status.split_type ='2' and order_status.split_no != 0 THEN concat('-', order_status.pco_item)\n" +
            "END\n" +
            ") \n" +
            "where order_modify.modify_type='A' "+
            " <if test = 'request.deptNoList != null and request.deptNoList.size() &gt; 0 ' >" +
            " and ( " +
            " customer.HRUnitId in " +
            "   <foreach collection = 'request.deptNoList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " ) " +
            "</if>"+
            "<if test = ' request.modelNo != null and request.modelNo != \"\" '>" +
            " and order_status.modelno  like #{request.modelNo} "+
            "</if>" +
            "<if test = ' request.modifyType != null and request.modifyType != \"\" '>" +
            " and order_modify.modify_type = #{request.modifyType}  "+
            "</if>" +
            "<if test = ' request.status != null and request.status != \"\" '>" +
            " and order_modify.status = #{request.status}  "+
            "</if>" +
            "<if test = ' request.changeType != null and request.changeType != \"\" '>" +
            " and order_modify.change_type = #{request.changeType}  "+
            "</if>" +
            "<if test = ' request.applyNo != null and request.applyNo != \"\" '>" +
            " and order_modify.apply_no = #{request.applyNo}  "+
            "</if>" +
            "<if test = ' request.remark != null and request.remark != \"\" '>" +
            " and order_modify.answer_text like #{request.remark}  "+
            "</if>" +
            // 大于500w
            "<if test = 'request.isGt500wFlag != null and request.isGt500wFlag != \"\" and request.isGt500wFlag == \"1\" ' >" +
            " and order_modify.special like '%\"isGt500w\":true%'  " +
            "</if> " +
            // 二次审批标识
            "<if test = 'request.sendProcessFlag != null and request.sendProcessFlag != \"\" and request.sendProcessFlag == \"1\" ' >" +
            " and order_modify.special like '%\"secondApproval\":true%'  " +
            "</if> " +
            "<if test = 'orderNoList != null and orderNoList.size() &gt; 0 ' >" +
            " and order_modify.order_no in " +
            "   <foreach collection = 'orderNoList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            "  " +
            "</if>"+
            "<if test = ' request.fromDateStr != null and request.toDateStr != null and request.dateType != null and request.dateType != \"\" and  request.dateType == \"1\"  '>" +
            " and order_modify.apply_time &gt;= #{request.fromDateStr} and order_modify.apply_time &lt;=  #{request.toDateStr} "+
            "</if>" +
            "<if test = ' request.fromDateStr != null and request.toDateStr != null and request.dateType != null and request.dateType != \"\" and request.dateType != \"1\"  '>" +
            " and order_modify.answer_time &gt;= #{request.fromDateStr} and order_modify.answer_time &lt;=  #{request.toDateStr} "+
            "</if>" +
            " order by order_modify.apply_time desc " +
            "</script>")
    List<Long> queryModifyIdsForZD(@Param("request") OrderModifyRequest request,
                                      @Param("orderNoList") List<String> orderNoList);


    @Select("<script>" +
            "SELECT \n" +
            "order_modify.[id] \n" +
            "FROM [ops_sharedb].[dbo].[order_modify] order_modify    \n" +
            "left join ops_core.dbo.rcvdetail rcvdetail on rcvdetail.rorder_fno = order_modify.order_no    \n" +
            "left join ops_core.dbo.rcvmaster rcvmaster on rcvmaster.rorder_no = rcvdetail.rorder_no    \n" +
            "left join ops_core.dbo.ops_customer customer on customer.customer_no = rcvmaster.end_user\n" +
            "where order_modify.modify_type!='A' "+
            " <if test = 'request.deptNoList != null and request.deptNoList.size() &gt; 0 ' >" +
            " and ( " +
            " customer.HRUnitId in " +
            "   <foreach collection = 'request.deptNoList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " ) " +
            "</if>"+
            "<if test = ' request.modelNo != null and request.modelNo != \"\" '>" +
            " and rcvdetail.model_no  like #{request.modelNo} "+
            "</if>" +
            "<if test = ' request.modifyType != null and request.modifyType != \"\" '>" +
            " and order_modify.modify_type = #{request.modifyType}  "+
            "</if>" +
            "<if test = ' request.status != null and request.status != \"\" '>" +
            " and order_modify.status = #{request.status} "+
            "</if>" +
            "<if test = ' request.changeType != null and request.changeType != \"\" '>" +
            " and order_modify.change_type = #{request.changeType}  "+
            "</if>" +
            "<if test = ' request.applyNo != null and request.applyNo != \"\" '>" +
            " and order_modify.apply_no = #{request.applyNo}  "+
            "</if>" +
            "<if test = ' request.remark != null and request.remark != \"\" '>" +
            " and order_modify.answer_text like #{request.remark}  "+
            "</if>" +
            // 大于500w
            "<if test = 'request.isGt500wFlag != null and request.isGt500wFlag != \"\" and request.isGt500wFlag == \"1\" ' >" +
            " and order_modify.special like '%\"isGt500w\":true%'  " +
            "</if> " +
            // 二次审批标识
            "<if test = 'request.sendProcessFlag != null and request.sendProcessFlag != \"\" and request.sendProcessFlag == \"1\" ' >" +
            " and order_modify.special like '%\"secondApproval\":true%'  " +
            "</if> " +
            "<if test = 'orderNoList != null and orderNoList.size() &gt; 0 ' >" +
            " and order_modify.order_no in " +
            "   <foreach collection = 'orderNoList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            "  " +
            "</if>"+
            "<if test = ' request.fromDateStr != null and request.toDateStr != null and request.dateType != null and request.dateType != \"\" and  request.dateType == \"1\"  '>" +
            " and order_modify.apply_time &gt;= #{request.fromDateStr} and order_modify.apply_time &lt;=  #{request.toDateStr} "+
            "</if>" +
            "<if test = ' request.fromDateStr != null and request.toDateStr != null and request.dateType != null and request.dateType != \"\" and request.dateType != \"1\"  '>" +
            " and order_modify.answer_time &gt;= #{request.fromDateStr} and order_modify.answer_time &lt;=  #{request.toDateStr} "+
            "</if>" +
            " order by order_modify.apply_time desc " +
            "</script>")
    List<Long> queryModifyIdsForNotZD(@Param("request") OrderModifyRequest request,
                                            @Param("orderNoList") List<String> orderNoList);

}
