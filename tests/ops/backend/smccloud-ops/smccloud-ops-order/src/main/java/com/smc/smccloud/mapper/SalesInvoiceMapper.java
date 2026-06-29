package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.InvoiceBalaceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
@DS("smcdb30")
public interface SalesInvoiceMapper extends BaseMapper<SalesInvoiceDO> {

//    @Select("select StockNo,ModelNo,sum(quantity) as qty\n" +
//            "  FROM SalesInvoice where StockNo like 'W%' and invflag='2' and InvFlag1='0' and InvDate>=#{fromDate} and invdate<=#{toDate}\n" +
//            "  group by StockNo,ModelNo")
    @Select("SELECT warehouse_code as StockNo,ModelNo,sum(Quantity) as qty FROM SalesData WHERE " +
            "warehouse_type = 'WT' and OptDate>=#{fromDate} and OptDate<=#{toDate} GROUP BY ModelNo,warehouse_code")
    List<InvoiceBalaceDTO> getBalaceData(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Select("<script>" +
            "SELECT Id,TradeCompanyId,ROrderNo,ModelNo,Quantity,Price,CustomerNo,UserNo,DlvEntire,Amount,TaxAmount,\n" +
            "NTaxAmount,InvFlag,OptDate,OptCode,ClassFlag,InvoiceNo,InvDate,OptState,StockNo,InvFlag1,ProdFlag,BillNo,\n" +
            "Username,OptTime,Receive_Emp,Receive_Cust,Receive_date,Receive_Remark,Receive_OptState,PurchaseNo,\n" +
            "AgentPrice_Flag,DeptNo,DiscountAmt,OrdType,invoicecode,invtype,InvoiceNo_Jp,TaxRate,\n" +
            "NTaxDiscountAmt,TaxDiscountAmt,InsertTime,CancelDate,Extracted,ExtractTime,\n" +
            "InvoiceGroupKey,Remark,orderno,itemno,ackdate,expdate,price_enduser FROM SalesInvoice " +
            "where ROrderNo in " +
            " <foreach collection='orderNoList' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach> " +
            "</script>")
    List<SalesInvoiceDO> selectInvoiceList(@Param("orderNoList") List<String> orderNoList);


    @Select("SELECT Id,TradeCompanyId,ROrderNo,ModelNo,Quantity,Price,CustomerNo,UserNo,DlvEntire,Amount,TaxAmount,\n" +
            "NTaxAmount,InvFlag,OptDate,OptCode,ClassFlag,InvoiceNo,InvDate,OptState,StockNo,InvFlag1,ProdFlag,BillNo,\n" +
            "Username,OptTime,Receive_Emp,Receive_Cust,Receive_date,Receive_Remark,Receive_OptState,PurchaseNo,\n" +
            "AgentPrice_Flag,DeptNo,DiscountAmt,OrdType,invoicecode,invtype,InvoiceNo_Jp,TaxRate,\n" +
            "NTaxDiscountAmt,TaxDiscountAmt,InsertTime,CancelDate,Extracted,ExtractTime,\n" +
            "InvoiceGroupKey,Remark,orderno,itemno,ackdate,expdate,price_enduser FROM SalesInvoice " +
            " WHERE ROrderNo = #{orderNo} and CustomerNo = #{customerNo} and price > 0 and ModelNo not in ('手续费','销售返利','维修费','延期付款利息') ")
    List<SalesInvoiceDO> selectInvoiceForReturn(@Param("orderNo") String orderNo,@Param("customerNo") String customerNo);
}
