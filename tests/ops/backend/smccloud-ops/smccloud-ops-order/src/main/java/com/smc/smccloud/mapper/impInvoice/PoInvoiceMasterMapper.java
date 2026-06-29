package com.smc.smccloud.mapper.impInvoice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.*;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2021/12/13 14:00
 */
@DS("opsdb")
@Mapper
public interface PoInvoiceMasterMapper  extends BaseMapper<OpsPoInvoiceDO> {

    /**
     * 查询明细的合计金额
     * @param invoiceId
     * @return
     */
    @Select("select sum(amount) as amount,sum(amount_rmb) as amount_rmb ,sum(isnull(customs_fee,0)) as customs_fee,sum(isnull(excise_tax,0)) as excise_tax," +
            " sum(isnull(vat_fee,0)) as vat_fee,sum(isnull(trans_fee,0)) as trans_fee,sum(isnull(other_fee,0)) as other_fee," +
            " sum(isnull(amount_total,0)) as amount_total " +
            " from ops_po_invoice_detail where invoice_id=#{invoiceId}  and status<>'9' and imp_type<>'1'")
    OpsPoInvoiceDO getPoInvoiceDetailAmount(@Param("invoiceId")Long invoiceId);


    /**
     * 查询无商业价值的关税金额
     * @param invoiceId
     * @return
     */
    @Select("select sum(isnull(customs_fee,0)) as customs_fee" +
            " from ops_po_invoice_detail where invoice_id=#{invoiceId}  and status<>'9' and imp_type='1'")
    BigDecimal getNonCommercialCustomsFee(@Param("invoiceId")Long invoiceId);
    /**
     * 查询明细的总合计金额
     * @param invoiceId
     * @return
     */
    @Select("select sum(isnull(amount_total,0)) as amount_total " +
            " from ops_po_invoice_detail where invoice_id=#{invoiceId}  and status<>'9' and imp_type<>'1'")
    BigDecimal getPoInvoiceDetailAmountTotal(@Param("invoiceId")Long invoiceId);
    @Select("select invoice_id,invoice_no,sum(isnull(amount,0)) as amount,sum(isnull(amount_adjust,0)) as amount_adjust,sum(isnull(amount_rmb,0))as amount_rmb," +
            "sum(isnull(customs_fee,0)) as customs_fee,sum(isnull(vat_fee,0)) as vat_fee,sum(isnull(trans_fee,0)) as trans_fee," +
            "sum(isnull(other_fee,0)) as other_fee,sum(isnull(amount_total,0)) as amount_total " +
            "from ops_po_invoice_detail where invoice_id=#{invoiceId}  and status<>'9' and imp_type<>'1' " +
            "group by invoice_id,invoice_no ")
    OpsPoInvoiceDO getPoInvoiceDetailSum(@Param("invoiceId")Long invoiceId);


    @Update("update ops_po_invoice set amount_total=isnull(amount_rmb,0)+ISNULL(customs_fee,0)+ISNULL(excise_tax,0)+ISNULL(trans_fee,0)+ISNULL(other_fee,0) " +
            " where invoice_id=#{invoiceId}")
    void updatepoInvoiceMasterAmountTotal(@Param("invoiceId") Long invoiceId);

    @Select("select TransCurrency from supplier where id=#{supplierCode}")
    String getCurrencyCode(@Param("supplierCode") String supplierCode);

    @Select("<script>" +
            "select supplier_code as suppliercode,invoice_no as invoiceno,invoice_no as orderno,invoice_no as overseainvoiceno," +
            " cost_time as costtime,amount,amount_rmb as amountrmb,customs_fee as customsfee,trans_fee as transfee,excise_tax as excisetax," +
            " other_fee as otherfee,exchange_rate as exchangerate,currency_code as currencycode,amount_total as amounttotal," +
            " invoice_date as invoicedate,pay_day as payday" +
            " from ops_po_invoice" +
            " where supplier_code in('JP','CN','TZ','YZ','CM','CT','GZ','HK','TW','GN')" +
            "<if test = 'request.invoiceNo != null and request.invoiceNo != \"\" ' >" +
            " and invoice_no = #{request.invoiceNo}"+
            "</if>"+
            "<if test = 'request.supplierCode != null and request.supplierCode != \"\" ' >" +
            " and supplier_code = #{request.supplierCode}"+
            "</if>"+
            "<if test = 'request.status != null and request.status != \"\" ' >" +
            " and status = #{request.status}"+
            "</if>"+
            "<if test = 'request.invoiceType != null and request.invoiceType != \"\" ' >" +
            " and invoice_type = #{request.invoiceType}"+
            "</if>"+
            "<if test = 'request.id != null and request.id != \"\" ' >" +
            " and id = #{request.id}"+
            "</if>"+
            "<if test = 'request.currencyCode != null and request.currencyCode != \"\" ' >" +
            " and currency_code = #{request.currencyCode}"+
            "</if>"+
            "<if test = 'request.qryDateType==1 and fromDate != null and fromDate != \"\" ' >" +
            " and imp_date &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==1 and toDate != null and toDate != \"\" ' >" +
            " and imp_date &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==2 and fromDate != null and fromDate != \"\" ' >" +
            " and cost_time &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==2 and toDate != null and toDate != \"\" ' >" +
            " and cost_time &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==3 and fromDate != null and fromDate != \"\" ' >" +
            " and receive_time &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==3 and toDate != null and toDate != \"\" ' >" +
            " and receive_time &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==4 and fromDate != null and fromDate != \"\" ' >" +
            " and invoice_date &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==4 and toDate != null and toDate != \"\" ' >" +
            " and invoice_date &lt; #{toDate}"+
            "</if>"+
            " order by invoice_no"+
            "</script>")
    List<PoInvoiceDetailReportVO> listPoInvoiceDetailReport(@Param("request") PoInvoiceMasterRequest request,
                                                            @Param("fromDate") String fromDate,
                                                            @Param("toDate") String toDate);

    @Select("<script>" +
            "select m.supplier_code as suppliercode,m.invoice_no as invoiceno,d.order_no as orderno,d.oversea_invoice_no as overseainvoiceno," +
            " m.cost_time as costtime,d.amount,d.amount_rmb as amountrmb,d.customs_fee as customsfee,d.trans_fee as transfee," +
            "  d.other_fee as otherfee,m.exchange_rate as exchangerate,m.currency_code as currencycode,d.amount_total as amounttotal," +
            " m.invoice_date as invoicedate,m.pay_day as payday" +
            "  from ops_po_invoice m inner join ops_po_invoice_detail d on m.invoice_id=d.invoice_id" +
            "  where m.supplier_code in('US','AP')  and d.status !='9'   and isnull(d.noncommercial,'0')!='1' " +
            "<if test = 'request.invoiceNo != null and request.invoiceNo != \"\" ' >" +
            " and m.invoice_no = #{request.invoiceNo}"+
            "</if>"+
            "<if test = 'request.supplierCode != null and request.supplierCode != \"\" ' >" +
            " and m.supplier_code = #{request.supplierCode}"+
            "</if>"+
            "<if test = 'request.status != null and request.status != \"\" ' >" +
            " and m.status = #{request.status}"+
            "</if>"+
            "<if test = 'request.id != null and request.id != \"\" ' >" +
            " and m.id = #{request.id}"+
            "</if>"+
            "<if test = 'request.currencyCode != null and request.currencyCode != \"\" ' >" +
            " and currency_code = #{request.currencyCode}"+
            "</if>"+
            "<if test = 'request.invoiceType != null and request.invoiceType != \"\" ' >" +
            " and invoice_type = #{request.invoiceType}"+
            "</if>"+
            "<if test = 'request.qryDateType==1 and fromDate != null and fromDate != \"\" ' >" +
            " and m.imp_date &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==1 and toDate != null and toDate != \"\" ' >" +
            " and m.imp_date &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==2 and fromDate != null and fromDate != \"\" ' >" +
            " and m.cost_time &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==2 and toDate != null and toDate != \"\" ' >" +
            " and m.cost_time &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==3 and fromDate != null and fromDate != \"\" ' >" +
            " and m.receive_time &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==3 and toDate != null and toDate != \"\" ' >" +
            " and m.receive_time &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==4 and fromDate != null and fromDate != \"\" ' >" +
            " and m.invoice_date &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==4 and toDate != null and toDate != \"\" ' >" +
            " and m.invoice_date &lt; #{toDate}"+
            "</if>"+
            " order by m.invoice_no"+
            "</script>")
    List<PoInvoiceDetailReportVO> listusAndApPoInvoiceDetailReport(@Param("request") PoInvoiceMasterRequest request,
                                                                   @Param("fromDate") String fromDate,
                                                                   @Param("toDate") String toDate);

    @Select("<script>" +
            "select m.supplier_code as suppliercode,m.invoice_no as invoiceno,d.oversea_invoice_no as orderno,d.oversea_invoice_no as overseainvoiceno," +
            "  m.cost_time as costtime,sum(d.amount) as amount,sum(d.amount_rmb) as amountrmb,sum(d.customs_fee) as customsfee,sum(d.trans_fee) as transfee," +
            "  sum(d.other_fee) as otherfee,m.exchange_rate as exchangerate,m.currency_code as currencycode,sum(d.amount_total) as amounttotal," +
            "  m.invoice_date as invoicedate,m.pay_day as payday" +
            "  from ops_po_invoice m inner join ops_po_invoice_detail d on m.invoice_id=d.invoice_id" +
            "  where m.supplier_code not in('JP','CN','TZ','YZ','CM','CT','GZ','US','AP','HK','TW','GN') and d.status !='9' and isnull(d.noncommercial,'0')!='1'   " +
            "<if test = 'request.invoiceNo != null and request.invoiceNo != \"\" ' >" +
            " and m.invoice_no = #{request.invoiceNo}"+
            "</if>"+
            "<if test = 'request.supplierCode != null and request.supplierCode != \"\" ' >" +
            " and m.supplier_code = #{request.supplierCode}"+
            "</if>"+
            "<if test = 'request.status != null and request.status != \"\" ' >" +
            " and m.status = #{request.status}"+
            "</if>"+
            "<if test = 'request.id != null and request.id != \"\" ' >" +
            " and m.id = #{request.id}"+
            "</if>"+
            "<if test = 'request.currencyCode != null and request.currencyCode != \"\" ' >" +
            " and currency_code = #{request.currencyCode}"+
            "</if>"+
            "<if test = 'request.invoiceType != null and request.invoiceType != \"\" ' >" +
            " and invoice_type = #{request.invoiceType}"+
            "</if>"+
            "<if test = 'request.qryDateType==1 and fromDate != null and fromDate != \"\" ' >" +
            " and m.imp_date &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==1 and toDate != null and toDate != \"\" ' >" +
            " and m.imp_date &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==2 and fromDate != null and fromDate != \"\" ' >" +
            " and m.cost_time &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==2 and toDate != null and toDate != \"\" ' >" +
            " and m.cost_time &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==3 and fromDate != null and fromDate != \"\" ' >" +
            " and m.receive_time &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==3 and toDate != null and toDate != \"\" ' >" +
            " and m.receive_time &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==4 and fromDate != null and fromDate != \"\" ' >" +
            " and m.invoice_date &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==4 and toDate != null and toDate != \"\" ' >" +
            " and m.invoice_date &lt; #{toDate}"+
            "</if>"+
            "  group by m.supplier_code,m.invoice_no,m.cost_time,m.exchange_rate,m.currency_code,m.invoice_date,m.pay_day,d.oversea_invoice_no"+
            "  order by m.invoice_no"+
            "</script>")
    //and m.cost_time>=#{fromDate} and m.cost_time<=#{toDate}
    List<PoInvoiceDetailReportVO> listOtherPoInvoiceDetailReport(@Param("request") PoInvoiceMasterRequest request,
                                                                 @Param("fromDate") String fromDate,
                                                                 @Param("toDate") String toDate);

    @Update("update ops_po_invoice_detail set ecode=p.ECode " +
            " from ops_po_invoice_detail d with (nolock) inner join product p with (nolock) on d.model_no=p.ModelNo " +
            " where invoice_id=#{invoiceId} and d.status<>'9' ")
    void updatepoInvoiceDeatailECode(@Param("invoiceId") Long invoiceId);

    @Select("select ECode from product where ModelNo=#{modelNo}")
    String getECodeByModelNo(@Param("modelNo") String modelNo);


    @Select("<script>" +
            "select ModelNo, ECode from product where "+
            "<if test = 'modelNos != null and  modelNos.size() &gt; 0' >" +
            "  <foreach collection = 'modelNos' item = 'item' index='index' open='(' close=')'  separator='or'> " +
            "   ModelNo= #{item} " +
            "  </foreach>" +
            "</if>"+
            "</script>")
    @MapKey("ModelNo")
    List<Map<String,String>> getECodeByModelNos(@Param("modelNos") List<String> modelNos);

    @Select("<script>" +
            "select m.supplier_code as suppliercode,m.invoice_no as invoiceno,d.order_no as orderno,d.oversea_invoice_no as overseainvoiceno," +
            " m.cost_time as costtime,d.amount,d.amount_rmb as amountrmb,d.customs_fee as customsfee,d.trans_fee as transfee," +
            "  d.other_fee as otherfee,m.exchange_rate as exchangerate,m.currency_code as currencycode,d.amount_total as amounttotal," +
            " m.invoice_date as invoicedate,m.pay_day as payday" +
            "  from ops_po_invoice m inner join ops_po_invoice_detail d on m.invoice_id=d.invoice_id" +
            " where   d.status !='9' "+
            "<if test = 'request.invoiceNo != null and request.invoiceNo != \"\" ' >" +
            " and m.invoice_no = #{request.invoiceNo}"+
            "</if>"+
            "<if test = 'request.supplierCode != null and request.supplierCode != \"\" ' >" +
            " and m.supplier_code = #{request.supplierCode}"+
            "</if>"+
            "<if test = 'request.status != null and request.status != \"\" ' >" +
            " and m.status = #{request.status}"+
            "</if>"+
            "<if test = 'request.id != null and request.id != \"\" ' >" +
            " and m.id = #{request.id}"+
            "</if>"+
            "<if test = 'request.qryDateType==1 and fromDate != null and fromDate != \"\" ' >" +
            " and m.imp_date &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==1 and toDate != null and toDate != \"\" ' >" +
            " and m.imp_date &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==2 and fromDate != null and fromDate != \"\" ' >" +
            " and m.cost_time &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==2 and toDate != null and toDate != \"\" ' >" +
            " and m.cost_time &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==3 and fromDate != null and fromDate != \"\" ' >" +
            " and m.receive_time &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==3 and toDate != null and toDate != \"\" ' >" +
            " and m.receive_time &lt; #{toDate}"+
            "</if>"+
            "<if test = 'request.qryDateType==4 and fromDate != null and fromDate != \"\" ' >" +
            " and m.invoice_date &gt;= #{fromDate}"+
            "</if>" +
            "<if test = 'request.qryDateType==4 and toDate != null and toDate != \"\" ' >" +
            " and m.invoice_date &lt; #{toDate}"+
            "</if>"+
            " order by m.invoice_no "+
            "</script>")
    List<PoInvoiceDetailReportVO> listPoInvoiceMasterAndDetail(@Param("request") PoInvoiceMasterRequest request,
                                                               @Param("fromDate") String fromDate,
                                                               @Param("toDate") String toDate);


    @Select(" select supplier_code,sum(amount) as amount,sum(customs_fee) as customs_fee,sum(excise_tax) as excise_tax,sum(trans_fee) as trans_fee,sum(other_fee) as other_fee" +
            " from imp_invoice_master where status=4 and invoice_date>=#{request.invoiceDateStart} and invoice_date<#{request.invoiceDateEnd}" +
            " group by supplier_code")
    List<ImpInvoiceMasterDO> listValueImpinvoice(@Param("request") ImpInvoiceMasterRequest request);

    @Select("select count(0) from ops_po_invoice_detail d with (nolock)" +
            "  inner join Imp_invoice_detail i with (nolock) on d.oversea_invoice_no=i.oversea_invoice_no and d.order_no=i.full_order_no" +
            "  where i.status=5 and d.status<>'9'  and d.invoice_id=#{invoiceId}")
    Integer getValueDataCount(@Param("invoiceId") Long invoiceId);

    @Select(" select top 1 price from  Imp_invoice_detail where status=5 and full_order_no=#{orderNo} and oversea_invoice_no=#{overseaInvoiceNo}")
    BigDecimal getValueInvoicePrice(@Param("orderNo") String orderNo,@Param("overseaInvoiceNo") String overseaInvoiceNo);

    @Update(" update ops_po_invoice set amount=m.amount,amount_rmb=m.amount_rmb,customs_fee=m.customs_fee,trans_fee=m.trans_fee,other_fee=m.other_fee,vat_fee=m.vat_fee " +
            " from ops_po_invoice o with(nolock) " +
            " inner join imp_invoice_master m with(nolock) on o.invoice_id=m.id " +
            "  where o.invoice_id=#{invoiceId}")
    void updateOpsPoInvoiceAmount(@Param("invoiceId") Long invoiceId);

    @Select(" select distinct o.invoice_id from ops_po_invoice o with(nolock)" +
            " left join  ops_po_invoice_detail d with(nolock) on o.invoice_id=d.invoice_id   and d.status<>'9' " +
            " where o.status=2 and (d.invoice_id is null or o.amount=0)")
    List<Long> listNoPoDetailInvoiceId();
}
