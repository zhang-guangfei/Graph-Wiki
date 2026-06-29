package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.ImpInvoiceMaster;
import com.sales.ops.db.entity.OpsPoInvoice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/7 16:35
 */
public interface ImpInvoiceMasterDao {

    @Select("select top 1 * from imp_invoice_master where invoice_No=#{invoiceNo} and supplier_Code=#{supplierNo} and status<>9 order by ship_date desc")
    ImpInvoiceMaster getLastImpInvoiceMaster(@Param("invoiceNo") String invoiceNo,
                                             @Param("supplierNo") String supplierNo);


    @Select("SELECT top 1 id,invoice_id ,status  from ops_po_invoice where invoice_id = #{invoiceId}")
    OpsPoInvoice getPoInvoiceOne(Long invoiceId);

    //方案V2,opt_status分两位，第一位是发票确认，第二位是发票签收
    @Select("select *from imp_invoice_master where confirm_date is not null and SUBSTRING(opt_status, 1, 1) = '0'")
    List<ImpInvoiceMaster> getImpInvoiceMasterConfirmDate();

    //查询opt_status字段中第二位字符是0的，SUBSTRING(opt_status, 2, 1) 从第二个字符开始取1个字符
    @Select("select *from imp_invoice_master where arrive_date is not null and SUBSTRING(opt_status, 2, 1) = '0'")
    List<ImpInvoiceMaster> getImpInvoiceMasterSignDate();

    //V3版本opt_status只有一位，直接查询 19413-38
    @Select("select *from imp_invoice_master where arrive_date is not null and opt_status = '0'")
    List<ImpInvoiceMaster> getImpInvoiceMasterSignDateV3();
}
