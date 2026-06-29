package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.*;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/4/23 10:26
 */
public interface CacheDao {

    @Select("SELECT inventory_type_code,match_customer_no ,match_project_code ,mathch_ppl ,match_group_customer_no" +
            ",match_group_customer_type ,mathch_qb_no  FROM ops_inventory_type where delflag = 0")
    List<OpsInventoryType> getInventoryType();

    @Select("select inventory_match_code ,match_warehouse_type_code ,match_inventory_status ,mathch_pre_rec_date " +
            ",inventory_type_code ,match_inventory_risk  from ops_inventory_match_config where delflag =0")
    List<OpsInventoryMatchConfig> getMatchConfig();

    @Select("select order_type ,order_tag ,rule_code ,sort,property_type ,dlv_entire  from ops_order_inventory_rule_config where delflag = 0")
    List<OpsOrderInventoryRuleConfig> getOrderInventoryRuleConfig();

    @Select("select rule_code ,rule_type ,rule_ass_flag ,warehouse_type_code ,rule_qty_flag  from ops_inventory_rule_config where delflag =0")
    List<OpsInventoryRuleConfig> getInventoryRuleConfig();

    @Select("select rule_code ,rule_item,inventory_match_code ,rule_sort  from ops_inventory_rule_config_item where delflag =0")
    List<OpsInventoryRuleConfigItem> getInventoryRuleConfigItem();


}
