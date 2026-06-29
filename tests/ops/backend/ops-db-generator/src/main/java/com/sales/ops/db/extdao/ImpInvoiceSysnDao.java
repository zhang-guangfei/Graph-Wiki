package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.ImpInvoiceMaster;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.ProductPhysics;
import com.sales.ops.db.entity.Supplier;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @description:从交付系统导入Impinvoice三张表的数据，用到的手工的sql
 * @create: 2024-08-08 15:04
 */
public interface ImpInvoiceSysnDao {


    @Select("  <script> SELECT * \n" +
            "  FROM imp_invoice_master \n" +
            "  where invoice_no = #{invoiceNo}  \n" +
            "    AND status <![CDATA[ <> ]]> #{status}  \n" +
            "    AND (ship_date <![CDATA[ >= ]]> #{fromDate} OR create_time <![CDATA[ >= ]]> #{fromDate})" +
            " <if test=\"supplierCode != null  and supplierCode != ''\"> and supplier_code = #{supplierCode} </if> </script> ")
    List<ImpInvoiceMaster> selectExitMaster(@Param("invoiceNo") String invoiceNo, @Param("status") String status, @Param("fromDate") Date fromDate, @Param("supplierCode") String supplierCode);

    @Select("select top 1 receiveWarehouseId FROM [ops_core].[dbo].ops_purchaseInvoice where orderNo=#{orderNo} and itemNo=#{itemNo} and isnull(splitItemNo,0)=#{splitItemNo}")
    String getReceiveWarehouseCodeByOrderNo(@Param("orderNo") String orderNo,
                                            @Param("itemNo") Integer itemNo,
                                            @Param("splitItemNo") Integer splitItemNo);

    @Select("SELECT * FROM product_physics WHERE ModelNo=#{modelNo}")
    ProductPhysics getProductPhysicsByModelNo(@Param("modelNo") String modelNo);

    @Select("select top 1 receiveWarehouseId FROM ops_purchaseInvoice where poNo=#{poNo}")
    String getReceiveWarehouseCode(@Param("poNo") String poNo);


    @Select("select Top(1) id from supplier where name like #{name} order by len(name) asc")
    String getSupplierCodeByName(@Param("name") String name);

    @Select("SELECT id FROM currency where AbbrName=#{currency}")
    String getCurrencyIdByName(@Param("currency") String currency);

    @Select("select name from supplier where id=#{id}")
    String getSupplierNameByid(@Param("id") String id);

    @Select("select * from supplier")
    List<Supplier> getSupplierName();

    @Select("SELECT companyId FROM supplier WHERE id=#{id}")
    String getCompanyIdById(@Param("id") String id);


    @Select("select * from ops_purchaseInvoice where pono=#{pono} and lineitem=#{lineitem}")
    OpsPurchaseinvoice getByPono(String pono, Integer lineitem);

    @Select("select sum(quantity) as total_qty,count(distinct full_order_no)as order_qty,count(distinct case_no) as box_qty," +
            " sum(weight) as weight,sum(quantity*price) as amount" +
            " from imp_invoice_detail with(nolock) where invoice_id=#{invoiceId}")
    ImpInvoiceMaster getTotalImpInvoiceDetail(@Param("invoiceId") Long invoiceId); //bug19197 【OPS-PSI-PMS】OPS接入PSI传入的箱单发票数据，核对core库的master的数据，box_qty字段的值和psi中master的值不一致
}
