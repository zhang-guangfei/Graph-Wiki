package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.db.entity.OpsInventoryType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author C12961
 * @date 2022/10/17 9:35
 */
public interface DebugPropertyDao {
    @Select("select * from ops_inventory_type ")
    List<OpsInventoryType> selectPropertyType();

    @Select("select uid from ops_inventory_property group by uid having count(*)>1")
    List<String> getUniqueKeyList();

    @Select("select * from ops_inventory_property ")
    List<OpsInventoryProperty> selectAllProperty();

    @Select("select * from ops_inventory_property where uid is null")
    List<OpsInventoryProperty> selectPropertyNullUid();

    @Select("select * from ops_inventory_property where uid= #{key} ")
    List<OpsInventoryProperty> selectPropertyByKey(String key);

    @Select("select sum(cnt) from (\n" +
            "select count(*)as cnt from ops_inventory where delflag=0 and inventory_property_id=#{id}\n" +
            "union\n" +
            "select count(*)as cnt from ops_inventory_move where delflag=0 and inventory_property_id=#{id}\n" +
            ")t")
    Integer selectInventoryCountByPropertyId(Long id);


    @Update("<script>" +
            "update ops_inventory " +
            "set inventory_property_id=#{newId} " +
            "where inventory_property_id " +
            "in " +
            " <foreach collection='ids' index='index' item='id' separator=',' close=')' open='('> " +
            " #{id} " +
            " </foreach> " +
            "</script> ")
    void mergePropertyforInventoryNormal(List<Long> ids, Long newId);

    @Update("<script>" +
            "update ops_inventory_move " +
            "set inventory_property_id=#{newId} " +
            "where inventory_property_id " +
            "in " +
            " <foreach collection='ids' index='index' item='id' separator=',' close=')' open='('> " +
            " #{id} " +
            " </foreach> " +
            "</script> ")
    void mergePropertyforInventoryMove(List<Long> ids, Long newId);

    @Update("<script>" +
            "update ops_inventory_property set delflag=1 where inventory_property_id " +
            " in " +
            " <foreach collection='ids' index='index' item='id' separator=',' close=')' open='('> " +
            " #{id} " +
            " </foreach> " +
            "</script> ")
    void deletePropertyByIds(List<Long> ids);



}
