package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.TmpImpInvoiceErrorDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-08-14
 */
@Mapper
@DS("opsdb")
public interface TmpImpInvoiceErrorMapper extends BaseMapper<TmpImpInvoiceErrorDO> {

    // 更新采购数量
    @Update(" update tmp_imp_invoice_error set po_qty=p.quantity-p.qtyImport,po_model_no=p.modelNo,\n" +
            "  po_warehouse_code=p.receiveWarehouseId\n" +
            "  from tmp_imp_invoice_error i with(nolock)\n" +
            "  inner join ops_purchaseInvoice p with(nolock) on p.poNo=i.poNo  and p.lineItem=i.poItemNo \n" +
            "  where i.invoice_id=#{id}")
    void updateTmpImpInvoiceErrorForPoQty(@Param("id") String id);

    // 数量有差异是 备注数量差异
    @Update("update tmp_imp_invoice_error set error_type=1,error_text='数量差异'\n" +
            "  where invoice_id = #{id} " +
            "  and qty<>pack_qty or qty>po_qty ")
    void updateDiffQty(@Param("id") String id);

    @Update(" update tmp_imp_invoice_error \n" +
            "  set error_type=isnull(error_type,0)|2 ,error_text= isnull(error_text,'') +' 型号不同'\n" +
            "  where model_no<>po_model_No and invoice_id= #{id} ")
    void updateDiffModelNo(@Param("id") String id);

    @Update(" update tmp_imp_invoice_error set invoice_no=m.invoice_no\n" +
            " from tmp_imp_invoice_error i inner join imp_invoice_master m on m.id=i.invoice_id \n" +
            " where i.invoice_id= #{id}")
    void updateTmpImpInvoiceForInvoiceNo(@Param("id") String id);

}
