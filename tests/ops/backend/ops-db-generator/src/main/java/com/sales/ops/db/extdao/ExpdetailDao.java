package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.Expdetail;
import org.apache.ibatis.annotations.Select;

public interface ExpdetailDao {


    @Select("select top 1 express_no from expdetail where delivery_no=#{doId} order by ship_date")
    String getExpressNoByDoId(String doId);

    @Select("select top 1 * from expdetail where order_fno=#{orderFno} and ship_date is not null order by ship_date desc")
    Expdetail getExpdetailByOrderFno(String orderFno);

    @Select("select count(*) from expdetail where order_fno=#{orderFno} and invoice_flag=1 ")
    int countExpdetailByOrderFnoAndInvoiceFlag(String orderFno);

}
