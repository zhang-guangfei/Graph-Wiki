package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsPoDeliveryUnusual;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface PurchaseUnusualOrderDao {

    @Select("select count(*) from ops_po_delivery_unusual where del_flag=0 and handle_status in (1,2) and supplier_code = #{supplierCode}")
    int countOpsPoUnusual(String supplierCode);


    @Select("select * from ops_po_delivery_unusual where del_flag=0 and handle_status in (1,2) and supplier_code = #{supplierCode} order by id ")
    List<OpsPoDeliveryUnusual> getOpsPoUnusual(String supplierCode);

    @Select("select * from ops_po_delivery_unusual where id=#{id}")
    List<OpsPoDeliveryUnusual> getOpsPoUnusualById(Long id);

    @Select("SELECT * FROM " +
            "( " +
            "SELECT ROW_NUMBER()over(order by id) AS rownumber,* from ops_po_delivery_unusual where del_flag=0 and handle_status in (1,2) and supplier_code = #{supplierCode}" +
            ") AS a " +
            "WHERE a.rownumber > ${pageNum * pageSize }  AND a.rownumber <= ${(pageNum+1) * pageSize } ")
    List<OpsPoDeliveryUnusual> getOpsPoUnusualPage(String supplierCode, Integer pageNum, Integer pageSize);


}
