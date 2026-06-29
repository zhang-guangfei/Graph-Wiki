package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsBsQuerypriceNewmodel;
import com.sales.ops.db.entity.ProductDelivery;
import com.sales.ops.dto.mainten.ModelData;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductBomDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface BasicsMaintenanceDao {

    @Select("<script>" +
            "select bo.bomId as bomId, bo.ModelNo as wholeModelNo, ai.ModelNo as splitModelNo, ai.Quantity as quantity, ai.classify as classify\n" +
            ", bo.Priority_Complete as priorityComplete, bo.update_time as updateTime, bo.update_user as updateUser, bo.create_time as loginDate\n" +
            ", Priority_level as versionNum, bo.IsValid as isValid from product_bom bo left join product_bom_detail ai on bo.bomId = ai.bomId\n" +
            "where" +
            " <if test='fuzzy == true'> bo.ModelNo like CONCAT(#{modelNo},'%')  </if> \n"
            +" <if test='fuzzy == false'> bo.ModelNo = #{modelNo}  </if> "
            +"<if test='startTime != null'>"
            +"and bo.update_time <![CDATA[ >= ]]> #{startTime}"
            +"</if>"
            +"<if test='endTime != null'>"
            +"and bo.update_time <![CDATA[ <= ]]> #{endTime}"
            +"</if>"
            +"</script>")
    List<ModelData> findProductModelByNo(String modelNo,Boolean fuzzy, String startTime, String endTime);

    @Select("<script>" +
            "select bo.bomId as bomId, bo.ModelNo as wholeModelNo, ai.ModelNo as splitModelNo, ai.Quantity as quantity, ai.classify as classify\n" +
            ", bo.Priority_Complete as priorityComplete, bo.update_time as updateTime, bo.update_user as updateUser, bo.create_time as loginDate\n" +
            ", Priority_level as versionNum, bo.IsValid as isValid from product_bom bo left join product_bom_detail ai on bo.bomId = ai.bomId\n" +
            "where bo.ModelNo like CONCAT(#{modelNo},'%')\n"
            +"<if test='startTime != null'>"
            +"and bo.update_time <![CDATA[ >= ]]> #{startTime}"
            +"</if>"
            +"<if test='endTime != null'>"
            +"and bo.update_time <![CDATA[ <= ]]> #{endTime}"
            +"</if>"
            +"</script>")
    List<ModelData> findProductModelLikeNo(String modelNo, String startTime, String endTime);

    @Select("select bo.ModelNo as wholeModelNo, ai.ModelNo as splitModelNo, ai.Quantity as quantity, ai.classify as classify\n" +
            ", bo.Priority_Complete as priorityComplete, bo.update_time as updateTime, bo.update_user as updateUser, bo.create_time as loginDate\n" +
            ", Priority_level as versionNum, bo.IsValid as isValid from product_bom bo left join product_bom_detail ai on bo.bomId = ai.bomId\n" +
            "where bo.ModelNo = #{modelNo} \n")
    List<ModelData> findProductModelNoByDim(String modelNo);

    @Select("select bo.bomId, bo.ModelNo as wholeModelNo, ai.ModelNo as splitModelNo, ai.Quantity as quantity, ai.classify as classify\n" +
            ", bo.IsValid as isValid \n" +
            "from product_bom bo left join product_bom_detail ai on bo.bomId = ai.bomId where bo.ModelNo = #{modelNo}")
    List<ModelData> findSplitInfoByWholeNo(String modelNo);

    @Select("select bo.bomId as bomid, bo.ModelNo as modelno, bo.Priority_Complete as priorityComplete, bo.Priority_level as priorityLevel, bo.IsValid as isvalid\n" +
            "from product_bom bo where bo.ModelNo = #{modelNo}")
    List<ProductBom> findWholeModelInfoByModelNo(String modelNo);

    @Select("select bo.bomId as bomid, bo.ModelNo as modelno, bo.Priority_Complete as priorityComplete, bo.Priority_level as priorityLevel, bo.IsValid as isvalid\n" +
            "from product_bom bo where bo.BomId = #{bomId}")
    ProductBom findWholeModelInfoByBomId(Long bomId);

    @Update("update product_bom set Priority_Complete = #{priorityComplete},  Priority_level = #{priorityLevel}," +
            " IsValid = #{isvalid}, update_user = #{updateuser}, update_time = #{updatetime} where bomId = #{bomid}")
    void updateWholeModelInfoByModelNo(ProductBom bom);

    @Select("select ISNULL(MAX(Priority_level), 0) AS maxNum from product_bom where ModelNo = #{modelNo}")
    int selectMaxNumByModelNo(String modelNo);

    @Insert("INSERT INTO product_bom (ModelNo, Priority_Complete, Priority_level, create_user, create_time, update_user, update_time) \n" +
            "VALUES (#{modelno}, #{priorityComplete}, #{priorityLevel}, #{createuser}, #{createtime}, #{updateuser}, #{updatetime})")
    @Options(useGeneratedKeys=true, keyProperty="bomid", keyColumn="bomid")
    void insertProductBom(ProductBom bom);

    @Insert("INSERT INTO product_bom_detail (bomId, ModelNo, Quantity, update_time, update_user, classify, create_time, create_user) \n" +
            "VALUES (#{bomid}, #{modelno}, #{quantity}, #{updatetime},  #{updateuser}, #{classify}, #{createtime}, #{createuser})")
    void insertProductBomDetails(ProductBomDetail bomDetails);

    // bug8411 B91717 供应商维护画面增加是否有效字段的显示
    @Select("<script>" +
            "select de.modelNo as modelno, REPLACE(de.orgCountryId,' ','') + '【' + co.name + '】' as orgcountryid\n" +
            ", REPLACE(de.supplyId,' ','') + '【' + su.name + '】' as supplyid, de.isPrimary as isprimary\n" +
            ", de.maxProdQty as maxprodqty, de.resultSourceDesc as resultsourcedesc, de.update_time as updatetime, ISNULL(em.Name, 'kettle') as updateUser,de.is_deleted from product_delivery de\n" +
            "left join country co on de.orgcountryid = co.Id left join supplier su on de.supplyId = su.id \n" +
            "left join [ops_ui].[dbo].[hr_employee] as em on em.Id = de.update_user where ModelNo like CONCAT(#{modelNo}, '%')"
            +"<if test='startTime != null'>"
            +"and de.update_time <![CDATA[ >= ]]> #{startTime}"
            +"</if>"
            +"<if test='endTime != null'>"
            +"and de.update_time <![CDATA[ <= ]]> #{endTime}"
            +"</if>"
            +"<if test='0 != 1'>"
            +" order by de.modelNo asc, de.isPrimary desc"
            +"</if>"
            +"</script>")
    List<ProductDelivery> findProductDeliveryByNo(String modelNo, String startTime, String endTime);

    @Select("<script>" +
            "select count(*) number from product_delivery where ModelNo like CONCAT(#{modelNo}, '%')"
            +"<if test='startTime != null'>"
            +"and update_time <![CDATA[ >= ]]> #{startTime}"
            +"</if>"
            +"<if test='endTime != null'>"
            +"and update_time <![CDATA[ <= ]]> #{endTime}"
            +"</if>"
            +"</script>")
    Long findProductDeliveryByNo_COUNT(String modelNo, String startTime, String endTime);

    @Select("select id, id + '【' + name + '】' as name from supplier order by sort")
    List<Map<String, String>> findAllSupplier();

    @Select("select Id as id, Id + '【' + Name + '】' as name from country")
    List<Map<String, String>> findAllCountry();

    @Select("select cou.Id as id, cou.Id + '【' + cou.Name + '】' as name from supplier su \n" +
            "left join supplier_company com on su.companyId = com.Id\n" +
            "left join country cou on com.CountryId = cou.Id\n" +
            "where su.id = #{supplyId}\n")
    Map<String, String> findCountryBySupplierId(String supplyId);

    @Select("select id, modelno, orgcountryid, supplyid, isprimary, maxprodqty" +
            ", update_time, resultSource as resultsource, resultSourceDesc as resultsourcedesc " +
            "from product_delivery where modelno = #{modelNo} and supplyid = #{supplyId}")
    ProductDelivery selectDeliveryByModelNo(String modelNo, String supplyId);

    @Insert("INSERT INTO product_delivery (modelNo, orgCountryId, supplyId, isPrimary, maxProdQty, resultSource" +
            ", resultSourceDesc, create_time, create_user, update_time, update_user) VALUES (#{modelno}, #{orgcountryid}, #{supplyid}, #{isprimary}, " +
            "#{maxprodqty}, #{resultsource}, #{resultsourcedesc}, #{createtime}, #{createuser}, #{updatetime}, #{updateuser})")
    void insertProductDelivery(ProductDelivery delivery);

    @Delete("delete from product_delivery where modelNo = #{modelNo} AND supplyId = #{supplyId}")
    void deleteProductDelivery(String modelNo, String supplyId);

    @Select("select * from product_delivery where modelNo = #{modelNo} AND supplyId = #{supplyId}")
    ProductDelivery selectProductDeliveryByModelNoAndSupplyId(String modelNo, String supplyId);

    @Select("select * from product_delivery where modelNo = #{modelNo} AND isPrimary = #{isprimary}")
    ProductDelivery selectProductDeliveryByModelNoAndIsPrimary(String modelNo, Boolean isprimary);

    @Select("select modelno, orgcountryid, supplyid, isprimary, maxprodqty FROM product_delivery WHERE ModelNo = #{modelNo}")
    List<ProductDelivery> selectDeliveryByNo(String modelNo);

    @Update("update product_delivery set isPrimary = 0,update_time = getdate() where modelNo = #{modelNo} and supplyId != #{supplyId}")
    void updateMainDeliveryBySupplyId(String modelNo, String supplyId);

    @Update("update product_delivery set modelNo = #{modelno}, orgCountryId = #{orgcountryid}, supplyId = #{supplyid}," +
            " isPrimary = #{isprimary}, maxProdQty = #{maxprodqty}, update_time = getdate(), update_user = #{updateuser} " +
            "where id = #{id}")
    void updateProductDelivery(ProductDelivery delivery);

    @Update("update product_delivery set isPrimary = #{isprimary}, update_time = getdate(), update_user = #{updateuser} " +
            "where id = #{id}")
    void updateProductDeliveryByIsPrimary(ProductDelivery delivery);

    @Select("select modelno, orgcountryid, supplyid, isprimary, maxprodqty" +
            ", update_time FROM product_delivery WHERE ModelNo like CONCAT(#{modelNo},'%')")
    List<ProductDelivery> selectDeliveryListByNo(String modelNo);

    @Select("select modelno, orgcountryid, supplyid, isprimary, maxprodqty" +
            ", update_time, resultSource as resultsource, resultSourceDesc as resultsourcedesc" +
            " FROM product_delivery WHERE ModelNo = #{modelNo}")
    List<ProductDelivery> findBeingSuppliersByModelNo(String modelNo);

    @Select("select id, modelNo, supplierId, orgCountryId, stdDlvDay, stdDlvDateMaxNumber" +
            ", statusCode, updateTime, updateUser from ops_bs_queryprice_newmodel where modelNo like CONCAT(#{modelNo},'%') and statusCode = '2'")
    List<OpsBsQuerypriceNewmodel> findQueryPriceNewModelByModelNo(String modelNo);

    @Update("update ops_bs_queryprice_newmodel set statusCode = '3' where modelNo = #{modelNo} and statusCode = '2'")
    void updateQueryPrice(String modelNo);
}
