package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.ImpInvoiceDetailDO;
import com.smc.smccloud.model.invoice.ImpInvoiceDetailDiffVO;
import com.smc.smccloud.model.invoice.ImpInvoiceErrorDO;
import com.smc.smccloud.model.invoice.ImpInvoiceMasterDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 *  * Author: B90034
 *  * Date: 2021-12-02 13:24
 *  * Description:
 */
@Mapper
public interface ImpInvoiceDetailMapper extends BaseMapper<ImpInvoiceDetailDO> {
    @Select("select * from (select order_no as orderNo,SUM(Quantity) AS quantity ,SUM(Price*Quantity) as amount " +
            " from Imp_invoice_detail where invoice_id=#{invoiceId}" +
            " group by order_no) AS A full join (" +
            " select order_no as packOrderNo,SUM(Quantity) AS packQuantity ,SUM(Price*Quantity) as packAmount" +
            " from Imp_invoice_detail_pack " +
            " where invoice_id=#{invoiceId}" +
            " group by order_no) B on A.orderNo=B.packOrderNo " +
            " where isnull(a.amount,0)<>isnull(b.packAmount,0)")
    List<ImpInvoiceDetailDiffVO> listImpInvoiceDetailDiffByInvoiceId(@Param("invoiceId") Integer invoiceId);

    @Update("update imp_invoice_master set total_qty=b.sumqty,order_qty=b.qty,box_qty=b.boxqty,weight=b.weight,amount=b.amount " +
            " from imp_invoice_master a inner join (" +
            "  select d.invoice_id,sum(d.quantity) as sumqty,count(d.quantity)as qty,count(d.case_no) as boxqty,sum(d.weight) as weight,sum(d.quantity*d.price) as amount" +
            "  from imp_invoice_master m inner join  Imp_invoice_detail d on m.id=d.invoice_id where m.id=#{invoiceId}" +
            "  group by d.invoice_id" +
            "  ) b on a.id=b.invoice_id" +
            "  where id=#{invoiceId}")
    Integer updateImpInvoiceQty(@Param("invoiceId") Integer invoiceId);

    @Select("select sum(quantity) as total_qty,count(distinct full_order_no)as order_qty,count(case_no) as box_qty," +
            " sum(weight) as weight,sum(quantity*price) as amount" +
            " from Imp_invoice_detail with(nolock) where invoice_id=#{invoiceId}")
    ImpInvoiceMasterDO getTotalImpInvoiceDetail(@Param("invoiceId") Long invoiceId);

    @Update("update imp_invoice_master set total_qty=b.sumqty,order_qty=b.ordqty,box_qty=b.boxqty,weight=b.weight,amount=b.amount " +
            " from imp_invoice_master a inner join (" +
            "  select d.invoice_id,sum(d.quantity) as sumqty,count(distinct d.full_order_no)as ordqty,count(d.case_no) as boxqty,sum(d.weight) as weight,sum(d.quantity*d.price) as amount" +
            "  from imp_invoice_master m inner join  Imp_invoice_detail_pack d on m.id=d.invoice_id where m.id=#{invoiceId}" +
            "  group by d.invoice_id" +
            "  ) b on a.id=b.invoice_id" +
            "  where id=#{invoiceId}")
    Integer updateImpInvoiceByPack(@Param("invoiceId") Integer invoiceId);

    @Select("select poNo,poItemNo,full_order_no as orderNo ,model_no,sum(quantity) as qty \n" +
            " from Imp_invoice_detail with(nolock)\n" +
            " where invoice_id = #{id} \n" +
            " group by poNo,poItemNo,full_order_no,model_no")
    List<ImpInvoiceErrorDO> selectInvoiceDetailByInvoiceId(@Param("id") String id);

    @Select(" select distinct invoice_id from Imp_invoice_detail where status!=5 and oversea_invoice_no=#{invoiceNo} and supplier_code=#{supplierCode}")
    List<Long> getInvoiceIdByNo(@Param("invoiceNo") String invoiceNo,@Param("supplierCode") String supplierCode);

    @Select("select isnull(packModelNo,costModelNo) as modelNo from " +
            " (" +
            " select model_no as packModelNo,sum(quantity) as packQty" +
            " from Imp_invoice_detail_pack where  invoice_id=#{invoiceId} and status<>9 and status<>7 " +
            " group by  model_no) as a" +
            " full join " +
            " (" +
            " select model_no as costModelNo,sum(quantity) as costQty" +
            " from Imp_invoice_detail where  invoice_id=#{invoiceId} and status<>9 and nonCommercial<>'1' and nonCommercial<>'*' " +
            " group by  model_no" +
            " ) b on b.costModelNo=a.packModelNo" +
            " where isnull(a.packQty,0)<>isnull(b.costqty,0)")
    List<String> getPackAndDetailDiffModelNo(@Param("invoiceId") Long invoiceId);

}
