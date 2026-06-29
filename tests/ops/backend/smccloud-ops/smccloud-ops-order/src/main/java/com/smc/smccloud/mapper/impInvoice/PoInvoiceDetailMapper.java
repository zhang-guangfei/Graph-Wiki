package com.smc.smccloud.mapper.impInvoice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.PoInvoiceDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2021/12/13 14:00
 */
@DS("opsdb")
@Mapper
public interface PoInvoiceDetailMapper extends BaseMapper<PoInvoiceDetailDO> {

    @Update("update ops_po_invoice_detail set price_rmb=round(price*#{rate},4),amount_rmb=round(amount*#{rate},2) where invoice_id=#{invoiceId} and  status<>'9' ")
    void updatePOInvoiceDetailRMBAmount(@Param("invoiceId") Long invoiceId, @Param("rate") BigDecimal exchangeRate);

    @Update("update ops_po_invoice_detail set amount=round(isnull(price,0)*quantity,2), amount_rmb=round(isnull(price,0)*quantity,2)" +
            "  where invoice_id=#{invoiceId}  and  status<>'9'")
    void updateGZPOInvoiceDetailAmount(@Param("invoiceId") Long invoiceId);

    /**
     * 查询明细的金额最大值
     *
     * @param invoiceId
     * @return
     */
    @Select("select top 1 id,amount from ops_po_invoice_detail where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1' order by amount desc")
    PoInvoiceDetailDO getMaxPoInvoiceAmount(@Param("invoiceId") Long invoiceId);

    @Update("update ops_po_invoice_detail set amount=amount+#{avAmount} where invoice_id=#{invoiceId} and  status<>'9' and amount>0 and imp_type<>'1' ")
    void updatePoInvoiceDetailAmount(@Param("invoiceId") Long invoiceId, @Param("avAmount") BigDecimal avAmount);

    @Select("select top 1 id, amount_rmb from ops_po_invoice_detail where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'  order by amount_rmb desc")
    PoInvoiceDetailDO getMaxPoInvoiceAmountRmb(@Param("invoiceId") Long invoiceId);

    @Update("update ops_po_invoice_detail set amount_rmb=amount_rmb+#{avAmount} where invoice_id=#{invoiceId} and  status<>'9' and amount_rmb>0 and imp_type<>'1'")
    void updatePoInvoiceDetailAmountRmb(@Param("invoiceId") Long invoiceId, @Param("avAmount") BigDecimal avAmount);

    @Select("select top 1 id,customs_fee from ops_po_invoice_detail where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1' order by customs_fee desc")
    PoInvoiceDetailDO getMaxPoInvoiceCustomsFee(@Param("invoiceId") Long invoiceId);

    @Select("select top 1 id,excise_tax from ops_po_invoice_detail where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1' order by excise_tax desc")
    PoInvoiceDetailDO getMaxPoInvoiceExciseTax(@Param("invoiceId") Long invoiceId);

    @Update("update ops_po_invoice_detail set customs_fee=isnull(customs_fee,0)+round((amount/#{totalAmount})*#{diffAmount},2)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailCustomsFee(@Param("invoiceId") Long invoiceId, @Param("diffAmount") BigDecimal diffAmount, @Param("totalAmount") BigDecimal totalAmount);

    @Update("update ops_po_invoice_detail set customs_fee=isnull(customs_fee,0)+round((isnull(customs_fee,0)/#{totalAmount})*#{diffAmount},2)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailCustomsFeeByCustomsFeeRate(@Param("invoiceId") Long invoiceId, @Param("diffAmount") BigDecimal diffAmount, @Param("totalAmount") BigDecimal totalAmount);


    @Update("update ops_po_invoice_detail set excise_tax=isnull(excise_tax,0)+round((amount/#{totalAmount})*#{diffAmount},2)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailExciseTax(@Param("invoiceId") Long invoiceId, @Param("diffAmount") BigDecimal diffAmount, @Param("totalAmount") BigDecimal totalAmount);

    @Update("update ops_po_invoice_detail set excise_tax=isnull(excise_tax,0)+round((isnull(excise_tax,0)/#{totalAmount})*#{diffAmount},2)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailExciseTaxByExciseTaxRate(@Param("invoiceId") Long invoiceId, @Param("diffAmount") BigDecimal diffAmount, @Param("totalAmount") BigDecimal totalAmount);

    @Select("select top 1 id, vat_fee from ops_po_invoice_detail where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1' order by vat_fee desc")
    PoInvoiceDetailDO getMaxPoInvoiceVatfee(@Param("invoiceId") Long invoiceId);

    @Update("update ops_po_invoice_detail set vat_fee=isnull(vat_fee,0)+round((amount/#{totalAmount})*#{diffAmount},2)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailVatfee(@Param("invoiceId") Long invoiceId, @Param("diffAmount") BigDecimal diffAmount, @Param("totalAmount") BigDecimal totalAmount);

    @Select("select top 1 id, trans_fee from ops_po_invoice_detail where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1' order by trans_fee desc")
    PoInvoiceDetailDO getMaxPoInvoiceTransfee(@Param("invoiceId") Long invoiceId);

    @Update("update ops_po_invoice_detail set trans_fee=isnull(trans_fee,0)+round((amount/#{totalAmount})*#{diffAmount},2)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailTransfee(@Param("invoiceId") Long invoiceId, @Param("diffAmount") BigDecimal diffAmount, @Param("totalAmount") BigDecimal totalAmount);

    @Update("update ops_po_invoice_detail set trans_fee=isnull(trans_fee,0)+round((isnull(trans_fee,0)/#{totalAmount})*#{diffAmount},2)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailTransfeeByTransFeeRate(@Param("invoiceId") Long invoiceId, @Param("diffAmount") BigDecimal diffAmount, @Param("totalAmount") BigDecimal totalAmount);


    @Select("select top 1 id,max(other_fee) as other_fee from ops_po_invoice_detail where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1' group by id")
    PoInvoiceDetailDO getMaxPoInvoiceOtherfee(@Param("invoiceId") Long invoiceId);

    @Update("update ops_po_invoice_detail set other_fee=isnull(other_fee,0)+round((amount/#{totalAmount})*#{diffAmount},2)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailOtherfee(@Param("invoiceId") Long invoiceId, @Param("diffAmount") BigDecimal diffAmount, @Param("totalAmount") BigDecimal totalAmount);

    @Update("update ops_po_invoice_detail set other_fee=isnull(other_fee,0)+round((isnull(other_fee,0)/#{totalAmount})*#{diffAmount},2)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailOtherfeeByOtherfeeRate(@Param("invoiceId") Long invoiceId, @Param("diffAmount") BigDecimal diffAmount, @Param("totalAmount") BigDecimal totalAmount);


    @Update("update ops_po_invoice_detail set amount_total=isnull(amount_rmb,0)+isnull(customs_fee,0)+isnull(excise_tax,0)+isnull(trans_fee,0)+isnull(other_fee,0)" +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1';" +
            "update ops_po_invoice_detail set price_total=round(isnull(amount_total,0)/(quantity*1.0),4) " +
            " where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    void updatePoInvoiceDetailAmountTotal(@Param("invoiceId") Long invoiceId);


    @Select("select distinct oversea_invoice_no,order_no from ops_po_invoice_detail where invoice_id=#{invoiceId} and  status<>'9' and imp_type<>'1'")
    List<PoInvoiceDetailDO> listoverseaInvoiceNo(@Param("invoiceId") Long invoiceId);


    @Select("<script>" +
            "select sum(price*quantity) as amount from Imp_invoice_detail where status=5 and oversea_invoice_no in " +
            " <foreach collection='overseaList' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            " and full_order_no in "+
            " <foreach collection='orderNolist' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "</script>")
    BigDecimal getValueAmount(@Param("overseaList") List<String> overseaList,@Param("orderNolist") List<String> orderNolist);

    @Update("<script>" +
            "update Imp_invoice_detail set status=6 where status=5 and oversea_invoice_no in " +
            " <foreach collection='overseaList' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            " and full_order_no in "+
            " <foreach collection='orderNolist' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "</script>")
    Integer updateValueInvoiceStatus(@Param("overseaList") List<String> overseaList,@Param("orderNolist") List<String> orderNolist);

    @Select("select distinct oversea_invoice_no from Imp_invoice_detail where invoice_id=#{invoiceId}")
    List<String> listInvoiceoverseaNo(@Param("invoiceId") Long invoiceId);

    @Select("select sum(price*quantity) as amount from Imp_invoice_detail where invoice_id=#{invoiceId}")
    BigDecimal getImpInvoiceAmount(@Param("invoiceId") Long invoiceId);

    @Select("select sum(price*quantity) as amount from Imp_invoice_detail where status=5 and supplier_code=#{supplierCode} and oversea_invoice_no=#{invoiceNo}")
    BigDecimal getImpInvoiceAmountByoverseaInvoiceNo(@Param("invoiceNo") String invoiceNo,@Param("supplierCode") String supplierCode);


    @Select("select sum(price*quantity) as amount from Imp_invoice_detail where status!=5 and oversea_invoice_no=#{invoiceNo} and supplier_code=#{supplierCode}")
    BigDecimal getImpDetailAmountByInvoiceNo(@Param("invoiceNo") String invoiceNo,@Param("supplierCode") String supplierCode);

    @Select("select invoice_date from imp_invoice_master where id=#{invoiceId}")
    Date getImpInvoiceDate(@Param("invoiceId") Long invoiceId);

    @Select("<script>" +
            "select sum(price*quantity) as amount from Imp_invoice_detail where status!=5 and oversea_invoice_no in " +
            " <foreach collection='overseaList' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "</script>")
    BigDecimal getsumShiptAmount(@Param("overseaList") List<String> overseaList);

    @Select("select oversea_invoice_no,sum(price*quantity) as amount from Imp_invoice_detail " +
            "where invoice_id=#{invoiceId} group by oversea_invoice_no")
    List<PoInvoiceDetailDO> listInvoiceDataByOverseaNo(@Param("invoiceId") Long invoiceId);

}
