package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.adapter.order.OrderDetailDTO;
import com.smc.smccloud.model.expdetail.ExpdetailDO;
import com.smc.smccloud.model.expdetail.ExpdetailSalesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-01-20
 */

@Mapper
@DS("opsdb")
public interface ExpdetailMapper extends BaseMapper<ExpdetailDO> {

    @Select("<script>" +
            "SELECT id, invoice_no, delivery_no, order_no, item_no, order_fno, model_no, quantity, barcode, " +
            " customer_no, user_no, ship_date, express_no, express_company, warehouse_code, price, " +
            " opt_code, corder_no, cmodel_no, case_no, weight, order_type, invoice_flag, invoice_time, " +
            " sign_time, stock_code, dlv_date, orOrderNo, sign_status, sender, dlv_site, volume, box_type, " +
            " create_time, create_user, update_time, update_user, dept_no FROM dbo.expdetail " +
            " where order_fno in " +
            " <foreach collection='orderNoList' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach> " +
            "</script>")
    List<ExpdetailDO> selectExpDetailList(@Param("orderNoList") List<String> orderNoList);


    @Select("{call ops_sharedb.dbo.exp_ExpdetailForGZ}")
    @Options(statementType = StatementType.CALLABLE)
    void callExpExpdetailForGZ();

    @Select("select top(1) ship_date from expdetail where order_fno = #{orderNo} order by ship_date desc ")
    Date seleShipDateByNo(@Param("orderNo") String orderNo);


    @Select("<script>" +
            "SELECT  " +
            " concat(b.do_id,b.expdetail_id,b.barcode) as id, " +
            " e.order_fno, " +
            " e.order_no, " +
            " e.item_no, " +
            " e.model_no, " +
            " b.quantity, " +
            " e.barcode AS outBarcode, " +
            " b.barcode AS innerBarcode, " +
            " e.customer_no AS agentNo, " +
            " e.user_no, " +
            " e.end_user, " +
            " e.ship_date, " +
            " e.express_no, " +
            " e.express_company, " +
            " e.warehouse_code, " +
            " e.corder_no, " +
            " e.weight,  " +
            " e.dlv_address,  " +
            " e.ContactPsn  " +
            " FROM " +
            " expdetail e " +
            " LEFT JOIN expdetail_barcode b ON e.barcode = b.expdetail_id and e.delivery_no = b.do_id " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " ISNULL(b.expdetail_id, '') != '' and  " +
            "<if test = ' detailDTO.agentNo != null and detailDTO.agentNo.size() &gt; 0' >" +
            " e.customer_no in " +
            "   <foreach collection = 'detailDTO.agentNo' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            "</if>" +
            "<if test = ' detailDTO.fromTimeStr != null and detailDTO.toTimeStr != null ' >" +
            " and e.ship_date &gt;= #{detailDTO.fromTimeStr}  and e.ship_date &lt;= #{detailDTO.toTimeStr} " +
            "</if>" +
            "</trim>" +
            "</script>")
    List<ExpdetailSalesVO> listOrderExpdetailWithSales(@Param("detailDTO") OrderDetailDTO detailDTO);
}
