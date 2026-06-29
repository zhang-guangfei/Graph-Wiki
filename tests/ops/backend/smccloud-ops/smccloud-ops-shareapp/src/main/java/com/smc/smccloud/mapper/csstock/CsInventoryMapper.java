package com.smc.smccloud.mapper.csstock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.model.csstock.CsInventoryRequestDTO;
import com.smc.smccloud.model.csstock.CsInventoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opsdb")
public interface CsInventoryMapper {

    @Select("<script>" +
            "select a.warehouse_code,b.warehouse_name,a.modelno,a.quantity, a.prepare_quantity, b.customer_no, c.customer_no as userNo " +
            " from ops_inventory a inner join ops_warehouse b on a.warehouse_code=b.warehouse_code " +
            " INNER JOIN ops_inventory_property c ON a.inventory_property_id = c.inventory_property_id " +
            " where b.warehouse_type='WT' and a.quantity > 0 "+
            "<if test = 'request.modelNo != null and request.modelNo != \"\" ' >" +
            " and a.modelNo = #{request.modelNo}" +
            "</if>" +
            "<if test = 'request.warehouseCode != null and request.warehouseCode != \"\" ' >" +
            " and a.warehouse_code = #{request.warehouseCode}" +
            "</if>" +
            "<if test = 'request.customerNo != null and request.customerNo != \"\" ' >" +
            " and b.customer_no = #{request.customerNo}" +
            "</if>" +
            "<if test = 'request.orderQuantity != null ' >" +
            " and a.quantity - a.prepare_quantity <![CDATA[>=]]> #{request.orderQuantity}" +
            "</if>" +
            "<if test = 'request.agentNo != null and request.agentNo != \"\" ' >" +
            " and b.customer_no=#{request.agentNo} " +
            "</if>" +
            "<if test = 'request.dataAuthoritySearchCondition != null and request.dataAuthoritySearchCondition.customerCodeListByDataAuthority != null and request.dataAuthoritySearchCondition.customerCodeListByDataAuthority.size > 0 '>"+
            "  and b.customer_no in "+
            "  <foreach collection='request.dataAuthoritySearchCondition.customerCodeListByDataAuthority' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            "  </foreach>" +
            "</if>"+
            "order by b.customer_no, a.modelNo "+
            "</script>")
    List<CsInventoryVO> listCsInventory(@Param("request") CsInventoryRequestDTO request);

}
