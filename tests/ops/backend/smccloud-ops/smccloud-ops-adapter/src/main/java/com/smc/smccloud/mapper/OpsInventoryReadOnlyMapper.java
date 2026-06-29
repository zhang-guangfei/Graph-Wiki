package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.smc.smccloud.model.stock.InventoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opsreaddb")
public interface OpsInventoryReadOnlyMapper {

    @Select("<script>" +
            "select inventory_property_id from ops_inventory_property where delflag=0 and customer_no =#{customerNo}" +
            "   <if test='inventoryType != null and inventoryType != \"\" '> " +
            "       and inventory_type_code = #{inventoryType} " +
            "   </if>" +
            "</script>")
    List<Long> selectInventoryPropertyIdsByCustomerNo(String customerNo,String inventoryType );

    @Select("select * from ops_inventory_property where delflag=0 ")
    List<OpsInventoryProperty> selectInventoryPropertyAll();



    @Select("select * from ops_inventory_property where delflag=0 and inventory_property_id =#{id}")
    OpsInventoryProperty selectInventoryProperty(Long id);

    @Select("<script>" +
            "select * from ops_inventory_property where delflag=0 " +
            "and inventory_property_id in" +
            "<foreach collection= 'ids' item= 'id' separator= ', ' open= '( ' close= ') '> " +
            "      #{id}" +
            "</foreach>" +
            "</script>"
    )
    List<OpsInventoryProperty> selectInventoryPropertyList(List<Long> ids);


    @Select("<script>" +
            "select " +
            "'N' as inventoryScope, " +
            "i.warehouse_code, " +
            "i.modelno, " +
            "i.quantity, " +
            "i.prepare_quantity, " +
            "i.quantity-i.prepare_quantity as available_quantity, " +
            "'N' as inventory_status, " +
            "i.inventory_property_id " +
            "from ops_inventory i " +
            "<where> " +
            "   i.delflag = 0 and i.quantity > 0 " +
            "   <if test='modelnoList != null and modelnoList.size>0 '> " +
            "   and i.modelno in " +
            "   <foreach collection= 'modelnoList' item= 'modelno' separator= ', ' open= '( ' close= ') '> " +
            "      #{modelno}" +
            "   </foreach>" +
            "   </if>" +
            "   <if test='warehouseCode != null and warehouseCode != \"\" '> " +
            "       and i.warehouse_code = #{warehouseCode} " +
            "   </if>" +
            "   <if test='propertyList != null and propertyList.size > 0 '> " +
            "   and i.inventory_property_id in " +
            "   <foreach collection= 'propertyList' item= 'property' separator= ', ' open= '( ' close= ') '> " +
            "      #{property}" +
            "   </foreach>" +
            "   </if>" +
            "   <if test='propertyList != null and propertyList.size == 0 '> " +
            "   and i.inventory_property_id is null " +
            "   </if>" +
            "</where>" +
            "</script>"
    )
    List<InventoryDto> selectInventoryNormal(
            List<String> modelnoList,
            String warehouseCode,
            List<Long> propertyList
    );

    @Select("<script>" +
            "select " +
            "'N' as inventoryScope, " +
            "i.warehouse_code, " +
            "i.modelno, " +
            "i.quantity, " +
            "i.prepare_quantity, " +
            "i.quantity-i.prepare_quantity as available_quantity, " +
            "r.quantity - r.prepare_quantity as available_risk_quantity, "+
            "'N' as inventory_status, " +
            "i.inventory_property_id ," +
            " case when  r.inventory_id is not null then 1 else 0 end  as riskFlag "+
            "from ops_inventory i left join ops_inventory_risk r on i.inventory_id = r.inventory_id AND r.delflag = 0  " +
            "<where> " +
            "   i.delflag = 0 and i.quantity > 0 " +
            "   <if test='modelnoList != null and modelnoList.size>0 '> " +
            "   and i.modelno in " +
            "   <foreach collection= 'modelnoList' item= 'modelno' separator= ', ' open= '( ' close= ') '> " +
            "      #{modelno}" +
            "   </foreach>" +
            "   </if>" +
            "   <if test='warehouseCode != null and warehouseCode != \"\" '> " +
            "       and i.warehouse_code = #{warehouseCode} " +
            "   </if>" +
            "   <if test='propertyList != null and propertyList.size > 0 '> " +
            "   and i.inventory_property_id in " +
            "   <foreach collection= 'propertyList' item= 'property' separator= ', ' open= '( ' close= ') '> " +
            "      #{property}" +
            "   </foreach>" +
            "   </if>" +
            "   <if test='propertyList != null and propertyList.size == 0 '> " +
            "   and i.inventory_property_id is null " +
            "   </if>" +
            "</where>" +
            "</script>"
    )
    List<InventoryDto> selectInventoryNormalV1(
            List<String> modelnoList,
            String warehouseCode,
            List<Long> propertyList
    );

    @Select("<script>" +
            "select " +
            "'M' as inventoryScope, " +
            "i.warehouse_code, " +
            "i.modelno, " +
            "i.quantity, " +
            "i.prepare_quantity, " +
            "i.quantity-i.prepare_quantity as available_quantity, " +
            "i.inventory_status, " +
            "i.inventory_property_id " +
            "from (select warehouse_code,modelno,inventory_status,inventory_property_id,sum(quantity) as quantity, sum(prepare_quantity) as prepare_quantity " +
            "       from ops_inventory_move " +
            "       where delflag=0 and quantity>0 " +
            "       group by warehouse_code,modelno,inventory_status,inventory_property_id) i  " +
            "<where>" +
            "   <if test='modelnoList != null and modelnoList.size>0 '> " +
            "   and i.modelno in " +
            "   <foreach collection= 'modelnoList' item= 'modelno' separator= ', ' open= '( ' close= ') '> " +
            "      #{modelno}" +
            "   </foreach>" +
            "   </if>" +
            "   <if test='warehouseCode != null and warehouseCode != \"\" '> " +
            "       and i.warehouse_code = #{warehouseCode} " +
            "   </if>" +
            "   <if test='propertyList != null and propertyList.size > 0 '> " +
            "   and i.inventory_property_id in " +
            "   <foreach collection= 'propertyList' item= 'property' separator= ', ' open= '( ' close= ') '> " +
            "      #{property}" +
            "   </foreach>" +
            "   </if>" +
            "   <if test='propertyList != null and propertyList.size == 0 '> " +
            "   and i.inventory_property_id is null " +
            "   </if>" +
            "</where>" +
            "</script>"
    )
    List<InventoryDto> selectInventoryMove(
            List<String> modelnoList,
            String warehouseCode,
            List<Long> propertyList
    );

    @Select("<script>" +
            "select  " +
            "   'S' as inventoryScope, " +
            "   supplierId as warehouseCode, " +
            "   modelNo, " +
            "   quantity, " +
            "   quantityPrepare as prepareQuantity, " +
            "   quantity - quantityPrepare as availableQuantity, " +
            "   'TY' as inventoryTypeCode, " +
            "   '通用在库' as inventoryTypeName " +
            "from inventory_supplier  " +
            "where " +
            "   quantity>0  " +
            "   <if test='modelnoList != null and modelnoList.size>0 '> " +
            "   and modelNo in " +
            "   <foreach collection= 'modelnoList' item= 'modelno' separator= ', ' open= '( ' close= ') '> " +
            "      #{modelno}" +
            "   </foreach>" +
            "   </if>" +
            "   <if test='supplier != null and supplier != \"\" '> " +
            "       and supplierId = #{supplier} " +
            "   </if>" +
            "</script>"
    )
    List<InventoryDto> selectInventorySupplier(
            @Param("modelnoList") List<String> modelnoList,
            @Param("supplier") String supplier
    );

    @Select("<script>"
            + "select count(*) from ( "
            + " SELECT ModelNo FROM Bindata where StockType = 1 and delFlag = 0 "
            + " <if test='customerNo != null'> " +
            "   union all "
            + " SELECT ModelNo FROM Bindata where StockType = 4 and delFlag = 0 and CustomerNo =#{ customerNo } "
            + " </if> ) t "
            + "where t.ModelNo =#{modelno} "
            + "</script>")
    Integer isBinModelno(String modelno, String customerNo);


    @Select("<script>" +
            "select customerNo from (\n" +
            "SELECT ModelNo, warehouse_code,'all' as CustomerNo FROM Bindata where StockType = 1 and delFlag = 0 \n" +
            " union all \n" +
            "SELECT ModelNo, warehouse_code,CustomerNo FROM Bindata where StockType = 4 and delFlag = 0 \n" +
            ")t where modelno=#{modelno} " +
            "</script>")
    List<String> getBindata(String modelno);


    @Select(" select top ${num} modelno from(\n" +
            "select modelno,warehouse_code,sum(quantity) as qty,sum(prepare_quantity) as pre_qty from (\n" +
            "(select inventory_id,modelno,warehouse_code,quantity,prepare_quantity from ops_inventory where quantity>0 and delflag=0 )\n" +
            "union all \n" +
            "(select inventory_id,modelno,warehouse_code,quantity,prepare_quantity from ops_inventory_move where quantity>0 and delflag=0 )\n" +
            ")i \n" +
            "group by modelno,warehouse_code having sum(quantity) >= 1000\n" +
            ")t order by newid()")
    List<String> randomModelno(Integer num);
}
