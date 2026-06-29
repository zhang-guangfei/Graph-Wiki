package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.adapter.order.*;
import com.smc.smccloud.model.orderstate.DelOrderVO;
import com.smc.smccloud.model.orderstate.OrderStateDO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/8/27 10:19
 * @Descripton TODO
 */
@DS("opsreaddb")
@Mapper
public interface OrderStateMapperReadOnlyMapper extends BaseMapper<OrderStateDO> {


  /**
   * 交货期查询
   */
  @Select("<script> "+
          "select * from ( " +
          " SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo " +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
          "<if test = 'condition.orderType == null or condition.orderType == \"\" or condition.orderType == \"20\" ' >" +
          " o.order_type != '20' and " +
          "</if>"+
          "<if test = ' (condition.orderNo == null or condition.orderNo == \"\") or condition.orderNo.startsWith(\"9990\") or condition.orderNo.startsWith(\"9930\") or condition.orderNo.startsWith(\"99GZ\") or condition.orderNo.startsWith(\"990\")  ' >" +
          " ( o.order_no not like '9990%' or o.order_no not like '9930%' or o.order_no not like '99GZ%' or o.order_no not like '990%' ) and " +
          "</if>"+
          " o.end_user != 'C1D7V' and " +
          " (o.customer_no is not null or o.customer_no != '' ) and ( o.end_user is not null or o.end_user != '' ) and " +
          // 权限
          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
          "  or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 ' > " +
          " ( " +
          "</if>" +
          // 客户担当权限
          "   <if test = 'finalEmployeeAuth.size() &gt; 0  '> " +
          "       c.PSNSMCID in " +
          "       <foreach collection = 'finalEmployeeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "   <if test = 'finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  or finalDeptNos.size &gt; 0  ' > " +
          "      or " +
          "    </if>" +
          "   </if>" +
          // 部门权限
          " <if test = 'finalDeptNos.size() &gt; 0 ' > " +
          "  c.HLCode in " +
          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item}" +
          "  </foreach>" +
          "  or " +
          "  c.HRUnitId in " +
          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item} " +
          "  </foreach>" +
          "   <if test = 'finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
          "    or " +
          "   </if>" +
          " </if> " +
          // 客户权限
          "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
          "       o.end_user in " +
          "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "    <if test = 'finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
          "      or " +
          "    </if>" +
          "   </if>" +
          // 行业权限
          "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
          " o.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
          " #{item}" +
          "</foreach>"+
          " ) " +
          " or "+
          " o.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
          " #{item}" +
          "</foreach>"+
          " ) " +
          " or " +
          " o.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
          " #{item}" +
          "</foreach>"+
          " ) " +
          "<if test = 'finalUserAuth.size() &gt; 0  '> " +
          " or " +
          "</if>" +
          "</if>" +
          // 用户权限
          "   <if test = 'finalUserAuth.size() &gt; 0  '>" +
          "     o.user_no in " +
          "       <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "   </if>" +
          "   <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
          "   or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 ' > " +
          "     ) " +
          " </if>" +
          "</trim>"+
          "<if test = ' condition.orderType == null or condition.orderType == \"\" or condition.orderType == \"20\"  '>" +
          " union all " +
          "SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo " +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          " where o.order_type = '20' " +
          " </if>" +
          "<if test = ' (condition.orderNo == null or condition.orderNo == \"\") or condition.orderNo.startsWith(\"9990\") or condition.orderNo.startsWith(\"9930\") or condition.orderNo.startsWith(\"99GZ\") or condition.orderNo.startsWith(\"990\")  ' >" +
          " union all " +
          "SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo " +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          " where " +
          "<if test = ' condition.orderType == null or condition.orderType == \"\" or condition.orderType == \"20\"  '>" +
          " o.order_type != '20' and  " +
          "</if>" +
          " ( o.order_no like '9990%' or o.order_no like '9930%' or o.order_no like '99GZ%' or o.order_no like '990%' )" +
          " </if>" +
          " union all " +
          "SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo " +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          " where o.end_user = 'C1D7V' " +
          " union all " +
          "SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo" +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o left join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          " where (o.customer_no is null or o.customer_no = '' )  and ( o.user_no is null  or o.user_no = '' ) and ( o.end_user is null or o.end_user = '' ) " +
          " ) o" +
          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
          // 供应商分类
          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
          " supplier_code in " +
          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
          " supplier_code not in " +
          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
          " supplier_code =  #{condition.supplier} and " +
          "</if>"+
          // 入库发票号
          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
          " po_invoice_no like  #{condition.invoiceNo} and " +
          "</if>"+
          // 客户PO号
          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
          " corderNo like  #{condition.purchaseNo} and " +
          "</if>"+
          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
          " state_code in " +
          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          // 画面-所属部门
          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
          " orderDeptNo =  #{condition.deptCode} and " +
          "</if>"+
          // 画面-部门选择
          " <if test = 'condition.deptCodes != null and condition.deptCodes.size() &gt; 0 ' > " +
          "  ( HLCode in " +
          "  <foreach collection = 'condition.deptCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item}" +
          "  </foreach>" +
          "  or " +
          "  HRUnitId in " +
          "  <foreach collection = 'condition.deptCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item} " +
          "  </foreach>" +
          " ) and "+
          " </if> " +
          // 手配号[供应商订单号]
          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
          " supplier_orderNo like  #{condition.supplierOrderNo} and " +
          "</if>"+
          // 订单类型
          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
          " o.order_type =  #{condition.orderType} and " +
          "</if>"+
          // 型号
          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
          " o.model_no like  #{condition.modelNo} and " +
          "</if>"+
          // 客户
          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
          " o.customer_no like  #{condition.customerNo} and " +
          "</if>"+
          // 用户
          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
          " o.user_no =  #{condition.userNo} and " +
          "</if>"+
          // 订单号
          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" and condition.exactSearchFlag == true '>" +
          " o.order_no =  #{condition.orderNo} and " +
          "</if>"+
          // 订单号
          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" and condition.exactSearchFlag == false '>" +
          " o.order_no like #{condition.orderNo} and " +
          "</if>"+
          // 下单日期
          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
          "</if>" +
          "</trim>"+
          " order by ${condition.property}  ${condition.order} " +
          " </script> "
  )
  @Options(timeout = 60)
  List<PurchaseOrderBean> listOrderStateWithCustomer(@Param("condition") PurchaseOrderCondition condition,
                                                     @Param("finalDeptNos") List<String> finalDeptNos,
                                                     @Param("finalUserAuth") List<String> finalUserAuth,
                                                     @Param("finalCustomerAuth") List<String> finalCustomerAuth,
                                                     @Param("finalInCodeAuth") List<String> finalInCodeAuth,
                                                     @Param("finalEmployeeAuth") List<String> finalEmployeeAuth);


  /**
   * 交货期导出
   */
  @Select("<script> "+
          "select " +
          "<if test = 'condition.exportNum != null ' >" +
          "  TOP ${condition.exportNum} " +
          " </if> " +
          " * from ( " +
          " SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo " +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
          "<if test = 'condition.orderType == null or condition.orderType == \"\" or condition.orderType == \"20\" ' >" +
          " o.order_type != '20' and " +
          "</if>"+
          "<if test = ' (condition.orderNo == null or condition.orderNo == \"\") or condition.orderNo.startsWith(\"9990\") or condition.orderNo.startsWith(\"9930\") or condition.orderNo.startsWith(\"99GZ\") or condition.orderNo.startsWith(\"990\")  ' >" +
          " ( o.order_no not like '9990%' or o.order_no not like '9930%' or o.order_no not like '99GZ%' or o.order_no not like '990%' ) and " +
          "</if>"+
          " o.end_user != 'C1D7V' and " +
          " (o.customer_no is not null or o.customer_no != '' ) and ( o.end_user is not null or o.end_user != '' ) and " +
          // 权限
          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
          "  or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 ' > " +
          " ( " +
          "</if>" +
          // 客户担当权限
          "   <if test = 'finalEmployeeAuth.size() &gt; 0  '> " +
          "       c.PSNSMCID in " +
          "       <foreach collection = 'finalEmployeeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "   <if test = 'finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  or finalDeptNos.size &gt; 0  ' > " +
          "      or " +
          "    </if>" +
          "   </if>" +
          // 部门权限
          " <if test = 'finalDeptNos.size() &gt; 0 ' > " +
          "  c.HLCode in " +
          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item}" +
          "  </foreach>" +
          "  or " +
          "  c.HRUnitId in " +
          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item} " +
          "  </foreach>" +
          "   <if test = 'finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
          "    or " +
          "   </if>" +
          " </if> " +
          // 客户权限
          "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
          "       o.end_user in " +
          "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "    <if test = 'finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
          "      or " +
          "    </if>" +
          "   </if>" +
          // 行业权限
          "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
          " o.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
          " #{item}" +
          "</foreach>"+
          " ) " +
          " or "+
          " o.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
          " #{item}" +
          "</foreach>"+
          " ) " +
          " or " +
          " o.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
          " #{item}" +
          "</foreach>"+
          " ) " +
          "<if test = 'finalUserAuth.size() &gt; 0  '> " +
          " or " +
          "</if>" +
          "</if>" +
          // 用户权限
          "   <if test = 'finalUserAuth.size() &gt; 0  '>" +
          "     o.user_no in " +
          "       <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "   </if>" +
          "   <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
          "   or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 ' > " +
          "     ) " +
          " </if>" +
          "</trim>"+
          "<if test = ' condition.orderType == null or condition.orderType == \"\" or condition.orderType == \"20\"  '>" +
          " union all " +
          "SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo " +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          " where o.order_type = '20' " +
          " </if>" +
          "<if test = ' (condition.orderNo == null or condition.orderNo == \"\") or condition.orderNo.startsWith(\"9990\") or condition.orderNo.startsWith(\"9930\") or condition.orderNo.startsWith(\"99GZ\") or condition.orderNo.startsWith(\"990\")  ' >" +
          " union all " +
          "SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo " +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          " where " +
          "<if test = ' condition.orderType == null or condition.orderType == \"\" or condition.orderType == \"20\"  '>" +
          " o.order_type != '20' and  " +
          "</if>" +
          " ( o.order_no like '9990%' or o.order_no like '9930%' or o.order_no like '99GZ%' or o.order_no like '990%' )" +
          " </if>" +
          " union all " +
          "SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo" +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          " where o.end_user = 'C1D7V' " +
          " union all " +
          "SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo " +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o left join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          " where (o.customer_no is null or o.customer_no = '' )  and ( o.user_no is null  or o.user_no = '' ) and ( o.end_user is null or o.end_user = '' ) " +
          " ) o" +
          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
          // 供应商分类
          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
          " supplier_code in " +
          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
          " supplier_code not in " +
          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
          " supplier_code =  #{condition.supplier} and " +
          "</if>"+
          // 入库发票号
          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
          " po_invoice_no like  #{condition.invoiceNo} and " +
          "</if>"+
          // 客户PO号
          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
          " corder_no like  #{condition.purchaseNo} and " +
          "</if>"+
          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
          " state_code in " +
          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          // 画面-所属部门
          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
          " orderDeptNo =  #{condition.deptCode} and " +
          "</if>"+
          // 画面-部门选择
          " <if test = 'condition.deptCodes != null and condition.deptCodes.size() &gt; 0 ' > " +
          "  ( HLCode in " +
          "  <foreach collection = 'condition.deptCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item}" +
          "  </foreach>" +
          "  or " +
          "  HRUnitId in " +
          "  <foreach collection = 'condition.deptCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item} " +
          "  </foreach>" +
          " ) and "+
          " </if> " +
          // 手配号[供应商订单号]
          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
          " supplier_orderNo like  #{condition.supplierOrderNo} and " +
          "</if>"+
          // 订单类型
          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
          " o.order_type =  #{condition.orderType} and " +
          "</if>"+
          // 型号
          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
          " o.model_no like  #{condition.modelNo} and " +
          "</if>"+
          // 客户
          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
          " o.customer_no like  #{condition.customerNo} and " +
          "</if>"+
          // 用户
          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
          " o.user_no =  #{condition.userNo} and " +
          "</if>"+
          // 订单号
          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" and condition.exactSearchFlag == true '>" +
          " o.order_no =  #{condition.orderNo} and " +
          "</if>"+
          // 订单号
          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" and condition.exactSearchFlag == false '>" +
          " o.order_no like #{condition.orderNo} and " +
          "</if>"+
          // 下单日期
          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
          "</if>" +
          "</trim>"+
          " order by ${condition.property}  ${condition.order} " +
          " </script> "
  )
  @Options(timeout = 60)
  List<PurchaseOrderBean> exportListOrderStateWithCustomer(@Param("condition") PurchaseOrderCondition condition,
                                                           @Param("finalDeptNos") List<String> finalDeptNos,
                                                           @Param("finalUserAuth") List<String> finalUserAuth,
                                                           @Param("finalCustomerAuth") List<String> finalCustomerAuth,
                                                           @Param("finalInCodeAuth") List<String> finalInCodeAuth,
                                                           @Param("finalEmployeeAuth") List<String> finalEmployeeAuth);



  /**
   * 门户业务系统删单查询
   */
  @Select("<script>" +
          "select * from ( " +
          " SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[rorder_no] " +
          " ,o.[state_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,d.price "+
          " ,d.status " +
          " ,o.[state_code] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[state_des] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o " +
          " inner join [ops_core].[dbo].[rcvdetail] d " +
          " on o.order_no = d.rorder_fno " +
          " inner join [ops_core].[dbo].[ops_customer] c " +
          " on o.end_user = c.customer_no " +
          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
          " o.end_user != 'C1D7V' and  " +
          // 权限
          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
          "  or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
          " ( " +
          "</if>" +
          // 部门权限
          " <if test = 'finalDeptNos.size() &gt; 0 ' > " +
          "  c.HLCode in " +
          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item}" +
          "  </foreach>" +
          "  or " +
          "  c.HRUnitId in " +
          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "  #{item} " +
          "  </foreach>" +
          "   <if test = 'finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
          "    or " +
          "   </if>" +
          " </if> " +
          // 客户权限
          "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
          "       o.end_user in " +
          "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "    <if test = 'finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
          "      or " +
          "    </if>" +
          "   </if>" +
          // 行业权限
          "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
          " o.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
          " #{item}" +
          "</foreach>"+
          " ) " +
          " or "+
          " o.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
          " #{item}" +
          "</foreach>"+
          " ) " +
          " or " +
          " o.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
          " #{item}" +
          "</foreach>"+
          " ) " +
          "<if test = 'finalUserAuth.size() &gt; 0  '> " +
          " or " +
          "</if>" +
          "</if>" +
          // 用户权限
          "   <if test = 'finalUserAuth.size() &gt; 0  '>" +
          "     o.user_no in " +
          "       <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "   </if>" +
          "   <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
          "   or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  ' > " +
          "     ) " +
          " </if>" +
          "</trim>"+
          " union all " +
          " SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[rorder_no] " +
          " ,o.[state_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,d.price "+
          " ,d.status " +
          " ,o.[state_code] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[state_des] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o " +
          " inner join [ops_core].[dbo].[rcvdetail] d " +
          " on o.order_no = d.rorder_fno " +
          " inner join [ops_core].[dbo].[ops_customer] c " +
          " on o.end_user = c.customer_no " +
          " where o.end_user = 'C1D7V' " +
          " ) o " +
          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
          " state_code = '90' and status = '9' and " +
          // 订单号
          "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
          " order_no like #{condition.orderNo} and " +
          "</if> " +
          // 客户
          "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
          " customer_no like #{condition.customerNo} and " +
          "</if> " +
          // 用户
          "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
          " user_no like #{condition.userNo} and " +
          "</if> " +
          // 型号
          "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
          " model_no like #{condition.modelNo} and " +
          "</if> " +
          // 申请日期
          "<if test = ' condition.applyDateStart != null and  condition.applyDateEnd != null ' >" +
          " state_date &gt;= #{condition.applyDateStart} and state_date &lt;= #{condition.applyDateEnd}  "+
          "</if>" +
          "</trim>"+
          " order by state_date desc, rorder_no asc, item_no asc  " +
          "</script>")
  @Options(timeout = 60)
  List<DelOrderVO> findDelOrder(@Param("condition") OrderDeleteCondition condition,
                                @Param("finalDeptNos") List<String> finalDeptNos,
                                @Param("finalUserAuth") List<String> finalUserAuth,
                                @Param("finalCustomerAuth") List<String> finalCustomerAuth,
                                @Param("finalInCodeAuth") List<String> finalInCodeAuth);

  /**
   * 代理店交货期查询
   */
  @Select("<script> "+
          " SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo" +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
          // 供应商分类
          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
          " o.supplier_code in " +
          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
          " o.supplier_code not in " +
          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
          " o.supplier_code =  #{condition.supplier} and " +
          "</if>"+
          // 入库发票号
          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
          " o.po_invoice_no like  #{condition.invoiceNo} and " +
          "</if>"+
          // 所属部门
          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
          " o.dept_no =  #{condition.deptCode} and " +
          "</if>"+
          // 客户PO号
          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
          " o.corder_no like  #{condition.purchaseNo} and " +
          "</if>"+
          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
          " o.state_code in " +
          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          // 手配号[供应商订单号]
          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
          "</if>"+
          // 订单类型
          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
          " o.order_type =  #{condition.orderType} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") or (condition.customerNo != null and condition.customerNo != \"\" ) or (condition.modelNo != null and condition.modelNo != \"\")  ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 型号
          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
          " o.model_no like  #{condition.modelNo} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") or (condition.customerNo != null and condition.customerNo != \"\" )  ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 客户
          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
          " o.customer_no like  #{condition.customerNo}  " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 用户
          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
          " o.user_no =  #{condition.userNo} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
          " or ( condition.orderNo != null and condition.orderNo != \"\" ) ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 订单号
          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" and condition.exactSearchFlag == false '>" +
          " o.order_no like  #{condition.orderNo} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 订单号
          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" and condition.exactSearchFlag == true '>" +
          " o.order_no =  #{condition.orderNo} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 下单日期
          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          "   <if test = 'finalCustomerAuth.size() &gt; 0 ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>" +
          // 权限
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          // 客户权限
          "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
          "       o.customer_no in " +
          "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "   </if>" +
          "</if>" +
          "</trim>"+
          " order by ${condition.property}  ${condition.order} " +
          " </script> "
  )
  @Options(timeout = 60)
  List<PurchaseOrderBean> listOrderStateWithCustomerForAgent(@Param("condition") PurchaseOrderCondition condition,
                                                     @Param("finalDeptNos") List<String> finalDeptNos,
                                                     @Param("finalUserAuth") List<String> finalUserAuth,
                                                     @Param("finalCustomerAuth") List<String> finalCustomerAuth,
                                                     @Param("finalInCodeAuth") List<String> finalInCodeAuth);



  /**
   * 代理店业务系统删单查询
   */
  @Select("<script>" +
          " SELECT " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[rorder_no] " +
          " ,o.[state_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,d.price"+
          " ,o.[state_code] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[state_des] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o " +
          " inner join [ops_core].[dbo].[rcvdetail] d " +
          " on o.order_no = d.rorder_fno " +
          " inner join [ops_core].[dbo].[ops_customer] c " +
          " on o.end_user = c.customer_no " +
          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
          " o.state_code = '90' and d.status = '9' and " +
          // 订单号
          "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
          " o.order_no like #{condition.orderNo} and " +
          "</if> " +
          // 客户
          "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
          " o.customer_no like #{condition.customerNo} and " +
          "</if> " +
          // 用户
          "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
          " o.user_no like #{condition.userNo} and  " +
          "</if> " +
          // 型号
          "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
          " o.model_no like #{condition.modelNo} and " +
          "</if> " +
          // 客户权限
          " <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
          "   o.customer_no in " +
          "     <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "         #{item}" +
          "     </foreach>" +
          "<if test = ' condition.applyDateStart != null and  condition.applyDateEnd != null ' >" +
          " and  " +
          "</if>"+
          "</if>" +
          // 申请日期
          "<if test = ' condition.applyDateStart != null and  condition.applyDateEnd != null ' >" +
          " o.state_date &gt;= #{condition.applyDateStart} and o.state_date &lt;= #{condition.applyDateEnd}  "+
          "</if>" +
          "</trim>"+
          " order by o.state_date desc, o.rorder_no asc, o.item_no asc  " +
          "</script>")
  @Options(timeout = 60)
  List<DelOrderVO> findDelOrderForAgent(@Param("condition") OrderDeleteCondition condition,
                                @Param("finalDeptNos") List<String> finalDeptNos,
                                @Param("finalUserAuth") List<String> finalUserAuth,
                                @Param("finalCustomerAuth") List<String> finalCustomerAuth,
                                @Param("finalInCodeAuth") List<String> finalInCodeAuth);


  /**
   * 代理店交货期导出
   */
  @Select("<script> "+
          " SELECT " +
          "<if test = 'condition.exportNum != null ' >" +
          "  TOP ${condition.exportNum} " +
          " </if> " +
          "  o.[id] " +
          " ,o.[order_no] " +
          " ,o.[item_no] " +
          " ,o.[split_no] " +
          " ,o.[rorder_no] " +
          " ,o.[order_type] " +
          " ,o.[model_no] " +
          " ,o.[quantity] " +
          " ,o.[state_code] " +
          " ,o.[state_des] " +
          " ,o.[state_date] " +
          " ,o.[state_type] " +
          " ,o.[create_time] " +
          " ,o.[update_time] " +
          " ,o.[cust_dlv_date] " +
          " ,o.[cust_ship_date] " +
          " ,o.[cust_sign_date] " +
          " ,o.[dept_dlv_date] " +
          " ,o.[po_dlv_date] " +
          " ,o.[po_reply_date] " +
          " ,o.[po_ship_date] " +
          " ,o.[customer_no] " +
          " ,o.[user_no] " +
          " ,o.[dept_no] as orderDeptNo " +
          " ,o.[supplier_code] " +
          " ,o.[supplier_no] " +
          " ,o.[act_arrival_date] " +
          " ,o.[es_arrival_date] " +
          " ,o.[es_arrive_date_req] " +
          " ,o.[provider] " +
          " ,o.[ship_date] " +
          " ,o.[es_ship_date] " +
          " ,o.[express_dlv_date] " +
          " ,o.[delay_day] " +
          " ,o.[po_delay_day] " +
          " ,o.[corder_no] as corderNo " +
          " ,o.[cmodel_no] as cmodelNo " +
          " ,o.[trans_Type]" +
          " ,o.[supplier_orderNo] " +
          " ,o.[shikomi_no] " +
          " ,o.[supplier_rcvtime] " +
          " ,o.[order_psn_no] " +
          " ,o.[order_appover_no] " +
          " ,o.[po_invoice_no] " +
          " ,o.[po_exp_type] " +
          " ,o.[po_holon] " +
          " ,o.[po_no] " +
          " ,o.[order_date] " +
          " ,o.[Receive_Date] " +
          " ,o.[po_date] " +
          " ,o.[purchase_type] " +
          " ,o.[opt_user_no] " +
          " ,o.[opt_user_name] " +
          " ,o.[po_rcv_time] " +
          " ,o.[inqA] " +
          " ,o.[inqB] " +
          " ,o.[rcv_status] " +
          " ,o.[po_reply_dateA] " +
          " ,o.[po_reply_dateB] " +
          " ,o.[po_reply_dateC] " +
          " ,o.[po_trans_type] " +
          " ,o.[warehouse_code] " +
          " ,o.[port_arrivedate] " +
          " ,o.[customs_date] " +
          " ,o.[begin_produce_date] " +
          " ,o.[po_price] " +
          " ,o.[order_psn_dept] " +
          " ,o.[end_user] " +
          " ,o.[intercept] " +
          " ,o.[po_facExpdate] " +
          " ,o.[expected_production_completion_dateH] " +
          " ,o.[expected_logistics_arrival_dateW] " +
          " ,c.[CustomerType] " +
          " ,c.[name] " +
          " ,c.[HRUnitId] " +
          " ,c.[HLCode] " +
          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
          " on o.end_user = c.customer_no " +
          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
          // 供应商分类
          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
          " o.supplier_code in " +
          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
          " o.supplier_code not in " +
          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
          " o.supplier_code =  #{condition.supplier} and " +
          "</if>"+
          // 入库发票号
          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
          " o.po_invoice_no like  #{condition.invoiceNo} and " +
          "</if>"+
          // 所属部门
          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
          " o.dept_no = #{condition.deptCode} and " +
          "</if>"+
          // 客户PO号
          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
          " o.corder_no like  #{condition.purchaseNo} and " +
          "</if>"+
          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
          " o.state_code in " +
          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "       #{item}" +
          "   </foreach>" +
          " and " +
          "</if>"+
          // 手配号[供应商订单号]
          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
          "</if>"+
          // 订单类型
          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
          " o.order_type =  #{condition.orderType} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") or (condition.customerNo != null and condition.customerNo != \"\" ) or (condition.modelNo != null and condition.modelNo != \"\")  ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 型号
          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
          " o.model_no like  #{condition.modelNo} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") or (condition.customerNo != null and condition.customerNo != \"\" )  ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 客户
          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
          " o.customer_no like  #{condition.customerNo}  " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 用户
          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
          " o.user_no =  #{condition.userNo} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
          " or ( condition.orderNo != null and condition.orderNo != \"\" ) ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 订单号
          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" '>" +
          " o.order_no like  #{condition.orderNo} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 订单号
          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" and condition.exactSearchFlag == false '>" +
          " o.order_no like  #{condition.orderNo} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 订单号
          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" and condition.exactSearchFlag == true '>" +
          " o.order_no =  #{condition.orderNo} " +
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          " <if test = ' finalCustomerAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) ' > " +
          " and " +
          "</if>" +
          "</if>" +
          "</if>"+
          // 权限
          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
          // 客户权限
          "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
          "       o.customer_no in " +
          "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
          "           #{item}" +
          "       </foreach>" +
          "   </if>" +
          "</if>" +
          "</trim>"+
          " order by ${condition.property}  ${condition.order} " +
          " </script> "
  )
  @Options(timeout = 60)
  List<PurchaseOrderBean> exportListOrderStateWithCustomerForAgent(@Param("condition") PurchaseOrderCondition condition,
                                                             @Param("finalDeptNos") List<String> finalDeptNos,
                                                             @Param("finalUserAuth") List<String> finalUserAuth,
                                                             @Param("finalCustomerAuth") List<String> finalCustomerAuth,
                                                             @Param("finalInCodeAuth") List<String> finalInCodeAuth);


  /**
   * 交货期查询
   */
//  @Select("<script> "+
//          "select distinct * from ( " +
//          " SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
//          " o.order_type =  #{condition.orderType} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
//          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") or (condition.customerNo != null and condition.customerNo != \"\" ) or (condition.modelNo != null and condition.modelNo != \"\")  ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>"+
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
//          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") or (condition.customerNo != null and condition.customerNo != \"\" )  ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>"+
//          // 客户
//          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
//          " o.customer_no like  #{condition.customerNo}  " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
//          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>"+
//          // 用户
//          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
//          " o.user_no =  #{condition.userNo} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
//          " or ( condition.orderNo != null and condition.orderNo != \"\" ) ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>"+
//          // 订单号
//          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" '>" +
//          " o.order_no like  #{condition.orderNo} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) ' > " +
//            " and " +
//           "</if>" +
//          "</if>" +
//          "</if>"+
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          "   <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          "   or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>" +
//          // 权限
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          "   <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          "   or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
//          " ( " +
//          "</if>" +
//          // 部门权限
//          " <if test = 'finalDeptNos != null and finalDeptNos.size() &gt; 0 ' > " +
//          "  c.HLCode in " +
//          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "  #{item}" +
//          "  </foreach>" +
//          "  or " +
//          "  c.HRUnitId in " +
//          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "  #{item} " +
//          "  </foreach>" +
//          " </if> " +
//          "   <if test = ' finalDeptNos.size() &gt; 0  and ( finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ) ' > " +
//          "    or " +
//          "   </if>" +
//          // 客户权限
//          "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
//          "       o.end_user in " +
//          "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "           #{item}" +
//          "       </foreach>" +
//          "   </if>" +
//          "   <if test = 'finalCustomerAuth.size() &gt; 0 and ( finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ) '> " +
//          "      or " +
//          "    </if>" +
//          // 行业权限
//          "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
//          " o.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//          " #{item}" +
//          "</foreach>"+
//          " ) " +
//          " or "+
//          " o.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//          " #{item}" +
//          "</foreach>"+
//          " ) " +
//          " or " +
//          " o.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//          " #{item}" +
//          "</foreach>"+
//          " ) " +
//          "<if test = 'finalUserAuth.size() &gt; 0  '> " +
//          " or " +
//          "</if>" +
//          "</if>" +
//          // 用户权限
//          "   <if test = 'finalUserAuth.size() &gt; 0  '>" +
//          "     o.user_no in " +
//          "       <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "           #{item}" +
//          "       </foreach>" +
//          "   </if>" +
//          "   <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          "   or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  ' > " +
//          "     ) " +
//          " </if>" +
//          "</if>" +
//          "</trim>"+
//          // union all 订单类型为20的(bin补库)
//          "<if test = ' ( condition.orderType == null or condition.orderType == \"\" or condition.orderType == \"20\" ) and condition.agentSearch != \"1\" '>" +
//            " union all " +
//          "SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=') ' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          " o.order_type = '20' and " +
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} and " +
//          "</if>"+
//          // 客户
//          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
//          " o.customer_no like  #{condition.customerNo} and " +
//          "</if>"+
//          // 用户
//          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
//          " o.user_no =  #{condition.userNo} and " +
//          "</if>"+
//          // 订单号
//          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" '>" +
//          " o.order_no like  #{condition.orderNo} and" +
//          "</if>"+
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "</if>" +
//          " </trim> " +
//          "</if>"+
//          // 订单号不为空 查 9990,9930,99GZ,990
//          "<if test = ' (condition.orderNo == null or condition.orderNo == \"\" ) and condition.agentSearch != \"1\" ' >" +
//          " union all " +
//          "SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
//          " o.order_type =  #{condition.orderType} and " +
//          "</if>"+
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} and " +
//          "</if>"+
//          // 客户
//          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
//          " o.customer_no like  #{condition.customerNo} and " +
//          "</if>"+
//          // 用户
//          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
//          " o.user_no =  #{condition.userNo} and " +
//          "</if>"+
//          // 订单号
//          " ( o.order_no like '9990%' or o.order_no like '9930%' or o.order_no like '99GZ%' or o.order_no like '9990%' or o.order_no like '990%' ) and " +
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "</if>" +
//          "</trim>"+
//          "</if>"+
//          "<if test = ' condition.agentSearch != null and condition.agentSearch != \"\" and condition.agentSearch != \"1\" ' >" +
//          " union all " +
//          " SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
//          " o.order_type =  #{condition.orderType} and " +
//          "</if>"+
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} and " +
//          "</if>"+
//          // 客户
//          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
//          " o.customer_no like  #{condition.customerNo} and " +
//          "</if>"+
//          // 用户
//          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
//          " o.user_no =  #{condition.userNo} and " +
//          "</if>"+
//          // 订单号
//          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" '>" +
//          " o.order_no like  #{condition.orderNo} and" +
//          "</if>"+
//          " o.end_user = 'C1D7V' and " +
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "</if>" +
//          "</trim>"+
//          "</if>"+
//          // 放开那些客户为空,用户为空,最终用户为空的订单权限
//          "<if test = ' condition.customerNo == null or condition.customerNo == \"\" ' >" +
//          " union all " +
//          " SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o " +
//          " left join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
//          " o.order_type =  #{condition.orderType} and " +
//          "</if>"+
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} and " +
//          "</if>"+
//          // 客户
//          "  (o.customer_no is null or o.customer_no = '' )  and ( o.user_no is null  or o.user_no = '' ) and ( o.end_user is null or o.end_user = '' ) and " +
//          // 订单号
//          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" '>" +
//          " o.order_no like  #{condition.orderNo} and" +
//          "</if>"+
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "</if>" +
//          "</trim>"+
//          "</if>"+
//          " ) o " +
//          " order by ${condition.property}  ${condition.order} " +
//          " </script> "
//  )
//  @Options(timeout = 60)
//  List<PurchaseOrderBean> listOrderStateWithCustomer(@Param("condition") PurchaseOrderCondition condition,
//                                                     @Param("finalDeptNos") List<String> finalDeptNos,
//                                                     @Param("finalUserAuth") List<String> finalUserAuth,
//                                                     @Param("finalCustomerAuth") List<String> finalCustomerAuth,
//                                                     @Param("finalInCodeAuth") List<String> finalInCodeAuth);

  /**
   * 交货期导出
   */
//  @Select("<script> "+
//          "select distinct " +
//          "<if test = 'condition.exportNum != null ' >" +
//          "  TOP ${condition.exportNum} " +
//          " </if> " +
//          " * from (" +
//          " SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
//          " o.order_type =  #{condition.orderType} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
//          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") or (condition.customerNo != null and condition.customerNo != \"\" ) or (condition.modelNo != null and condition.modelNo != \"\")  ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>"+
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
//          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") or (condition.customerNo != null and condition.customerNo != \"\" )  ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>"+
//          // 客户
//          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
//          " o.customer_no like  #{condition.customerNo}  " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
//          " or ( condition.orderNo != null and condition.orderNo != \"\" ) or (condition.userNo != null and condition.userNo != \"\") ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>"+
//          // 用户
//          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
//          " o.user_no =  #{condition.userNo} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) " +
//          " or ( condition.orderNo != null and condition.orderNo != \"\" ) ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>"+
//          // 订单号
//          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" '>" +
//          " o.order_no like  #{condition.orderNo} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          " <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          " or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 or (condition.startDate != null and condition.endDate != null ) ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>"+
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          "   <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          "   or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
//          " and " +
//          "</if>" +
//          "</if>" +
//          "</if>" +
//          // 权限
//          "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//          "   <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          "   or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
//          " ( " +
//          "</if>" +
//          // 部门权限
//          " <if test = 'finalDeptNos != null and finalDeptNos.size() &gt; 0 ' > " +
//          "  c.HLCode in " +
//          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "  #{item}" +
//          "  </foreach>" +
//          "  or " +
//          "  c.HRUnitId in " +
//          "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "  #{item} " +
//          "  </foreach>" +
//          " </if> " +
//          "   <if test = ' finalDeptNos.size() &gt; 0  and ( finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ) ' > " +
//          "    or " +
//          "   </if>" +
//          // 客户权限
//          "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
//          "       o.end_user in " +
//          "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "           #{item}" +
//          "       </foreach>" +
//          "   </if>" +
//          "   <if test = 'finalCustomerAuth.size() &gt; 0 and ( finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ) '> " +
//          "      or " +
//          "    </if>" +
//          // 行业权限
//          "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
//          " o.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//          " #{item}" +
//          "</foreach>"+
//          " ) " +
//          " or "+
//          " o.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//          " #{item}" +
//          "</foreach>"+
//          " ) " +
//          " or " +
//          " o.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//          "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//          " #{item}" +
//          "</foreach>"+
//          " ) " +
//          "<if test = 'finalUserAuth.size() &gt; 0  '> " +
//          " or " +
//          "</if>" +
//          "</if>" +
//          // 用户权限
//          "   <if test = 'finalUserAuth.size() &gt; 0  '>" +
//          "     o.user_no in " +
//          "       <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "           #{item}" +
//          "       </foreach>" +
//          "   </if>" +
//          "   <if test = ' finalDeptNos.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 " +
//          "   or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  ' > " +
//          "     ) " +
//          " </if>" +
//          "</if>" +
//          "</trim>"+
//          // union all 订单类型为20的(bin补库)
//          "<if test = ' ( condition.orderType == null or condition.orderType == \"\" or condition.orderType == \"20\" ) and condition.agentSearch != \"1\" '>" +
//          " union all " +
//          "SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=') ' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          " o.order_type = '20' and " +
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} and " +
//          "</if>"+
//          // 客户
//          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
//          " o.customer_no like  #{condition.customerNo} and " +
//          "</if>"+
//          // 用户
//          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
//          " o.user_no =  #{condition.userNo} and " +
//          "</if>"+
//          // 订单号
//          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" '>" +
//          " o.order_no like  #{condition.orderNo} and" +
//          "</if>"+
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "</if>" +
//          " </trim> " +
//          "</if>"+
//          // 订单号不为空 查 9990,9930,99GZ,990
//          "<if test = ' (condition.orderNo == null or condition.orderNo == \"\" ) and condition.agentSearch != \"1\" ' >" +
//          " union all " +
//          "SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
//          " o.order_type =  #{condition.orderType} and " +
//          "</if>"+
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} and " +
//          "</if>"+
//          // 客户
//          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
//          " o.customer_no like  #{condition.customerNo} and " +
//          "</if>"+
//          // 用户
//          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
//          " o.user_no =  #{condition.userNo} and " +
//          "</if>"+
//          // 订单号
//          " ( o.order_no like '9990%' or o.order_no like '9930%' or o.order_no like '99GZ%' or o.order_no like '9990%' or o.order_no like '990%' ) and " +
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "</if>" +
//          "</trim>"+
//          "</if>"+
//          "<if test = ' condition.agentSearch != null and condition.agentSearch != \"\" and condition.agentSearch != \"1\" ' >" +
//          " union all " +
//          " SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o inner join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
//          " o.order_type =  #{condition.orderType} and " +
//          "</if>"+
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} and " +
//          "</if>"+
//          // 客户
//          "<if test = ' condition.customerNo != null and condition.customerNo != \"\" '>" +
//          " o.customer_no like  #{condition.customerNo} and " +
//          "</if>"+
//          // 用户
//          "<if test = ' condition.userNo != null and condition.userNo != \"\" '>" +
//          " o.user_no =  #{condition.userNo} and " +
//          "</if>"+
//          // 订单号
//          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" '>" +
//          " o.order_no like  #{condition.orderNo} and" +
//          "</if>"+
//          " o.end_user = 'C1D7V' and " +
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "</if>" +
//          "</trim>"+
//          "</if>"+
//          // 放开那些客户为空,用户为空,最终用户为空的订单权限
//          "<if test = ' condition.customerNo == null or condition.customerNo == \"\" ' >" +
//          " union all " +
//          " SELECT " +
//          "  o.[id] " +
//          " ,o.[order_no] " +
//          " ,o.[item_no] " +
//          " ,o.[split_no] " +
//          " ,o.[rorder_no] " +
//          " ,o.[order_type] " +
//          " ,o.[model_no] " +
//          " ,o.[quantity] " +
//          " ,o.[state_code] " +
//          " ,o.[state_des] " +
//          " ,o.[state_date] " +
//          " ,o.[state_type] " +
//          " ,o.[create_time] " +
//          " ,o.[update_time] " +
//          " ,o.[cust_dlv_date] " +
//          " ,o.[cust_ship_date] " +
//          " ,o.[cust_sign_date] " +
//          " ,o.[dept_dlv_date] " +
//          " ,o.[po_dlv_date] " +
//          " ,o.[po_reply_date] " +
//          " ,o.[po_ship_date] " +
//          " ,o.[customer_no] " +
//          " ,o.[user_no] " +
//          " ,o.[dept_no] " +
//          " ,o.[supplier_code] " +
//          " ,o.[supplier_no] " +
//          " ,o.[act_arrival_date] " +
//          " ,o.[es_arrival_date] " +
//          " ,o.[es_arrive_date_req] " +
//          " ,o.[provider] " +
//          " ,o.[ship_date] " +
//          " ,o.[es_ship_date] " +
//          " ,o.[express_dlv_date] " +
//          " ,o.[delay_day] " +
//          " ,o.[po_delay_day] " +
//          " ,o.[corder_no] as corderNo " +
//          " ,o.[cmodel_no] as cmodelNo " +
//          " ,o.[trans_Type]" +
//          " ,o.[supplier_orderNo] " +
//          " ,o.[shikomi_no] " +
//          " ,o.[supplier_rcvtime] " +
//          " ,o.[order_psn_no] " +
//          " ,o.[order_appover_no] " +
//          " ,o.[po_invoice_no] " +
//          " ,o.[po_exp_type] " +
//          " ,o.[po_holon] " +
//          " ,o.[po_no] " +
//          " ,o.[order_date] " +
//          " ,o.[Receive_Date] " +
//          " ,o.[po_date] " +
//          " ,o.[purchase_type] " +
//          " ,o.[opt_user_no] " +
//          " ,o.[opt_user_name] " +
//          " ,o.[po_rcv_time] " +
//          " ,o.[inqA] " +
//          " ,o.[inqB] " +
//          " ,o.[rcv_status] " +
//          " ,o.[po_reply_dateA] " +
//          " ,o.[po_reply_dateB] " +
//          " ,o.[po_reply_dateC] " +
//          " ,o.[po_trans_type] " +
//          " ,o.[warehouse_code] " +
//          " ,o.[port_arrivedate] " +
//          " ,o.[customs_date] " +
//          " ,o.[begin_produce_date] " +
//          " ,o.[po_price] " +
//          " ,o.[order_psn_dept] " +
//          " ,o.[end_user] " +
//          " ,o.[intercept] " +
//          " ,c.[CustomerType] " +
//          " ,c.[name] " +
//          " ,c.[HRUnitId] " +
//          " ,c.[HLCode] " +
//          "  FROM [ops_core].[dbo].[order_state] o " +
//          " left join [ops_core].[dbo].[ops_customer] c" +
//          " on o.end_user = c.customer_no " +
//          "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//          // 供应商分类
//          "<if test = 'condition.chinaSupplierList != null and condition.chinaSupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code in " +
//          "   <foreach collection = 'condition.chinaSupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = 'condition.notChinasupplierList != null and condition.notChinasupplierList.size() &gt; 0 ' >" +
//          " o.supplier_code not in " +
//          "   <foreach collection = 'condition.notChinasupplierList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          "<if test = ' condition.supplier != null and condition.supplier != \"\" '>" +
//          " o.supplier_code =  #{condition.supplier} and " +
//          "</if>"+
//          // 入库发票号
//          "<if test = ' condition.invoiceNo != null and condition.invoiceNo != \"\" '>" +
//          " o.po_invoice_no like  #{condition.invoiceNo} and " +
//          "</if>"+
//          // 所属部门
//          "<if test = ' condition.deptCode != null and condition.deptCode != \"\" '>" +
//          " ( c.HLCode =  #{condition.deptCode} or c.HRUnitId = #{condition.deptCode} ) and " +
//          "</if>"+
//          // 客户PO号
//          "<if test = ' condition.purchaseNo != null and condition.purchaseNo != \"\" '>" +
//          " o.corder_no like  #{condition.purchaseNo} and " +
//          "</if>"+
//          "<if test = 'condition.statusList != null and condition.statusList.size() &gt; 0 ' >" +
//          " o.state_code in " +
//          "   <foreach collection = 'condition.statusList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//          "       #{item}" +
//          "   </foreach>" +
//          " and " +
//          "</if>"+
//          // 手配号[供应商订单号]
//          "<if test = ' condition.supplierOrderNo != null and condition.supplierOrderNo != \"\" '>" +
//          " o.supplier_orderNo like  #{condition.supplierOrderNo} and " +
//          "</if>"+
//          // 订单类型
//          "<if test = ' condition.orderType != null and condition.orderType != \"\" '>" +
//          " o.order_type =  #{condition.orderType} and " +
//          "</if>"+
//          // 型号
//          "<if test = ' condition.modelNo != null and condition.modelNo != \"\" '>" +
//          " o.model_no like  #{condition.modelNo} and " +
//          "</if>"+
//          // 客户
//          "  (o.customer_no is null or o.customer_no = '' )  and ( o.user_no is null  or o.user_no = '' ) and ( o.end_user is null or o.end_user = '' ) and " +
//          // 订单号
//          "<if test = ' condition.orderNo != null and condition.orderNo != \"\" '>" +
//          " o.order_no like  #{condition.orderNo} and" +
//          "</if>"+
//          // 下单日期
//          "<if test = ' condition.startDate != null and condition.endDate != null ' >" +
//          "  o.order_date &gt;= #{condition.startDate}  and o.order_date &lt;= #{condition.endDate} " +
//          "</if>" +
//          "</trim>"+
//          "</if>"+
//          " ) o " +
//          " order by ${condition.property}  ${condition.order} " +
//          " </script> "
//  )
//  @Options(timeout = 60)
//  List<PurchaseOrderBean> exportListOrderStateWithCustomer(@Param("condition") PurchaseOrderCondition condition,
//                                                     @Param("finalDeptNos") List<String> finalDeptNos,
//                                                     @Param("finalUserAuth") List<String> finalUserAuth,
//                                                     @Param("finalCustomerAuth") List<String> finalCustomerAuth,
//                                                     @Param("finalInCodeAuth") List<String> finalInCodeAuth);

}
