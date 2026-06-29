package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.yulichang.base.MPJBaseMapper;
import com.smc.smccloud.model.OpsOrderAssignResultDO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.model.invoice.SalesInvoiceDO;
import com.smc.smccloud.model.order.RcvDetailDO;
import com.smc.smccloud.model.order.RcvSaleDTO;
import com.smc.smccloud.model.order.ResultPurchaseDTO;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import com.smc.smccloud.model.receiveorder.RcvOrderInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@DS("opsdb")
@Mapper
public interface RcvdetailMapper extends MPJBaseMapper<RcvDetailDO> {

    @Select("<script>" +
            "SELECT COUNT(r.id)  from rcvdetail r \n" +
            "inner JOIN  rcvmaster rm ON r.rorder_no = rm.rorder_no\n" +
            "where r.status = 1 " +
            "<if test = 'tradecompanyId != null and tradecompanyId != \"\" ' >" +
            " and rm.trade_companyId=#{tradecompanyId}" +
            "</if>" +
            "<if test = 'areaDeptNo != null and areaDeptNo != \"\" ' >" +
            " and rm.dept_No = #{areaDeptNo} " +
            "</if>" +
            "</script>")
    Integer getProcessingOrderCount(@Param("tradecompanyId") String tradecompanyId, @Param("areaDeptNo") String areaDeptNo);


    @Select("<script>" +
            "SELECT COUNT(*)  from rcvdetail r LEFT JOIN  rcvmaster rm ON r.rorder_no = rm.rorder_no " +
            "where r.status = 11" +
            "<if test = 'tradecompanyId != null and tradecompanyId != \"\" ' >" +
            " and rm.trade_companyId = #{tradecompanyId} " +
            "</if>" +
            "<if test = 'areaDeptNo != null and areaDeptNo != \"\" ' >" +
            " and rm.dept_No = #{areaDeptNo}  " +
            "</if>" +
            "</script>")
    Integer getNotHandleOrderCount(@Param("tradecompanyId") String tradecompanyId, @Param("areaDeptNo") String areaDeptNo);

    @Select("<script>" +
            "SELECT COUNT(*)  from rcvdetail r LEFT JOIN  rcvmaster rm ON r.rorder_no = rm.rorder_no " +
            "where r.status = 10" +
            "<if test = 'tradecompanyId != null and tradecompanyId != \"\" ' >" +
            " and rm.trade_companyId = #{tradecompanyId} " +
            "</if>" +
            "<if test = 'areaDeptNo != null and areaDeptNo != \"\" ' >" +
            " and rm.dept_No = #{areaDeptNo}  " +
            "</if>" +
            "</script>")
    Integer getExceptionHandCount(@Param("tradecompanyId") String tradecompanyId, @Param("areaDeptNo") String areaDeptNo);

    @Select("SELECT model_no,stock_code,quantity,price,amount,returned_qty,status,rorder_no,rorder_item rorderItemNo,customer_no,user_no,rorder_fno,eprice, exp_qty " +
            "FROM dbo.rcvorder_view WHERE rorder_no=#{orderNo} AND rorder_item= #{itemNo}")
    RcvOrderInfoVO getRcvOrderInfo(@Param("orderNo") String orderNo, @Param("itemNo") Integer itemNo);

    @Select(" select sum(quantity) from rcvdetail where rorder_no = #{orderNo} ")
    int getQtyByOrderNo(@Param("orderNo") String orderNo);

    @Select(" select sum(amount) from rcvdetail where rorder_no = #{orderNo} ")
    BigDecimal getAmountByOrderNo(@Param("orderNo") String orderNo);

    @Select("<script>" +
            "SELECT MAX(b.ROrdDate) saleEndTime,MIN(b.ROrdDate) saleStartTime FROM rcvdetail a INNER JOIN rcvmaster b ON a.rorder_no = b.rorder_no WHERE b.ROrdDate &gt; #{applyDate} AND b.ROrdDate  &lt; #{nowDate} " +
            " AND a.prod_flag = 0 AND a.model_no in " +
            "   <foreach collection = 'modelNoList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            "</script>")
    RcvSaleDTO getSaleDate(@Param("modelNoList") List<String> modelNoList, @Param("applyDate") Date applyDate, @Param("nowDate") Date nowDate);

    @Select("SELECT modelno FROM v_shikomi_total WHERE ShikomiNo=(\n" +
            "SELECT TOP 1 ShikomiNo FROM v_shikomi_total WHERE modelno=#{modelNo})")
    List<String> getShikomiModels(@Param("modelNo") String modelNo);



    @Select("<script>" +
            "SELECT a.rorder_fno ROrderNo,a.model_no ModelNo,a.price Price,a.quantity FROM rcvdetail a INNER JOIN" +
            " rcvmaster b ON a.rorder_no = b.rorder_no WHERE a.rorder_fno =#{orderNo} AND b.customer_no =#{customerNo} " +
            "</script>")
    SalesInvoiceDO getSampOrderData(@Param("orderNo") String orderNo, @Param("customerNo") String customerNo);

    @Select("SELECT * FROM rcvdetail WHERE rorder_fno=#{orderNo}")
    RcvDetailVO getRcvdetailQtyByNo(@Param("orderNo") String orderNo);

    @Select("SELECT top 1 * FROM rcvdetail d INNER JOIN rcvmaster m ON d.rorder_no = m.rorder_no WHERE m.customer_no = 'CH016' " )
    List<ExpdetailVO> listOrderDetailForAgent();


    @Select("select id,price ,tax_rate, quantity from rcvdetail where ntax_pice is null or ntax_pice = '' or ntax_amount is null or ntax_amount = '' or tax_amount is null or tax_amount = ''")
    List<RcvDetailDO> selectDataForTaxAmount();


    /**
     * add by LiYingChao from bug 8420  in  2022/10/21
     * @param customerNo 客户编码
     * @return
     */
    @Select("select customer_no,HRUnitId,HLCode,InvoiceType,CustomerType,name from ops_customer where customer_no = #{customerNo} ")
    CustomerVO getCustomerByCustomerNo(@Param("customerNo")String customerNo);

    //     // add by A78027 from bug 11410 in 2023/7/21
    @Select("select distinct a.rorder_fno,a.rorder_no,a.rorder_item,a.model_no,a.quantity,a.prod_flag,a.ship_time,a.exp_time,a.shikomi_no,a.stock_type,a.eprice\n" +
            "from rcvdetail a \n" +
            "inner join product_price b on b.modelNo=a.model_no and b.MinQuantity>1 and b.is_deleted=0 \n" +
            "where a.ship_time>#{fromDate} and a.prod_flag<>'2'")
    List<RcvDetailDO> listRcvOrderModify(@Param("fromDate") Date fromDate);


    @Select("SELECT shikomi_no FROM rcvdetail WHERE rorder_no =#{orderNo} AND rorder_item =#{itemNo}")
    String getRcvDetailShikomi(@Param("orderNo") String orderNo,@Param("itemNo") Integer itemNo);

    @Select("select top 1 * from ops_order_assign_result where order_no =#{orderNo} and order_item=#{itemNo} and ModelNo=#{modelNo} ")
    OpsOrderAssignResultDO getOrderAssignResult(@Param("orderNo") String orderNo,
                                                @Param("itemNo") Integer itemNo,@Param("modelNo") String modelNo);

    @Select("select order_no,order_item,modelno,sum(quantity) as quantity,stock_type \n" +
            "from ops_order_assign_result\n" +
            "where order_no =#{orderNo} and order_item=#{itemNo} and delflag=0 \n" +
            "group by order_no,order_item,modelno,stock_type")
    List<OpsOrderAssignResultDO> getOrderAssignResultModelQty(@Param("orderNo") String orderNo,
                                                @Param("itemNo") Integer itemNo);

        @Select("select\n" +
                "r.order_no as order_id,\n" +
                "r.order_item as order_item,\n" +
                "r.modelno,\n" +
                "r.quantity,\n" +
                "r.stock_type,\n" +
                "r.stock_code,\n" +
                "r.inventory_type_code,\n" +
                "p.orderNo,\n" +
                "p.itemNo,\n" +
                "p.splitItemNo,\n" +
                "p.stateCode as po_state_code,\n" +
                "p.invoiceNo as invoiceNo,\n" +
                "p.supplierId as supplier_no,\n" +
                "p.src_delivery_time as delivery_time,\n" +
                "p.replyOrderNo as supplier_order_no,\n" +
                "s.binflag as binflag\n" +
                "from ops_order_assign_result r \n" +
                "LEFT JOIN ops_purchaseInvoice p on r.associate_no = p.orderNo and r.associate_no_item = p.itemNo and r.associate_no_split_no = ISNULL(p.splitItemNo,0)\n" +
                "left join inventory_supplier s on s.supplierId=p.supplierId and s.modelNo=p.modelNo\n" +
                "where r.delflag = 0 " +
                "and r.order_no=#{orderId}" +
                " and r.order_item=#{orderItem} " +
                "order by r.update_time desc")
    List<ResultPurchaseDTO> getAssignResultAndPoInfoByOrderNo(@Param("orderId") String orderId,
                                                              @Param("orderItem") Integer orderItem);
    @Select("select\n" +
            "r.order_no as order_id,\n" +
            "r.order_item as order_item,\n" +
            "r.modelno,\n" +
            "r.quantity,\n" +
            "r.stock_type,\n" +
            "r.stock_code,\n" +
            "r.inventory_type_code,\n" +
            "p.orderNo,\n" +
            "p.itemNo,\n" +
            "p.splitItemNo,\n" +
            "p.stateCode as po_state_code,\n" +
            "p.invoiceNo as invoiceNo,\n" +
            "p.supplierId as supplier_no,\n" +
            "p.src_delivery_time as delivery_time,\n" +
            "p.replyOrderNo as supplier_order_no,\n" +
            "s.binflag as binflag\n" +
            "from ops_order_assign_result r \n" +
            "LEFT JOIN ops_purchaseInvoice p on r.associate_no = p.orderNo and r.associate_no_item = p.itemNo and r.associate_no_split_no = ISNULL(p.splitItemNo,0)\n" +
            "left join inventory_supplier s on s.supplierId=p.supplierId and s.modelNo=p.modelNo\n" +
            "where r.id =#{id}")
    List<ResultPurchaseDTO> getAssignResultAndPoInfoById(Long id);

    @Select("select* from ops_order_assign_result where order_no=#{orderId} and order_item=#{orderItem} order by update_time desc ")
    List<OpsOrderAssignResultDO> getOrderAssignResultIncludeDeleted(@Param("orderId") String orderId, @Param("orderItem") Integer orderItem);


    // add by A78027 from bug 11410 in 2023/7/21
    /**
     * 查子型号有批量价格的拆分订单
     * @param fromTime
     * @return
     */
    @Select("select distinct a.rorder_fno,a.rorder_no,a.rorder_item,a.model_no,a.quantity,a.shikomi_no,a.ship_time,a.exp_time,a.ready_time,a.eprice\n" +
            "            from rcvdetail a \n" +
            "            inner join ops_order_assign_result  b on a.rorder_no=b.order_no and a.rorder_item=b.order_item\n" +
            "            inner join product_price c on c.modelNo=b.modelno and c.MinQuantity>1 and c.is_deleted=0 \n" +
            "            where a.ship_time>#{fromTime} and a.prod_flag='2' and b.delflag=0 \n" +
            "            and a.status<>9")
    List<RcvDetailDO> listSplitModelRcvOrder(@Param("fromTime") Date fromTime);

}
