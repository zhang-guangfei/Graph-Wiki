package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.EmailBarcode;
import com.sales.ops.db.entity.LowFrequencyInterceptionChecklist;
import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/9 11:16
 */
@Mapper
@DS("opsdb")
public interface LowFrequencyOpsdbDao {

    /**
     *  bugid: 14635
     * 1. 查询 库存表 条件：1.有效在库大于0 ；2. 非委托在库 3. 'GK-TY','GK-PJ','GK-PPL'
     * 排除 '95001','95002'
     * @return
     */
    @Select("SELECT DISTINCT modelno FROM ops_core.dbo.ops_inventory WHERE " +
            "delflag =0 and quantity - prepare_quantity > 0 and LEN(warehouse_code)= 3 and inventory_property_id in " +
            "(SELECT inventory_property_id from ops_inventory_property where customer_no not in ('95001','95002') and " +
            "inventory_type_code IN ('GK-TY','GK-PJ','GK-PPL') and delflag =0) ")
    List<String> getInventoryModelNos();


    /**
     * 4.0 全量更新失效 operator ='死库拦截'
     * @param
     * @return
     */
    @Select("update ops_core.dbo.ops_requestPurchase_intercept_config set enable = 0,updateTime =GETDATE() where operator ='死库拦截'")
    Integer updateAllPoInterConfigToEnable();

    /**
     * 4.1 插入或更新  型号带* 转换为 (.*)
     * bugid: 14635 采购拦截配置表
     * @param modelNo
     * @return
     */
    @Select("SELECT top 1 * from ops_core.dbo.ops_requestPurchase_intercept_config where ruleKey = #{modelNo} and operator ='死库拦截'")
    OpsRequestpurchaseInterceptConfig getPoInterceptConfigList(@Param("modelNo") String modelNo);


    /**
     * 4.2 插入或更新
     *  bugid: 14635  采购拦截配置表
     * @param id
     * @param enable
     * @return
     */
    @Update("update ops_core.dbo.ops_requestPurchase_intercept_config set enable = #{enable},updateTime =GETDATE() where id = #{id}")
    Integer updatePoInterceptConfig(@Param("id") Integer id , @Param("enable") Boolean enable);

    /**
     *
     *  bugid: 14635
     *  近8月低频型号清单
     * @param list
     * @return
     */
    @Insert("<script> " +
                "INSERT INTO low_frequency_interception_checklist( types, batch, modelNo, updateTime, creator) values" +
                " <foreach collection='list' item='item' index='index' separator=','>  " +
                    "( #{item.types,jdbcType=VARCHAR}," +
                    " #{item.batch,jdbcType=VARCHAR}," +
                    " #{item.modelno,jdbcType=VARCHAR}," +
                    " #{item.updatetime,jdbcType=TIMESTAMP}," +
                    " #{item.creator,jdbcType=VARCHAR})" +
                " </foreach> " +
            "</script>")
    Integer BatchInsertLowFreqInterChecklist(@Param("list") List<LowFrequencyInterceptionChecklist> list);

    /**
     * 4.3 插入或更新
     *  bugid: 14635
     *  //批量插入数据 采购拦截配置表
     * @param list
     * @return
     */
    @Insert("<script> " +
                "INSERT INTO ops_core.dbo.ops_requestPurchase_intercept_config " +
                "(typeId,ruleKey,reason,remark,enable,defaultAction,operator,updateTime) values " +
                " <foreach collection='list' item='item' index='index' separator=','>  " +
                    " (#{item.typeid},#{item.rulekey}," +
                    " #{item.reason},#{item.remark}," +
                    " #{item.enable},#{item.defaultaction}," +
                    " #{item.operator},#{item.updatetime}) " +
                " </foreach> " +
            "</script>")
    Integer BatchInsertPoInterceptConfig(@Param("list") List<OpsRequestpurchaseInterceptConfig> list);
}
