package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.adapter.order.SMSSearchListInfo;
import com.smc.smccloud.model.order.*;
import com.smc.smccloud.model.order.orderEdit.OrderModifyUpInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/8/27 9:59
 * @Descripton TODO
 */

@DS("opsreaddb")
@Mapper
public interface RcvDetailMapperReadOnlyMapper extends BaseMapper<RcvDetailDO> {

    /**
     * 接单查询
     */
    @Select("<script>" +
            "select * from (" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No], " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " rm.end_user != 'C1D7V' and " +
            // 权限
            "<if test = ' finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ( " +
            "</if>" +
            // 用户id(客户担当)权限
            "   <if test = 'finalEmployeeAuth.size() &gt; 0 ' > " +
            "       rm.Employee in " +
            "       <foreach collection = 'finalEmployeeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "         #{item}" +
            "       </foreach>" +
            "   <if test = 'finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            "    or " +
            "   </if>" +
            "   </if>" +
            // 部门权限
            " <if test = 'finalDeptAuth.size() &gt; 0 ' > " +
            "  c.HLCode in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item}" +
            "  </foreach>" +
            "  or " +
            "  c.HRUnitId in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item} " +
            "  </foreach>" +
            "   <if test = ' finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  ' > " +
            "    or " +
            "   </if>" +
            " </if> " +
            // 客户权限
            "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
            "       rm.end_user in " +
            "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   <if test = ' finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
            "      or " +
            "    </if>" +
            "   </if>" +
            // 行业权限
            "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or "+
            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or " +
            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
            "     rm.user_no in " +
            "     <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "     </foreach>" +
            "   </if>" +
            "<if test = 'finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ) " +
            "</if>" +
            "</trim> " +
            " union all " +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No], " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            " where rm.end_user = 'C1D7V'  " +
            " ) t " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            "  remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            "  stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            "  ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            "  QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            "  PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            "  EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            "  Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            "  end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            "  exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " ( exp_dlv_type &amp; 8192 != 8192 or exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            "  status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            "  order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            "  user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            "  customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            "  cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            "  model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            "  rorder_fno like #{condition.orderNo} and " +
            "</if> " +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "   ROrdDate &gt;= #{condition.orderDateStartStr}  and  ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            " order by ${condition.property}  ${condition.order}" +
            "</script>")
    @Options(timeout = 60)
    List<RcvOrderWithCustomerForViewDO> queryOrderWithCustomer(@Param("condition") SMSSearchListInfo condition, @Param("types") List<String> types,
                                                                @Param("status") List<String> status, @Param("purchasingSupplier") List<String> purchasingSupplier,
                                                                @Param("finalUserAuth") List<String> finalUserAuth, @Param("finalDeptAuth") List<String> finalDeptAuth,
                                                                @Param("finalCustomerAuth") List<String> finalCustomerAuth, @Param("finalEmployeeAuth") List<String> finalEmployeeAuth,
                                                                @Param("finalInCodeAuth") List<String> finalInCodeAuth
    );


    /**
     * 接单查询导出
     */
    @Select("<script>" +
            "select " +
            "<if test = 'condition.exportNum != null ' >" +
            "  TOP ${condition.exportNum} " +
            " </if> " +
            " * from (" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No], " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " rm.end_user != 'C1D7V' and " +
            // 权限
            "<if test = ' finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ( " +
            "</if>" +
            // 用户id(客户担当)权限
            "   <if test = 'finalEmployeeAuth.size() &gt; 0 ' > " +
            "       rm.Employee in " +
            "       <foreach collection = 'finalEmployeeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "         #{item}" +
            "       </foreach>" +
            "   <if test = 'finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            "    or " +
            "   </if>" +
            "   </if>" +
            // 部门权限
            " <if test = 'finalDeptAuth.size() &gt; 0 ' > " +
            "  c.HLCode in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item}" +
            "  </foreach>" +
            "  or " +
            "  c.HRUnitId in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item} " +
            "  </foreach>" +
            "   <if test = ' finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  ' > " +
            "    or " +
            "   </if>" +
            " </if> " +
            // 客户权限
            "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
            "       rm.end_user in " +
            "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   <if test = ' finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
            "      or " +
            "    </if>" +
            "   </if>" +
            // 行业权限
            "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or "+
            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or " +
            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
            "     rm.user_no in " +
            "     <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "     </foreach>" +
            "   </if>" +
            "<if test = 'finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ) " +
            "</if>" +
            "</trim> " +
            " union all " +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No], " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            " where rm.end_user = 'C1D7V'  " +
            " ) t " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            "  remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            "  stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            "  ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            "  QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            "  PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            "  EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            "  Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            "  end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            "  exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " ( exp_dlv_type &amp; 8192 != 8192 or exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            "  status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            "  order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            "  user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            "  customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            "  cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            "  model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            "  rorder_fno like #{condition.orderNo} and " +
            "</if> " +
            // 订货日期
//            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            "   ROrdDate &gt;= #{condition.orderDateStart}  and  ROrdDate &lt;= #{condition.orderDateEnd} " +
//            "</if>" +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "   ROrdDate &gt;= #{condition.orderDateStartStr}  and  ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            " order by ${condition.property}  ${condition.order}" +
            "</script>")
    @Options(timeout = 60)
    List<RcvOrderWithCustomerForViewDO> exportRcvOrder(@Param("condition") SMSSearchListInfo condition, @Param("types") List<String> types,
                                                               @Param("status") List<String> status, @Param("purchasingSupplier") List<String> purchasingSupplier,
                                                               @Param("finalUserAuth") List<String> finalUserAuth, @Param("finalDeptAuth") List<String> finalDeptAuth,
                                                               @Param("finalCustomerAuth") List<String> finalCustomerAuth, @Param("finalEmployeeAuth") List<String> finalEmployeeAuth,
                                                               @Param("finalInCodeAuth") List<String> finalInCodeAuth
    );

    /**
     * 行业接单查询
     */
    @Select("<script>" +
            "select * from (" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee] as empSale , " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo] as hddno , " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No] as saleDeptNo , " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no] , " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno] as orderNo , " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " rm.end_user != 'C1D7V' and " +
            // 权限
            "<if test = ' finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ( " +
            "</if>" +
            // 用户id(客户担当)权限
            "   <if test = 'finalEmployeeAuth.size() &gt; 0 ' > " +
            "       rm.Employee in " +
            "       <foreach collection = 'finalEmployeeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "         #{item}" +
            "       </foreach>" +
            "   <if test = 'finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            "    or " +
            "   </if>" +
            "   </if>" +
            // 部门权限
            " <if test = 'finalDeptAuth.size() &gt; 0 ' > " +
            "  c.HLCode in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item}" +
            "  </foreach>" +
            "  or " +
            "  c.HRUnitId in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item} " +
            "  </foreach>" +
            "   <if test = ' finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  ' > " +
            "    or " +
            "   </if>" +
            " </if> " +
            // 客户权限
            "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
            "       rm.end_user in " +
            "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   <if test = ' finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
            "      or " +
            "    </if>" +
            "   </if>" +
            // 行业权限
            "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or "+
            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or " +
            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
            "     rm.user_no in " +
            "     <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "     </foreach>" +
            "   </if>" +
            "<if test = 'finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ) " +
            "</if>" +
            "</trim> " +
            " union all " +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo] as hddno , " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No] as saleDeptNo , " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no] , " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno] as orderNo , " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            " where rm.end_user = 'C1D7V'  " +
            " ) t " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            "  remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            "  stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            "  ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            "  QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            "  PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            "  EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            "  empSale = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            "  end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            "  exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " ( exp_dlv_type &amp; 8192 != 8192 or exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            "  status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            "  order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            "  user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            "  customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            "  cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            "  model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            "  orderNo like #{condition.orderNo} and " +
            "</if> " +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "   ROrdDate &gt;= #{condition.orderDateStartStr}  and  ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            " order by ${condition.property}  ${condition.order}" +
            "</script>")
    @Options(timeout = 60)
    List<IndCodeEntity> queryRcvOrderByIndCode(@Param("condition") SMSSearchListInfo condition, @Param("types") List<String> types,
                                               @Param("status") List<String> status, @Param("purchasingSupplier") List<String> purchasingSupplier,
                                               @Param("finalUserAuth") List<String> finalUserAuth, @Param("finalDeptAuth") List<String> finalDeptAuth,
                                               @Param("finalCustomerAuth") List<String> finalCustomerAuth, @Param("finalEmployeeAuth") List<String> finalEmployeeAuth,
                                               @Param("finalInCodeAuth") List<String> finalInCodeAuth
    );

    /**
     * 行业接单查询导出
     */
    @Select("<script>" +
            " select " +
            "<if test = 'condition.exportNum != null ' >" +
            "  TOP ${condition.exportNum} " +
            " </if> " +
            " * from (" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee] as empSale , " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo] as hddno , " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No] as saleDeptNo , " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no] , " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno] as orderNo , " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " rm.end_user != 'C1D7V' and " +
            // 权限
            "<if test = ' finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ( " +
            "</if>" +
            // 用户id(客户担当)权限
            "   <if test = 'finalEmployeeAuth.size() &gt; 0 ' > " +
            "       rm.Employee in " +
            "       <foreach collection = 'finalEmployeeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "         #{item}" +
            "       </foreach>" +
            "   <if test = 'finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            "    or " +
            "   </if>" +
            "   </if>" +
            // 部门权限
            " <if test = 'finalDeptAuth.size() &gt; 0 ' > " +
            "  c.HLCode in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item}" +
            "  </foreach>" +
            "  or " +
            "  c.HRUnitId in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item} " +
            "  </foreach>" +
            "   <if test = ' finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  ' > " +
            "    or " +
            "   </if>" +
            " </if> " +
            // 客户权限
            "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
            "       rm.end_user in " +
            "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   <if test = ' finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
            "      or " +
            "    </if>" +
            "   </if>" +
            // 行业权限
            "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or "+
            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or " +
            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
            "     rm.user_no in " +
            "     <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "     </foreach>" +
            "   </if>" +
            "<if test = 'finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ) " +
            "</if>" +
            "</trim> " +
            " union all " +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee] as empSale , " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo] as hddno , " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No] as saleDeptNo , " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no] , " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno] as orderNo , " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            " where rm.end_user = 'C1D7V'  " +
            " ) t " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            "  remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            "  stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            "  ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            "  QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            "  PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            "  EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            "  empSale = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            "  end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            " exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " ( exp_dlv_type &amp; 8192 != 8192 or exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            "  status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            "  order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            "  user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            "  customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            "  cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            "  model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            "  orderNo like #{condition.orderNo} and " +
            "</if> " +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "   ROrdDate &gt;= #{condition.orderDateStartStr}  and  ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            " order by ${condition.property}  ${condition.order}" +
            "</script>")
    @Options(timeout = 60)
    List<IndCodeEntity> exportInCode(@Param("condition") SMSSearchListInfo condition, @Param("types") List<String> types,
                                               @Param("status") List<String> status, @Param("purchasingSupplier") List<String> purchasingSupplier,
                                               @Param("finalUserAuth") List<String> finalUserAuth, @Param("finalDeptAuth") List<String> finalDeptAuth,
                                               @Param("finalCustomerAuth") List<String> finalCustomerAuth, @Param("finalEmployeeAuth") List<String> finalEmployeeAuth,
                                               @Param("finalInCodeAuth") List<String> finalInCodeAuth
    );

    /**
     * 代理店接单查询导出
     */
    @Select("<script>" +
            " select " +
            "<if test = 'condition.exportNum != null ' >" +
            "  TOP ${condition.exportNum} " +
            " </if> " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo] as hddno , " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No] as saleDeptNo , " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no] , " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno] as orderNo , " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            " from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            " inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            " rd.remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            " rd.stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            " rm.ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            " rm.QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            " rm.PurchaseNO = #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            " rm.EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            " rm.Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            " ( rm.end_user = #{condition.endUserNo} or rm.user_no = #{condition.endUserNo} or rm.customer_no = #{condition.endUserNo} ) and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            " rd.exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " ( exp_dlv_type &amp; 8192 != 8192 or exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " rd.intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            " rd.status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            " rd.order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " ( rm.user_no = #{condition.userNo} or rm.customer_no = #{condition.userNo} or rm.end_user = #{condition.userNo} ) and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " ( rm.customer_no = #{condition.customerNo} or rm.user_no = #{condition.customerNo} or rm.end_user = #{condition.customerNo} ) and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            " rd.cproduct_no = #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " rd.model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " rd.rorder_fno like #{condition.orderNo}  " +
            "   <if test = ' condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or ( condition.orderDateStart != null and condition.orderDateEnd != null ) ' > " +
            " and " +
            "   </if>" +
            "</if> " +
            // 权限
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            // 客户权限
            " <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
            "    rm.customer_no in " +
            "    <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "        #{item}" +
            "    </foreach>" +
            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' > " +
            " and " +
            "</if>" +
            " </if>" +
            "</if>" +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "  rm.ROrdDate &gt;= #{condition.orderDateStartStr}  and rm.ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>" +
            " order by ${condition.property}  ${condition.order}" +
            "</script>")
    @Options(timeout = 60)
    List<IndCodeEntity> exportInCodeForAgent(@Param("condition") SMSSearchListInfo condition,
                                                       @Param("types") List<String> types,
                                                       @Param("status") List<String> status,
                                                       @Param("purchasingSupplier") List<String> purchasingSupplier,
                                                       @Param("finalDeptNos") List<String> finalDeptNos);


    /**
     * 代理店接单查询
     */
    @Select("<script>" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No], " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            " rd.remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            " rd.stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            " rm.ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            " rm.QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            " rm.PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            " rm.EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            " rm.Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            " rm.end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            " rd.exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " (rd.exp_dlv_type &amp; 8192 != 8192 or rd.exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " rd.intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            " rd.status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            " rd.order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " rm.user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " rm.customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            " rd.cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " rd.model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " rd.rorder_fno like #{condition.orderNo}  " +
            "   <if test = ' condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or ( condition.orderDateStart != null and condition.orderDateEnd != null ) ' > " +
            " and " +
            "   </if>" +
            "</if> " +
            // 权限
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            // 客户权限
            " <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
            "    rm.customer_no in " +
            "    <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "        #{item}" +
            "    </foreach>" +
            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' > " +
            " and " +
            "</if>" +
            " </if>" +
            "</if>" +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "  rm.ROrdDate &gt;= #{condition.orderDateStartStr}  and rm.ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            " order by ${condition.property}  ${condition.order}" +
            "</script>")
    @Options(timeout = 60)
    List<RcvOrderWithCustomerForViewDO> queryOrderByAgent(@Param("condition") SMSSearchListInfo condition,
                                                               @Param("types") List<String> types,
                                                               @Param("status") List<String> status,
                                                               @Param("purchasingSupplier") List<String> purchasingSupplier);

    /**
     * 代理店行业接单导出
     */
    @Select("<script>" +
            " select " +
            "<if test = 'condition.exportNum != null ' >" +
            "  TOP ${condition.exportNum} " +
            " </if> " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No], " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            " rd.remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            " rd.stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            " rm.ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            " rm.QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            " rm.PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            " rm.EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            " rm.Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            " rm.end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            " rd.exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " (rd.exp_dlv_type &amp; 8192 != 8192 or rd.exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " rd.intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            " rd.status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            " rd.order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " rm.user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " rm.customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            " rd.cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " rd.model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " rd.rorder_fno like #{condition.orderNo}  " +
            "   <if test = ' condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or ( condition.orderDateStart != null and condition.orderDateEnd != null ) ' > " +
            " and " +
            "   </if>" +
            "</if> " +
            // 权限
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            // 客户权限
            " <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
            "    rm.customer_no in " +
            "    <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "        #{item}" +
            "    </foreach>" +
            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' > " +
            " and " +
            "</if>" +
            " </if>" +
            "</if>" +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "  rm.ROrdDate &gt;= #{condition.orderDateStartStr}  and rm.ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            " order by ${condition.property}  ${condition.order}" +
            "</script>")
    List<RcvOrderWithCustomerForViewDO> exportRcvOrderForAgent(@Param("condition") SMSSearchListInfo condition,
                                                       @Param("types") List<String> types,
                                                       @Param("status") List<String> status,
                                                       @Param("purchasingSupplier") List<String> purchasingSupplier);

    /**
     * 代理店行业接单查询
     */
    @Select("<script>" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[dlv_entire], " +
            "  rm.[DlvSite], " +
            "  rm.[TransFee], " +
            "  rm.[PrjCode], " +
            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[OptTime], " +
            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
            "  rm.[ContractNo] as hddno , " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rm.[dept_No] as saleDeptNo , " +
            "  rm.[delivery_dept_no], " +
            "  rm.[trade_companyId], " +
            "  rd.[id], " +
            "  rd.[rorder_no] , " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno] as orderNo , " +
            "  rd.[model_no], " +
            "  rd.[quantity], " +
            "  rd.[price], " +
            "  rd.[price_enduser], " +
            "  rd.[eprice], " +
            "  rd.[tax_rate], " +
            "  rd.[ntax_pice], " +
            "  rd.[ntax_amount], " +
            "  rd.[tax_amount], " +
            "  rd.[amount], " +
            "  rd.[discount], " +
            "  rd.[dlv_date], " +
            "  rd.[cdlv_date], " +
            "  rd.[wms_dlv_date], " +
            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[spec_mark], " +
            "  rd.[remark], " +
            "  rd.[product_name], " +
            "  rd.[opponent], " +
            "  rd.[ppl_no], " +
            "  rd.[project_no], " +
            "  rd.[shikomi_no], " +
            "  rd.[sales_info_no], " +
            "  rd.[pre_sales_order_no], " +
            "  rd.[corder_no], " +
            "  rd.[custom_code], " +
            "  rd.[order_type], " +
            "  rd.create_user , " +
            "  rd.[status], " +
            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
            "  rd.[stock_type], " +
            "  rd.[inventory_type_code], " +
            "  rd.[prod_flag], " +
            "  rd.[ready_time], " +
            "  rd.[exp_time], " +
            "  rd.[ship_time], " +
            "  rd.[ready_qty], " +
            "  rd.[exp_qty], " +
            "  rd.[returned_qty], " +
            "  rd.[address_no], " +
            "  rd.[exp_msg], " +
            "  rd.[invoice_qty], " +
            "  rd.[invoice_time], " +
            "  rd.[carrierId], " +
            "  rd.[expressNo], " +
            "  rd.[handover_time], " +
            "  rd.[update_time], " +
            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  rd.[intercept_time], " +
            "  rd.[expected_delivery_time], " +
            "  c.[name], " +
            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode] " +
            " from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            " inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            " rd.remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            " rd.stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            " rm.ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            " rm.QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            " rm.PurchaseNO = #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            " rm.EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            " rm.Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            " ( rm.end_user = #{condition.endUserNo} or rm.user_no = #{condition.endUserNo} or rm.customer_no = #{condition.endUserNo} ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " rd.intercept = #{condition.intercepter} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            " rd.exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " (rd.exp_dlv_type &amp; 8192 != 8192 or rd.exp_dlv_type is null ) and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            " rd.status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            " rd.order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " ( rm.user_no = #{condition.userNo} or rm.customer_no = #{condition.userNo} or rm.end_user = #{condition.userNo} ) and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " ( rm.customer_no = #{condition.customerNo} or rm.user_no = #{condition.customerNo} or rm.end_user = #{condition.customerNo} ) and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            " rd.cproduct_no = #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " rd.model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " rd.rorder_fno like #{condition.orderNo}  " +
            "   <if test = ' condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or ( condition.orderDateStart != null and condition.orderDateEnd != null ) ' > " +
            " and " +
            "   </if>" +
            "</if> " +
            // 权限
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            // 客户权限
            " <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
            "    rm.customer_no in " +
            "    <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "        #{item}" +
            "    </foreach>" +
            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' > " +
            " and " +
            "</if>" +
            " </if>" +
            "</if>" +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "  rm.ROrdDate &gt;= #{condition.orderDateStartStr}  and rm.ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>" +
            " order by ${condition.property}  ${condition.order}" +
            "</script>")
    @Options(timeout = 60)
    List<IndCodeEntity> queryRcvOrderByIndCodeForAgent(@Param("condition") SMSSearchListInfo condition,
                                                @Param("types") List<String> types,
                                                @Param("status") List<String> status,
                                                @Param("purchasingSupplier") List<String> purchasingSupplier,
                                                @Param("finalDeptNos") List<String> finalDeptNos);



    @Select("<script>" +
            "select * from (" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No], " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
            "  rd.[order_type], " +
//            "  rd.create_user , " +
            "  rd.[status], " +
//            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode], " +
            " isnull(rd.amount,0.00) as totalAmount , isnull(rd.ntax_amount,0.00) as totalNtaxAmount ,isnull(rd.eprice,0.00)*isnull(rd.quantity,0) as eDiscount " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " rm.end_user != 'C1D7V' and " +
            // 权限
            "<if test = ' finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ( " +
            "</if>" +
            // 用户id(客户担当)权限
            "   <if test = 'finalEmployeeAuth.size() &gt; 0 ' > " +
            "       rm.Employee in " +
            "       <foreach collection = 'finalEmployeeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "         #{item}" +
            "       </foreach>" +
            "   <if test = 'finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            "    or " +
            "   </if>" +
            "   </if>" +
            // 部门权限
            " <if test = 'finalDeptAuth.size() &gt; 0 ' > " +
            "  c.HLCode in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item}" +
            "  </foreach>" +
            "  or " +
            "  c.HRUnitId in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item} " +
            "  </foreach>" +
            "   <if test = ' finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  ' > " +
            "    or " +
            "   </if>" +
            " </if> " +
            // 客户权限
            "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
            "       rm.end_user in " +
            "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   <if test = ' finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
            "      or " +
            "    </if>" +
            "   </if>" +
            // 行业权限
            "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or "+
            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or " +
            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
            "     rm.user_no in " +
            "     <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "     </foreach>" +
            "   </if>" +
            "<if test = 'finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ) " +
            "</if>" +
            "</trim> " +
            " union all " +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No], " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
            "  rd.[order_type], " +
//            "  rd.create_user , " +
            "  rd.[status], " +
//            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode], " +
            " isnull(rd.amount,0.00) as totalAmount , isnull(rd.ntax_amount,0.00) as totalNtaxAmount ,isnull(rd.eprice,0.00)*isnull(rd.quantity,0) as eDiscount " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            " where rm.end_user = 'C1D7V'  " +
            " ) t " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            "  remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            "  stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            "  ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            "  QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            "  PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            "  EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            "  Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            "  end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            "  exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " ( exp_dlv_type &amp; 8192 != 8192 or exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            "  status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            "  order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            "  user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            "  customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            "  cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            "  model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            "  rorder_fno like #{condition.orderNo} and " +
            "</if> " +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "   ROrdDate &gt;= #{condition.orderDateStartStr}  and  ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            "</script>")
    @Options(timeout = 60)
    List<RcvOrderWithCustomerForViewDO> queryAmountAndEdiscount(@Param("condition") SMSSearchListInfo condition, @Param("types") List<String> types,
                                                @Param("status") List<String> status, @Param("purchasingSupplier") List<String> purchasingSupplier,
                                                @Param("finalUserAuth") List<String> finalUserAuth, @Param("finalDeptAuth") List<String> finalDeptAuth,
                                                @Param("finalCustomerAuth") List<String> finalCustomerAuth, @Param("finalEmployeeAuth") List<String> finalEmployeeAuth,
                                                @Param("finalInCodeAuth") List<String> finalInCodeAuth);

    @Select("<script>" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No], " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
            "  rd.[order_type], " +
//            "  rd.create_user , " +
            "  rd.[status], " +
//            "  rd.[delete_status], " +
            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode], " +
            " isnull(rd.amount,0.00) as totalAmount, isnull(rd.ntax_amount,0.00) as totalNtaxAmount ,isnull(rd.eprice,0.00)*isnull(rd.quantity,0) as eDiscount " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            " rd.remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            " rd.stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            " rm.ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            " rm.QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            " rm.PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            " rm.EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            " rm.Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            " rm.end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            " rd.exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " (rd.exp_dlv_type &amp; 8192 != 8192 or rd.exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " rd.intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            " rd.status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            " rd.order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " rm.user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " rm.customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            " rd.cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " rd.model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " rd.rorder_fno like #{condition.orderNo}  " +
            "   <if test = ' condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or ( condition.orderDateStart != null and condition.orderDateEnd != null ) ' > " +
            " and " +
            "   </if>" +
            "</if> " +
            // 权限
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            // 客户权限
            " <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
            "    rm.customer_no in " +
            "    <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "        #{item}" +
            "    </foreach>" +
            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' > " +
            " and " +
            "</if>" +
            " </if>" +
            "</if>" +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "  rm.ROrdDate &gt;= #{condition.orderDateStartStr}  and rm.ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            "</script>")
    @Options(timeout = 60)
    List<RcvOrderWithCustomerForViewDO> queryAmountAndEdiscountByAgent(@Param("condition") SMSSearchListInfo condition,
                                                          @Param("types") List<String> types,
                                                          @Param("status") List<String> status,
                                                          @Param("purchasingSupplier") List<String> purchasingSupplier);

    @Select("<script>" +
            " select  CAST(round(sum(totalAmount),2) AS decimal(18, 2)) as totalAmount,  CAST(round( sum(totalNtaxAmount) /sum(eDiscount) -1,3) AS decimal(18, 3)) as eDiscount  from ( " +
            "select * from (" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[ORorderNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[remark], " +
            "  rd.[order_type], " +
            "  rd.[status], " +
            "  rd.[stock_code], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode], " +
            " isnull(rd.amount,0.00) as totalAmount , isnull(rd.ntax_amount,0.00) as totalNtaxAmount ,isnull(rd.eprice,0.00)*isnull(rd.quantity,0) as eDiscount " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " rm.end_user != 'C1D7V' and " +
            // 权限
            "<if test = ' finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ( " +
            "</if>" +
            // 用户id(客户担当)权限
            "   <if test = 'finalEmployeeAuth.size() &gt; 0 ' > " +
            "       rm.Employee in " +
            "       <foreach collection = 'finalEmployeeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "         #{item}" +
            "       </foreach>" +
            "   <if test = 'finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            "    or " +
            "   </if>" +
            "   </if>" +
            // 部门权限
            " <if test = 'finalDeptAuth.size() &gt; 0 ' > " +
            "  c.HLCode in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item}" +
            "  </foreach>" +
            "  or " +
            "  c.HRUnitId in " +
            "  <foreach collection = 'finalDeptAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "  #{item} " +
            "  </foreach>" +
            "   <if test = ' finalCustomerAuth.size() &gt; 0 or finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0  ' > " +
            "    or " +
            "   </if>" +
            " </if> " +
            // 客户权限
            "   <if test = 'finalCustomerAuth.size() &gt; 0  '> " +
            "       rm.end_user in " +
            "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   <if test = ' finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
            "      or " +
            "    </if>" +
            "   </if>" +
            // 行业权限
            "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or "+
            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or " +
            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
            "     rm.user_no in " +
            "     <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "     </foreach>" +
            "   </if>" +
            "<if test = 'finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalEmployeeAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ) " +
            "</if>" +
            "</trim> " +
            " union all " +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[ORorderNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[remark], " +
            "  rd.[order_type], " +
            "  rd.[status], " +
            "  rd.[stock_code], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode], " +
            " isnull(rd.amount,0.00) as totalAmount , isnull(rd.ntax_amount,0.00) as totalNtaxAmount ,isnull(rd.eprice,0.00)*isnull(rd.quantity,0) as eDiscount " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no " +
            " where rm.end_user = 'C1D7V'  " +
            " ) t " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            "  remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            "  stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            "  ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            "  QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            "  PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            "  EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            "  Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            "  end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            "  exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " ( exp_dlv_type &amp; 8192 != 8192 or exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            "  status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            "  order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            "  user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            "  customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            "  cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            "  model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            "  rorder_fno like #{condition.orderNo} and " +
            "</if> " +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "   ROrdDate &gt;= #{condition.orderDateStartStr}  and  ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            ") a " +
            "</script>")
    @Options(timeout = 60)
    AmountAndEdiscountVO queryAmountAndEdiscount2(@Param("condition") SMSSearchListInfo condition, @Param("types") List<String> types,
                                                                @Param("status") List<String> status, @Param("purchasingSupplier") List<String> purchasingSupplier,
                                                                @Param("finalUserAuth") List<String> finalUserAuth, @Param("finalDeptAuth") List<String> finalDeptAuth,
                                                                @Param("finalCustomerAuth") List<String> finalCustomerAuth, @Param("finalEmployeeAuth") List<String> finalEmployeeAuth,
                                                                @Param("finalInCodeAuth") List<String> finalInCodeAuth);

    @Select("<script>" +
            " select  CAST(round(sum(totalAmount),2) AS decimal(10, 2)) as totalAmount,  CAST(round((sum(totalNtaxAmount)/(sum(eDiscount) - 1)),3) AS decimal(10, 3)) as eDiscount  from ( " +
            "select * from (" +
            " select " +
            "  rm.[customer_no], " +
            "  rm.[user_no], " +
            "  rm.[end_user], " +
            "  rm.[ROrdDate], " +
            "  rm.[Employee], " +
            "  rm.[EmployeeNo], " +
            "  rm.[ORorderNo], " +
            "  rm.[QuotationNo], " +
            "  rm.[PurchaseNO], " +
            "  rd.[rorder_no], " +
            "  rd.[rorder_item], " +
            "  rd.[rorder_fno], " +
            "  rd.[model_no], " +
            "  rd.[cproduct_no], " +
            "  rd.[remark], " +
            "  rd.[order_type], " +
            "  rd.[status], " +
            "  rd.[stock_code], " +
            "  rd.exp_dlv_type, " +
            "  rd.[intercept], " +
            "  c.[HRUnitId], " +
            "  c.[HLCode], " +
            " isnull(rd.amount,0.00) as totalAmount, isnull(rd.ntax_amount,0.00) as totalNtaxAmount ,isnull(rd.eprice,0.00)*isnull(rd.quantity,0) as eDiscount " +
            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
            "  inner join ops_customer c on rm.end_user = c.customer_no ) t " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            " remark like #{condition.remark} and " +
            "</if> " +
            // 采购供应商
            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            " stock_code in  " +
            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            " ORorderNo = #{condition.contractNo} and " +
            "</if> " +
            // 报价单号
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            " QuotationNo = #{condition.quotationNo} and " +
            "</if> " +
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            " PurchaseNO like #{condition.purchaseNo} and " +
            "</if> " +
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            " EmployeeNo = #{condition.createId} and  " +
            "</if> " +
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            " Employee = #{condition.empSale} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            " end_user = #{condition.endUserNo} and " +
            "</if> " +
            // 是否特发
            "<if test = ' condition.specFlag == 1 ' >" +
            " exp_dlv_type &amp; 8192=8192 and " +
            "</if> " +
            // 是否普通
            "<if test = ' condition.specFlag == 0 ' >" +
            " (exp_dlv_type &amp; 8192 != 8192 or exp_dlv_type is null ) and " +
            "</if> " +
            // 是否信用拦截
            "<if test = ' condition.intercepter != null ' >" +
            " intercept = #{condition.intercepter} and " +
            "</if> " +
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0 ' > " +
            " status in " +
            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            " order_type in " +
            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " user_no = #{condition.userNo} and " +
            "</if> " +
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            " cproduct_no like #{condition.custProductNo} and " +
            "</if> " +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " model_no like #{condition.modelNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " rorder_fno like #{condition.orderNo}  " +
            "   <if test = ' condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or ( condition.orderDateStart != null and condition.orderDateEnd != null ) ' > " +
            " and " +
            "   </if>" +
            "</if> " +
            // 权限
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            // 客户权限
            " <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
            "    customer_no in " +
            "    <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "        #{item}" +
            "    </foreach>" +
            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' > " +
            " and " +
            "</if>" +
            " </if>" +
            "</if>" +
            // 订货日期
            "<if test = ' condition.orderDateStartStr != null and condition.orderDateEndStr != null ' >" +
            "  ROrdDate &gt;= #{condition.orderDateStartStr}  and ROrdDate &lt;= #{condition.orderDateEndStr} " +
            "</if>" +
            "</trim>"+
            ") a " +
            "</script>")
    @Options(timeout = 60)
    AmountAndEdiscountVO queryAmountAndEdiscountByAgent2(@Param("condition") SMSSearchListInfo condition,
                                                                       @Param("types") List<String> types,
                                                                       @Param("status") List<String> status,
                                                                       @Param("purchasingSupplier") List<String> purchasingSupplier);

    @Select("<script>" +
            " select r.rorder_fno as orderFno ,r.rorder_no as orderNo,r.rorder_item as orderItem, r.quantity as qty ,r.model_no,r.eprice,r.price as unitPrice,m.purchaseNo,r.cproduct_no,r.stock_code as supplier ," +
            " m.customer_no,m.user_no as userNo,r.status , r.intercept, r.prod_flag , r.exp_qty " +
            " from rcvmaster m inner join rcvdetail r on m.rorder_no = r.rorder_no " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            "<if test='prodFlag == 0 '>" +
//            " r.prod_flag = 0 and  " +
//            "</if>"+
            "<if test = ' orderNoList != null and orderNoList.size() &gt; 0' >" +
            " r.rorder_fno in  " +
            "   <foreach collection = 'orderNoList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            "</if>" +
            "</trim>"+
            "</script>")
    List<OrderModifyUpInfoVO> findOrderInfoByOrderNos(@Param("orderNoList")List<String> orderNoList);

    /**
     * bug18359 2025-08-20 用三位单号查询订单拆分子项
     * @param orderId
     * @param orderItem
     * @param splitNo
     * @return
     */
    @Select(" select \n" +
            " s.qty,\n" +
            " s.modelno as modelNo,\n" +
            " m.purchaseNo,\n" +
            " r.cproduct_no,\n" +
            " '' as supplier ,\n" +
            " m.customer_no,\n" +
            " m.user_no as userNo,\n" +
            " r.status ,\n" +
            " r.intercept, \n" +
            " r.prod_flag \n" +
            "from rcvmaster m\n" +
            "inner join rcvdetail r on m.rorder_no = r.rorder_no \n" +
            "left join order_status s on s.order_id = r.rorder_no  and s.order_item = r.rorder_item\n" +
            "where s.order_id = #{orderId} and s.order_item = #{orderItem} and s.split_no = #{splitNo} ")
    List<OrderModifyUpInfoVO> findOrderInfoWithOrderStatusAssQty(@Param("orderId") String orderId,
                                                         @Param("orderItem") String orderItem,
                                                         @Param("splitNo") String splitNo );


    @Select(" select \n" +
            " s.qty,\n" +
            " s.modelno as modelNo,\n" +
            " m.purchaseNo,\n" +
            " r.cproduct_no,\n" +
            " '' as supplier ,\n" +
            " m.customer_no,\n" +
            " m.user_no as userNo,\n" +
            " r.status ,\n" +
            " r.intercept, \n" +
            " r.prod_flag \n" +
            "from rcvmaster m\n" +
            "inner join rcvdetail r on m.rorder_no = r.rorder_no \n" +
            "left join order_status s on s.order_id = r.rorder_no  and s.order_item = r.rorder_item\n" +
            "where s.order_id = #{orderId} and s.order_item = #{orderItem} and s.pco_item = #{splitNo} ")
    List<OrderModifyUpInfoVO> findOrderInfoWithOrderStatusAssModel(@Param("orderId") String orderId,
                                                         @Param("orderItem") String orderItem,
                                                         @Param("splitNo") String splitNo );

    @Select("<script>" +
           " select r.rorder_fno as orderFno ,r.quantity as qty ,r.model_no,r.eprice,r.price as unitPrice,m.purchaseNo,r.cproduct_no,r.stock_code as supplier ,m.customer_no,m.user_no as userNo ,r.status,p.stateCode " +
            "from ops_purchaseOrder p " +
            "left join rcvdetail r on  p.orderNo = r.rorder_no and p.itemNo = r.rorder_item " +
            "left join rcvmaster m  on m.rorder_no = r.rorder_no  " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            "<if test = ' orderNoList != null and orderNoList.size() &gt; 0' >" +
            " r.rorder_fno in  " +
            "   <foreach collection = 'orderNoList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            "</if>" +
            "</trim>"+
            "</script>")
    List<OrderModifyUpInfoVO> findPurOrderInfoByOrderNos(@Param("orderNoList")List<String> orderNoList);


    // 信用拦截信息查询
    @Select("<script>" +
            "select r.rorder_fno,r.model_no,r.quantity,r.amount,r.dlv_date,r.status, r.intercept\n" +
            "from rcvdetail r left join rcvmaster m on r.rorder_no = m.rorder_no\n" +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            "<if test = 'info.customerNo != null and info.customerNo != \"\" '>" +
            " m.customer_no = #{info.customerNo} and r.status in ('2','3','4','5','6') and " +
            "</if>" +
            "<if test = 'info.orderNo != null and info.orderNo != \"\" '>" +
            " r.rorder_fno = #{info.orderNo} and  " +
            "</if>" +
            "<if test = 'info.modelNo != null and info.modelNo != \"\" '>" +
            " r.model_no = #{info.modelNo} "  +
            "</if>" +
            "</trim>" +
            "</script> ")
    List<QueryOrderIntercepterVO> getOrderIntercpterInfo(@Param("info") OrderIntercepterInfoVO infoVO);



    /**
     * 接单查询
     */
//    @Select("<script>" +
//            "select distinct * from (" +
//            " select " +
//            "  rm.[customer_no], " +
//            "  rm.[user_no], " +
//            "  rm.[end_user], " +
//            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
//            "  rm.[Employee], " +
//            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
//            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo], " +
//            "  rm.[QuotationNo], " +
//            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No], " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
//            "  rd.[rorder_no], " +
//            "  rd.[rorder_item], " +
//            "  rd.[rorder_fno], " +
//            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
//            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
//            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
//            "  rd.[order_type], " +
//            "  rd.create_user , " +
//            "  rd.[status], " +
//            "  rd.[delete_status], " +
//            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
//            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
//            "  c.[HRUnitId], " +
//            "  c.[HLCode] " +
//            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
//            "  inner join ops_customer c on rm.end_user = c.customer_no " +
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 备注
//            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
//            " rd.remark like #{condition.remark} and " +
//            "</if> " +
//            // 采购供应商
//            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
//            " rd.stock_code in  " +
//            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 合同号
//            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
//            " rm.ORorderNo = #{condition.contractNo} and " +
//            "</if> " +
//            // 报价单号
//            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
//            " rm.QuotationNo = #{condition.quotationNo} and " +
//            "</if> " +
//            // 客户po号
//            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
//            " rm.PurchaseNO like #{condition.purchaseNo} and " +
//            "</if> " +
//            // 制单担当
//            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
//            " rd.create_user = #{condition.createId} and  " +
//            "</if> " +
//            // 客户担当
//            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
//            " rm.Employee = #{condition.empSale} and " +
//            "</if> " +
//            // 最终用户
//            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
//            " rm.end_user = #{condition.endUserNo} and " +
//            "</if> " +
//            // 订单状态
//            "<if test = ' status != null and status.size() &gt; 0 ' > " +
//            " rd.status in " +
//            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 订单类型
//            "<if test = 'types != null and types.size() &gt; 0  '> " +
//            " rd.order_type in " +
//            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 用户编码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " rm.user_no = #{condition.userNo} and " +
//            "</if> " +
//            // 客户编码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " rm.customer_no = #{condition.customerNo} and " +
//            "</if> " +
//            // 物料号
//            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
//            " rd.cproduct_no like #{condition.custProductNo} and " +
//            "</if> " +
//            // 型号
//            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
//            " rd.model_no like #{condition.modelNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " rd.rorder_fno like #{condition.orderNo}  " +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 " +
//            "or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 or ( condition.orderDateStart != null and condition.orderDateEnd != null ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if> " +
//            // 权限
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            " ( " +
//            "   </if>" +
//            // 用户id(客户担当)权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 ' > " +
//            "       rm.Employee in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 and ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 )' > " +
//            "       or " +
//            "   </if>" +
//            // 部门权限
//            " <if test = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 ' > " +
//            "  c.HLCode in " +
//            "  <foreach collection = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "  #{item}" +
//            "  </foreach>" +
//            "  or " +
//            "  c.HRUnitId in " +
//            "  <foreach collection = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "  #{item} " +
//            "  </foreach>" +
//            " </if> " +
//            "   <if test = ' condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0  and ( condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) ' > " +
//            "    or " +
//            "   </if>" +
//            // 客户权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
//            "       rm.end_user in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 and  (condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) '> " +
//            "      or " +
//            "    </if>" +
//            // 行业权限
//            "<if test = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' >" +
//            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or "+
//            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or " +
//            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            "<if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0  '> " +
//            " or " +
//            "</if>" +
//            "</if>" +
//            // 用户权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0  '>" +
//            "     rm.user_no in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 " +
//            "   or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            "       ) " +
//            " <if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            " and " +
//            " </if>"+
//            "   </if>" +
//            "</if>" +
//            // 订货日期
//            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            "  rm.ROrdDate &gt;= #{condition.orderDateStart}  and rm.ROrdDate &lt;= #{condition.orderDateEnd} " +
//            "</if>" +
//            "</trim>"+
//            "<if test = ' condition.agentSearch != null and condition.agentSearch != \"\" and condition.agentSearch != \"1\" ' >" +
//            " union all " +
//            " select " +
//            "  rm.[customer_no], " +
//            "  rm.[user_no], " +
//            "  rm.[end_user], " +
//            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
//            "  rm.[Employee], " +
//            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
//            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo], " +
//            "  rm.[QuotationNo], " +
//            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No], " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
//            "  rd.[rorder_no], " +
//            "  rd.[rorder_item], " +
//            "  rd.[rorder_fno], " +
//            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
//            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
//            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
//            "  rd.[order_type], " +
//            "  rd.create_user , " +
//            "  rd.[status], " +
//            "  rd.[delete_status], " +
//            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
//            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
//            "  c.[HRUnitId], " +
//            "  c.[HLCode] " +
//            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
//            "  inner join ops_customer c on rm.end_user = c.customer_no " +
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 备注
//            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
//            " rd.remark like #{condition.remark} and " +
//            "</if> " +
//            // 采购供应商
//            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
//            " rd.stock_code in  " +
//            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 合同号
//            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
//            " rm.ORorderNo = #{condition.contractNo} and " +
//            "</if> " +
//            // 报价单号
//            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
//            " rm.QuotationNo = #{condition.quotationNo} and " +
//            "</if> " +
//            // 客户po号
//            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
//            " rm.PurchaseNO like #{condition.purchaseNo} and " +
//            "</if> " +
//            // 制单担当
//            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
//            " rd.create_user = #{condition.createId} and  " +
//            "</if> " +
//            // 客户担当
//            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
//            " rm.Employee = #{condition.empSale} and " +
//            "</if> " +
//            // 最终用户
//            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
//            " rm.end_user = #{condition.endUserNo} and " +
//            "</if> " +
//            "<if test = 'condition.endUserNo == null or condition.endUserNo == \"\" ' >" +
//            " rm.end_user = 'C1D7V' and  " +
//            "</if> " +
//            // 订单状态
//            "<if test = ' status != null and status.size() &gt; 0 ' > " +
//            " rd.status in " +
//            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 订单类型
//            "<if test = 'types != null and types.size() &gt; 0  '> " +
//            " rd.order_type in " +
//            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 用户编码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " rm.user_no = #{condition.userNo} and " +
//            "</if> " +
//            // 客户编码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " rm.customer_no = #{condition.customerNo} and " +
//            "</if> " +
//            // 物料号
//            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
//            " rd.cproduct_no like #{condition.custProductNo} and " +
//            "</if> " +
//            // 型号
//            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
//            " rd.model_no like #{condition.modelNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " rd.rorder_fno like #{condition.orderNo}  and " +
//            "</if> " +
//            // 订货日期
//            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            "  rm.ROrdDate &gt;= #{condition.orderDateStart}  and rm.ROrdDate &lt;= #{condition.orderDateEnd} " +
//            "</if>" +
//            "</trim>"+
//            "</if>"+
//            " ) rd " +
//            " order by ${condition.property}  ${condition.order}" +
//            "</script>")
//    @Options(timeout = 60)
//    List<RcvOrderWithCustomerForViewDO> queryOrderWithCustomer(@Param("condition") SMSSearchListInfo condition,
//                                                               @Param("types") List<String> types,
//                                                               @Param("status") List<String> status,
//                                                               @Param("purchasingSupplier") List<String> purchasingSupplier);

    /**
     * 接单查询导出
     */
//    @Select("<script>" +
//            "select distinct " +
//            "<if test = 'condition.exportNum != null ' >" +
//            "  TOP ${condition.exportNum} " +
//            " </if> " +
//            " * from (" +
//            " select " +
//            "  rm.[customer_no], " +
//            "  rm.[user_no], " +
//            "  rm.[end_user], " +
//            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
//            "  rm.[Employee], " +
//            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
//            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo], " +
//            "  rm.[QuotationNo], " +
//            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No], " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
//            "  rd.[rorder_no], " +
//            "  rd.[rorder_item], " +
//            "  rd.[rorder_fno], " +
//            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
//            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
//            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
//            "  rd.[order_type], " +
//            "  rd.create_user , " +
//            "  rd.[status], " +
//            "  rd.[delete_status], " +
//            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
//            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
//            "  c.[HRUnitId], " +
//            "  c.[HLCode] " +
//            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
//            "  inner join ops_customer c on rm.end_user = c.customer_no " +
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 备注
//            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
//            " rd.remark like #{condition.remark} and " +
//            "</if> " +
//            // 采购供应商
//            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
//            " rd.stock_code in  " +
//            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 合同号
//            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
//            " rm.ORorderNo = #{condition.contractNo} and " +
//            "</if> " +
//            // 报价单号
//            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
//            " rm.QuotationNo = #{condition.quotationNo} and " +
//            "</if> " +
//            // 客户po号
//            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
//            " rm.PurchaseNO like #{condition.purchaseNo} and " +
//            "</if> " +
//            // 制单担当
//            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
//            " rd.create_user = #{condition.createId} and  " +
//            "</if> " +
//            // 客户担当
//            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
//            " rm.Employee = #{condition.empSale} and " +
//            "</if> " +
//            // 最终用户
//            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
//            " rm.end_user = #{condition.endUserNo} and " +
//            "</if> " +
//            // 订单状态
//            "<if test = ' status != null and status.size() &gt; 0 ' > " +
//            " rd.status in " +
//            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 订单类型
//            "<if test = 'types != null and types.size() &gt; 0  '> " +
//            " rd.order_type in " +
//            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 用户编码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " rm.user_no = #{condition.userNo} and " +
//            "</if> " +
//            // 客户编码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " rm.customer_no = #{condition.customerNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " rd.rorder_fno like #{condition.orderNo} and " +
//            "</if> " +
//            // 物料号
//            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
//            " rd.cproduct_no like #{condition.custProductNo} and " +
//            "</if> " +
//            // 型号
//            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
//            " rd.model_no like #{condition.modelNo} and " +
//            "</if> " +
//            // 权限
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            " ( " +
//            "   </if>" +
//            // 用户id(客户担当)权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 ' > " +
//            "       rm.Employee in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 and ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 )' > " +
//            "       or " +
//            "   </if>" +
//            // 部门权限
//            " <if test = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 ' > " +
//            "  c.HLCode in " +
//            "  <foreach collection = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "  #{item}" +
//            "  </foreach>" +
//            "  or " +
//            "  c.HRUnitId in " +
//            "  <foreach collection = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "  #{item} " +
//            "  </foreach>" +
//            " </if> " +
//            "   <if test = ' condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0  and ( condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) ' > " +
//            "    or " +
//            "   </if>" +
//            // 客户权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
//            "       rm.end_user in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 and  (condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) '> " +
//            "      or " +
//            "    </if>" +
//            // 行业权限
//            "<if test = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' >" +
//            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or "+
//            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or " +
//            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            "<if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0  '> " +
//            " or " +
//            "</if>" +
//            "</if>" +
//            // 用户权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0  '>" +
//            "     rm.user_no in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 " +
//            "   or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            "       ) " +
//            " <if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            " and " +
//            " </if>"+
//            "   </if>" +
//            "</if>" +
//            // 订货日期
//            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            "  rm.ROrdDate &gt;= #{condition.orderDateStart}  and rm.ROrdDate &lt;= #{condition.orderDateEnd} " +
//            "</if>" +
//            "</trim>" +
//            "<if test = ' condition.agentSearch != null and condition.agentSearch != \"\" and condition.agentSearch != \"1\" ' >" +
//            " union all " +
//            " select " +
//            "  rm.[customer_no], " +
//            "  rm.[user_no], " +
//            "  rm.[end_user], " +
//            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
//            "  rm.[Employee], " +
//            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
//            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo], " +
//            "  rm.[QuotationNo], " +
//            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No], " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
//            "  rd.[rorder_no], " +
//            "  rd.[rorder_item], " +
//            "  rd.[rorder_fno], " +
//            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
//            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
//            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
//            "  rd.[order_type], " +
//            "  rd.create_user , " +
//            "  rd.[status], " +
//            "  rd.[delete_status], " +
//            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
//            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
//            "  c.[HRUnitId], " +
//            "  c.[HLCode] " +
//            "  from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
//            "  inner join ops_customer c on rm.end_user = c.customer_no " +
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 备注
//            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
//            " rd.remark like #{condition.remark} and " +
//            "</if> " +
//            // 采购供应商
//            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
//            " rd.stock_code in  " +
//            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 合同号
//            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
//            " rm.ORorderNo = #{condition.contractNo} and " +
//            "</if> " +
//            // 报价单号
//            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
//            " rm.QuotationNo = #{condition.quotationNo} and " +
//            "</if> " +
//            // 客户po号
//            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
//            " rm.PurchaseNO like #{condition.purchaseNo} and " +
//            "</if> " +
//            // 制单担当
//            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
//            " rd.create_user = #{condition.createId} and  " +
//            "</if> " +
//            // 客户担当
//            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
//            " rm.Employee = #{condition.empSale} and " +
//            "</if> " +
//            // 最终用户
//            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
//            " rm.end_user = #{condition.endUserNo} and " +
//            "</if> " +
//            "<if test = 'condition.endUserNo == null or condition.endUserNo == \"\" ' >" +
//            " rm.end_user = 'C1D7V' and  " +
//            "</if> " +
//            // 订单状态
//            "<if test = ' status != null and status.size() &gt; 0 ' > " +
//            " rd.status in " +
//            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 订单类型
//            "<if test = 'types != null and types.size() &gt; 0  '> " +
//            " rd.order_type in " +
//            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 用户编码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " rm.user_no = #{condition.userNo} and " +
//            "</if> " +
//            // 客户编码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " rm.customer_no = #{condition.customerNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " rd.rorder_fno like #{condition.orderNo} and " +
//            "</if> " +
//            // 物料号
//            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
//            " rd.cproduct_no like #{condition.custProductNo} and " +
//            "</if> " +
//            // 型号
//            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
//            " rd.model_no like #{condition.modelNo} and " +
//            "</if> " +
//            // 订货日期
//            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            "  rm.ROrdDate &gt;= #{condition.orderDateStart}  and rm.ROrdDate &lt;= #{condition.orderDateEnd} " +
//            "</if>" +
//            "</trim>" +
//            "</if>" +
//            " ) rd " +
//            " order by ${condition.property}  ${condition.order}" +
//            "</script>")
//    @Options(timeout = 60)
//    List<RcvOrderWithCustomerForViewDO> exportRcvOrder(@Param("condition") SMSSearchListInfo condition,
//                                                       @Param("types") List<String> types,
//                                                       @Param("status") List<String> status,
//                                                       @Param("purchasingSupplier") List<String> purchasingSupplier);

    /**
     * 行业接单查询
     */
//    @Select("<script>" +
//            "select distinct * from ( " +
//            " select " +
//            "  rm.[customer_no], " +
//            "  rm.[user_no], " +
//            "  rm.[end_user], " +
//            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
//            "  rm.[Employee], " +
//            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
//            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo] as hddno , " +
//            "  rm.[QuotationNo], " +
//            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No] as saleDeptNo , " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
//            "  rd.[rorder_no] , " +
//            "  rd.[rorder_item], " +
//            "  rd.[rorder_fno] as orderNo , " +
//            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
//            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
//            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
//            "  rd.[order_type], " +
//            "  rd.create_user , " +
//            "  rd.[status], " +
//            "  rd.[delete_status], " +
//            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
//            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
//            "  c.[HRUnitId], " +
//            "  c.[HLCode] " +
//            " from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
//            " inner join ops_customer c on rm.end_user = c.customer_no " +
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 备注
//            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
//            " rd.remark like #{condition.remark} and " +
//            "</if> " +
//            // 采购供应商
//            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
//            " rd.stock_code in  " +
//            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 合同号
//            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
//            " rm.ORorderNo = #{condition.contractNo} and " +
//            "</if> " +
//            // 报价单号
//            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
//            " rm.QuotationNo = #{condition.quotationNo} and " +
//            "</if> " +
//            // 客户po号
//            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
//            " rm.PurchaseNO = #{condition.purchaseNo} and " +
//            "</if> " +
//            // 制单担当
//            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
//            " rd.create_user = #{condition.createId} and  " +
//            "</if> " +
//            // 客户担当
//            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
//            " rm.Employee = #{condition.empSale} and " +
//            "</if> " +
//            // 最终用户
//            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
//            " ( rm.end_user = #{condition.endUserNo} or rm.user_no = #{condition.endUserNo} or rm.customer_no = #{condition.endUserNo} ) and " +
//            "</if> " +
//            // 订单状态
//            "<if test = ' status != null and status.size() &gt; 0 ' > " +
//            " rd.status in " +
//            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 订单类型
//            "<if test = 'types != null and types.size() &gt; 0  '> " +
//            " rd.order_type in " +
//            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 用户编码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " ( rm.user_no = #{condition.userNo} or rm.customer_no = #{condition.userNo} or rm.end_user = #{condition.userNo} ) and " +
//            "</if> " +
//            // 客户编码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " ( rm.customer_no = #{condition.customerNo} or rm.user_no = #{condition.customerNo} or rm.end_user = #{condition.customerNo} ) and " +
//            "</if> " +
//            // 物料号
//            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
//            " rd.cproduct_no = #{condition.custProductNo} and " +
//            "</if> " +
//            // 型号
//            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
//            " rd.model_no like #{condition.modelNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " rd.rorder_fno like #{condition.orderNo}  " +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 " +
//            "or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 or ( condition.orderDateStart != null and condition.orderDateEnd != null ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if> " +
//            // 权限
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 " +
//            "   or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            " ( " +
//            "   </if>" +
//            // 用户id(客户担当)权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 ' > " +
//            "       rm.Employee in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 and ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) ' > " +
//            "       or " +
//            "   </if>" +
//            // 部门权限
//            " <if test = 'finalDeptNos != null and finalDeptNos.size() &gt; 0 ' > " +
//            "  c.HLCode in " +
//            "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "  #{item}" +
//            "  </foreach>" +
//            "  or " +
//            "  c.HRUnitId in " +
//            "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "  #{item} " +
//            "  </foreach>" +
//            " </if> " +
//            "   <if test = ' finalDeptNos.size() &gt; 0  and ( condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) ' > " +
//            "    or " +
//            "   </if>" +
//            // 客户权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
//            "       rm.end_user in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 and ( condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) '> " +
//            "      or " +
//            "    </if>" +
//            // 行业权限
//            "<if test = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' >" +
//            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or "+
//            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or " +
//            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            "<if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0  '> " +
//            " or " +
//            "</if>" +
//            "</if>" +
//            // 用户权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0  '>" +
//            "     rm.user_no in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 " +
//            "   or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0  ' > " +
//            "       ) " +
//            " <if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            " and " +
//            " </if>"+
//            " </if>" +
//            "</if>" +
//            // 订货日期
//            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            "  rm.ROrdDate &gt;= #{condition.orderDateStart}  and rm.ROrdDate &lt;= #{condition.orderDateEnd} " +
//            "</if>" +
//            "</trim>" +
//            "<if test = ' condition.agentSearch != null and condition.agentSearch != \"\" and condition.agentSearch != \"1\" ' >" +
//            " union all " +
//            " select " +
//            "  rm.[customer_no], " +
//            "  rm.[user_no], " +
//            "  rm.[end_user], " +
//            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
//            "  rm.[Employee] as empSale , " +
//            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
//            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo] as hddno , " +
//            "  rm.[QuotationNo], " +
//            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No] as saleDeptNo , " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
//            "  rd.[rorder_no] , " +
//            "  rd.[rorder_item], " +
//            "  rd.[rorder_fno] as orderNo , " +
//            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
//            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
//            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
//            "  rd.[order_type], " +
//            "  rd.create_user , " +
//            "  rd.[status], " +
//            "  rd.[delete_status], " +
//            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
//            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
//            "  c.[HRUnitId], " +
//            "  c.[HLCode] " +
//            " from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
//            " inner join ops_customer c on rm.end_user = c.customer_no " +
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 备注
//            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
//            " rd.remark like #{condition.remark} and " +
//            "</if> " +
//            // 采购供应商
//            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
//            " rd.stock_code in  " +
//            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 合同号
//            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
//            " rm.ORorderNo = #{condition.contractNo} and " +
//            "</if> " +
//            // 报价单号
//            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
//            " rm.QuotationNo = #{condition.quotationNo} and " +
//            "</if> " +
//            // 客户po号
//            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
//            " rm.PurchaseNO = #{condition.purchaseNo} and " +
//            "</if> " +
//            // 制单担当
//            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
//            " rd.create_user = #{condition.createId} and  " +
//            "</if> " +
//            // 客户担当
//            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
//            " rm.Employee = #{condition.empSale} and " +
//            "</if> " +
//            // 最终用户
//            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
//            " ( rm.end_user = #{condition.endUserNo} or rm.user_no = #{condition.endUserNo} or rm.customer_no = #{condition.endUserNo} ) and " +
//            "</if> " +
//            // 订单状态
//            "<if test = ' status != null and status.size() &gt; 0 ' > " +
//            " rd.status in " +
//            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 订单类型
//            "<if test = 'types != null and types.size() &gt; 0  '> " +
//            " rd.order_type in " +
//            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 用户编码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " ( rm.user_no = #{condition.userNo} or rm.customer_no = #{condition.userNo} or rm.end_user = #{condition.userNo} ) and " +
//            "</if> " +
//            // 客户编码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " ( rm.customer_no = #{condition.customerNo} or rm.user_no = #{condition.customerNo} or rm.end_user = #{condition.customerNo} ) and " +
//            "</if> " +
//            // 物料号
//            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
//            " rd.cproduct_no = #{condition.custProductNo} and " +
//            "</if> " +
//            // 型号
//            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
//            " rd.model_no like #{condition.modelNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " rd.rorder_fno like #{condition.orderNo} and " +
//            "</if> " +
//            // 放开样品查询权限
//            "<if test = 'condition.endUserNo == null or condition.endUserNo == \"\" ' >" +
//            " rm.end_user = 'C1D7V' and  " +
//            "</if> " +
//            // 订货日期
//            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            "  rm.ROrdDate &gt;= #{condition.orderDateStart}  and rm.ROrdDate &lt;= #{condition.orderDateEnd} " +
//            "</if>" +
//            "</trim>" +
//            "</if>" +
//            " ) rd "+
//            " order by ${condition.property}  ${condition.order}" +
//            "</script>")
//    @Options(timeout = 60)
//    List<IndCodeEntity> queryRcvOrderByIndCode2(@Param("condition") SMSSearchListInfo condition,
//                                                @Param("types") List<String> types,
//                                                @Param("status") List<String> status,
//                                                @Param("purchasingSupplier") List<String> purchasingSupplier,
//                                                @Param("finalDeptNos") List<String> finalDeptNos);

    /**
     * 行业接单查询导出
     */
//    @Select("<script>" +
//            "select distinct " +
//            "<if test = 'condition.exportNum != null ' >" +
//            "  TOP ${condition.exportNum} " +
//            " </if> " +
//            " * from (" +
//            " select " +
//            "  rm.[customer_no], " +
//            "  rm.[user_no], " +
//            "  rm.[end_user], " +
//            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
//            "  rm.[Employee], " +
//            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
//            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo] as hddno , " +
//            "  rm.[QuotationNo], " +
//            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No] as saleDeptNo , " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
//            "  rd.[rorder_no] , " +
//            "  rd.[rorder_item], " +
//            "  rd.[rorder_fno] as orderNo , " +
//            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
//            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
//            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
//            "  rd.[order_type], " +
//            "  rd.create_user , " +
//            "  rd.[status], " +
//            "  rd.[delete_status], " +
//            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
//            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
//            "  c.[HRUnitId], " +
//            "  c.[HLCode] " +
//            " from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
//            " inner join ops_customer c on rm.end_user = c.customer_no " +
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 备注
//            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
//            " rd.remark like #{condition.remark} and " +
//            "</if> " +
//            // 采购供应商
//            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
//            " rd.stock_code in  " +
//            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 合同号
//            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
//            " rm.ORorderNo = #{condition.contractNo} and " +
//            "</if> " +
//            // 报价单号
//            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
//            " rm.QuotationNo = #{condition.quotationNo} and " +
//            "</if> " +
//            // 客户po号
//            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
//            " rm.PurchaseNO = #{condition.purchaseNo} and " +
//            "</if> " +
//            // 制单担当
//            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
//            " rd.create_user = #{condition.createId} and  " +
//            "</if> " +
//            // 客户担当
//            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
//            " rm.Employee = #{condition.empSale} and " +
//            "</if> " +
//            // 最终用户
//            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
//            " ( rm.end_user = #{condition.endUserNo} or rm.user_no = #{condition.endUserNo} or rm.customer_no = #{condition.endUserNo} ) and " +
//            "</if> " +
//            // 订单状态
//            "<if test = ' status != null and status.size() &gt; 0 ' > " +
//            " rd.status in " +
//            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 订单类型
//            "<if test = 'types != null and types.size() &gt; 0  '> " +
//            " rd.order_type in " +
//            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 用户编码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " ( rm.user_no = #{condition.userNo} or rm.customer_no = #{condition.userNo} or rm.end_user = #{condition.userNo} ) and " +
//            "</if> " +
//            // 客户编码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " ( rm.customer_no = #{condition.customerNo} or rm.user_no = #{condition.customerNo} or rm.end_user = #{condition.customerNo} ) and " +
//            "</if> " +
//            // 物料号
//            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
//            " rd.cproduct_no = #{condition.custProductNo} and " +
//            "</if> " +
//            // 型号
//            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
//            " rd.model_no like #{condition.modelNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " rd.rorder_fno like #{condition.orderNo}  " +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 " +
//            "or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 or ( condition.orderDateStart != null and condition.orderDateEnd != null ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if> " +
//            // 权限
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 " +
//            "   or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            " ( " +
//            "   </if>" +
//            // 用户id(客户担当)权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 ' > " +
//            "       rm.Employee in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 and ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) ' > " +
//            "       or " +
//            "   </if>" +
//            // 部门权限
//            " <if test = 'finalDeptNos != null and finalDeptNos.size() &gt; 0 ' > " +
//            "  c.HLCode in " +
//            "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "  #{item}" +
//            "  </foreach>" +
//            "  or " +
//            "  c.HRUnitId in " +
//            "  <foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "  #{item} " +
//            "  </foreach>" +
//            " </if> " +
//            "   <if test = ' finalDeptNos.size() &gt; 0  and ( condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) ' > " +
//            "    or " +
//            "   </if>" +
//            // 客户权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
//            "       rm.end_user in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 and ( condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) '> " +
//            "      or " +
//            "    </if>" +
//            // 行业权限
//            "<if test = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' >" +
//            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or "+
//            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or " +
//            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            "<if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0  '> " +
//            " or " +
//            "</if>" +
//            "</if>" +
//            // 用户权限
//            "   <if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0  '>" +
//            "     rm.user_no in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 " +
//            "   or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0  ' > " +
//            "       ) " +
//            " <if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            " and " +
//            " </if>"+
//            " </if>" +
//            "</if>" +
//            // 订货日期
//            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            "  rm.ROrdDate &gt;= #{condition.orderDateStart}  and rm.ROrdDate &lt;= #{condition.orderDateEnd} " +
//            "</if>" +
//            "</trim>" +
//            "<if test = ' condition.agentSearch != null and condition.agentSearch != \"\" and condition.agentSearch != \"1\" ' >" +
//            " union all " +
//            " select " +
//            "<if test = 'condition.exportNum != null ' >" +
//            "  TOP ${condition.exportNum}  " +
//            " </if> " +
//            "  rm.[customer_no], " +
//            "  rm.[user_no], " +
//            "  rm.[end_user], " +
//            "  rm.[ROrdDate], " +
//            "  rm.[dlv_entire], " +
//            "  rm.[DlvSite], " +
//            "  rm.[TransFee], " +
//            "  rm.[PrjCode], " +
//            "  rm.[DlvType], " +
//            "  rm.[Employee], " +
//            "  rm.[EmployeeNo], " +
//            "  rm.[OptTime], " +
//            "  rm.[Operator], " +
//            "  rm.[ORorderNo], " +
//            "  rm.[ContractNo] as hddno , " +
//            "  rm.[QuotationNo], " +
//            "  rm.[PurchaseNO], " +
//            "  rm.[dept_No] as saleDeptNo , " +
//            "  rm.[delivery_dept_no], " +
//            "  rm.[trade_companyId], " +
//            "  rd.[id], " +
//            "  rd.[rorder_no] , " +
//            "  rd.[rorder_item], " +
//            "  rd.[rorder_fno] as orderNo , " +
//            "  rd.[model_no], " +
//            "  rd.[quantity], " +
//            "  rd.[price], " +
//            "  rd.[price_enduser], " +
//            "  rd.[eprice], " +
//            "  rd.[tax_rate], " +
//            "  rd.[ntax_pice], " +
//            "  rd.[ntax_amount], " +
//            "  rd.[tax_amount], " +
//            "  rd.[amount], " +
//            "  rd.[discount], " +
//            "  rd.[dlv_date], " +
//            "  rd.[cdlv_date], " +
//            "  rd.[wms_dlv_date], " +
//            "  rd.[spec_offer_no], " +
//            "  rd.[cproduct_no], " +
//            "  rd.[spec_mark], " +
//            "  rd.[remark], " +
//            "  rd.[product_name], " +
//            "  rd.[opponent], " +
//            "  rd.[ppl_no], " +
//            "  rd.[project_no], " +
//            "  rd.[shikomi_no], " +
//            "  rd.[sales_info_no], " +
//            "  rd.[pre_sales_order_no], " +
//            "  rd.[corder_no], " +
//            "  rd.[custom_code], " +
//            "  rd.[order_type], " +
//            "  rd.create_user , " +
//            "  rd.[status], " +
//            "  rd.[delete_status], " +
//            "  rd.[stock_code], " +
//            "  rd.[stock_type], " +
//            "  rd.[inventory_type_code], " +
//            "  rd.[prod_flag], " +
//            "  rd.[ready_time], " +
//            "  rd.[exp_time], " +
//            "  rd.[ship_time], " +
//            "  rd.[ready_qty], " +
//            "  rd.[exp_qty], " +
//            "  rd.[returned_qty], " +
//            "  rd.[address_no], " +
//            "  rd.[exp_msg], " +
//            "  rd.[invoice_qty], " +
//            "  rd.[invoice_time], " +
//            "  rd.[carrierId], " +
//            "  rd.[expressNo], " +
//            "  rd.[handover_time], " +
//            "  rd.[update_time], " +
//            "  rd.[price_user], " +
//            "  rd.[intercept], " +
//            "  rd.[intercept_time], " +
//            "  c.[name], " +
//            "  c.[CustomerType], " +
//            "  c.[HRUnitId], " +
//            "  c.[HLCode] " +
//            " from rcvmaster rm inner join rcvdetail rd on rm.rorder_no = rd.rorder_no " +
//            " inner join ops_customer c on rm.end_user = c.customer_no " +
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 备注
//            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
//            " rd.remark like #{condition.remark} and " +
//            "</if> " +
//            // 采购供应商
//            "<if test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
//            " rd.stock_code in  " +
//            "   <foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 合同号
//            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
//            " rm.ORorderNo = #{condition.contractNo} and " +
//            "</if> " +
//            // 报价单号
//            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
//            " rm.QuotationNo = #{condition.quotationNo} and " +
//            "</if> " +
//            // 客户po号
//            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
//            " rm.PurchaseNO = #{condition.purchaseNo} and " +
//            "</if> " +
//            // 制单担当
//            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
//            " rd.create_user = #{condition.createId} and  " +
//            "</if> " +
//            // 客户担当
//            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
//            " rm.Employee = #{condition.empSale} and " +
//            "</if> " +
//            // 最终用户
//            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
//            " ( rm.end_user = #{condition.endUserNo} or rm.user_no = #{condition.endUserNo} or rm.customer_no = #{condition.endUserNo} ) and " +
//            "</if> " +
//            // 订单状态
//            "<if test = ' status != null and status.size() &gt; 0 ' > " +
//            " rd.status in " +
//            "   <foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 订单类型
//            "<if test = 'types != null and types.size() &gt; 0  '> " +
//            " rd.order_type in " +
//            "   <foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            // 用户编码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " ( rm.user_no = #{condition.userNo} or rm.customer_no = #{condition.userNo} or rm.end_user = #{condition.userNo} ) and " +
//            "</if> " +
//            // 客户编码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " ( rm.customer_no = #{condition.customerNo} or rm.user_no = #{condition.customerNo} or rm.end_user = #{condition.customerNo} ) and " +
//            "</if> " +
//            // 物料号
//            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
//            " rd.cproduct_no = #{condition.custProductNo} and " +
//            "</if> " +
//            // 型号
//            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
//            " rd.model_no like #{condition.modelNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " rd.rorder_fno like #{condition.orderNo} and " +
//            "</if> " +
//            // 放开样品查询权限
//            "<if test = 'condition.endUserNo == null or condition.endUserNo == \"\" ' >" +
//            " rm.end_user = 'C1D7V' and  " +
//            "</if> " +
//            // 订货日期
//            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
//            "  rm.ROrdDate &gt;= #{condition.orderDateStart}  and rm.ROrdDate &lt;= #{condition.orderDateEnd} " +
//            "</if>" +
//            "</trim>" +
//            "</if>" +
//            " ) rd" +
//            " order by ${condition.property}  ${condition.order}" +
//            "</script>")
//    @Options(timeout = 60)
//    List<IndCodeEntity> exportInCode(@Param("condition") SMSSearchListInfo condition,
//                                                @Param("types") List<String> types,
//                                                @Param("status") List<String> status,
//                                                @Param("purchasingSupplier") List<String> purchasingSupplier,
//                                                @Param("finalDeptNos") List<String> finalDeptNos);

}
