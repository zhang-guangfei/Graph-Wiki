package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：C14717
 * @version: $ bugid 19186 合并smccode
 * @description：
 * @date ：Created in 2025/11/27 8:37
 */
public interface SplitSmcCodeDao {

    @Select("SELECT top 1  merge_invoice_id ,original_invoice_no ,split_invoice_id ,split_invoice_no from ops_core.dbo.invoice_split " +
            " where delflag =0 and split_invoice_id = #{invoiceId}  ")
    InvoiceSplit getInvoiceSplitListById(Long invoiceId);

    @Update("UPDATE [ops_core].[dbo].ops_purchaseInvoice set sub_code =#{subCode},receiveWarehouseId =#{receiveWarehouseId} where orderNo=#{orderNo} and itemNo=#{itemNo} and isnull(splitItemNo,0)=#{splitItemNo}")
    Integer updatePurchaseInvoiceByOrderNo(@Param("orderNo") String orderNo,
                                           @Param("itemNo") Integer itemNo,
                                           @Param("splitItemNo") Integer splitItemNo,
                                           @Param("subCode") String subCode,
                                           @Param("receiveWarehouseId") String receiveWarehouseId);

    @Select("SELECT top 1 warehouseid  from ops_po_destination_config where productType IN (1,2) and  sub_code =#{subCode}")
    String getWarehouseCodeBySubCode(String subCode);

    @Select("select top 1 sub_code,receiveWarehouseId FROM [ops_core].[dbo].ops_purchaseInvoice where orderNo=#{orderNo} and itemNo=#{itemNo} and isnull(splitItemNo,0)=#{splitItemNo}")
    OpsPurchaseinvoice getSubCodeByOrderNo(@Param("orderNo") String orderNo,
                                           @Param("itemNo") Integer itemNo,
                                           @Param("splitItemNo") Integer splitItemNo);

    @Select("SELECT top 1 logistics_status,carried ,express_code from transfer_info where delflag =0 and invoice_id =#{invoiceId}")
    TransferInfo  getTransferInfoInvoiceId(Long invoiceId );

    @Select("SELECT * from transfer_info where delflag =0 and DateDiff(day,create_time,getdate()) <= 90 and  invoice_no =#{invoiceNo}")
    List<TransferInfo> getTransferInfoExpress(String invoiceNo );

    @Select("SELECT count(id) from transfer_info where delflag =0 and DateDiff(day,create_time,getdate()) <= 90 and  invoice_no =#{invoiceNo} and express_code =#{expressCode}")
    Integer countTransferInfoExpress(String invoiceNo, String expressCode);

    @Select("SELECT count(id) FROM transfer_info where delflag =0 and DateDiff(day,create_time,getdate()) <= 90 and invoice_no = #{invoiceNo}  ")
    Integer countTransferInfo(String invoiceNo);

    @Select("SELECT top 1 split_invoice_no from invoice_split where delflag =0 and split_invoice_id = #{invoiceId}  ")
    String getInvoiceSplitById(Long invoiceId);


    @Select("SELECT count(id) FROM imp_invoice_master where  DateDiff(day,create_time,getdate()) <= 90 and invoice_no = #{invoiceNo}  ")
    Integer countImpInvoiceMaster(String invoiceNo);

    @Select("SELECT top 1 invoice_no ,id  FROM imp_invoice_master where  DateDiff(day,create_time,getdate()) <= 90 and invoice_no = #{invoiceNo}  ")
    ImpInvoiceMaster getImpInvoiceMaster(String invoiceNo);

    @Select("SELECT count(id) FROM invoice_split where delflag=0 and DateDiff(day,create_time,getdate()) <= 90 and original_invoice_no = #{invoiceNo} ")
    Integer countInvoiceSplit(String invoiceNo);

    @Select("SELECT * FROM invoice_split where delflag=0 and DateDiff(day,create_time,getdate()) <= 90 and original_invoice_no = #{invoiceNo} ")
    List<InvoiceSplit> findInvoiceSplit(String invoiceNo);

    @Select("SELECT InspectionsGroupId FROM ops_sharedb.dbo.MDM_V_InspectionsGroup where Model = #{modelNo}")
    String getProductType(String modelNo);

    @Select("select smccode ,sub_code from ops_po_destination_config " +
            " where warehouseid=#{warehouseCode} and supplierId=#{supplierId} " +
            " and productType=#{productType}  and orderType=#{ordType}  and trans_type=#{transType} ")
    List<OpsPoDestinationConfig> getSmcCodeAndSubCode(@Param("supplierId") String supplierId, @Param("warehouseCode") String warehouseCode
            , @Param("productType") Integer productType, @Param("ordType") String ordType, @Param("transType") String transType);


    @Select("SELECT order_no,item_no,split_item_no FROM imp_invoice_detail where invoice_id=#{invoiceId} group by order_no,item_no,split_item_no")
    List<ImpInvoiceDetail> getImpInvoiceDetailList(Long invoiceId);
}
