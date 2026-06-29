package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.ImpInvoiceMaster;
import com.sales.ops.db.entity.OpsPoDeliveryFact;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.PurchaseStatusRule;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PoDeliverQueryDao {


    @Select("<script>select "
            + "stateCode,"
            + "supplierId,"
            + "transType,"
            + "hopeExportDate,"
            + "replyOrderNo,"
            + "replyOrderDate,"
            + "purchaseDate,"
            + "producefactory as produceFactory,"
            + "replyExportDate,"
            + "beginProduceDate,"
            + "customs_date as customsDate,"
            + "invoiceNo,"
            + "invoiceId"
            + " from ops_purchaseInvoice a with(nolock)"
            + " where a.orderno=#{orderno} "
            + " <if test='itemno != null'> and a.itemno=#{itemno} </if>"
            + " <if test='splitno != null and splitno != 0'> and a.splitItemNo=#{splitno} </if>"
            + " <if test='splitno == null or splitno == 0'> and a.splitItemNo is null </if>"
            + "</script>")
    List<OpsPurchaseinvoice> selectDeliverInfoByOrderNo(@Param("orderno") String orderno,
                                                        @Param("itemno") Integer itemno,
                                                        @Param("splitno") Integer splitno);


    @Select("select * from imp_invoice_master where invoice_no=#{invoiceNo} and id=#{invoiceId}")
    List<ImpInvoiceMaster> selectImpInvoiceMasterByInvoiceNo(@Param("invoiceNo") String invoiceNo,
                                                             @Param("invoiceId") Long invoiceId);


    @Select("<script>select isnull(sum(quantity), 0) as totalQuantity from Imp_invoice_detail "
            + "where invoice_id=#{invoiceId} and invoice_no=#{invoiceNo} "
            + " and order_no=#{orderno} and item_no=#{itemno} "
            + " <if test='splitno != null and splitno != 0'> and split_item_no=#{splitno} </if>"
            + " <if test='splitno == null or splitno == 0'> and split_item_no is null </if></script>")
    Integer selectImpDetailTotalQuantity(@Param("orderno") String orderno,
                                         @Param("itemno") Integer itemno,
                                         @Param("splitno") Integer splitno,
                                         @Param("invoiceNo") String invoiceNo,
                                         @Param("invoiceId") Long invoiceId);


    @Select("<script>select top 1 * from ops_po_delivery_fact where del_flag=0 and order_no=#{orderno} "
            + " and item_no=#{itemno} "
            + " <if test='splitno != null and splitno != 0'> and split_no=#{splitno} </if>"
            + " <if test='splitno == null or splitno == 0'> and split_no is null </if>"
            + " order by coalesce(update_time, create_time) desc</script>")
    OpsPoDeliveryFact selectLatestFactByOrderNo(@Param("orderno") String orderno,
                                                @Param("itemno") Integer itemno,
                                                @Param("splitno") Integer splitno);

    @Select("select * from purchase_status_rule")
    List<PurchaseStatusRule> selectPurchaseStatusRule();


    @Update("<script>update ops_purchaseInvoice "
            + " set detailStatusCode=#{detailStatusCode}, statusDescription=#{statusDescription} "
            + " where orderno=#{orderno} "
            + " <if test='itemno != null'> and itemno=#{itemno} </if>"
            + " <if test='splitno != null and splitno != 0'> and splitItemNo=#{splitno} </if>"
            + " <if test='splitno == null or splitno == 0'> and splitItemNo is null </if>"
            + "</script>")
    Integer updatePurchaseInvoieDetailStatusAndStatusDesc(@Param("orderno") String orderno,
                                                          @Param("itemno") Integer itemno,
                                                          @Param("splitno") Integer splitno,
                                                          @Param("detailStatusCode") String detailStatusCode,
                                                          @Param("statusDescription") String statusDescription);

}
