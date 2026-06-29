package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.Rcvdetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface OpsRcvDetailDao {


    /**
     *
     * @param limit
     * @return
     */
    @Select("select top ${limit} rorder_no, rorder_item, id, status, rorder_fno, model_no, quantity, price, price_enduser, " +
            "    eprice, tax_rate, ntax_pice, ntax_amount, tax_amount, amount, discount, " +
            "    dlv_date, cdlv_date, wms_dlv_date, spec_offer_no, delete_status, source_type, cproduct_no, " +
            "    stock_code, stock_type, inventory_type_code, prod_flag, spec_mark, remark, product_name, " +
            "    opponent, allot_time, ready_time, exp_time, ship_time, ready_qty, exp_qty, returned_qty, " +
            "     ppl_no, project_no, group_customer_no, shikomi_no, sales_info_no, " +
            "    corder_no, address_no, custom_code, order_type, process_date, borrow_no, po_qty, " +
            "    exp_msg, exp_dlv_type, exp_link_no, version, create_time, update_time, update_user, " +
            "    create_user,inqb_apply_no from dbo.rcvdetail WHERE  status = #{status} and create_time > #{credate} order by create_time")
    List<Rcvdetail> getRcvdetailByLimit(@Param("limit") Integer limit, @Param("status") Short status, @Param("credate") String credate);


    /**
     *
     * @param limit ID偶数
     * @return
     */
    @Select("select top ${limit} rorder_no, rorder_item, id, status, rorder_fno, model_no, quantity, price, price_enduser, " +
            "    eprice, tax_rate, ntax_pice, ntax_amount, tax_amount, amount,  discount, " +
            "    dlv_date, cdlv_date, wms_dlv_date, spec_offer_no, delete_status, source_type, cproduct_no, " +
            "    stock_code, stock_type, inventory_type_code, prod_flag, spec_mark, remark, product_name, " +
            "    opponent, allot_time, ready_time, exp_time, ship_time, ready_qty, exp_qty, returned_qty, " +
            "     ppl_no, project_no, group_customer_no, shikomi_no, sales_info_no, " +
            "    corder_no, address_no, custom_code, order_type, process_date, borrow_no, po_qty, " +
            "    exp_msg, exp_dlv_type, exp_link_no, version, create_time, update_time, update_user, " +
            "    create_user,inqb_apply_no from dbo.rcvdetail WHERE  status = #{status} and id % 2=0  and create_time > #{credate} order by create_time")
    List<Rcvdetail> getRcvdetailByLimitAndModEvenNumberId(@Param("limit") Integer limit, @Param("status") Short status, @Param("credate") String credate);

    /**
     *
     * @param limit ID 奇数 Odd number
     * @return
     */
    @Select("select top ${limit} rorder_no, rorder_item, id, status, rorder_fno, model_no, quantity, price, price_enduser, " +
            "    eprice, tax_rate, ntax_pice, ntax_amount, tax_amount, amount,  discount, " +
            "    dlv_date, cdlv_date, wms_dlv_date, spec_offer_no, delete_status, source_type, cproduct_no, " +
            "    stock_code, stock_type, inventory_type_code, prod_flag, spec_mark, remark, product_name, " +
            "    opponent, allot_time, ready_time, exp_time, ship_time, ready_qty, exp_qty, returned_qty, " +
            "     ppl_no, project_no, group_customer_no, shikomi_no, sales_info_no, " +
            "    corder_no, address_no, custom_code, order_type, process_date, borrow_no, po_qty, " +
            "    exp_msg, exp_dlv_type, exp_link_no, version, create_time, update_time, update_user, " +
            "    create_user,inqb_apply_no from dbo.rcvdetail WHERE status = #{status} and   id % 2=1  and create_time > #{credate} order by create_time")
    List<Rcvdetail> getRcvdetailByLimitAndModOddNumberId(@Param("limit") Integer limit, @Param("status") Short status, @Param("credate") String credate);

    @Select("select rorder_no+'-'+cast(rorder_item as varchar(5))from rcvdetail where intercept =1 and status not in (7,8,9,10,11,13)")
    List<String> getRorderFnoByCredit();

    @Select("select rorder_no+'-'+cast(rorder_item as varchar(5))from rcvdetail where intercept =1 and status in (7,8,9,10,11,12,13)")
    List<String> getRorderFnoByCredit2();

    @Select("select rorder_no+'-'+cast(rorder_item as varchar(5))from rcvdetail where rorder_no=#{orderNo}")
    List<String> getRorderFnoByRorderNo(String orderNo);


    @Update("update rcvdetail set estimated_delivery_day=null ,update_time=getdate() where rorder_no=#{orderNo} and rorder_item=#{orderItem}")
    int updateEstimatedDeliveryDayToNUll(String orderNo, String orderItem);

    @Update("update rcvdetail set ready_time=#{readyTime} ,update_time=getdate() where rorder_no=#{orderNo} and rorder_item=#{orderItem}")
    int updateReadyTime(String orderNo, String orderItem, Date readyTime);
}
