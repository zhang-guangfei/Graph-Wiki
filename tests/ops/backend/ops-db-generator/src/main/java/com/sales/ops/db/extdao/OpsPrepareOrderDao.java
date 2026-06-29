package com.sales.ops.db.extdao;


import com.sales.ops.dto.prepareOrder.CanUsePrepareOrderParam;
import com.sales.ops.dto.prepareOrder.OpsPrepareOrderVO;
import com.sales.ops.dto.prepareOrder.PrepareOrderQueryParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OpsPrepareOrderDao {


    @Select("<script>" +
            "SELECT p.*, i.ppl, i.project_code, i.group_customer_no, i.inventory_type_code " +
            "FROM ops_prepare_order p " +
            "LEFT JOIN ops_inventory_property i ON p.inventory_property_id = i.inventory_property_id " +
            "WHERE p.del_flag = 0 " +
            "<if test='param.orderFno != null and param.orderFno != \"\"'>" +
            "   AND p.order_fno LIKE CONCAT('%', #{param.orderFno}, '%')" +
            "</if>" +
            "<if test='param.manuOrderNo != null and param.manuOrderNo != \"\"'>" +
            "   AND p.manu_order_no LIKE CONCAT('%', #{param.manuOrderNo}, '%')" +
            "</if>" +
            "<if test='param.manuFactSupplierCode != null and param.manuFactSupplierCode != \"\"'>" +
            "   AND p.manu_fact_supplier_code = #{param.manuFactSupplierCode}" +
            "</if>" +
            "<if test='param.modelNo != null and param.modelNo != \"\"'>" +
            "   AND p.model_no LIKE CONCAT('%', #{param.modelNo}, '%')" +
            "</if>" +
            "<if test='param.status != null'>" +
            "   AND p.status = #{param.status}" +
            "</if>" +
            "<if test='param.customerNo != null and param.customerNo != \"\"'>" +
            "   AND p.customer_no = #{param.customerNo}" +
            "</if>" +
            "<if test='param.applyNo != null and param.applyNo != \"\"'>" +
            "   AND p.apply_no = #{param.applyNo}" +
            "</if>" +
            "<if test='param.ppl != null and param.ppl != \"\"'>" +
            "   AND i.ppl = #{param.ppl}" +
            "</if>" +
            "<if test='param.projectCode != null and param.projectCode != \"\"'>" +
            "   AND i.project_code = #{param.projectCode}" +
            "</if>" +
            "<if test='param.groupCustomerNo != null and param.groupCustomerNo != \"\"'>" +
            "   AND i.group_customer_no = #{param.groupCustomerNo}" +
            "</if>" +
            "<if test='param.applyDeptNos != null and param.applyDeptNos.size() > 0'>" +
            "   AND p.apply_dept_no IN " +
            "   <foreach collection='param.applyDeptNos' item='deptNo' open='(' separator=',' close=')'>" +
            "       #{deptNo}" +
            "   </foreach>" +
            "</if>" +
            "<if test='param.statusList != null and param.statusList.size() > 0'>" +
            "   AND p.status IN " +
            "   <foreach collection='param.statusList' item='status' open='(' separator=',' close=')'>" +
            "       #{status}" +
            "   </foreach>" +
            "</if>" +
            "<if test='param.startApplyDate != null and param.startApplyDate != \"\" and param.endApplyDate != null and param.endApplyDate != \"\"'>" +
            "   AND p.apply_time >= #{param.startApplyDate} AND p.apply_time &lt;= #{param.endApplyDate}" +
            "</if>" +
            "ORDER BY p.id DESC" +
            "</script>")
    List<OpsPrepareOrderVO> queryPrepareOrderList(@Param("param") PrepareOrderQueryParam param);




    @Select("<script>" +
            "SELECT p.*, i.ppl, i.project_code, i.group_customer_no, i.inventory_type_code,i.customer_no as inventoryCustomerNo " +
            "FROM ops_prepare_order p " +
            "LEFT JOIN ops_inventory_property i ON p.inventory_property_id = i.inventory_property_id " +
            "WHERE p.del_flag = 0 and p.remain_qty - p.pre_qty > 0 and p.can_use_flag = '1' and   p.status = '5' " +
            "<if test='param.modelNo != null and param.modelNo != \"\" '>" +
            "  and p.model_no = #{param.modelNo} " +
            "</if>" +
            "<if test='param.endUserNo != null and param.endUserNo != \"\" '>" +
            "   AND p.available_customers like CONCAT('%', #{param.endUserNo}, '%') " +
            "</if>" +
            "<if test='param.ppl != null and param.ppl != \"\"'>" +
            "   AND i.ppl = #{param.ppl}" +
            "</if>" +
            "<if test='param.pj != null and param.pj != \"\"'>" +
            "   AND i.project_code = #{param.pj}" +
            "</if>" +
            "<if test='param.listGroupCustomerNos != null and param.listGroupCustomerNos.size() > 0'>" +
            "   AND i.group_customer_no IN " +
            "   <foreach collection='param.listGroupCustomerNos' item='groupCustomerNo' open='(' separator=',' close=')'>" +
            "       #{groupCustomerNo}" +
            "   </foreach>" +
            "</if>" +
            "</script>")
    List<OpsPrepareOrderVO> getAvailablePrepareOrderList(@Param("param") CanUsePrepareOrderParam param);
}
