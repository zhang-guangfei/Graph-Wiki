package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.model.MergeInvoiceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * bugid 19186 合并smccode
 * @author ：C14717
 * @version: $ bugid 19186 合并smccode
 * @description：
 * @date ：Created in 2025/12/1 8:36
 */
@Mapper
@DS("opsdb")
public interface MergeSmcCodeDao {

    @Select("SELECT merge_invoice_id ,original_invoice_no ,split_invoice_id ,split_invoice_no from ops_core.dbo.invoice_split " +
            " where delflag =0 and merge_invoice_id = #{invoiceId}  ")
    List<MergeInvoiceDto> getInvoiceSplitListByMegreId(Long invoiceId);

    @Select("SELECT merge_invoice_id ,original_invoice_no ,split_invoice_id ,split_invoice_no from ops_core.dbo.invoice_split " +
            " where delflag =0 and split_invoice_id = #{invoiceId}  ")
    List<MergeInvoiceDto> getInvoiceSplitListById(Long invoiceId);


    @Select("<script> SELECT sum(total_qty) from ops_core.dbo.imp_invoice_master where id in " +
            "<if test = 'invoiceDtos != null and  invoiceDtos.size() &gt; 0' >" +
            "  <foreach collection='invoiceDtos' item='item' index='index'  open='(' separator=',' close=')' > " +
            " #{item.splitInvoiceId} " +
            "  </foreach>" +
            "</if>" +
            " </script>")
    Integer getImpInvoiceMasterByIds(List<MergeInvoiceDto> invoiceDtos);


    @Update("UPDATE transfer_info set logistics_status = 3,update_time =GETDATE(),updator = #{updator} where delflag =0 and invoice_id = #{invoiceId}")
    Integer upTransferInfoStatus(Long invoiceId,String updator);

}
