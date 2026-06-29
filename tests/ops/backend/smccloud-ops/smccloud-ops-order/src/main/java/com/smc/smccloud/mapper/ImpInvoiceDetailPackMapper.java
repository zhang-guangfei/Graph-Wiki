package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.Temprequisition;
import com.smc.smccloud.model.adapter.order.POOrderNOVO;
import com.smc.smccloud.model.invoice.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2021/12/7 11:22
 */
@Mapper
@DS("opsdb")
public interface ImpInvoiceDetailPackMapper extends BaseMapper<ImpInvoiceDetailPackDO> {

    @Select("select SUM(quantity) as quantity from ops_core.dbo.Imp_invoice_detail_pack where invoice_no = #{invoiceNo} GROUP BY order_no ")
    List<Integer> findQuantity(@Param("invoiceNo") String invoiceNo);

    @Select("select distinct invoice_id,invoice_no from Imp_invoice_detail_pack where status=2 and update_time>#{updateDate} ")
    List<ImpInvoiceDetailPackDO> listToImpDataPack(@Param("updateDate") Date updateDate);

    @Select("select poNo,poItemNo,full_order_no,model_no,sum(quantity) as quantity \n" +
            "\t  from Imp_invoice_detail_pack with(nolock)\n" +
            "\t  where invoice_id= #{id} \n" +
            "\t  group by poNo,poItemNo,full_order_no,model_no")
    List<ImpInvoiceDetailPackDO> selectInvoiceDetailPackByInvoiceId(@Param("id") String id);

    // 分包中存在，发票中不存在
    @Select("select poNo,poItemNo,full_order_no as orderNo,model_no,sum(quantity) as qty " +
            " from Imp_invoice_detail_pack with(nolock) " +
            " where invoice_id= #{id} " +
            " group by poNo,poItemNo,full_order_no,model_no ")
    List<ImpInvoiceErrorDO> selectTmpImpInvoiceErrorForNoExits(@Param("id") String id);


    @Select("<script>" +
            "select   id,invoice_id,invoice_no,full_order_no,status   "+
            " from Imp_invoice_detail_pack  where "+
            "<if test = 'poInvoiceDTOS != null and  poInvoiceDTOS.size() &gt; 0' >" +
            "  <foreach collection = 'poInvoiceDTOS' item = 'item' index='index' open='(' close=')'  separator='or'> " +
            "   (invoice_id= #{item.invoiceId} and invoice_no=#{item.invoiceNo} and full_order_no=#{item.fullOrderNo}) " +
            "  </foreach>" +
            "</if>"+
            " and status in ('1','2')"+
            "</script>")
    List<ImpInvoiceDetailPackDO>  selectInvoiceDetailPacOnProcessByPoInvoiceDTO(@Param("poInvoiceDTOS") List<PoInvoiceDTO> poInvoiceDTOS);

    @Select("<script>" +
            "select   id,invoice_id,invoice_no,full_order_no,status  "+
            " from Imp_invoice_detail_pack  where "+
            "<if test = 'poInvoiceDTOS != null and  poInvoiceDTOS.size() &gt; 0' >" +
            "  <foreach collection = 'poInvoiceDTOS' item = 'item' index='index' open='(' close=')'  separator='or'> " +
            "   (invoice_id= #{item.invoiceId} and invoice_no=#{item.invoiceNo} and full_order_no=#{item.fullOrderNo}) " +
            "  </foreach>" +
            "</if>"+
            " and status='3'"+
            "</script>")
    List<ImpInvoiceDetailPackDO> selectInvoiceDetailPacOnErrorByPoInvoiceDTO(@Param("poInvoiceDTOS") List<PoInvoiceDTO> poInvoiceDTOS);
}
