package com.sales.ops.db.extdao;

import com.sales.ops.dto.po.PoMergeRuleConfigDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * bugid:17619
 * @author C14717
 * @date 2025/05/13
 */
public interface PoMergeRuleConfigDao {

    @Select("SELECT rule_code ,rule_param ,rule_desc from ops_po_merge_rule_config where rule_code in ('MIDQTY','MINCOUNT') and delflag =0")
    List<PoMergeRuleConfigDto> getPoMergeRuleConfigListByMIDQTYAndMINCOUNT();

    @Update("UPDATE ops_po_merge_rule_config set rule_param = #{ruleParam},modifier =#{modifier} , modify_time = GETDATE()  where rule_code =#{ruleCode} and delflag =0")
    int updateMergeConfig(@Param("ruleParam") String ruleParam,@Param("modifier") String modifier, @Param("ruleCode") String ruleCode);

}
