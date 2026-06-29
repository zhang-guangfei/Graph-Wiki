package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OrderState;
import com.sales.ops.db.entity.RcvView;
import com.sales.ops.db.entity.Rcvdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-10-24
 */
@DS("opsdb")
@Mapper
public interface OpsRcvdetailMapper {


    @Select(" <script> select  * from rcvdetail  where  rorder_fno=  #{orderno} \n" +
            " </script> ")
    List<Rcvdetail> getRcvdetailByOrderno(@Param("orderno") String orderno); // 获取purchaseNo字段


    @Select(" <script> select  * from rcv_view  where  rorder_fno=  #{orderno} \n" +
            " </script> ")
    List<RcvView> getRcvViewByOrderno(@Param("orderno") String orderno); // 查询Rcvdetail视图信息

    @Select(" <script> select warehouse_code code,warehouse_name codename from ops_warehouse with(nolock)\n" +
            "union all\n" +
            "select id code,name codename from supplier  with(nolock) \n" +
            " </script> ")
    List<Map<String,String>> getWarehouseSupplier();

    @Select(" <script> select top 1 * from order_state with(nolock) where order_no = #{orderno}   \n" +
            " </script> ")
    OrderState getOrderState(@Param("orderno") String orderno);

    @Select(" <script> select top 1 * from ops_purchaseInvoice where poNo = #{pono} and lineItem = #{lineItem}   \n" +
            " </script> ")
    OpsPurchaseinvoice getPurchaseinvoice(@Param("pono") String pono, @Param("lineItem") Integer lineItem);

}
