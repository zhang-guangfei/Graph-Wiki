package com.smc.ops.delivery.mapper.inqb;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inqb.InqbInventoryVerifyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 查询 ops_core的公共mapper
 */
@Mapper
@DS("opsdb")
public interface InqbOpscoreMapper {

    @Select(" <script>  select * from product_delivery where modelNo = #{modelno} and is_deleted = '0'  and isPrimary = '1' </script>")
    List<ProductDelivery> getProductDelivery(@Param("modelno") String modelno);

    @Select(" <script>  select * from ops_customer where customer_no =  #{customerNo}  </script>")
    OpsCustomer getOpsCustomerByNo(@Param("customerNo") String customerNo);

    @Select(" <script>  select * from hr_organization where Level in ('课内机构(HL)','驻在所HL') and CompanyCode = '200000' and Id = #{id}   </script>")
    HrOrganization getHLOrganizationById(@Param("id") String id);

    @Select(" <script>  select * from hr_organization where  Id = #{id}   </script>")
    HrOrganization getHrOrganizationById(@Param("id") String id);

    // 根据提供的型号等信息查询可用的库存
    @Select(" <script> " +
            "select modelno,SUM(qtyN-pqtyN)  avaQty  from inventory_report where  inventory_type_code = 'TY' and modelno = #{modelNo} and qtyN-pqtyN <![CDATA[ >= ]]> #{quantity} group by modelno\n" +
            "union all \n" +
            "select  modelno,SUM(qtyN-pqtyN) avaQty from inventory_report where  inventory_type_code = 'GK-TY' and modelno = #{modelNo}  and customer_no = #{endUser} and qtyN-pqtyN <![CDATA[ >= ]]> #{quantity}  group by modelno \n" +
            "  </script>")
    List<InqbInventoryVerifyDto> getInventoryQty(@Param("modelNo") String modelNo, @Param("quantity") Integer quantity, @Param("endUser") String endUser);
    @Select(" <script> SELECT B.* FROM PRODUCT_BOM  A INNER JOIN PRODUCT_BOM_DETAIL B ON  A.bomId = B.bomId \n" +
            "WHERE  A.IsValid = 1 and Priority_level = 1 and  A.MODELNO=#{modelno}    </script>")
    List<ProductBomDetail> getMdelNoBom(@Param("modelno") String modelno);


    @Select(" <script>  select * from supplier   </script>")
    List<Supplier> getSupplierList();
}
