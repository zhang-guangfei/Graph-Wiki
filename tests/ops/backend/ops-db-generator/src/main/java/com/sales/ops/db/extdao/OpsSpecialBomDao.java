package com.sales.ops.db.extdao;

import com.sales.ops.dto.common.OpsSpecialBomDetailDto;
import com.sales.ops.dto.common.OpsSpecialBomDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: $ bugid:17799
 * @description：
 * @date ：Created in 2025/6/13 10:16
 */
public interface OpsSpecialBomDao {


    // 21029 增加优先级 排序
    @Select("SELECT top 1 bom_id,bom_type ,priority_complete,model_no from ops_special_bom where model_no =#{modelNo} " +
            "and customer_code =#{customerCode} and del_flag = 0  order by priority_level")
    OpsSpecialBomDto selectOpsSpecialBomDto(String modelNo, String customerCode);

    // 21029 增加 delFlag
    @Select("SELECT bom_id ,sub_model_no ,quantity  from ops_special_bom_detail where bom_id =#{bomId} and del_flag = 0")
    List<OpsSpecialBomDetailDto> selectOpsSpecialBomDetailDto(Long bomId);

}
