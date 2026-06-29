package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.expdetail.ExpdetailDO;
import com.smc.smccloud.model.expdetail.ExpdetailRequest;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/8/27 10:21
 * @Descripton TODO
 */
@DS("opsreaddb")
@Mapper
public interface ExpdetailMapperReadOnly extends BaseMapper<ExpdetailDO> {


    /**
     * 发货查询
     */
    @Select("<script>" +
            "select * from (" +
            "SELECT   " +
            "e.[id] " +
            ",e.[invoice_no] " +
            ",e.[delivery_no] " +
            ",e.[order_no] " +
            ",e.[item_no] " +
            ",e.[order_fno] " +
            ",e.[model_no] " +
            ",e.[quantity] " +
            ",e.[barcode] " +
            ",e.[customer_no] " +
            ",e.[user_no] " +
            ",e.[ship_date] " +
            ",e.[express_no] " +
            ",e.[express_company] " +
            ",e.[warehouse_code] " +
            ",e.[price] " +
            ",e.[opt_code] " +
            ",e.[corder_no] " +
            ",e.[cmodel_no] " +
            ",e.[case_no] " +
            ",e.[weight] " +
            ",e.[order_type] " +
            ",e.[invoice_flag] " +
            ",e.[invoice_time] " +
            ",e.[sign_time] " +
            ",e.[stock_code] " +
            ",e.[dlv_date] " +
            ",e.[orOrderNo] " +
            ",e.[sign_status] " +
            ",e.[sender] " +
            ",e.[dlv_site] " +
            ",e.[volume] " +
            ",e.[box_type] " +
            ",e.[create_time] " +
            ",e.[create_user] " +
            ",e.[update_time] " +
            ",e.[update_user] " +
            ",e.[dept_no] " +
            ",e.[sign_order_no] " +
            ",e.[dlv_address] " +
            ",e.[ContactPsn] " +
            ",e.[end_user] " +
            ",c.name " +
            ",c.CustomerType " +
            ",c.HRUnitId " +
            ",c.HLCode " +
            "  FROM [ops_core].[dbo].[expdetail] e  " +
            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " e.end_user != 'C1D7V' and " +
            // 权限
            "<if test = ' finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ( " +
            "</if>" +
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
            "       e.end_user in " +
            "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   <if test = ' finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
            "      or " +
            "    </if>" +
            "   </if>" +
            // 行业权限
            "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
            " e.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or "+
            " e.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or " +
            " e.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
            "     e.user_no in " +
            "     <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "     </foreach>" +
            "   </if>" +
            "<if test = 'finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ) " +
            "</if>" +
            "</trim>" +
            " union all " +
            "SELECT   " +
            "e.[id] " +
            ",e.[invoice_no] " +
            ",e.[delivery_no] " +
            ",e.[order_no] " +
            ",e.[item_no] " +
            ",e.[order_fno] " +
            ",e.[model_no] " +
            ",e.[quantity] " +
            ",e.[barcode] " +
            ",e.[customer_no] " +
            ",e.[user_no] " +
            ",e.[ship_date] " +
            ",e.[express_no] " +
            ",e.[express_company] " +
            ",e.[warehouse_code] " +
            ",e.[price] " +
            ",e.[opt_code] " +
            ",e.[corder_no] " +
            ",e.[cmodel_no] " +
            ",e.[case_no] " +
            ",e.[weight] " +
            ",e.[order_type] " +
            ",e.[invoice_flag] " +
            ",e.[invoice_time] " +
            ",e.[sign_time] " +
            ",e.[stock_code] " +
            ",e.[dlv_date] " +
            ",e.[orOrderNo] " +
            ",e.[sign_status] " +
            ",e.[sender] " +
            ",e.[dlv_site] " +
            ",e.[volume] " +
            ",e.[box_type] " +
            ",e.[create_time] " +
            ",e.[create_user] " +
            ",e.[update_time] " +
            ",e.[update_user] " +
            ",e.[dept_no] " +
            ",e.[sign_order_no] " +
            ",e.[dlv_address] " +
            ",e.[ContactPsn] " +
            ",e.[end_user] " +
            ",c.name " +
            ",c.CustomerType " +
            ",c.HRUnitId " +
            ",c.HLCode " +
            "  FROM [ops_core].[dbo].[expdetail] e  " +
            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
            " where e.end_user = 'C1D7V'  " +
            " ) e " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " e.model_no like #{condition.modelNo} and " +
            "</if> " +
            // 单据状态
            "<if test = 'condition.status != null and condition.status != \"\" ' >" +
            " e.sign_status = #{condition.status} and " +
            "</if> " +
            // 发票号
            "<if test = 'condition.invoiceNo != null and condition.invoiceNo != \"\" ' >" +
            " e.invoice_no like #{condition.invoiceNo} and " +
            "</if> " +
            // update by LiyingChao from bug 9298 in 2023-1-9
            // 仓库
            "<if test = ' condition.warehouseCodes != null and condition.warehouseCodes.size() &gt; 0  '> " +
            " e.warehouse_code in " +
            "  <foreach collection = 'condition.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "  </foreach>" +
            " and " +
            "</if>" +
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" and !condition.warehouseCode.startsWith(\"W\") ' >" +
//            " e.warehouse_code like #{condition.warehouseCode} and " +
//            "</if> " +
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" and condition.warehouseCode.startsWith(\"W\") ' >" +
//            " e.stock_code like 'W%' and " +
//            "</if> " +
            // 出库单号
            "<if test = 'condition.deliveryNo != null and condition.deliveryNo != \"\" ' >" +
            " e.delivery_no like #{condition.deliveryNo} and " +
            "</if> " +
            // 客户代码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " e.customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 用户代码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " e.user_no = #{condition.userNo} and " +
            "</if> " +
            // 货物条码
            "<if test = 'condition.barcode != null and condition.barcode != \"\" ' >" +
            " e.barcode = #{condition.barcode} and " +
            "</if> " +
            // 客户订单号
            "<if test = 'condition.corderNo != null and condition.corderNo != \"\" ' >" +
            " e.corder_no like #{condition.corderNo} and " +
            "</if> " +
            // 快递单号
            "<if test = 'condition.expressNo != null and condition.expressNo != \"\" ' >" +
            " e.express_no like #{condition.expressNo} and " +
            "</if> " +
            // 合同订单号
            "<if test = 'condition.orOrderNo != null and condition.orOrderNo != \"\" ' >" +
            " e.orOrderNo like #{condition.orOrderNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " e.order_fno like #{condition.orderNo} and " +
            "</if> " +
            // 客户型号
            "<if test = 'condition.cmodelNo != null and condition.cmodelNo != \"\" ' >" +
            " e.cmodel_no like #{condition.cmodelNo} and " +
            "</if> " +
            // 最终用户
            "<if test = 'condition.endUser != null and condition.endUser != \"\" ' >" +
            " e.end_user = #{condition.endUser} and " +
            "</if> " +
            // 订单类型
            "<if test = ' orderType != null and orderType.size() &gt; 0' >" +
            " e.order_type in  " +
            "   <foreach collection = 'orderType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 发货日期范围
            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
            "  e.ship_date &gt;= #{condition.strFromDate} and e.ship_date &lt;= #{condition.strToDate} " +
            " </if>"+
            "</trim>"+
            " order by ship_date desc, order_no asc , item_no asc "+
            "</script>")
    @Options(timeout = 60)
    List<ExpdetailVO> listExpdetail(@Param("condition") ExpdetailRequest condition,
                                    @Param("orderType") List<String> orderType,
                                    @Param("finalUserAuth") List<String> finalUserAuth,
                                    @Param("finalDeptAuth") List<String> finalDeptAuth,
                                    @Param("finalCustomerAuth") List<String> finalCustomerAuth,
                                    @Param("finalInCodeAuth") List<String> finalInCodeAuth
                                    );


    /**
     * 发货查询导出
     */
    @Select("<script>" +
            "select " +
            "<if test = 'condition.exportNum != null ' >" +
            "  TOP ${condition.exportNum} " +
            " </if> " +
            " * from (" +
            "SELECT   " +
            "e.[id] " +
            ",e.[invoice_no] " +
            ",e.[delivery_no] " +
            ",e.[order_no] " +
            ",e.[item_no] " +
            ",e.[order_fno] " +
            ",e.[model_no] " +
            ",e.[quantity] " +
            ",e.[barcode] " +
            ",e.[customer_no] " +
            ",e.[user_no] " +
            ",e.[ship_date] " +
            ",e.[express_no] " +
            ",e.[express_company] " +
            ",e.[warehouse_code] " +
            ",e.[price] " +
            ",e.[opt_code] " +
            ",e.[corder_no] " +
            ",e.[cmodel_no] " +
            ",e.[case_no] " +
            ",e.[weight] " +
            ",e.[order_type] " +
            ",e.[invoice_flag] " +
            ",e.[invoice_time] " +
            ",e.[sign_time] " +
            ",e.[stock_code] " +
            ",e.[dlv_date] " +
            ",e.[orOrderNo] " +
            ",e.[sign_status] " +
            ",e.[sender] " +
            ",e.[dlv_site] " +
            ",e.[volume] " +
            ",e.[box_type] " +
            ",e.[create_time] " +
            ",e.[create_user] " +
            ",e.[update_time] " +
            ",e.[update_user] " +
            ",e.[dept_no] " +
            ",e.[sign_order_no] " +
            ",e.[dlv_address] " +
            ",e.[ContactPsn] " +
            ",e.[end_user] " +
            ",c.name " +
            ",c.CustomerType " +
            ",c.HRUnitId " +
            ",c.HLCode " +
            "  FROM [ops_core].[dbo].[expdetail] e  " +
            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " e.end_user != 'C1D7V' and " +
            // 权限
            "<if test = ' finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ( " +
            "</if>" +
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
            "       e.end_user in " +
            "       <foreach collection = 'finalCustomerAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            "   <if test = ' finalUserAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 '> " +
            "      or " +
            "    </if>" +
            "   </if>" +
            // 行业权限
            "<if test = 'finalInCodeAuth.size() &gt; 0 ' >" +
            " e.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or "+
            " e.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
            "<foreach collection = 'finalInCodeAuth' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            " #{item}" +
            "</foreach>"+
            " ) " +
            " or " +
            " e.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
            "     e.user_no in " +
            "     <foreach collection = 'finalUserAuth' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "     </foreach>" +
            "   </if>" +
            "<if test = 'finalUserAuth.size() &gt; 0 or finalDeptAuth.size() &gt; 0 or finalCustomerAuth.size() &gt; 0 or finalInCodeAuth.size() &gt; 0 ' > " +
            " ) " +
            "</if>" +
            "</trim>" +
            " union all " +
            "SELECT   " +
            "e.[id] " +
            ",e.[invoice_no] " +
            ",e.[delivery_no] " +
            ",e.[order_no] " +
            ",e.[item_no] " +
            ",e.[order_fno] " +
            ",e.[model_no] " +
            ",e.[quantity] " +
            ",e.[barcode] " +
            ",e.[customer_no] " +
            ",e.[user_no] " +
            ",e.[ship_date] " +
            ",e.[express_no] " +
            ",e.[express_company] " +
            ",e.[warehouse_code] " +
            ",e.[price] " +
            ",e.[opt_code] " +
            ",e.[corder_no] " +
            ",e.[cmodel_no] " +
            ",e.[case_no] " +
            ",e.[weight] " +
            ",e.[order_type] " +
            ",e.[invoice_flag] " +
            ",e.[invoice_time] " +
            ",e.[sign_time] " +
            ",e.[stock_code] " +
            ",e.[dlv_date] " +
            ",e.[orOrderNo] " +
            ",e.[sign_status] " +
            ",e.[sender] " +
            ",e.[dlv_site] " +
            ",e.[volume] " +
            ",e.[box_type] " +
            ",e.[create_time] " +
            ",e.[create_user] " +
            ",e.[update_time] " +
            ",e.[update_user] " +
            ",e.[dept_no] " +
            ",e.[sign_order_no] " +
            ",e.[dlv_address] " +
            ",e.[ContactPsn] " +
            ",e.[end_user] " +
            ",c.name " +
            ",c.CustomerType " +
            ",c.HRUnitId " +
            ",c.HLCode " +
            "  FROM [ops_core].[dbo].[expdetail] e  " +
            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
            " where e.end_user = 'C1D7V'  " +
            " ) e " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 单据状态
            "<if test = 'condition.status != null and condition.status != \"\" ' >" +
            " e.sign_status = #{condition.status} and " +
            "</if> " +
            // 发票号
            "<if test = 'condition.invoiceNo != null and condition.invoiceNo != \"\" ' >" +
            " e.invoice_no like #{condition.invoiceNo} and " +
            "</if> " +
            // update by LiyingChao from bug 9298 in 2023-1-9
            // 仓库
            "<if test = ' condition.warehouseCodes != null and condition.warehouseCodes.size() &gt; 0  '> " +
            " e.warehouse_code in " +
            "  <foreach collection = 'condition.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "  </foreach>" +
            " and " +
            "</if>" +
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" and !condition.warehouseCode.startsWith(\"W\") ' >" +
//            " e.warehouse_code like #{condition.warehouseCode} and " +
//            "</if> " +
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" and condition.warehouseCode.startsWith(\"W\") ' >" +
//            " e.stock_code like 'W%' and " +
//            "</if> " +
            // 出库单号
            "<if test = 'condition.deliveryNo != null and condition.deliveryNo != \"\" ' >" +
            " e.delivery_no like #{condition.deliveryNo} and " +
            "</if> " +
            // 客户代码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " e.customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 用户代码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " e.user_no = #{condition.userNo} and " +
            "</if> " +
            // 货物条码
            "<if test = 'condition.barcode != null and condition.barcode != \"\" ' >" +
            " e.barcode = #{condition.barcode} and " +
            "</if> " +
            // 客户订单号
            "<if test = 'condition.corderNo != null and condition.corderNo != \"\" ' >" +
            " e.corder_no like #{condition.corderNo} and " +
            "</if> " +
            // 快递单号
            "<if test = 'condition.expressNo != null and condition.expressNo != \"\" ' >" +
            " e.express_no like #{condition.expressNo} and " +
            "</if> " +
            // 合同订单号
            "<if test = 'condition.orOrderNo != null and condition.orOrderNo != \"\" ' >" +
            " e.orOrderNo like #{condition.orOrderNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " e.order_fno like #{condition.orderNo} and " +
            "</if> " +
            // 订单类型
            "<if test = ' orderType != null and orderType.size() &gt; 0' >" +
            " e.order_type in  " +
            "   <foreach collection = 'orderType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and " +
            "</if>" +
            // 发货日期范围
            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
            "  e.ship_date &gt;= #{condition.strFromDate} and e.ship_date &lt;= #{condition.strToDate} " +
            " </if>"+
            "</trim>"+
            " order by ship_date desc, order_no asc , item_no asc "+
            "</script>")
    @Options(timeout = 60)
    List<ExpdetailVO> exportExpdetail(@Param("condition") ExpdetailRequest condition,
                                      @Param("orderType") List<String> orderType,
                                      @Param("finalUserAuth") List<String> finalUserAuth,
                                      @Param("finalDeptAuth") List<String> finalDeptAuth,
                                      @Param("finalCustomerAuth") List<String> finalCustomerAuth,
                                      @Param("finalInCodeAuth") List<String> finalInCodeAuth
    );


    /**
     * 代理店发货查询
     */
    @Select("<script>" +
            "SELECT " +
            "e.[id] " +
            ",e.[invoice_no] " +
            ",e.[delivery_no] " +
            ",e.[order_no] " +
            ",e.[item_no] " +
            ",e.[order_fno] " +
            ",e.[model_no] " +
            ",e.[quantity] " +
            ",e.[barcode] " +
            ",e.[customer_no] " +
            ",e.[user_no] " +
            ",e.[ship_date] " +
            ",e.[express_no] " +
            ",e.[express_company] " +
            ",e.[warehouse_code] " +
            ",e.[price] " +
            ",e.[opt_code] " +
            ",e.[corder_no] " +
            ",e.[cmodel_no] " +
            ",e.[case_no] " +
            ",e.[weight] " +
            ",e.[order_type] " +
            ",e.[invoice_flag] " +
            ",e.[invoice_time] " +
            ",e.[sign_time] " +
            ",e.[stock_code] " +
            ",e.[dlv_date] " +
            ",e.[orOrderNo] " +
            ",e.[sign_status] " +
            ",e.[sender] " +
            ",e.[dlv_site] " +
            ",e.[volume] " +
            ",e.[box_type] " +
            ",e.[create_time] " +
            ",e.[create_user] " +
            ",e.[update_time] " +
            ",e.[update_user] " +
            ",e.[dept_no] " +
            ",e.[sign_order_no] " +
            ",e.[dlv_address] " +
            ",e.[ContactPsn] " +
            ",e.[end_user] " +
            ",c.name " +
            ",c.CustomerType " +
            ",c.HRUnitId " +
            ",c.HLCode " +
            "  FROM [ops_core].[dbo].[expdetail] e  " +
            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " e.model_no like  #{condition.modelNo} and " +
            "</if> " +
            // 单据状态
            "<if test = 'condition.status != null and condition.status != \"\" ' >" +
            " e.sign_status = #{condition.status} and " +
            "</if> " +
            // 发票号
            "<if test = 'condition.invoiceNo != null and condition.invoiceNo != \"\" ' >" +
            " e.invoice_no like #{condition.invoiceNo} and " +
            "</if> " +
            // 仓库
            // update by LiyingChao from bug 9298 in 2023-1-9
            "<if test = ' condition.warehouseCodes != null and condition.warehouseCodes.size() &gt; 0  '> " +
            " e.warehouse_code in " +
            "  <foreach collection = 'condition.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "  </foreach>" +
            " and " +
            "</if>" +
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" and !condition.warehouseCode.startsWith(\"W\") ' >" +
//            " e.warehouse_code like #{condition.warehouseCode} and " +
//            "</if> " +
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" and condition.warehouseCode.startsWith(\"W\") ' >" +
//            " e.stock_code like 'W%' and " +
//            "</if> " +
            // 出库单号
            "<if test = 'condition.deliveryNo != null and condition.deliveryNo != \"\" ' >" +
            " e.delivery_no like #{condition.deliveryNo} and " +
            "</if> " +
            // 客户代码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " e.customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 用户代码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " e.user_no = #{condition.userNo} and " +
            "</if> " +
            // 货物条码
            "<if test = 'condition.barcode != null and condition.barcode != \"\" ' >" +
            " e.barcode = #{condition.barcode} and " +
            "</if> " +
            // 客户订单号
            "<if test = 'condition.corderNo != null and condition.corderNo != \"\" ' >" +
            " e.corder_no like #{condition.corderNo} and " +
            "</if> " +
            // 快递单号
            "<if test = 'condition.expressNo != null and condition.expressNo != \"\" ' >" +
            " e.express_no like #{condition.expressNo} and " +
            "</if> " +
            // 合同订单号
            "<if test = 'condition.orOrderNo != null and condition.orOrderNo != \"\" ' >" +
            " e.orOrderNo like #{condition.orOrderNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " e.order_fno like #{condition.orderNo} and " +
            "</if> " +
            // 订单类型
            "<if test = ' orderType != null and orderType.size() &gt; 0' >" +
            " e.order_type in  " +
            "   <foreach collection = 'orderType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            "   <if test = ' ( condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 ) " +
            " or (condition.strFromDate != null and condition.strToDate != null) ' > " +
            " and " +
            "   </if>" +
            "</if>" +
            "</if>" +
            // 权限
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            // 客户权限
            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
            "       e.customer_no in " +
            "       <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
            " and "+
            "</if>"+
            "   </if>" +
            "</if>" +
            // 发货日期范围
            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
            "  e.ship_date &gt;= #{condition.strFromDate} and e.ship_date &lt;= #{condition.strToDate} " +
            " </if>"+
            "</trim>"+
            " order by e.ship_date desc, e.order_no asc , e.item_no asc "+
            "</script>")
    @Options(timeout = 60)
    List<ExpdetailVO> listExpdetailForAgent(@Param("condition") ExpdetailRequest condition,
                                    @Param("orderType") List<String> orderType);


    /**
     * 代理店发货查询导出
     */
    @Select("<script>" +
            "SELECT " +
            "<if test = 'condition.exportNum != null ' >" +
            "  TOP ${condition.exportNum} " +
            " </if> " +
            "e.[id] " +
            ",e.[invoice_no] " +
            ",e.[delivery_no] " +
            ",e.[order_no] " +
            ",e.[item_no] " +
            ",e.[order_fno] " +
            ",e.[model_no] " +
            ",e.[quantity] " +
            ",e.[barcode] " +
            ",e.[customer_no] " +
            ",e.[user_no] " +
            ",e.[ship_date] " +
            ",e.[express_no] " +
            ",e.[express_company] " +
            ",e.[warehouse_code] " +
            ",e.[price] " +
            ",e.[opt_code] " +
            ",e.[corder_no] " +
            ",e.[cmodel_no] " +
            ",e.[case_no] " +
            ",e.[weight] " +
            ",e.[order_type] " +
            ",e.[invoice_flag] " +
            ",e.[invoice_time] " +
            ",e.[sign_time] " +
            ",e.[stock_code] " +
            ",e.[dlv_date] " +
            ",e.[orOrderNo] " +
            ",e.[sign_status] " +
            ",e.[sender] " +
            ",e.[dlv_site] " +
            ",e.[volume] " +
            ",e.[box_type] " +
            ",e.[create_time] " +
            ",e.[create_user] " +
            ",e.[update_time] " +
            ",e.[update_user] " +
            ",e.[dept_no] " +
            ",e.[sign_order_no] " +
            ",e.[dlv_address] " +
            ",e.[ContactPsn] " +
            ",e.[end_user] " +
            ",c.name " +
            ",c.CustomerType " +
            ",c.HRUnitId " +
            ",c.HLCode " +
            "  FROM [ops_core].[dbo].[expdetail] e  " +
            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            // 型号
            "<if test = 'condition.modelNo != null and condition.modelNo != \"\" ' >" +
            " e.model_no like  #{condition.modelNo} and " +
            "</if> " +
            // 单据状态
            "<if test = 'condition.status != null and condition.status != \"\" ' >" +
            " e.sign_status = #{condition.status} and " +
            "</if> " +
            // 发票号
            "<if test = 'condition.invoiceNo != null and condition.invoiceNo != \"\" ' >" +
            " e.invoice_no like #{condition.invoiceNo} and " +
            "</if> " +
            // update by LiyingChao from bug 9298 in 2023-1-9
            // 仓库
            "<if test = ' condition.warehouseCodes != null and condition.warehouseCodes.size() &gt; 0  '> " +
            " e.warehouse_code in " +
            "  <foreach collection = 'condition.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "  </foreach>" +
            " and " +
            "</if>" +
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" and !condition.warehouseCode.startsWith(\"W\") ' >" +
//            " e.warehouse_code like #{condition.warehouseCode} and " +
//            "</if> " +
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" and condition.warehouseCode.startsWith(\"W\") ' >" +
//            " e.stock_code like 'W%' and " +
//            "</if> " +
            // 出库单号
            "<if test = 'condition.deliveryNo != null and condition.deliveryNo != \"\" ' >" +
            " e.delivery_no like #{condition.deliveryNo} and " +
            "</if> " +
            // 客户代码
            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
            " e.customer_no = #{condition.customerNo} and " +
            "</if> " +
            // 用户代码
            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
            " e.user_no = #{condition.userNo} and " +
            "</if> " +
            // 货物条码
            "<if test = 'condition.barcode != null and condition.barcode != \"\" ' >" +
            " e.barcode = #{condition.barcode} and " +
            "</if> " +
            // 客户订单号
            "<if test = 'condition.corderNo != null and condition.corderNo != \"\" ' >" +
            " e.corder_no like #{condition.corderNo} and " +
            "</if> " +
            // 快递单号
            "<if test = 'condition.expressNo != null and condition.expressNo != \"\" ' >" +
            " e.express_no like #{condition.expressNo} and " +
            "</if> " +
            // 合同订单号
            "<if test = 'condition.orOrderNo != null and condition.orOrderNo != \"\" ' >" +
            " e.orOrderNo like #{condition.orOrderNo} and " +
            "</if> " +
            // 订单号
            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
            " e.order_fno like #{condition.orderNo} and " +
            "</if> " +
            // 订单类型
            "<if test = ' orderType != null and orderType.size() &gt; 0' >" +
            " e.order_type in  " +
            "   <foreach collection = 'orderType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            "   <if test = ' ( condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 ) " +
            " or (condition.strFromDate != null and condition.strToDate != null) ' > " +
            " and " +
            "   </if>" +
            "</if>" +
            "</if>" +
            // 权限
            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
            // 客户权限
            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0  '> " +
            "       e.customer_no in " +
            "       <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "           #{item}" +
            "       </foreach>" +
            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
            " and "+
            "</if>"+
            "   </if>" +
            "</if>" +
            // 发货日期范围
            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
            "  e.ship_date &gt;= #{condition.strFromDate} and e.ship_date &lt;= #{condition.strToDate} " +
            " </if>"+
            "</trim>"+
            " order by e.ship_date desc, e.order_no asc , e.item_no asc "+
            "</script>")
    @Options(timeout = 60)
    List<ExpdetailVO> exportExpdetailForAgent(@Param("condition") ExpdetailRequest condition,
                                      @Param("orderType") List<String> orderType);


    /**
     * 发货查询
     */
//    @Select("<script>" +
//            "select distinct * from (" +
//            "SELECT   " +
//            "e.[id] " +
//            ",e.[invoice_no] " +
//            ",e.[delivery_no] " +
//            ",e.[order_no] " +
//            ",e.[item_no] " +
//            ",e.[order_fno] " +
//            ",e.[model_no] " +
//            ",e.[quantity] " +
//            ",e.[barcode] " +
//            ",e.[customer_no] " +
//            ",e.[user_no] " +
//            ",e.[ship_date] " +
//            ",e.[express_no] " +
//            ",e.[express_company] " +
//            ",e.[warehouse_code] " +
//            ",e.[price] " +
//            ",e.[opt_code] " +
//            ",e.[corder_no] " +
//            ",e.[cmodel_no] " +
//            ",e.[case_no] " +
//            ",e.[weight] " +
//            ",e.[order_type] " +
//            ",e.[invoice_flag] " +
//            ",e.[invoice_time] " +
//            ",e.[sign_time] " +
//            ",e.[stock_code] " +
//            ",e.[dlv_date] " +
//            ",e.[orOrderNo] " +
//            ",e.[sign_status] " +
//            ",e.[sender] " +
//            ",e.[dlv_site] " +
//            ",e.[volume] " +
//            ",e.[box_type] " +
//            ",e.[create_time] " +
//            ",e.[create_user] " +
//            ",e.[update_time] " +
//            ",e.[update_user] " +
//            ",e.[dept_no] " +
//            ",e.[sign_order_no] " +
//            ",e.[dlv_address] " +
//            ",e.[ContactPsn] " +
//            ",e.[end_user] " +
//            ",c.name " +
//            ",c.CustomerType " +
//            ",c.HRUnitId " +
//            ",c.HLCode " +
//            "  FROM [ops_core].[dbo].[expdetail] e  " +
//            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 单据状态
//            "<if test = 'condition.status != null and condition.status != \"\" ' >" +
//            " e.sign_status = #{condition.status} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) " +
//            " or (condition.customerNo != null and condition.customerNo != \"\") or (condition.deliveryNo != null and condition.deliveryNo != \"\") or (condition.warehouseCode != null and condition.warehouseCode != \"\") or (condition.invoiceNo != null and condition.invoiceNo != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 发票号
//            "<if test = 'condition.invoiceNo != null and condition.invoiceNo != \"\" ' >" +
//            " e.invoice_no like #{condition.invoiceNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) " +
//            " or (condition.customerNo != null and condition.customerNo != \"\") or (condition.deliveryNo != null and condition.deliveryNo != \"\") or (condition.warehouseCode != null and condition.warehouseCode != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 仓库
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" ' >" +
//            " e.warehouse_code like #{condition.warehouseCode} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) " +
//            " or (condition.customerNo != null and condition.customerNo != \"\") or (condition.deliveryNo != null and condition.deliveryNo != \"\")  ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 出库单号
//            "<if test = 'condition.deliveryNo != null and condition.deliveryNo != \"\" ' >" +
//            " e.delivery_no like #{condition.deliveryNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) " +
//            "or (condition.customerNo != null and condition.customerNo != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 客户代码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " e.customer_no = #{condition.customerNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 用户代码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " e.user_no = #{condition.userNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 货物条码
//            "<if test = 'condition.barcode != null and condition.barcode != \"\" ' >" +
//            " e.barcode = #{condition.barcode} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 客户订单号
//            "<if test = 'condition.corderNo != null and condition.corderNo != \"\" ' >" +
//            " e.corder_no like #{condition.corderNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 快递单号
//            "<if test = 'condition.expressNo != null and condition.expressNo != \"\" ' >" +
//            " e.express_no like #{condition.expressNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 合同订单号
//            "<if test = 'condition.orOrderNo != null and condition.orOrderNo != \"\" ' >" +
//            " e.orOrderNo like #{condition.orOrderNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " e.order_fno like #{condition.orderNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 订单类型
//            "<if test = ' orderType != null and orderType.size() &gt; 0' >" +
//            " e.order_type in  " +
//            "   <foreach collection = 'orderType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if>" +
//            // 权限
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            " ( " +
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
//            "       e.end_user in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 and  (condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) '> " +
//            "      or " +
//            "    </if>" +
//            // 行业权限
//            "<if test = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' >" +
//            " e.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or "+
//            " e.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or " +
//            " e.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
//            "     e.user_no in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 " +
//            "   or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            "       ) " +
//            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
//            " and " +
//            " </if>"+
//            " </if>" +
//            "</if>" +
//            // 发货日期范围
//            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
//            "  e.ship_date &gt;= #{condition.strFromDate} and e.ship_date &lt;= #{condition.strToDate} " +
//            " </if>"+
//            "</trim>"+
//            "<if test = ' condition.agentSearch != null and condition.agentSearch != \"\" and condition.agentSearch != \"1\" ' >" +
//            " union all " +
//            "SELECT   " +
//            "e.[id] " +
//            ",e.[invoice_no] " +
//            ",e.[delivery_no] " +
//            ",e.[order_no] " +
//            ",e.[item_no] " +
//            ",e.[order_fno] " +
//            ",e.[model_no] " +
//            ",e.[quantity] " +
//            ",e.[barcode] " +
//            ",e.[customer_no] " +
//            ",e.[user_no] " +
//            ",e.[ship_date] " +
//            ",e.[express_no] " +
//            ",e.[express_company] " +
//            ",e.[warehouse_code] " +
//            ",e.[price] " +
//            ",e.[opt_code] " +
//            ",e.[corder_no] " +
//            ",e.[cmodel_no] " +
//            ",e.[case_no] " +
//            ",e.[weight] " +
//            ",e.[order_type] " +
//            ",e.[invoice_flag] " +
//            ",e.[invoice_time] " +
//            ",e.[sign_time] " +
//            ",e.[stock_code] " +
//            ",e.[dlv_date] " +
//            ",e.[orOrderNo] " +
//            ",e.[sign_status] " +
//            ",e.[sender] " +
//            ",e.[dlv_site] " +
//            ",e.[volume] " +
//            ",e.[box_type] " +
//            ",e.[create_time] " +
//            ",e.[create_user] " +
//            ",e.[update_time] " +
//            ",e.[update_user] " +
//            ",e.[dept_no] " +
//            ",e.[sign_order_no] " +
//            ",e.[dlv_address] " +
//            ",e.[ContactPsn] " +
//            ",e.[end_user] " +
//            ",c.name " +
//            ",c.CustomerType " +
//            ",c.HRUnitId " +
//            ",c.HLCode " +
//            "  FROM [ops_core].[dbo].[expdetail] e  " +
//            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 单据状态
//            "<if test = 'condition.status != null and condition.status != \"\" ' >" +
//            " e.sign_status = #{condition.status} and " +
//            "</if> " +
//            // 发票号
//            "<if test = 'condition.invoiceNo != null and condition.invoiceNo != \"\" ' >" +
//            " e.invoice_no like #{condition.invoiceNo} and " +
//            "</if> " +
//            // 仓库
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" ' >" +
//            " e.warehouse_code like #{condition.warehouseCode} and " +
//            "</if> " +
//            // 出库单号
//            "<if test = 'condition.deliveryNo != null and condition.deliveryNo != \"\" ' >" +
//            " e.delivery_no like #{condition.deliveryNo} and  " +
//            "</if> " +
//            // 客户代码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " e.customer_no = #{condition.customerNo} and " +
//            "</if> " +
//            // 用户代码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " e.user_no = #{condition.userNo} and " +
//            "</if> " +
//            // 货物条码
//            "<if test = 'condition.barcode != null and condition.barcode != \"\" ' >" +
//            " e.barcode = #{condition.barcode} and " +
//            "</if> " +
//            // 客户订单号
//            "<if test = 'condition.corderNo != null and condition.corderNo != \"\" ' >" +
//            " e.corder_no like #{condition.corderNo} and " +
//            "</if> " +
//            // 快递单号
//            "<if test = 'condition.expressNo != null and condition.expressNo != \"\" ' >" +
//            " e.express_no like #{condition.expressNo} and " +
//            "</if> " +
//            // 合同订单号
//            "<if test = 'condition.orOrderNo != null and condition.orOrderNo != \"\" ' >" +
//            " e.orOrderNo like #{condition.orOrderNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " e.order_fno like #{condition.orderNo} and " +
//            "</if> " +
//            // 订单类型
//            "<if test = ' orderType != null and orderType.size() &gt; 0' >" +
//            " e.order_type in  " +
//            "   <foreach collection = 'orderType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and " +
//            "</if>" +
//            " e.end_user = 'C1D7V' and  " +
//            // 发货日期范围
//            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
//            "  e.ship_date &gt;= #{condition.strFromDate} and e.ship_date &lt;= #{condition.strToDate} " +
//            " </if>"+
//            "</trim>"+
//            "</if>"+
//            " ) e " +
//            " order by e.ship_date desc, e.order_no asc , e.item_no asc "+
//            "</script>")
//    @Options(timeout = 60)
//    List<ExpdetailVO> listExpdetail(@Param("condition") ExpdetailRequest condition,
//                                    @Param("orderType") List<String> orderType);

    /**
     * 发货查询导出
     */
//    @Select("<script>" +
//            "select distinct " +
//            "<if test = 'condition.exportNum != null ' >" +
//            "  TOP ${condition.exportNum} " +
//            " </if> " +
//            " * from (" +
//            "SELECT   " +
//            "e.[id] " +
//            ",e.[invoice_no] " +
//            ",e.[delivery_no] " +
//            ",e.[order_no] " +
//            ",e.[item_no] " +
//            ",e.[order_fno] " +
//            ",e.[model_no] " +
//            ",e.[quantity] " +
//            ",e.[barcode] " +
//            ",e.[customer_no] " +
//            ",e.[user_no] " +
//            ",e.[ship_date] " +
//            ",e.[express_no] " +
//            ",e.[express_company] " +
//            ",e.[warehouse_code] " +
//            ",e.[price] " +
//            ",e.[opt_code] " +
//            ",e.[corder_no] " +
//            ",e.[cmodel_no] " +
//            ",e.[case_no] " +
//            ",e.[weight] " +
//            ",e.[order_type] " +
//            ",e.[invoice_flag] " +
//            ",e.[invoice_time] " +
//            ",e.[sign_time] " +
//            ",e.[stock_code] " +
//            ",e.[dlv_date] " +
//            ",e.[orOrderNo] " +
//            ",e.[sign_status] " +
//            ",e.[sender] " +
//            ",e.[dlv_site] " +
//            ",e.[volume] " +
//            ",e.[box_type] " +
//            ",e.[create_time] " +
//            ",e.[create_user] " +
//            ",e.[update_time] " +
//            ",e.[update_user] " +
//            ",e.[dept_no] " +
//            ",e.[sign_order_no] " +
//            ",e.[dlv_address] " +
//            ",e.[ContactPsn] " +
//            ",e.[end_user] " +
//            ",c.name " +
//            ",c.CustomerType " +
//            ",c.HRUnitId " +
//            ",c.HLCode " +
//            "  FROM [ops_core].[dbo].[expdetail] e  " +
//            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 单据状态
//            "<if test = 'condition.status != null and condition.status != \"\" ' >" +
//            " e.sign_status = #{condition.status} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) " +
//            " or (condition.customerNo != null and condition.customerNo != \"\") or (condition.deliveryNo != null and condition.deliveryNo != \"\") or (condition.warehouseCode != null and condition.warehouseCode != \"\") or (condition.invoiceNo != null and condition.invoiceNo != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 发票号
//            "<if test = 'condition.invoiceNo != null and condition.invoiceNo != \"\" ' >" +
//            " e.invoice_no like #{condition.invoiceNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) " +
//            " or (condition.customerNo != null and condition.customerNo != \"\") or (condition.deliveryNo != null and condition.deliveryNo != \"\") or (condition.warehouseCode != null and condition.warehouseCode != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 仓库
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" ' >" +
//            " e.warehouse_code like #{condition.warehouseCode} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) " +
//            " or (condition.customerNo != null and condition.customerNo != \"\") or (condition.deliveryNo != null and condition.deliveryNo != \"\")  ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 出库单号
//            "<if test = 'condition.deliveryNo != null and condition.deliveryNo != \"\" ' >" +
//            " e.delivery_no like #{condition.deliveryNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) " +
//            "or (condition.customerNo != null and condition.customerNo != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 客户代码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " e.customer_no = #{condition.customerNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") or (condition.userNo != null and condition.userNo != \"\" ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 用户代码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " e.user_no = #{condition.userNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) or (condition.barcode != null and condition.barcode != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 货物条码
//            "<if test = 'condition.barcode != null and condition.barcode != \"\" ' >" +
//            " e.barcode = #{condition.barcode} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) or ( condition.corderNo != null and condition.corderNo != \"\" ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 客户订单号
//            "<if test = 'condition.corderNo != null and condition.corderNo != \"\" ' >" +
//            " e.corder_no like #{condition.corderNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") " +
//            " or (condition.expressNo != null and condition.expressNo != \"\" ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 快递单号
//            "<if test = 'condition.expressNo != null and condition.expressNo != \"\" ' >" +
//            " e.express_no like #{condition.expressNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") or (condition.orOrderNo != null and condition.orOrderNo != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 合同订单号
//            "<if test = 'condition.orOrderNo != null and condition.orOrderNo != \"\" ' >" +
//            " e.orOrderNo like #{condition.orOrderNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) or (condition.orderNo != null and condition.orderNo != \"\") ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " e.order_fno like #{condition.orderNo} " +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) or ( orderType != null and orderType.size() &gt; 0 ) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if> " +
//            // 订单类型
//            "<if test = ' orderType != null and orderType.size() &gt; 0' >" +
//            " e.order_type in  " +
//            "   <foreach collection = 'orderType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = ' ( condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) " +
//            " or (condition.strFromDate != null and condition.strToDate != null) ' > " +
//            " and " +
//            "   </if>" +
//            "</if>" +
//            "</if>" +
//            // 权限
//            "<if test = 'condition.dataAuthoritySearchCondition != null' >" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            " ( " +
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
//            "       e.end_user in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 and  (condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ) '> " +
//            "      or " +
//            "    </if>" +
//            // 行业权限
//            "<if test = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' >" +
//            " e.customer_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or "+
//            " e.user_no in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
//            "<foreach collection = 'condition.dataAuthoritySearchCondition.indCodeListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > "+
//            " #{item}" +
//            "</foreach>"+
//            " ) " +
//            " or " +
//            " e.end_user in ( select customerno from ops_customer_nine_industryCode where IndustryMediamCode in " +
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
//            "     e.user_no in " +
//            "       <foreach collection = 'condition.dataAuthoritySearchCondition.userNoListByDataAuthority' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "           #{item}" +
//            "       </foreach>" +
//            "   </if>" +
//            "   <if test = 'condition.dataAuthoritySearchCondition.deptCodeListByDataAuthority.size() &gt; 0 " +
//            "   or condition.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.userNoListByDataAuthority.size() &gt; 0 or condition.dataAuthoritySearchCondition.indCodeListByDataAuthority.size() &gt; 0 ' > " +
//            "       ) " +
//            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
//            " and " +
//            " </if>"+
//            " </if>" +
//            "</if>" +
//            // 发货日期范围
//            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
//            "  e.ship_date &gt;= #{condition.strFromDate} and e.ship_date &lt;= #{condition.strToDate} " +
//            " </if>"+
//            "</trim>"+
//            "<if test = ' condition.agentSearch != null and condition.agentSearch != \"\" and condition.agentSearch != \"1\" ' >" +
//            " union all " +
//            "SELECT   " +
//            "e.[id] " +
//            ",e.[invoice_no] " +
//            ",e.[delivery_no] " +
//            ",e.[order_no] " +
//            ",e.[item_no] " +
//            ",e.[order_fno] " +
//            ",e.[model_no] " +
//            ",e.[quantity] " +
//            ",e.[barcode] " +
//            ",e.[customer_no] " +
//            ",e.[user_no] " +
//            ",e.[ship_date] " +
//            ",e.[express_no] " +
//            ",e.[express_company] " +
//            ",e.[warehouse_code] " +
//            ",e.[price] " +
//            ",e.[opt_code] " +
//            ",e.[corder_no] " +
//            ",e.[cmodel_no] " +
//            ",e.[case_no] " +
//            ",e.[weight] " +
//            ",e.[order_type] " +
//            ",e.[invoice_flag] " +
//            ",e.[invoice_time] " +
//            ",e.[sign_time] " +
//            ",e.[stock_code] " +
//            ",e.[dlv_date] " +
//            ",e.[orOrderNo] " +
//            ",e.[sign_status] " +
//            ",e.[sender] " +
//            ",e.[dlv_site] " +
//            ",e.[volume] " +
//            ",e.[box_type] " +
//            ",e.[create_time] " +
//            ",e.[create_user] " +
//            ",e.[update_time] " +
//            ",e.[update_user] " +
//            ",e.[dept_no] " +
//            ",e.[sign_order_no] " +
//            ",e.[dlv_address] " +
//            ",e.[ContactPsn] " +
//            ",e.[end_user] " +
//            ",c.name " +
//            ",c.CustomerType " +
//            ",c.HRUnitId " +
//            ",c.HLCode " +
//            "  FROM [ops_core].[dbo].[expdetail] e  " +
//            "  inner join [ops_core].[dbo].ops_customer c on e.end_user = c.customer_no "+
//            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
//            // 单据状态
//            "<if test = 'condition.status != null and condition.status != \"\" ' >" +
//            " e.sign_status = #{condition.status} and " +
//            "</if> " +
//            // 发票号
//            "<if test = 'condition.invoiceNo != null and condition.invoiceNo != \"\" ' >" +
//            " e.invoice_no like #{condition.invoiceNo} and " +
//            "</if> " +
//            // 仓库
//            "<if test = 'condition.warehouseCode != null and condition.warehouseCode != \"\" ' >" +
//            " e.warehouse_code like #{condition.warehouseCode} and " +
//            "</if> " +
//            // 出库单号
//            "<if test = 'condition.deliveryNo != null and condition.deliveryNo != \"\" ' >" +
//            " e.delivery_no like #{condition.deliveryNo} and " +
//            "</if> " +
//            // 客户代码
//            "<if test = 'condition.customerNo != null and condition.customerNo != \"\" ' >" +
//            " e.customer_no = #{condition.customerNo} and " +
//            "</if> " +
//            // 用户代码
//            "<if test = 'condition.userNo != null and condition.userNo != \"\" ' >" +
//            " e.user_no = #{condition.userNo} and " +
//            "</if> " +
//            // 货物条码
//            "<if test = 'condition.barcode != null and condition.barcode != \"\" ' >" +
//            " e.barcode = #{condition.barcode} and " +
//            "</if> " +
//            // 客户订单号
//            "<if test = 'condition.corderNo != null and condition.corderNo != \"\" ' >" +
//            " e.corder_no like #{condition.corderNo} and " +
//            "</if> " +
//            // 快递单号
//            "<if test = 'condition.expressNo != null and condition.expressNo != \"\" ' >" +
//            " e.express_no like #{condition.expressNo} and " +
//            "</if> " +
//            // 合同订单号
//            "<if test = 'condition.orOrderNo != null and condition.orOrderNo != \"\" ' >" +
//            " e.orOrderNo like #{condition.orOrderNo} and " +
//            "</if> " +
//            // 订单号
//            "<if test = 'condition.orderNo != null and condition.orderNo != \"\" ' >" +
//            " e.order_fno like #{condition.orderNo} and " +
//            "</if> " +
//            // 订单类型
//            "<if test = ' orderType != null and orderType.size() &gt; 0' >" +
//            " e.order_type in  " +
//            "   <foreach collection = 'orderType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
//            "       #{item}" +
//            "   </foreach>" +
//            " and "+
//            "</if>" +
//            " e.end_user = 'C1D7V' and  " +
//            // 发货日期范围
//            " <if test = ' condition.strFromDate != null and condition.strToDate != null ' >" +
//            "  e.ship_date &gt;= #{condition.strFromDate} and e.ship_date &lt;= #{condition.strToDate} " +
//            " </if>"+
//            "</trim>"+
//            "</if>" +
//            " ) e" +
//            " order by e.ship_date desc, e.order_no asc , e.item_no asc "+
//            "</script>")
//    @Options(timeout = 60)
//    List<ExpdetailVO> exportExpdetail(@Param("condition") ExpdetailRequest condition,
//                                    @Param("orderType") List<String> orderType);


}
