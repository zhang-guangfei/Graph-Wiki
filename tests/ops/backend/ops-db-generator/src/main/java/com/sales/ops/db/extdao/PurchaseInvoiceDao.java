package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.dto.po.invoice.ErrorTypeDto;
import com.sales.ops.dto.po.invoice.InvoiceErrorDto;
import com.sales.ops.dto.po.invoice.InvoiceErrorParamDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/3/12 9:03
 */
public interface PurchaseInvoiceDao {

    /**
     * @description 插入并返回主键
     */
    @Insert("<script>" +
            "insert into ops_purchaseInvoice" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='orderno != null '> orderNo, </if> " +
            "      <if test='itemno != null'> itemNo, </if> " +
            "      <if test='pono != null'> poNo, </if> " +
            "      <if test='splititemno != null'> splitItemNo, </if> " +
            "      <if test='lineitem != null'> lineItem, </if> " +
            "      <if test='modelno != null'> modelNo, </if> " +
            "      <if test='jpsplitvt != null'> jpSplitVT, </if> " +
            "      <if test='statecode != null'> stateCode, </if> " +
            "       updateTime,supplierId,receiveWarehouseId" +
            "    </trim> " +
            "     values " +
            "    <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "      <if test='orderno != null'> #{orderno},</if> " +
            "      <if test='itemno != null'> #{itemno},</if> " +
            "      <if test='pono != null'> #{pono},</if> " +
            "      <if test='splititemno != null'> #{splititemno},</if> " +
            "      <if test='lineitem != null'> #{lineitem},</if> " +
            "      <if test='modelno != null'> #{modelno},</if> " +
            "      <if test='jpsplitvt != null'> #{jpsplitvt},</if> " +
            "      <if test='statecode != null'> #{statecode},</if> " +
            "       #{updatetime},#{supplierid},#{receivewarehouseid}" +
            "    </trim> " +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertOpsPurchaseinvoice(OpsPurchaseinvoice obj);

    // BUGID:20149 C14717
    @Select("<script>" +
            "select m.supplier_code ,e.invoice_id ,e.invoice_no ,e.order_no ,e.model_no ,e.po_model_no ,e.qty ,e.po_qty \n" +
            ",e.pack_qty ,e.error_text ,e.po_warehouse_code ,e.ignore_error ,e.ignore_psn ,e.ignore_time ,e.ignore_reason ,e.remark ,e.create_time ,e.error_type \n" +
            "from imp_invoice_error e left join  imp_invoice_master m on e.invoice_id = m.id " +
            "where 1=1 " +
            "<if test = 'supplierCodeList != null and  supplierCodeList.size() &gt; 0' >" +
            " and m.supplier_code in " +
            " <foreach collection='supplierCodeList' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            " </foreach>" +
            "</if>"+
            "<if test = 'errorTypeList != null and  errorTypeList.size() &gt; 0' >" +
            " AND (\n" +
            "    <foreach collection=\"errorTypeList\" item=\"item\" separator=\" OR \"> " +
            "      (e.error_type &amp; #{item}) > 0 " +
            "    </foreach> " +
            "  ) "+
            "</if>"+
            " <if test=\"invoiceNo != null  and invoiceNo != ''\">and  e.invoice_no =#{invoiceNo} </if>" +
            " <if test=\"orderNo != null  and orderNo != ''\">and  e.order_no like concat(#{orderNo},'%') </if>" +
            " <if test=\"modelNo != null  and modelNo != ''\">and  e.model_no =#{modelNo} </if>" +
            "<if test='startTime != null'>"+
            "and e.ignore_time <![CDATA[ >= ]]> #{startTime}"+
            "</if>"+
            "<if test='endTime != null'>"+
            "and e.ignore_time <![CDATA[ <= ]]> #{endTime}"+
            "</if>"+
            "</script>")
    List<InvoiceErrorDto> getInvoiceError(InvoiceErrorParamDto paramDto);

    // BUGID:20149 C14717
    @Select("SELECT bit_value as id ,error_text as name FROM invoice_check_rule")
    List<ErrorTypeDto> getInvoiceCheckRule();

}
