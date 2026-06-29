package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsInventoryProperty;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author C12961
 * @date 2022/1/24 13:54
 */
public interface InventoryPropertyDao {

    /**
     * @description 插入并返回主键
     */
    @Insert("<script>" +
            "insert into ops_inventory_property" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='inventoryTypeCode != null '> inventory_type_code, </if> " +
            "      <if test='customerNo != null'> customer_no, </if> " +
            "      <if test='ppl != null'> ppl, </if> " +
            "      <if test='projectCode != null'> project_code, </if> " +
            "      <if test='groupCustomerNo != null'> group_customer_no, </if> " +
            "      <if test='salesInfoNo != null'> sales_info_no, </if> " +
            "      <if test='expDate != null'> exp_date, </if> " +
            "      <if test='uid != null'> uid, </if> " +
            "       creator" +
            "    </trim> " +
            "     values " +
            "    <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "      <if test='inventoryTypeCode != null'> #{inventoryTypeCode},</if> " +
            "      <if test='customerNo != null'> #{customerNo},</if> " +
            "      <if test='ppl != null'> #{ppl},</if> " +
            "      <if test='projectCode != null'> #{projectCode},</if> " +
            "      <if test='groupCustomerNo != null'> #{groupCustomerNo},</if> " +
            "      <if test='salesInfoNo != null'> #{salesInfoNo},</if> " +
            "      <if test='expDate != null'> #{expDate},</if> " +
            "      <if test='uid != null'> #{uid},</if> " +
            "       #{creator}" +
            "    </trim> " +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "inventoryPropertyId")
    Long insertInventoryProperty(OpsInventoryProperty property);
}
