package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.ImpInvoiceError;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

public interface ImpInvoiceErrorDao {

    @Select("{#{Count,mode=OUT,jdbcType=INTEGER}=call dbo.ImpInvoiceCheck(#{invoiceId,mode=IN,jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    void checkImpInvoiceError(Map<String  ,Object> params);

    @Select("select * from imp_invoice_error where model_no like '3c-%' and po_model_no not like '3C-%' and invoice_id=#{invoiceId}")
    List<ImpInvoiceError> listImpInvoiceErrorToStoc(@Param("invoiceId")Long invoiceId);


    @Delete(" delete from imp_invoice_error  where invoice_id = #{invoiceId}" )
    void deleteErrorInvoice(@Param("invoiceId") String invoiceId);

    @Delete("delete from tmp_imp_invoice_error where invoice_id = #{invoiceId}")
    void deleteTemImpErrorInvoice(@Param("invoiceId") String invoiceId);


    // 更新采购数量
    @Update(" update imp_invoice_error set po_qty=p.quantity-p.qtyImport,po_model_no=p.modelNo,\n" +
            "  po_warehouse_code=p.receiveWarehouseId\n" +
            "  from imp_invoice_error i with(nolock)\n" +
            "  inner join ops_purchaseInvoice p with(nolock) on p.poNo=i.poNo  and p.lineItem=i.poItemNo \n" +
            "  where i.invoice_id=#{id}")
    void updateTmpImpInvoiceErrorForPoQty(@Param("id") String id);

    // 数量有差异是 备注数量差异
    @Update("update imp_invoice_error set error_type=1,error_text='数量差异'\n" +
            "  where invoice_id = #{id} " +
            "  and qty<>pack_qty or qty>po_qty ")
    void updateDiffQty(@Param("id") String id);

    @Update(" update imp_invoice_error \n" +
            "  set error_type=isnull(error_type,0)|2 ,error_text= isnull(error_text,'') +' 型号不同'\n" +
            "  where model_no<>po_model_No and invoice_id= #{id} ")
    void updateDiffModelNo(@Param("id") String id);


    @Update(" update imp_invoice_error set invoice_no=m.invoice_no\n" +
            " from imp_invoice_error i inner join imp_invoice_master m on m.id=i.invoice_id \n" +
            " where i.invoice_id= #{id}")
    void updateTmpImpInvoiceForInvoiceNo(@Param("id") String id);

    @Delete("delete from imp_invoice_error where invoice_id=#{id} and error_type = 0 ")
    void deleteCorrectInvoice(@Param("id") String id);

    @Select("<script>" +
            "update  imp_invoice_error set ignore_error= #{errorDO.ignoreError} ," +
            "ignore_psn= #{errorDO.ignorePsn} ,ignore_time= #{errorDO.ignoreTime} ," +
            "ignore_reason=#{errorDO.ignoreReason}    where "+
            "<if test = 'errorDOList != null and  errorDOList.size() &gt; 0' >" +
            "  <foreach collection = 'errorDOList' item = 'item' index='index' open='(' close=')'  separator='or'> " +
            "   (invoice_id= #{item.invoiceId} and invoice_no=#{item.invoiceNo} and order_no=#{item.orderNo}) " +
            "  </foreach>" +
            "</if>"+
            "</script>")
    void updateIgnoreInfo(@Param("errorDOList")  List<ImpInvoiceError> errorDOList, @Param("errorDO")  ImpInvoiceError errorDO);
}
