package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.adapter.order.SMSSearchListInfo;
import com.smc.smccloud.model.order.IndCodeEntity;
import com.smc.smccloud.model.order.RcvDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/8/27 9:41
 * @Descripton TODO
 */
@Mapper
@DS("opsreaddb")
public interface RcvOrderByIndCodeMapperReadOnlyMapper extends BaseMapper<RcvDetailDO> {


    @Select("<script>" +
            "SELECT rd.id, rd.rorder_no as mainOrderNo, rd.rorder_item,rd.rorder_fno as orderNo,rd.model_no,rd.quantity,\n" +
            "rd.price,rd.price_enduser,rd.eprice,rd.tax_rate,rd.ntax_pice,rd.ntax_amount,\n" +
            "rd.tax_amount,rd.amount,rd.discount,rd.dlv_date,rd.cdlv_date,rd.wms_dlv_date,\n" +
            "rd.spec_offer_no,rd.cproduct_no,rd.spec_mark,rd.remark,rd.product_name,rd.opponent,\n" +
            "rd.ppl_no,rd.project_no,rd.group_customer_no,rd.shikomi_no,rd.sales_info_no,rd.pre_sales_order_no,\n" +
            "rd.corder_no,rd.custom_code,rd.order_type,rd.status,rd.delete_status,rd.stock_code,\n" +
            "rd.stock_type,rd.inventory_type_code,rd.prod_flag,rd.ready_time,rd.exp_time,rd.ship_time,\n" +
            "rd.ready_qty,rd.exp_qty,rd.returned_qty,rd.address_no,rd.exp_msg,rd.invoice_qty,\n" +
            "rd.invoice_time,rd.carrierId,rd.expressNo,rd.handover_time,rd.create_time,rd.update_time,\n" +
            "rd.update_user,rd.create_user,rd.price_user,\n" +
            "rm.QuotationNo,rm.HLCode,rm.dept_No as saleDeptNo,rm.user_no,rm.customer_no,rm.end_user,rm.Employee,rm.ROrdDate,rm.PurchaseNO,rm.ORorderNo as hddno \n" +
            " FROM dbo.rcvmaster rm  \n" +
            "inner join dbo.rcvdetail rd \n" +
            "on rd.rorder_no = rm.rorder_no"+
            "  where 1=1 " +
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            "<if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
            " and ( "+
            "</if>"+
            // 用户id(客户担当)权限
            "<if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 ' > "+
            "rm.Employee in "+
            "<foreach collection = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            "#{item}" +
            "</foreach>"+
            "</if>"+
            "<if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 and ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0  )' > " +
            " or "+
            "</if>"+
            // 部门权限
            "<if test = 'finalDeptNos != null and finalDeptNos.size() &gt; 0 ' > "+
            " rm.dept_No in "+
            "<foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            "#{item}" +
            "</foreach>"+
            " or "+
            " rm.HLCode in "+
            "<foreach collection = 'finalDeptNos' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            "#{item}" +
            "</foreach>"+
            "</if>"+
            "<if test = ' finalDeptNos.size() &gt; 0  and ( condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0  ) ' >" +
            " or "+
            "</if>"+
            // 客户权限
            "<if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '>" +
            " rm.customer_no in "+
            "<foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            "#{item}" +
            "</foreach>"+
            "</if>"+
            "<if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 and (condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) '>" +
            " or "+
            "</if>"+
            // 用户权限
            "<if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0  '>" +
            " rm.user_no in "+
            "<foreach collection = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            "#{item}" +
            "</foreach>"+
            "</if>"+
            "<if test = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 and condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 '>" +
            " or "+
            "</if>"+
            // 行业权限
            "<if test = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' >" +
            " rm.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or "+
            " rm.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or " +
            " rm.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            "</if>" +
            "<if test = 'condition.dataAuthoritySearchCondition.userIdListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 " +
            "or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
            " ) "+
            "</if>"+
            "</if>"+
            // 订单状态
            "<if test = ' status != null and status.size() &gt; 0' > " +
            " and rd.status in "+
            "<foreach collection = 'status' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            "</if>"+
            // 采购供应商
            "<if  test = ' purchasingSupplier != null and purchasingSupplier.size() &gt; 0' >" +
            " and rd.stock_code in "+
            "<foreach collection = 'purchasingSupplier' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            "</if>"+
            // 备注
            "<if test = 'condition.remark != null and condition.remark != \"\" ' >" +
            " and rd.remark like #{condition.remark}  " +
            "</if> "+
            // 客户po号
            "<if test = 'condition.purchaseNo != null and condition.purchaseNo != \"\" ' >" +
            " and rm.PurchaseNO = #{condition.purchaseNo}  " +
            "</if> "+
            // 客户编码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " and ( rm.customer_no = #{condition.customerNo} or rm.user_no = #{condition.customerNo} or rm.end_user = #{condition.customerNo} )" +
            "</if> "+
            // 用户编码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " and ( rm.user_no = #{condition.userNo} or rm.customer_no = #{condition.userNo} or rm.end_user = #{condition.userNo} ) " +
            "</if> "+
            // 合同号
            "<if test = 'condition.contractNo != null and condition.contractNo != \"\" ' >" +
            " and rm.ORorderNo = #{condition.contractNo}  " +
            "</if> "+
            // 报价单
            "<if test = 'condition.quotationNo != null and condition.quotationNo != \"\" ' >" +
            " and rm.QuotationNo = #{condition.quotationNo}  " +
            "</if> "+
            // 制单担当
            "<if test = 'condition.createId != null and condition.createId != \"\" ' >" +
            " and rd.create_user = #{condition.createId} " +
            "</if> "+
            // 订单类型
            "<if test = 'types != null and types.size() &gt; 0  '> " +
            " and rd.order_type in "+
            "<foreach collection = 'types' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            "</if>"+
            // 客户担当
            "<if test = 'condition.empSale != null and condition.empSale != \"\" ' >" +
            " and rm.Employee = #{condition.empSale}  " +
            "</if> "+
            // 最终用户
            "<if test = 'condition.endUserNo != null and condition.endUserNo != \"\" ' >" +
            " and ( rm.end_user = #{condition.endUserNo} or rm.user_no = #{condition.endUserNo} or rm.customer_no = #{condition.endUserNo} ) " +
            "</if> "+
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " and rd.model_no like #{condition.modelNo} " +
            "</if> "+
            // 物料号
            "<if test = 'condition.custProductNo != null and condition.custProductNo != \"\" ' >" +
            " and rd.cproduct_no like #{condition.custProductNo} " +
            "</if> "+
//            // 部门
//            "<if test = ' condition.deptCodes != null and condition.deptCodes.size() &gt; 0' > " +
//            " and ( rm.dept_no in "+
//            "<foreach collection = 'condition.deptCodes' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " or "+
//            " rm.HLCode in "+
//            "<foreach collection = 'condition.deptCodes' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            "#{item}" +
//            "</foreach>"+
//            " ) "+
//            "</if>"+
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " and rd.rorder_fno like #{condition.orderNo} " +
            "</if> "+
            "<if test = ' condition.orderDateStart != null and condition.orderDateEnd != null ' >" +
            " and rm.ROrdDate &gt;= #{condition.orderDateStart}  and rm.ROrdDate &lt;= #{condition.orderDateEnd} "+
            "</if>"+
            " order by ${condition.property}  ${condition.order}"+
            "</script>")
    List<IndCodeEntity> queryRcvOrderByIndCode(@Param("condition") SMSSearchListInfo condition,
                                               @Param("types") List<String> types,
                                               @Param("status") List<String> status,
                                               @Param("purchasingSupplier") List<String> purchasingSupplier,
                                               @Param("finalDeptNos") List<String> finalDeptNos);


}
